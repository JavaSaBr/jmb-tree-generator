package com.ss.editor.tree.generator.editor;

import static com.ss.rlib.util.ObjectUtils.notNull;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.light.DirectionalLight;
import com.jme3.material.MatParam;
import com.jme3.material.Material;
import com.jme3.material.MaterialDef;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.*;
import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.Tree;
import com.simsilica.arboreal.TreeGenerator;
import com.simsilica.arboreal.TreeParameters;
import com.simsilica.arboreal.mesh.*;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.JmeThread;
import com.ss.editor.model.EditorCamera;
import com.ss.editor.plugin.api.editor.part3d.AdvancedPbrWithStudioSky3DEditorPart;
import com.ss.editor.tree.generator.parameters.MaterialsParameters;
import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.editor.util.EditorUtil;
import com.ss.editor.util.TangentGenerator;
import com.ss.rlib.geom.util.AngleUtils;
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

    @Nullable
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

    public TreeGeneratorEditor3DPart(@NotNull final TreeGeneratorFileEditor fileEditor) {
        super(fileEditor);

        final EditorCamera editorCamera = notNull(getEditorCamera());
        editorCamera.setDefaultHorizontalRotation(H_ROTATION);
        editorCamera.setDefaultVerticalRotation(V_ROTATION);

        this.treeNode = new Node("Tree");
        this.treeNode.addControl(new LodSwitchControl());

        setLightEnabled(true);
    }

    @Override
    @JmeThread
    public void initialize(@NotNull final AppStateManager stateManager, @NotNull final Application application) {
        super.initialize(stateManager, application);

        final Node modelNode = getModelNode();
        modelNode.attachChild(treeNode);
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
    public void open(@NotNull final ProjectParameters parameters) {
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
    private void setParameters(@NotNull final ProjectParameters parameters) {
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
    public void generate(@NotNull final Consumer<Node> consumer) {
        EXECUTOR_MANAGER.addBackgroundTask(() -> {

            final Node treeNode = new Node("Tree");
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
    private void generateImpl(@NotNull final Node treeNode, final boolean needWire) {

        final LodSwitchControl lodControl = treeNode.getControl(LodSwitchControl.class);
        lodControl.clearLevels();

        treeNode.detachAllChildren();

        final ProjectParameters parameters = getParameters();
        final TreeParameters treeParameters = parameters.getTreeParameters();
        final MaterialsParameters materialParameters = parameters.getMaterialParameters();
        final Material treeMaterial = materialParameters.getTreeMaterial();
        final Material leafMaterial = materialParameters.getLeafMaterial();
        final Material impostorMaterial = materialParameters.getImpostorMaterial();
        final Material flatMaterial = materialParameters.getFlatMaterial();
        final Material flatWireMaterial = makeWareMaterial(flatMaterial.clone());
        final Material impostorWireMaterial = makeWareMaterial(impostorMaterial.clone());

        updateMaterials();

        final LevelGeometry[] levels = new LevelGeometry[treeParameters.getLodCount()];
        final TreeGenerator treeGen = new TreeGenerator();
        final Tree tree = treeGen.generateTree(treeParameters.getSeed(), treeParameters);

        final AtomicReference<BoundingBox> trunkBoundsRef = new AtomicReference<>();
        final AtomicReference<BoundingBox> leafBoundsRef = new AtomicReference<>();
        final AtomicReference<List<Vertex>> baseTipsRef = new AtomicReference<>();

        final AtomicBoolean generateLeaves = new AtomicBoolean();

        final float yOffset = treeParameters.getYOffset();
        final float textureVScale = treeParameters.getTextureVScale();
        final int textureURepeat = treeParameters.getTextureURepeat();

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
        for (final LevelGeometry level : levels) {
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

        final ProjectParameters parameters = getParameters();
        final TreeParameters treeParameters = parameters.getTreeParameters();
        final MaterialsParameters materialParameters = parameters.getMaterialParameters();
        final Material treeMaterial = materialParameters.getTreeMaterial();
        final Material leafMaterial = materialParameters.getLeafMaterial();
        final Material impostorMaterial = materialParameters.getImpostorMaterial();
        final Material flatMaterial = materialParameters.getFlatMaterial();

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
    private void applyWindParameters(@NotNull final TreeParameters treeParameters, @NotNull final Material material) {

        MaterialDef def = material.getMaterialDef();
        MatParam param = def.getMaterialParam("FlexHeight");

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
    private void generateLeafs(@NotNull final TreeParameters treeParameters, @NotNull final Material leafMaterial,
                               @NotNull final AtomicReference<BoundingBox> leafBoundsRef,
                               @NotNull final AtomicReference<List<Vertex>> baseTipsRef,
                               @NotNull final LevelOfDetailParameters lod, @NotNull final LevelGeometry level) {

        final BillboardedLeavesMeshGenerator leafGen = new BillboardedLeavesMeshGenerator();
        final Mesh leafMesh = leafGen.generateMesh(baseTipsRef.get(), treeParameters.getLeafScale());
        leafBoundsRef.set((BoundingBox) leafMesh.getBound());

        level.leafGeom = new Geometry("leaves:" + lod.reduction, leafMesh);
        level.leafGeom.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        level.leafGeom.setQueueBucket(RenderQueue.Bucket.Transparent);
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
    private void generateImposter(@NotNull final TreeParameters treeParameters,
                                  @NotNull final Material impostorMaterial,
                                  @NotNull final Material impostorWireMaterial, @NotNull final Tree tree,
                                  @NotNull final AtomicReference<BoundingBox> trunkBoundsRef,
                                  @NotNull final AtomicReference<BoundingBox> leafBoundsRef,
                                  @NotNull final AtomicReference<List<Vertex>> baseTipsRef,
                                  @NotNull final LevelGeometry level, @NotNull final LevelOfDetailParameters lod,
                                  final float yOffset, final float textureVScale, final int textureURepeat) {

        if (trunkBoundsRef.get() == null) {

            // Generate the mesh just to throw it away
            final SkinnedTreeMeshGenerator skinnedGen = new SkinnedTreeMeshGenerator();

            List<Vertex> tips = null;

            if (baseTipsRef.get() == null) {
                tips = new ArrayList<>();
                baseTipsRef.set(tips);
            }

            final Mesh treeMesh = skinnedGen.generateMesh(tree, treeParameters.getLod(0),
                    yOffset, textureURepeat, textureVScale, tips);

            trunkBoundsRef.set((BoundingBox) treeMesh.getBound());
        }

        final BoundingBox trunkBounds = trunkBoundsRef.get();
        final BoundingBox impostorBounds = (BoundingBox) trunkBounds.clone();

        if (leafBoundsRef.get() == null && treeParameters.getGenerateLeaves()) {
            final BillboardedLeavesMeshGenerator leafGen = new BillboardedLeavesMeshGenerator();
            final Mesh leafMesh = leafGen.generateMesh(baseTipsRef.get(), treeParameters.getLeafScale());
            leafBoundsRef.set((BoundingBox) leafMesh.getBound());
        } else if (treeParameters.getGenerateLeaves()) {
            impostorBounds.mergeLocal(leafBoundsRef.get());
        }

        float rootHeight = treeParameters.getRootHeight();

        Vector3f min = trunkBounds.getMin(null);
        Vector3f max = trunkBounds.getMax(null);

        final BoundingBox leafBounds = leafBoundsRef.get();

        if (leafBounds != null) {
            min.minLocal(leafBounds.getMin(null));
            max.maxLocal(leafBounds.getMax(null));
        }

        //float radius = (max.y - min.y) * 0.5f;

        float xSize = Math.max(Math.abs(min.x), Math.abs(max.x));
        float ySize = max.y - min.y;
        float zSize = Math.max(Math.abs(min.z), Math.abs(max.z));

        float size = ySize * 0.5f;
        size = Math.max(size, xSize);
        size = Math.max(size, zSize);
        float radius = size;

        // Just do it here raw for now
        final Mesh mesh = new Mesh();
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
        level.treeGeom.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        level.treeGeom.setLocalTranslation(0, 0, 0);
        level.treeGeom.setQueueBucket(RenderQueue.Bucket.Transparent);
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
    private void generateFlatPoly(@NotNull final TreeParameters treeParameters, @NotNull final Material flatMaterial,
                                  @NotNull final Material flatWireMaterial, @NotNull final Tree tree,
                                  @NotNull final AtomicReference<List<Vertex>> baseTipsRef,
                                  @NotNull final AtomicBoolean generateLeaves, @NotNull final LevelGeometry level,
                                  @NotNull final LevelOfDetailParameters lod, final float yOffset,
                                  final float textureVScale, final int textureURepeat) {

        final FlatPolyTreeMeshGenerator flatPolyGen = new FlatPolyTreeMeshGenerator();

        List<Vertex> tips = null;

        if (baseTipsRef.get() == null) {
            tips = new ArrayList<>();
            baseTipsRef.set(tips);
        }

        final Mesh treeMesh = flatPolyGen.generateMesh(tree, lod, yOffset, textureURepeat, textureVScale, tips);

        level.treeGeom = new Geometry("tree:" + lod.reduction, treeMesh);
        level.treeGeom.setMaterial(flatMaterial);
        level.treeGeom.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
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
    private void generateNormal(@NotNull final TreeParameters treeParameters, @NotNull final Material treeMaterial,
                                @NotNull final Tree tree, @NotNull final AtomicReference<BoundingBox> trunkBoundsRef,
                                @NotNull final AtomicReference<List<Vertex>> baseTipsRef,
                                @NotNull final AtomicBoolean generateLeaves, @NotNull final LevelOfDetailParameters lod,
                                @NotNull final LevelGeometry level, final float yOffset, final float textureVScale,
                                final int textureURepeat) {

        final SkinnedTreeMeshGenerator skinnedGen = new SkinnedTreeMeshGenerator();

        List<Vertex> tips = null;

        if (baseTipsRef.get() == null) {
            tips = new ArrayList<>();
            baseTipsRef.set(tips);
        }

        final Mesh treeMesh = skinnedGen.generateMesh(tree, lod, yOffset, textureURepeat, textureVScale, tips);
        trunkBoundsRef.set((BoundingBox) treeMesh.getBound());

        level.treeGeom = new Geometry("tree:" + lod.reduction, treeMesh);
        level.treeGeom.setMaterial(treeMaterial);
        level.treeGeom.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
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
    private @NotNull Material makeWareMaterial(@NotNull final Material material) {

        final MaterialDef def = material.getMaterialDef();

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
    public void updateLightEnabled(final boolean enabled) {
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
    private void setLightEnabled(final boolean lightEnabled) {
        this.lightEnabled = lightEnabled;
    }

    /**
     * The process of updating the light.
     */
    @JmeThread
    private void updateLightEnabledImpl(boolean enabled) {
        if (enabled == isLightEnabled()) return;

        final DirectionalLight light = getLightForCamera();
        final Node stateNode = getStateNode();

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

        LevelGeometry(final float distance) {
            this.distance = distance;
        }

        void attach(@NotNull final LodSwitchControl control, final boolean needWire) {
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
