package com.ss.editor.tree.generator;

import com.ss.editor.plugin.api.messages.MessagesPluginFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

/**
 * The class with localised all plugin messages.
 *
 * @author JavaSaBr
 */
public class PluginMessages {

    @NotNull
    private static final ResourceBundle RESOURCE_BUNDLE = MessagesPluginFactory.getResourceBundle(TreeGeneratorEditorPlugin.class,
            "com/ss/editor/tree/generator/messages/messages");

    public static final String TREE_GENERATOR_CREATOR_TITLE;
    public static final String TREE_GENERATOR_CREATOR_DESCRIPTION;
    public static final String TREE_GENERATOR_EDITOR_NAME;
    public static final String TREE_GENERATOR_EDITOR_EXPORT_ACTION;
    public static final String TREE_GENERATOR_EDITOR_TREE_TOOL;
    public static final String TREE_GENERATOR_EDITOR_NODE_SETTINGS;
    public static final String TREE_GENERATOR_EDITOR_NODE_TREE;
    public static final String TREE_GENERATOR_EDITOR_NODE_BRANCHES;
    public static final String TREE_GENERATOR_EDITOR_NODE_BRANCH;
    public static final String TREE_GENERATOR_EDITOR_NODE_LODS;
    public static final String TREE_GENERATOR_EDITOR_NODE_LOD;
    public static final String TREE_GENERATOR_EDITOR_NODE_ROOTS;
    public static final String TREE_GENERATOR_EDITOR_NODE_ROOT;
    public static final String TREE_GENERATOR_EDITOR_NODE_MATERIALS;
    public static final String TREE_GENERATOR_EDITOR_NODE_MATERIAL_TREE;
    public static final String TREE_GENERATOR_EDITOR_NODE_MATERIAL_LEAF;
    public static final String TREE_GENERATOR_EDITOR_NODE_MATERIAL_FLAT;
    public static final String TREE_GENERATOR_EDITOR_NODE_MATERIAL_IMPOSTOR;
    public static final String TREE_GENERATOR_EDITOR_ACTION_DELETE;
    public static final String TREE_GENERATOR_EDITOR_ACTION_ADD_BRANCH;
    public static final String TREE_GENERATOR_EDITOR_ACTION_ADD_ROOT;
    public static final String TREE_GENERATOR_EDITOR_ACTION_ADD_LOD;

    public static final String TREE_GENERATOR_PROPERTY_SHOW_WIRE;
    public static final String TREE_GENERATOR_PROPERTY_REDUCTION;
    public static final String TREE_GENERATOR_PROPERTY_BRANCH_DEPTH;
    public static final String TREE_GENERATOR_PROPERTY_ROOT_DEPTH;
    public static final String TREE_GENERATOR_PROPERTY_MAX_RADIAL_SEGMENTS;
    public static final String TREE_GENERATOR_PROPERTY_ENABLED;
    public static final String TREE_GENERATOR_PROPERTY_INHERIT;
    public static final String TREE_GENERATOR_PROPERTY_HAS_END_JOINT;
    public static final String TREE_GENERATOR_PROPERTY_INCLINATION;
    public static final String TREE_GENERATOR_PROPERTY_LENGTH_SCALE;
    public static final String TREE_GENERATOR_PROPERTY_RADIUS_SCALE;
    public static final String TREE_GENERATOR_PROPERTY_SEGMENT_VARIATION;
    public static final String TREE_GENERATOR_PROPERTY_SIDE_JOINT_START_ANGLE;
    public static final String TREE_GENERATOR_PROPERTY_TAPER;
    public static final String TREE_GENERATOR_PROPERTY_TIP_ROTATION;
    public static final String TREE_GENERATOR_PROPERTY_TWIST;
    public static final String TREE_GENERATOR_PROPERTY_LENGTH_SEGMENTS;
    public static final String TREE_GENERATOR_PROPERTY_RADIAL_SEGMENTS;
    public static final String TREE_GENERATOR_PROPERTY_SIDE_JOINT_COUNT;
    public static final String TREE_GENERATOR_PROPERTY_GENERATE_LEAVES;
    public static final String TREE_GENERATOR_PROPERTY_ENABLE_WIND;
    public static final String TREE_GENERATOR_PROPERTY_LEAF_SCALE;
    public static final String TREE_GENERATOR_PROPERTY_BASE_SCALE;
    public static final String TREE_GENERATOR_PROPERTY_TEXTURE_V_SCALE;
    public static final String TREE_GENERATOR_PROPERTY_TRUNK_HEIGHT;
    public static final String TREE_GENERATOR_PROPERTY_TRUNK_RADIUS;
    public static final String TREE_GENERATOR_PROPERTY_Y_OFFSET;
    public static final String TREE_GENERATOR_PROPERTY_FLEX_HEIGHT;
    public static final String TREE_GENERATOR_PROPERTY_TRUNK_FLEXIBILITY;
    public static final String TREE_GENERATOR_PROPERTY_BRANCH_FLEXIBILITY;
    public static final String TREE_GENERATOR_PROPERTY_SEED;
    public static final String TREE_GENERATOR_PROPERTY_TEXTURE_U_REPEAT;
    public static final String TREE_GENERATOR_PROPERTY_DISTANCE;

    static {
        TREE_GENERATOR_CREATOR_TITLE = RESOURCE_BUNDLE.getString("TreeGeneratorCreatorTitle");
        TREE_GENERATOR_CREATOR_DESCRIPTION = RESOURCE_BUNDLE.getString("TreeGeneratorCreatorDescription");
        TREE_GENERATOR_EDITOR_NAME = RESOURCE_BUNDLE.getString("TreeGeneratorEditorName");
        TREE_GENERATOR_EDITOR_EXPORT_ACTION = RESOURCE_BUNDLE.getString("TreeGeneratorEditorExportAction");
        TREE_GENERATOR_EDITOR_TREE_TOOL = RESOURCE_BUNDLE.getString("TreeGeneratorEditorTreeTool");
        TREE_GENERATOR_EDITOR_NODE_TREE = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeTree");
        TREE_GENERATOR_EDITOR_NODE_SETTINGS = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeSettings");
        TREE_GENERATOR_EDITOR_NODE_BRANCHES = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeBranches");
        TREE_GENERATOR_EDITOR_NODE_BRANCH = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeBranch");
        TREE_GENERATOR_EDITOR_NODE_LODS = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeLods");
        TREE_GENERATOR_EDITOR_NODE_LOD = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeLod");
        TREE_GENERATOR_EDITOR_NODE_ROOTS = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeRoots");
        TREE_GENERATOR_EDITOR_NODE_ROOT = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeRoot");
        TREE_GENERATOR_EDITOR_NODE_MATERIALS = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterials");
        TREE_GENERATOR_EDITOR_NODE_MATERIAL_TREE = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterialTree");
        TREE_GENERATOR_EDITOR_NODE_MATERIAL_LEAF = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterialLeaf");
        TREE_GENERATOR_EDITOR_NODE_MATERIAL_FLAT = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterialFlat");
        TREE_GENERATOR_EDITOR_NODE_MATERIAL_IMPOSTOR = RESOURCE_BUNDLE.getString("TreeGeneratorEditorNodeMaterialImposter");
        TREE_GENERATOR_EDITOR_ACTION_DELETE = RESOURCE_BUNDLE.getString("TreeGeneratorEditorActionDelete");
        TREE_GENERATOR_EDITOR_ACTION_ADD_BRANCH = RESOURCE_BUNDLE.getString("TreeGeneratorEditorActionAddBranch");
        TREE_GENERATOR_EDITOR_ACTION_ADD_ROOT = RESOURCE_BUNDLE.getString("TreeGeneratorEditorActionAddRoot");
        TREE_GENERATOR_EDITOR_ACTION_ADD_LOD = RESOURCE_BUNDLE.getString("TreeGeneratorEditorActionAddLod");
        TREE_GENERATOR_PROPERTY_SHOW_WIRE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyShowWire");
        TREE_GENERATOR_PROPERTY_REDUCTION = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyReduction");
        TREE_GENERATOR_PROPERTY_BRANCH_DEPTH = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyBranchDepth");
        TREE_GENERATOR_PROPERTY_ROOT_DEPTH = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyRootDepth");
        TREE_GENERATOR_PROPERTY_MAX_RADIAL_SEGMENTS = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyMaxRadialSegments");
        TREE_GENERATOR_PROPERTY_ENABLED = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyEnabled");
        TREE_GENERATOR_PROPERTY_INHERIT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyInherit");
        TREE_GENERATOR_PROPERTY_HAS_END_JOINT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyHasEndJoint");
        TREE_GENERATOR_PROPERTY_INCLINATION = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyInclination");
        TREE_GENERATOR_PROPERTY_LENGTH_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyLengthScale");
        TREE_GENERATOR_PROPERTY_RADIUS_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyRadiusScale");
        TREE_GENERATOR_PROPERTY_SEGMENT_VARIATION = RESOURCE_BUNDLE.getString("TreeGeneratorPropertySegmentVariation");
        TREE_GENERATOR_PROPERTY_SIDE_JOINT_START_ANGLE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertySideJointStartAngle");
        TREE_GENERATOR_PROPERTY_TAPER = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTaper");
        TREE_GENERATOR_PROPERTY_TIP_ROTATION = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTipRotation");
        TREE_GENERATOR_PROPERTY_TWIST = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTwist");
        TREE_GENERATOR_PROPERTY_LENGTH_SEGMENTS = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyLengthSegments");
        TREE_GENERATOR_PROPERTY_RADIAL_SEGMENTS = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyRadialSegments");
        TREE_GENERATOR_PROPERTY_SIDE_JOINT_COUNT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertySideJointCount");
        TREE_GENERATOR_PROPERTY_GENERATE_LEAVES = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyGenerateLeaves");
        TREE_GENERATOR_PROPERTY_ENABLE_WIND = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyEnableWind");
        TREE_GENERATOR_PROPERTY_LEAF_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyLeafScale");
        TREE_GENERATOR_PROPERTY_BASE_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyBaseScale");
        TREE_GENERATOR_PROPERTY_TEXTURE_V_SCALE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTextureVScale");
        TREE_GENERATOR_PROPERTY_TRUNK_HEIGHT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTrunkHeight");
        TREE_GENERATOR_PROPERTY_TRUNK_RADIUS = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTrunkRadius");
        TREE_GENERATOR_PROPERTY_Y_OFFSET = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyYOffset");
        TREE_GENERATOR_PROPERTY_FLEX_HEIGHT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyFlexHeight");
        TREE_GENERATOR_PROPERTY_TRUNK_FLEXIBILITY = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTrunkFlexibility");
        TREE_GENERATOR_PROPERTY_BRANCH_FLEXIBILITY = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyBranchFlexibility");
        TREE_GENERATOR_PROPERTY_SEED = RESOURCE_BUNDLE.getString("TreeGeneratorPropertySeed");
        TREE_GENERATOR_PROPERTY_TEXTURE_U_REPEAT = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyTextureURepeat");
        TREE_GENERATOR_PROPERTY_DISTANCE = RESOURCE_BUNDLE.getString("TreeGeneratorPropertyDistance");
    }
}
