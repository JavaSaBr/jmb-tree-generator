package com.ss.editor.tree.generator;

import static com.ss.editor.plugin.api.messages.MessagesPluginFactory.getResourceBundle;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

/**
 * The class with localised all plugin messages.
 *
 * @author JavaSaBr
 */
public interface PluginMessages {

    @NotNull ResourceBundle RESOURCE_BUNDLE = getResourceBundle(TreeGeneratorEditorPlugin.class,
            "com/ss/editor/tree/generator/messages/messages");

    @NotNull String TREE_GENERATOR_CREATOR_TITLE = RESOURCE_BUNDLE.getString("TreeGeneratorCreatorTitle");
    @NotNull String TREE_GENERATOR_CREATOR_DESCRIPTION = RESOURCE_BUNDLE.getString("TreeGeneratorCreatorDescription");
    @NotNull String TREE_GENERATOR_EDITOR_NAME = RESOURCE_BUNDLE.getString("TreeGeneratorEditorName");
    @NotNull String TREE_GENERATOR_EDITOR_EXPORT_ACTION = RESOURCE_BUNDLE.getString("TreeGeneratorEditorExportAction");
    @NotNull String TREE_GENERATOR_EDITOR_TREE_TOOL = RESOURCE_BUNDLE.getString("TreeGeneratorEditorTreeTool");
    @NotNull String TREE_GENERATOR_EDITOR_NODE_SETTINGS = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeSettings");
    @NotNull String TREE_GENERATOR_EDITOR_NODE_TREE = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeTree");
    @NotNull String TREE_GENERATOR_EDITOR_NODE_BRANCHES = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeBranches");
    @NotNull String TREE_GENERATOR_EDITOR_NODE_BRANCH = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeBranch");
    @NotNull String TREE_GENERATOR_EDITOR_NODE_LODS = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeLods");
    @NotNull String TREE_GENERATOR_EDITOR_NODE_LOD = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeLod");
    @NotNull String TREE_GENERATOR_EDITOR_NODE_ROOTS = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeRoots");
    @NotNull String TREE_GENERATOR_EDITOR_NODE_ROOT = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeRoot");
    @NotNull String TREE_GENERATOR_EDITOR_NODE_MATERIALS = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterials");
    @NotNull String TREE_GENERATOR_EDITOR_NODE_MATERIAL_TREE = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterialTree");
    @NotNull String TREE_GENERATOR_EDITOR_NODE_MATERIAL_LEAF = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterialLeaf");
    @NotNull String TREE_GENERATOR_EDITOR_NODE_MATERIAL_FLAT = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterialFlat");
    @NotNull String TREE_GENERATOR_EDITOR_NODE_MATERIAL_IMPOSTOR = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterialImposter");
    @NotNull String TREE_GENERATOR_EDITOR_ACTION_DELETE = RESOURCE_BUNDLE.getString("TreeGeneratorEditorActionDelete");
    @NotNull String TREE_GENERATOR_EDITOR_ACTION_ADD_BRANCH = RESOURCE_BUNDLE.getString("TreeGeneratorEditorActionAddBranch");
    @NotNull String TREE_GENERATOR_EDITOR_ACTION_ADD_ROOT = RESOURCE_BUNDLE.getString("TreeGeneratorEditorActionAddRoot");
    @NotNull String TREE_GENERATOR_EDITOR_ACTION_ADD_LOD = RESOURCE_BUNDLE.getString("TreeGeneratorEditorActionAddLod");

    @NotNull String TREE_GENERATOR_PROPERTY_SHOW_WIRE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyShowWire");
    @NotNull String TREE_GENERATOR_PROPERTY_REDUCTION = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyReduction");
    @NotNull String TREE_GENERATOR_PROPERTY_BRANCH_DEPTH = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyBranchDepth");
    @NotNull String TREE_GENERATOR_PROPERTY_ROOT_DEPTH = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyRootDepth");
    @NotNull String TREE_GENERATOR_PROPERTY_MAX_RADIAL_SEGMENTS = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyMaxRadialSegments");
    @NotNull String TREE_GENERATOR_PROPERTY_ENABLED = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyEnabled");
    @NotNull String TREE_GENERATOR_PROPERTY_INHERIT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyInherit");
    @NotNull String TREE_GENERATOR_PROPERTY_HAS_END_JOINT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyHasEndJoint");
    @NotNull String TREE_GENERATOR_PROPERTY_INCLINATION = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyInclination");
    @NotNull String TREE_GENERATOR_PROPERTY_LENGTH_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyLengthScale");
    @NotNull String TREE_GENERATOR_PROPERTY_RADIUS_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyRadiusScale");
    @NotNull String TREE_GENERATOR_PROPERTY_SEGMENT_VARIATION = RESOURCE_BUNDLE.getString("TreeGeneratorPropertySegmentVariation");
    @NotNull String TREE_GENERATOR_PROPERTY_SIDE_JOINT_START_ANGLE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertySideJointStartAngle");
    @NotNull String TREE_GENERATOR_PROPERTY_TAPER = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTaper");
    @NotNull String TREE_GENERATOR_PROPERTY_TIP_ROTATION = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTipRotation");
    @NotNull String TREE_GENERATOR_PROPERTY_TWIST = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTwist");
    @NotNull String TREE_GENERATOR_PROPERTY_LENGTH_SEGMENTS = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyLengthSegments");
    @NotNull String TREE_GENERATOR_PROPERTY_RADIAL_SEGMENTS = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyRadialSegments");
    @NotNull String TREE_GENERATOR_PROPERTY_SIDE_JOINT_COUNT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertySideJointCount");
    @NotNull String TREE_GENERATOR_PROPERTY_GENERATE_LEAVES = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyGenerateLeaves");
    @NotNull String TREE_GENERATOR_PROPERTY_ENABLE_WIND = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyEnableWind");
    @NotNull String TREE_GENERATOR_PROPERTY_LEAF_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyLeafScale");
    @NotNull String TREE_GENERATOR_PROPERTY_BASE_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyBaseScale");
    @NotNull String TREE_GENERATOR_PROPERTY_TEXTURE_V_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTextureVScale");
    @NotNull String TREE_GENERATOR_PROPERTY_TRUNK_HEIGHT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTrunkHeight");
    @NotNull String TREE_GENERATOR_PROPERTY_TRUNK_RADIUS = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTrunkRadius");
    @NotNull String TREE_GENERATOR_PROPERTY_Y_OFFSET = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyYOffset");
    @NotNull String TREE_GENERATOR_PROPERTY_FLEX_HEIGHT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyFlexHeight");
    @NotNull String TREE_GENERATOR_PROPERTY_TRUNK_FLEXIBILITY = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTrunkFlexibility");
    @NotNull String TREE_GENERATOR_PROPERTY_BRANCH_FLEXIBILITY = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyBranchFlexibility");
    @NotNull String TREE_GENERATOR_PROPERTY_SEED = RESOURCE_BUNDLE.getString("TreeGeneratorPropertySeed");
    @NotNull String TREE_GENERATOR_PROPERTY_TEXTURE_U_REPEAT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTextureURepeat");
    @NotNull String TREE_GENERATOR_PROPERTY_DISTANCE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyDistance");
}
