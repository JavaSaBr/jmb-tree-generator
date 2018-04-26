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

    String TREE_GENERATOR_CREATOR_TITLE = RESOURCE_BUNDLE.getString("TreeGeneratorCreatorTitle");
    String TREE_GENERATOR_CREATOR_DESCRIPTION = RESOURCE_BUNDLE.getString("TreeGeneratorCreatorDescription");
    String TREE_GENERATOR_EDITOR_NAME = RESOURCE_BUNDLE.getString("TreeGeneratorEditorName");
    String TREE_GENERATOR_EDITOR_EXPORT_ACTION = RESOURCE_BUNDLE.getString("TreeGeneratorEditorExportAction");
    String TREE_GENERATOR_EDITOR_TREE_TOOL = RESOURCE_BUNDLE.getString("TreeGeneratorEditorTreeTool");
    String TREE_GENERATOR_EDITOR_NODE_SETTINGS = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeSettings");
    String TREE_GENERATOR_EDITOR_NODE_TREE = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeTree");
    String TREE_GENERATOR_EDITOR_NODE_BRANCHES = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeBranches");
    String TREE_GENERATOR_EDITOR_NODE_BRANCH = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeBranch");
    String TREE_GENERATOR_EDITOR_NODE_LODS = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeLods");
    String TREE_GENERATOR_EDITOR_NODE_LOD = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeLod");
    String TREE_GENERATOR_EDITOR_NODE_ROOTS = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeRoots");
    String TREE_GENERATOR_EDITOR_NODE_ROOT = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeRoot");
    String TREE_GENERATOR_EDITOR_NODE_MATERIALS = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterials");
    String TREE_GENERATOR_EDITOR_NODE_MATERIAL_TREE = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterialTree");
    String TREE_GENERATOR_EDITOR_NODE_MATERIAL_LEAF = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterialLeaf");
    String TREE_GENERATOR_EDITOR_NODE_MATERIAL_FLAT = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterialFlat");
    String TREE_GENERATOR_EDITOR_NODE_MATERIAL_IMPOSTOR = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterialImposter");
    String TREE_GENERATOR_EDITOR_ACTION_DELETE = RESOURCE_BUNDLE.getString("TreeGeneratorEditorActionDelete");
    String TREE_GENERATOR_EDITOR_ACTION_ADD_BRANCH = RESOURCE_BUNDLE.getString("TreeGeneratorEditorActionAddBranch");
    String TREE_GENERATOR_EDITOR_ACTION_ADD_ROOT = RESOURCE_BUNDLE.getString("TreeGeneratorEditorActionAddRoot");
    String TREE_GENERATOR_EDITOR_ACTION_ADD_LOD = RESOURCE_BUNDLE.getString("TreeGeneratorEditorActionAddLod");

    String TREE_GENERATOR_PROPERTY_SHOW_WIRE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyShowWire");
    String TREE_GENERATOR_PROPERTY_REDUCTION = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyReduction");
    String TREE_GENERATOR_PROPERTY_BRANCH_DEPTH = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyBranchDepth");
    String TREE_GENERATOR_PROPERTY_ROOT_DEPTH = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyRootDepth");
    String TREE_GENERATOR_PROPERTY_MAX_RADIAL_SEGMENTS = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyMaxRadialSegments");
    String TREE_GENERATOR_PROPERTY_ENABLED = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyEnabled");
    String TREE_GENERATOR_PROPERTY_INHERIT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyInherit");
    String TREE_GENERATOR_PROPERTY_HAS_END_JOINT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyHasEndJoint");
    String TREE_GENERATOR_PROPERTY_INCLINATION = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyInclination");
    String TREE_GENERATOR_PROPERTY_LENGTH_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyLengthScale");
    String TREE_GENERATOR_PROPERTY_RADIUS_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyRadiusScale");
    String TREE_GENERATOR_PROPERTY_SEGMENT_VARIATION = RESOURCE_BUNDLE.getString("TreeGeneratorPropertySegmentVariation");
    String TREE_GENERATOR_PROPERTY_SIDE_JOINT_START_ANGLE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertySideJointStartAngle");
    String TREE_GENERATOR_PROPERTY_TAPER = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTaper");
    String TREE_GENERATOR_PROPERTY_TIP_ROTATION = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTipRotation");
    String TREE_GENERATOR_PROPERTY_TWIST = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTwist");
    String TREE_GENERATOR_PROPERTY_LENGTH_SEGMENTS = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyLengthSegments");
    String TREE_GENERATOR_PROPERTY_RADIAL_SEGMENTS = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyRadialSegments");
    String TREE_GENERATOR_PROPERTY_SIDE_JOINT_COUNT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertySideJointCount");
    String TREE_GENERATOR_PROPERTY_GENERATE_LEAVES = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyGenerateLeaves");
    String TREE_GENERATOR_PROPERTY_ENABLE_WIND = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyEnableWind");
    String TREE_GENERATOR_PROPERTY_LEAF_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyLeafScale");
    String TREE_GENERATOR_PROPERTY_BASE_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyBaseScale");
    String TREE_GENERATOR_PROPERTY_TEXTURE_V_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTextureVScale");
    String TREE_GENERATOR_PROPERTY_TRUNK_HEIGHT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTrunkHeight");
    String TREE_GENERATOR_PROPERTY_TRUNK_RADIUS = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTrunkRadius");
    String TREE_GENERATOR_PROPERTY_Y_OFFSET = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyYOffset");
    String TREE_GENERATOR_PROPERTY_FLEX_HEIGHT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyFlexHeight");
    String TREE_GENERATOR_PROPERTY_TRUNK_FLEXIBILITY = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTrunkFlexibility");
    String TREE_GENERATOR_PROPERTY_BRANCH_FLEXIBILITY = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyBranchFlexibility");
    String TREE_GENERATOR_PROPERTY_SEED = RESOURCE_BUNDLE.getString("TreeGeneratorPropertySeed");
    String TREE_GENERATOR_PROPERTY_TEXTURE_U_REPEAT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTextureURepeat");
    String TREE_GENERATOR_PROPERTY_DISTANCE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyDistance");
}
