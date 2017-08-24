package com.ss.editor.tree.generator.editor;

import static com.ss.rlib.util.ObjectUtils.notNull;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
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
import com.ss.editor.annotation.JMEThread;
import com.ss.editor.model.EditorCamera;
import com.ss.editor.plugin.api.editor.part3d.AdvancedPBRWithStudioSky3DEditorState;
import com.ss.editor.tree.generator.parameters.MaterialParameters;
import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.rlib.geom.util.AngleUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The implementation of 3D part of {@link TreeGeneratorFileEditor}.
 *
 * @author JavaSaBr
 */
public class TreeGeneratorEditor3DState extends AdvancedPBRWithStudioSky3DEditorState<TreeGeneratorFileEditor> {

    private static final float H_ROTATION = AngleUtils.degreeToRadians(75);
    private static final float V_ROTATION = AngleUtils.degreeToRadians(25);

    @Nullable
    private static final Material WIRE_MATERIAL;

    static {
        WIRE_MATERIAL = new Material(EDITOR.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
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

    @Nullable
    private LevelGeometry[] lods;

    private int seed;

    public TreeGeneratorEditor3DState(@NotNull final TreeGeneratorFileEditor fileEditor) {
        super(fileEditor);

        final EditorCamera editorCamera = notNull(getEditorCamera());
        editorCamera.setDefaultHorizontalRotation(H_ROTATION);
        editorCamera.setDefaultVerticalRotation(V_ROTATION);

        this.treeNode = new Node("Tree");
        this.treeNode.addControl(new LodSwitchControl());

        final Node modelNode = getModelNode();
        modelNode.attachChild(treeNode);
    }

    @Override
    protected boolean needMovableCamera() {
        return false;
    }

    @Override
    protected boolean needEditorCamera() {
        return true;
    }

    @Override
    protected boolean needLightForCamera() {
        return true;
    }

    @Override
    protected boolean needUpdateCameraLight() {
        return true;
    }

    /**
     * Open ang generate tree by the parameters.
     *
     * @param parameters the parameters.
     */
    @FromAnyThread
    public void open(@NotNull final ProjectParameters parameters) {
        EXECUTOR_MANAGER.addJMETask(() -> {
            setParameters(parameters);
            generateImpl();
        });
    }

    /**
     * @param parameters the project parameters.
     */
    @JMEThread
    private void setParameters(@NotNull final ProjectParameters parameters) {
        this.parameters = parameters;
    }

    /**
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
        EXECUTOR_MANAGER.addJMETask(this::generateImpl);
    }

    /**
     * @return the tree node.
     */
    @FromAnyThread
    private @NotNull Node getTreeNode() {
        return treeNode;
    }

    @JMEThread
    private void generateImpl() {

        final LodSwitchControl lodControl = treeNode.getControl(LodSwitchControl.class);
        lodControl.clearLevels();

        final Node treeNode = getTreeNode();
        treeNode.detachAllChildren();

        final ProjectParameters parameters = getParameters();
        final TreeParameters treeParameters = parameters.getTreeParameters();
        final MaterialParameters materialParameters = parameters.getMaterialParameters();
        final Material treeMaterial = materialParameters.getTreeMaterial();
        final Material leafMaterial = materialParameters.getLeafMaterial();
        final Material impostorMaterial = materialParameters.getImpostorMaterial();
        final Material flatMaterial = materialParameters.getFlatMaterial();
        final Material flatWireMaterial =  makeWareMateial(flatMaterial.clone());
        final Material impostorWireMaterial = makeWareMateial(impostorMaterial.clone());

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
        for (int i = 0; i < levels.length; i++) {
            final LevelGeometry level = levels[i];
            level.attach(lodControl);
            if (!parameters.isShowWire() && level.wireGeom != null) {
                level.wireGeom.setCullHint(Spatial.CullHint.Always);
            }
        }

        lods = levels;
    }

    @JMEThread
    private void updateMaterials() {

        final ProjectParameters parameters = getParameters();
        final TreeParameters treeParameters = parameters.getTreeParameters();
        final MaterialParameters materialParameters = parameters.getMaterialParameters();
        final Material treeMaterial = materialParameters.getTreeMaterial();
        final Material leafMaterial = materialParameters.getLeafMaterial();
        final Material impostorMaterial = materialParameters.getImpostorMaterial();
        final Material flatMaterial = materialParameters.getFlatMaterial();

        if (treeMaterial.getKey() == null) {
            treeMaterial.setFloat("FlexHeight", treeParameters.getFlexHeight());
            treeMaterial.setFloat("TrunkFlexibility", treeParameters.getTrunkFlexibility());
            treeMaterial.setFloat("BranchFlexibility", treeParameters.getBranchFlexibility());
        }

        if (leafMaterial.getKey() == null) {
            leafMaterial.setFloat("FlexHeight", treeParameters.getFlexHeight());
            leafMaterial.setFloat("TrunkFlexibility", treeParameters.getTrunkFlexibility());
            leafMaterial.setFloat("BranchFlexibility", treeParameters.getBranchFlexibility());
        }

        if (flatMaterial.getKey() == null) {
            flatMaterial.setFloat("FlexHeight", treeParameters.getFlexHeight());
            flatMaterial.setFloat("TrunkFlexibility", treeParameters.getTrunkFlexibility());
            flatMaterial.setFloat("BranchFlexibility", treeParameters.getBranchFlexibility());
        }

        if (impostorMaterial.getKey() == null) {
            impostorMaterial.setFloat("TrunkFlexibility", treeParameters.getTrunkFlexibility());
        }
    }

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

            final Mesh treeMesh = skinnedGen.generateMesh(tree, treeParameters.getLod(0), yOffset,
                    textureURepeat, textureVScale, tips);

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
                min.y + rootHeight, 0, 0,
                min.y + (size * 2) + rootHeight, 0, 0, min.y + (size * 2) + rootHeight, 0
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

    private @NotNull Material makeWareMateial(@NotNull final Material material) {
        material.clearParam("DiffuseMap");
        material.setColor("Diffuse", ColorRGBA.Yellow.mult(10));
        material.setColor("Ambient", ColorRGBA.Yellow.mult(10));
        material.getAdditionalRenderState().setWireframe(true);
        return material;
    }

    /**
     *  Encapsulates all of the tree geometry for a
     *  particular level of detail.
     */
    private class LevelGeometry {

        float distance;
        Node levelNode;
        Geometry treeGeom;
        Geometry wireGeom;
        Geometry leafGeom;

        public LevelGeometry( float distance ) {
            this.distance = distance;
        }

        public void attach( LodSwitchControl control ) {
            levelNode = new Node("level:" + distance);
            if( treeGeom != null ) {
                levelNode.attachChild(treeGeom);
                levelNode.attachChild(wireGeom);
            }
            if( leafGeom != null ) {
                levelNode.attachChild(leafGeom);
            }
            control.addLevel(distance, levelNode);
        }

        public void release() {
            levelNode.removeFromParent();
        }
    }
}
