package com.ss.editor.tree.generator.editor;

import static com.ss.rlib.common.util.ObjectUtils.notNull;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.MatParam;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.*;
import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.Tree;
import com.simsilica.arboreal.TreeGenerator;
import com.simsilica.arboreal.TreeParameters;
import com.simsilica.arboreal.mesh.*;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.JmeThread;
import com.ss.editor.plugin.api.editor.part3d.AdvancedPbrWithStudioSky3DEditorPart;
import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.editor.util.EditorUtil;
import com.ss.editor.util.TangentGenerator;
import com.ss.rlib.common.geom.util.AngleUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * The implementation of 3D part of {@link TreeGeneratorFileEditor}.
 * Took from https://github.com/Simsilica/SimArboreal-Editor/blob/master/src/main/java/com/simsilica/arboreal/TreeBuilderReference.java
 *
 * @author JavaSaBr
 */
public class TreeGeneratorEditor3DPart extends AdvancedPbrWithStudioSky3DEditorPart<TreeGeneratorFileEditor> {

    private static final float H_ROTATION = AngleUtils.degreeToRadians(75);
    private static final float V_ROTATION = AngleUtils.degreeToRadians(25);

    private static final Material WIRE_MATERIAL;

    static {
        WIRE_MATERIAL = new Material(EditorUtil.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        WIRE_MATERIAL.setColor("Color", ColorRGBA.Yellow);
        WIRE_MATERIAL.getAdditionalRenderState().setWireframe(true);
    }

    /**
     * The tree node.
     */
    @NotNull
    private final Node treeNode;

    /**
     * The project parameters.
     */
    @Nullable
    private ProjectParameters parameters;

    /**
     * The flag of activity light of the camera.
     */
    private boolean lightEnabled;

    public TreeGeneratorEditor3DPart(@NotNull TreeGeneratorFileEditor fileEditor) {
        super(fileEditor);

        var editorCamera = notNull(getEditorCamera());
        editorCamera.setDefaultHorizontalRotation(H_ROTATION);
        editorCamera.setDefaultVerticalRotation(V_ROTATION);

        this.treeNode = new Node("Tree");
        this.treeNode.addControl(new LodSwitchControl());

        setLightEnabled(true);
    }

    @Override
    @JmeThread
    public void initialize(@NotNull AppStateManager stateManager, @NotNull Application application) {
        super.initialize(stateManager, application);
        getModelNode().attachChild(treeNode);
    }

    @Override
    @JmeThread
    protected boolean needMovableCamera() {
        return false;
    }

    @Override
    @JmeThread
    protected boolean needEditorCamera() {
        return true;
    }

    @Override
    @JmeThread
    protected boolean needLightForCamera() {
        return true;
    }

    @Override
    @JmeThread
    protected boolean needUpdateCameraLight() {
        return true;
    }

    /**
     * Open and generate tree by the parameters.
     *
     * @param parameters the parameters.
     */
    @FromAnyThread
    public void open(@NotNull ProjectParameters parameters) {
        EXECUTOR_MANAGER.addJmeTask(() -> {
            setParameters(parameters);
            generateImpl(getTreeNode(), true);
        });
    }

    /**
     * Set the project parameters.
     *
     * @param parameters the project parameters.
     */
    @JmeThread
    private void setParameters(@NotNull ProjectParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Get the project parameters.
     *
     * @return the project parameters.
     */
    @FromAnyThread
    private @NotNull ProjectParameters getParameters() {
        return notNull(parameters);
    }

    /**
     * Generate new tree.
     */
    @FromAnyThread
    public void generate() {
        EXECUTOR_MANAGER.addJmeTask(() -> generateImpl(getTreeNode(), true));
    }

    /**
     * Generate new node with new tree.
     *
     * @param consumer the result consumer.
     */
    @FromAnyThread
    public void generate(@NotNull Consumer<Node> consumer) {
        EXECUTOR_MANAGER.addBackgroundTask(() -> {

            var treeNode = new Node("Tree");
            treeNode.addControl(new LodSwitchControl());

            generateImpl(treeNode, false);

            EXECUTOR_MANAGER.addFxTask(() -> consumer.accept(treeNode));
        });
    }

    /**
     * Get the tree node.
     *
     * @return the tree node.
     */
    @FromAnyThread
    private @NotNull Node getTreeNode() {
        return treeNode;
    }

    /**
     * The process of generating tree.
     *
     * @param treeNode the tree node.
     * @param needWire the flag if need to add wire.
     */
    @JmeThread
    private void generateImpl(@NotNull Node treeNode, boolean needWire) {

        var lodControl = treeNode.getControl(LodSwitchControl.class);
        lodControl.clearLevels();

        treeNode.detachAllChildren();

        var parameters = getParameters();
        var treeParameters = parameters.getTreeParameters();
        var materialParameters = parameters.getMaterialParameters();
        var treeMaterial = materialParameters.getTreeMaterial();
        var leafMaterial = materialParameters.getLeafMaterial();
        var impostorMaterial = materialParameters.getImpostorMaterial();
        var flatMaterial = materialParameters.getFlatMaterial();
        var flatWireMaterial = makeWareMaterial(flatMaterial.clone());
        var impostorWireMaterial = makeWareMaterial(impostorMaterial.clone());

        updateMaterials();

        var levels = new LevelGeometry[treeParameters.getLodCount()];
        var treeGen = new TreeGenerator();
        var tree = treeGen.generateTree(treeParameters.getSeed(), treeParameters);

        var trunkBoundsRef = new AtomicReference<BoundingBox>();
        var leafBoundsRef = new AtomicReference<BoundingBox>();
        var baseTipsRef = new AtomicReference<List<Vertex>>();

        var generateLeaves = new AtomicBoolean();

        var yOffset = treeParameters.getYOffset();
        var textureVScale = treeParameters.getTextureVScale();
        var textureURepeat = treeParameters.getTextureURepeat();

        for (int i = 0; i < levels.length; i++) {

            final LevelOfDetailParameters lod = treeParameters.getLod(i);
            final LevelGeometry level = new LevelGeometry(lod.distance);

            generateLeaves.set(false);

            levels[i] = level;

            switch (lod.reduction) {
                case Normal:
                    generateNormal(treeParameters, treeMaterial, tree, trunkBoundsRef, baseTipsRef, generateLeaves,
                            lod, level, yOffset, textureVScale, textureURepeat);
                    break;
                case FlatPoly:
                    generateFlatPoly(treeParameters, flatMaterial, flatWireMaterial, tree, baseTipsRef, generateLeaves,
                            level, lod, yOffset, textureVScale, textureURepeat);
                    break;
                case Impostor:
                    generateImposter(treeParameters, impostorMaterial, impostorWireMaterial, tree, trunkBoundsRef,
                            leafBoundsRef, baseTipsRef, level, lod, yOffset, textureVScale, textureURepeat);
                    break;
            }

            if (generateLeaves.get() && treeParameters.getGenerateLeaves() && baseTipsRef.get() != null) {
                generateLeafs(treeParameters, leafMaterial, leafBoundsRef, baseTipsRef, lod, level);
            }
        }

        treeNode.setLocalScale(treeParameters.getBaseScale());

        // Add in the new ones
        for (var level : levels) {

            level.attach(lodControl, needWire);
            TangentGenerator.useMikktspaceGenerator(level.levelNode);

            if (!parameters.isShowWire() && level.wireGeom != null) {
                level.wireGeom.setCullHint(Spatial.CullHint.Always);
            }
        }
    }

    /**
     * Update materials.
     */
    @JmeThread
    private void updateMaterials() {

        var parameters = getParameters();
        var treeParameters = parameters.getTreeParameters();
        var materialParameters = parameters.getMaterialParameters();
        var treeMaterial = materialParameters.getTreeMaterial();
        var leafMaterial = materialParameters.getLeafMaterial();
        var impostorMaterial = materialParameters.getImpostorMaterial();
        var flatMaterial = materialParameters.getFlatMaterial();

        if (treeMaterial.getKey() == null) {
            applyWindParameters(treeParameters, treeMaterial);
        }

        if (leafMaterial.getKey() == null) {
            applyWindParameters(treeParameters, leafMaterial);
        }

        if (flatMaterial.getKey() == null) {
            applyWindParameters(treeParameters, flatMaterial);
        }

        if (impostorMaterial.getKey() == null) {
            applyWindParameters(treeParameters, impostorMaterial);
        }
    }

    /**
     * Apply wind settings to the material.
     *
     * @param treeParameters the tree parameters.
     * @param material       the material.
     */
    @JmeThread
    private void applyWindParameters(@NotNull TreeParameters treeParameters, @NotNull Material material) {

        var def = material.getMaterialDef();
        var param = def.getMaterialParam("FlexHeight");

        if (param != null) {
            material.setFloat(param.getName(), treeParameters.getFlexHeight());
        }

        param = def.getMaterialParam("TrunkFlexibility");

        if (param != null) {
            material.setFloat(param.getName(), treeParameters.getTrunkFlexibility());
        }

        param = def.getMaterialParam("BranchFlexibility");

        if (param != null) {
            material.setFloat(param.getName(), treeParameters.getBranchFlexibility());
        }

        param = def.getMaterialParam("UseWind");

        if (param != null) {
            material.setBoolean(param.getName(), treeParameters.isUseWind());
        }
    }

    /**
     * Generate leafs.
     *
     * @param treeParameters the tree parameters.
     * @param leafMaterial   the leaf material.
     * @param leafBoundsRef  the leaf bounds reference.
     * @param baseTipsRef    the base tips reference.
     * @param lod            the level of detail.
     * @param level          the level of geometry.
     */
    @JmeThread
    private void generateLeafs(
            @NotNull TreeParameters treeParameters,
            @NotNull Material leafMaterial,
            @NotNull AtomicReference<BoundingBox> leafBoundsRef,
            @NotNull AtomicReference<List<Vertex>> baseTipsRef,
            @NotNull LevelOfDetailParameters lod,
            @NotNull LevelGeometry level
    ) {

        var leafGen = new BillboardedLeavesMeshGenerator();
        var leafMesh = leafGen.generateMesh(baseTipsRef.get(), treeParameters.getLeafScale());
        leafBoundsRef.set((BoundingBox) leafMesh.getBound());

        level.leafGeom = new Geometry("leaves:" + lod.reduction, leafMesh);
        level.leafGeom.setShadowMode(ShadowMode.CastAndReceive);
        level.leafGeom.setQueueBucket(Bucket.Transparent);
        level.leafGeom.setMaterial(leafMaterial);
        level.leafGeom.setLocalTranslation(0, treeParameters.getRootHeight(), 0);
    }

    /**
     * Generate a impostar tree model.
     *
     * @param treeParameters   the ree parameters.
     * @param impostorMaterial the impostar material.
     * @param tree             the tree.
     * @param baseTipsRef      the base tips reference.
     * @param lod              the level of detail parameters.
     * @param level            the level of geometry.
     * @param yOffset          the offset by Y.
     * @param textureVScale    the texture V scale.
     * @param textureURepeat   the texture U repeat.
     */
    @JmeThread
    private void generateImposter(
            @NotNull TreeParameters treeParameters,
            @NotNull Material impostorMaterial,
            @NotNull Material impostorWireMaterial,
            @NotNull Tree tree,
            @NotNull AtomicReference<BoundingBox> trunkBoundsRef,
            @NotNull AtomicReference<BoundingBox> leafBoundsRef,
            @NotNull AtomicReference<List<Vertex>> baseTipsRef,
            @NotNull LevelGeometry level,
            @NotNull LevelOfDetailParameters lod,
            float yOffset,
            float textureVScale,
            int textureURepeat
    ) {

        if (trunkBoundsRef.get() == null) {

            // Generate the mesh just to throw it away
            var skinnedGen = new SkinnedTreeMeshGenerator();

            List<Vertex> tips = null;

            if (baseTipsRef.get() == null) {
                tips = new ArrayList<>();
                baseTipsRef.set(tips);
            }

            var treeMesh = skinnedGen.generateMesh(tree, treeParameters.getLod(0),
                    yOffset, textureURepeat, textureVScale, tips);

            trunkBoundsRef.set((BoundingBox) treeMesh.getBound());
        }

        var trunkBounds = trunkBoundsRef.get();
        var impostorBounds = (BoundingBox) trunkBounds.clone();

        if (leafBoundsRef.get() == null && treeParameters.getGenerateLeaves()) {
            var leafGen = new BillboardedLeavesMeshGenerator();
            var leafMesh = leafGen.generateMesh(baseTipsRef.get(), treeParameters.getLeafScale());
            leafBoundsRef.set((BoundingBox) leafMesh.getBound());
        } else if (treeParameters.getGenerateLeaves()) {
            impostorBounds.mergeLocal(leafBoundsRef.get());
        }

        var rootHeight = treeParameters.getRootHeight();

        var min = trunkBounds.getMin(null);
        var max = trunkBounds.getMax(null);

        var leafBounds = leafBoundsRef.get();

        if (leafBounds != null) {
            min.minLocal(leafBounds.getMin(null));
            max.maxLocal(leafBounds.getMax(null));
        }

        //float radius = (max.y - min.y) * 0.5f;

        var xSize = Math.max(Math.abs(min.x), Math.abs(max.x));
        var ySize = max.y - min.y;
        var zSize = Math.max(Math.abs(min.z), Math.abs(max.z));

        var size = ySize * 0.5f;
        size = Math.max(size, xSize);
        size = Math.max(size, zSize);

        var radius = size;

        // Just do it here raw for now
        var mesh = new Mesh();
        mesh.setBuffer(VertexBuffer.Type.Position, 3, new float[]{0,
                min.y + rootHeight, 0, 0,
                min.y + rootHeight, 0, 0, min.y + (size * 2) + rootHeight, 0, 0, min.y + (size * 2) + rootHeight, 0
                //0, max.y + rootHeight, 0,
                //0, max.y + rootHeight, 0
        });
        mesh.setBuffer(VertexBuffer.Type.Size, 1, new float[]{-radius, radius, -radius, radius});
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, new float[]{0, 0, 1, 0, 0, 1f, 1, 1f});
        mesh.setBuffer(VertexBuffer.Type.Index, 3, new short[]{0, 1, 3, 0, 3, 2});
        //mesh.updateBound();

        // Give the mesh the same bound that the real tree would have
        // had.
        impostorBounds.getCenter().addLocal(0, treeParameters.getRootHeight(), 0);
        mesh.setBound(impostorBounds);

        level.treeGeom = new Geometry("tree:" + lod.reduction, mesh);
        level.treeGeom.setMaterial(impostorMaterial);
        level.treeGeom.setShadowMode(ShadowMode.CastAndReceive);
        level.treeGeom.setLocalTranslation(0, 0, 0);
        level.treeGeom.setQueueBucket(Bucket.Transparent);
        level.wireGeom = new Geometry("wire:" + lod.reduction, mesh);
        level.wireGeom.setMaterial(impostorWireMaterial);
        level.wireGeom.setLocalTranslation(0, 0, 0);
    }

    /**
     * Generate a flat polygon tree model.
     *
     * @param treeParameters the ree parameters.
     * @param flatMaterial   the flat material.
     * @param tree           the tree.
     * @param baseTipsRef    the base tips reference.
     * @param generateLeaves true of need to generate leaves.
     * @param lod            the level of detail parameters.
     * @param level          the level of geometry.
     * @param yOffset        the offset by Y.
     * @param textureVScale  the texture V scale.
     * @param textureURepeat the texture U repeat.
     */
    @JmeThread
    private void generateFlatPoly(
            @NotNull TreeParameters treeParameters,
            @NotNull Material flatMaterial,
            @NotNull Material flatWireMaterial,
            @NotNull Tree tree,
            @NotNull AtomicReference<List<Vertex>> baseTipsRef,
            @NotNull AtomicBoolean generateLeaves,
            @NotNull LevelGeometry level,
            @NotNull LevelOfDetailParameters lod,
            float yOffset,
            float textureVScale,
            int textureURepeat
    ) {

        var flatPolyGen = new FlatPolyTreeMeshGenerator();

        List<Vertex> tips = null;

        if (baseTipsRef.get() == null) {
            tips = new ArrayList<>();
            baseTipsRef.set(tips);
        }

        var treeMesh = flatPolyGen.generateMesh(tree, lod, yOffset, textureURepeat, textureVScale, tips);

        level.treeGeom = new Geometry("tree:" + lod.reduction, treeMesh);
        level.treeGeom.setMaterial(flatMaterial);
        level.treeGeom.setShadowMode(ShadowMode.CastAndReceive);
        level.treeGeom.setLocalTranslation(0, treeParameters.getRootHeight(), 0);
        level.wireGeom = new Geometry("wire:" + lod.reduction, treeMesh);
        level.wireGeom.setMaterial(flatWireMaterial);
        level.wireGeom.setLocalTranslation(0, treeParameters.getRootHeight(), 0);

        generateLeaves.set(true);
    }

    /**
     * Generate a normal tree model.
     *
     * @param treeParameters the ree parameters.
     * @param treeMaterial   the tree material.
     * @param tree           the tree.
     * @param trunkBoundsRef the trunk bounds reference.
     * @param baseTipsRef    the base tips reference.
     * @param generateLeaves true of need to generate leaves.
     * @param lod            the level of detail parameters.
     * @param level          the level of geometry.
     * @param yOffset        the offset by Y.
     * @param textureVScale  the texture V scale.
     * @param textureURepeat the texture U repeat.
     */
    @JmeThread
    private void generateNormal(
            @NotNull TreeParameters treeParameters,
            @NotNull Material treeMaterial,
            @NotNull Tree tree,
            @NotNull AtomicReference<BoundingBox> trunkBoundsRef,
            @NotNull AtomicReference<List<Vertex>> baseTipsRef,
            @NotNull AtomicBoolean generateLeaves,
            @NotNull LevelOfDetailParameters lod,
            @NotNull LevelGeometry level,
            float yOffset,
            float textureVScale,
            int textureURepeat
    ) {

        var skinnedGen = new SkinnedTreeMeshGenerator();

        List<Vertex> tips = null;

        if (baseTipsRef.get() == null) {
            tips = new ArrayList<>();
            baseTipsRef.set(tips);
        }

        var treeMesh = skinnedGen.generateMesh(tree, lod, yOffset, textureURepeat, textureVScale, tips);
        trunkBoundsRef.set((BoundingBox) treeMesh.getBound());

        level.treeGeom = new Geometry("tree:" + lod.reduction, treeMesh);
        level.treeGeom.setMaterial(treeMaterial);
        level.treeGeom.setShadowMode(ShadowMode.CastAndReceive);
        level.treeGeom.setLocalTranslation(0, treeParameters.getRootHeight(), 0);
        level.wireGeom = new Geometry("wire:" + lod.reduction, treeMesh);
        level.wireGeom.setMaterial(WIRE_MATERIAL);
        level.wireGeom.setLocalTranslation(0, treeParameters.getRootHeight(), 0);

        generateLeaves.set(true);
    }

    /**
     * Mate a new material to show wire.
     *
     * @param material the material.
     * @return the prepared material to show wire.
     */
    @JmeThread
    private @NotNull Material makeWareMaterial(@NotNull Material material) {

        var def = material.getMaterialDef();

        MatParam param = def.getMaterialParam("DiffuseMap");

        if (param != null) {
            material.clearParam(param.getName());
        }

        param = def.getMaterialParam("BaseColorMap");

        if (param != null) {
            material.clearParam(param.getName());
        }

        param = def.getMaterialParam("Diffuse");

        if (param != null) {
            material.setColor(param.getName(), ColorRGBA.Yellow.mult(10));
        }

        param = def.getMaterialParam("BaseColor");

        if (param != null) {
            material.setColor(param.getName(), ColorRGBA.Yellow.mult(10));
        }

        material.getAdditionalRenderState().setWireframe(true);
        return material;
    }

    /**
     * Update light.
     *
     * @param enabled the enabled
     */
    @FromAnyThread
    public void updateLightEnabled(boolean enabled) {
        EXECUTOR_MANAGER.addJmeTask(() -> updateLightEnabledImpl(enabled));
    }

    /**
     * @return true if the light of the camera is enabled.
     */
    @JmeThread
    private boolean isLightEnabled() {
        return lightEnabled;
    }

    /**
     * @param lightEnabled the flag of activity light of the camera.
     */
    @FromAnyThread
    private void setLightEnabled(boolean lightEnabled) {
        this.lightEnabled = lightEnabled;
    }

    /**
     * The process of updating the light.
     */
    @JmeThread
    private void updateLightEnabledImpl(boolean enabled) {

        if (enabled == isLightEnabled()) {
            return;
        }

        var light = getLightForCamera();
        var stateNode = getStateNode();

        if (enabled) {
            stateNode.addLight(light);
        } else {
            stateNode.removeLight(light);
        }

        setLightEnabled(enabled);
    }

    /**
     * Encapsulates all of the tree geometry for a
     * particular level of detail.
     */
    private class LevelGeometry {

        float distance;
        Node levelNode;
        Geometry treeGeom;
        Geometry wireGeom;
        Geometry leafGeom;

        LevelGeometry(float distance) {
            this.distance = distance;
        }

        void attach(@NotNull LodSwitchControl control, boolean needWire) {

            levelNode = new Node("level:" + distance);

            if (treeGeom != null) {
                levelNode.attachChild(treeGeom);
            }

            if (wireGeom != null && needWire) {
                levelNode.attachChild(wireGeom);
            }

            if (leafGeom != null) {
                levelNode.attachChild(leafGeom);
            }

            control.addLevel(distance, levelNode);
        }
    }
}
