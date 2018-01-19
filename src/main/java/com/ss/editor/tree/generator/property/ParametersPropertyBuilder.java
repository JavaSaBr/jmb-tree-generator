package com.ss.editor.tree.generator.property;

import static com.ss.editor.extension.property.EditablePropertyType.*;
import com.jme3.asset.AssetManager;
import com.jme3.asset.MaterialKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.Messages;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.extension.property.EditableProperty;
import com.ss.editor.extension.property.SimpleProperty;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.MaterialParameters;
import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.editor.ui.control.property.builder.PropertyBuilder;
import com.ss.editor.ui.control.property.builder.impl.EditableObjectPropertyBuilder;
import com.ss.editor.ui.control.property.impl.MaterialKeyPropertyControl;
import com.ss.editor.util.EditorUtil;
import com.ss.rlib.ui.util.FXUtils;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The implementation of {@link PropertyBuilder} to build property controls for {@link com.simsilica.arboreal.Parameters} objects.
 *
 * @author JavaSaBr
 */
public class ParametersPropertyBuilder extends EditableObjectPropertyBuilder<ChangeConsumer> {

    @NotNull
    private static final BiConsumer<MaterialParameters, MaterialKey> MATERIAL_APPLY_HANDLER = (parameters, materialKey) -> {

        final AssetManager assetManager = EditorUtil.getAssetManager();
        final Consumer<@NotNull Material> changeHandler = parameters.getChangeHandler();

        if (materialKey == null) {

            final Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            material.setColor("Color", ColorRGBA.Gray);

            changeHandler.accept(material);
            parameters.setMaterial(material);

        } else {
            final Material material = assetManager.loadAsset(materialKey);
            changeHandler.accept(material);
            parameters.setMaterial(material);
        }
    };

    @NotNull
    private static final Function<MaterialParameters, MaterialKey> MATERIAL_SYNC_HANDLER = parameters -> {
        final Material material = parameters.getSyncHandler().get();
        return (MaterialKey) material.getKey();
    };

    @NotNull
    private static final ParametersPropertyBuilder INSTANCE = new ParametersPropertyBuilder();

    @FromAnyThread
    public static @NotNull ParametersPropertyBuilder getInstance() {
        return INSTANCE;
    }

    protected ParametersPropertyBuilder() {
        super(ChangeConsumer.class);
    }

    @Override
    @FxThread
    protected @Nullable List<EditableProperty<?, ?>> getProperties(@NotNull final Object object) {

        if (!(object instanceof ProjectParameters || object instanceof TreeParameters ||
                object instanceof LevelOfDetailParameters || object instanceof BranchParameters)) {
            return null;
        }

        final List<EditableProperty<?, ?>> result = new ArrayList<>();

        if(object instanceof ProjectParameters) {

            final ProjectParameters parameters = (ProjectParameters) object;

            result.add(new SimpleProperty<>(BOOLEAN, PluginMessages.TREE_GENERATOR_PROPERTY_SHOW_WIRE, parameters,
                    ProjectParameters::isShowWire,
                    ProjectParameters::setShowWire));

        } else if(object instanceof LevelOfDetailParameters) {

            final LevelOfDetailParameters parameters = (LevelOfDetailParameters) object;

            result.add(new SimpleProperty<>(ENUM, PluginMessages.TREE_GENERATOR_PROPERTY_REDUCTION, parameters,
                    LevelOfDetailParameters::getReduction,
                    LevelOfDetailParameters::setReduction));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_DISTANCE, 1F, 0F, Float.MAX_VALUE, parameters,
                    LevelOfDetailParameters::getDistance,
                    LevelOfDetailParameters::setDistance));
            result.add(new SimpleProperty<>(INTEGER, PluginMessages.TREE_GENERATOR_PROPERTY_BRANCH_DEPTH, 1F, 1F, 100F, parameters,
                    LevelOfDetailParameters::getBranchDepth,
                    LevelOfDetailParameters::setBranchDepth));
            result.add(new SimpleProperty<>(INTEGER, PluginMessages.TREE_GENERATOR_PROPERTY_ROOT_DEPTH, 1F, 1F, 100F, parameters,
                    LevelOfDetailParameters::getRootDepth,
                    LevelOfDetailParameters::setRootDepth));
            result.add(new SimpleProperty<>(INTEGER, PluginMessages.TREE_GENERATOR_PROPERTY_MAX_RADIAL_SEGMENTS, 1F, 1F, 100F, parameters,
                    LevelOfDetailParameters::getMaxRadialSegments,
                    LevelOfDetailParameters::setMaxRadialSegments));

        } else if(object instanceof BranchParameters) {

            final BranchParameters parameters = (BranchParameters) object;

            result.add(new SimpleProperty<>(BOOLEAN, PluginMessages.TREE_GENERATOR_PROPERTY_ENABLED, parameters,
                    BranchParameters::isEnabled,
                    BranchParameters::setEnabled));
            result.add(new SimpleProperty<>(BOOLEAN, PluginMessages.TREE_GENERATOR_PROPERTY_INHERIT, parameters,
                    BranchParameters::isInherit,
                    BranchParameters::setInherit));
            result.add(new SimpleProperty<>(BOOLEAN, PluginMessages.TREE_GENERATOR_PROPERTY_HAS_END_JOINT, parameters,
                    BranchParameters::isHasEndJoint,
                    BranchParameters::setHasEndJoint));
            result.add(new SimpleProperty<>(FLOAT, Messages.MODEL_PROPERTY_GRAVITY, 0.01F, parameters,
                    BranchParameters::getGravity,
                    BranchParameters::setGravity));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_INCLINATION, 0.1F, parameters,
                    BranchParameters::getInclination,
                    BranchParameters::setInclination));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_LENGTH_SCALE, 0.01F, parameters,
                    BranchParameters::getLengthScale,
                    BranchParameters::setLengthScale));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_RADIUS_SCALE, 0.05F, parameters,
                    BranchParameters::getRadiusScale,
                    BranchParameters::setRadiusScale));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_SEGMENT_VARIATION, 0.01F, 0F, 1F, parameters,
                    BranchParameters::getSegmentVariation,
                    BranchParameters::setSegmentVariation));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_SIDE_JOINT_START_ANGLE, 0.3F, parameters,
                    BranchParameters::getSideJointStartAngle,
                    BranchParameters::setSideJointStartAngle));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_TAPER, 0.05F, parameters,
                    BranchParameters::getTaper,
                    BranchParameters::setTaper));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_TIP_ROTATION, 0.3F, parameters,
                    BranchParameters::getTipRotation,
                    BranchParameters::setTipRotation));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_TWIST, 0.3F, parameters,
                    BranchParameters::getTwist,
                    BranchParameters::setTwist));
            result.add(new SimpleProperty<>(INTEGER, PluginMessages.TREE_GENERATOR_PROPERTY_LENGTH_SEGMENTS, 1F, 0F, 100F, parameters,
                    BranchParameters::getLengthSegments,
                    BranchParameters::setLengthSegments));
            result.add(new SimpleProperty<>(INTEGER, PluginMessages.TREE_GENERATOR_PROPERTY_RADIAL_SEGMENTS, 1F, 1F, 100F, parameters,
                    BranchParameters::getRadialSegments,
                    BranchParameters::setRadialSegments));
            result.add(new SimpleProperty<>(INTEGER, PluginMessages.TREE_GENERATOR_PROPERTY_SIDE_JOINT_COUNT, 1F, 1F, 100F, parameters,
                    BranchParameters::getSideJointCount,
                    BranchParameters::setSideJointCount));

        } else {

            final TreeParameters parameters = (TreeParameters) object;

            result.add(new SimpleProperty<>(BOOLEAN, PluginMessages.TREE_GENERATOR_PROPERTY_GENERATE_LEAVES, parameters,
                    TreeParameters::getGenerateLeaves,
                    TreeParameters::setGenerateLeaves));
            result.add(new SimpleProperty<>(BOOLEAN, PluginMessages.TREE_GENERATOR_PROPERTY_ENABLE_WIND, parameters,
                    TreeParameters::isUseWind,
                    TreeParameters::setUseWind));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_LEAF_SCALE, 0.01F, 0.01F, Integer.MAX_VALUE, parameters,
                    TreeParameters::getLeafScale,
                    TreeParameters::setLeafScale));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_BASE_SCALE, 0.01F, 0.01F, Integer.MAX_VALUE, parameters,
                    TreeParameters::getBaseScale,
                    TreeParameters::setBaseScale));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_TEXTURE_V_SCALE, 0.4F, 0.01F, Integer.MAX_VALUE, parameters,
                    TreeParameters::getTextureVScale,
                    TreeParameters::setTextureVScale));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_TRUNK_HEIGHT, 0.4F, 0.01F, Integer.MAX_VALUE, parameters,
                    TreeParameters::getTrunkHeight,
                    TreeParameters::setTrunkHeight));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_TRUNK_RADIUS, 0.1F, 0.01F, Integer.MAX_VALUE, parameters,
                    TreeParameters::getTrunkRadius,
                    TreeParameters::setTrunkRadius));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_Y_OFFSET, parameters,
                    TreeParameters::getYOffset,
                    TreeParameters::setYOffset));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_FLEX_HEIGHT, 2F, 0.01F, Integer.MAX_VALUE, parameters,
                    TreeParameters::getFlexHeight,
                    TreeParameters::setFlexHeight));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_TRUNK_FLEXIBILITY, 2F, 0.01F, Integer.MAX_VALUE, parameters,
                    TreeParameters::getTrunkFlexibility,
                    TreeParameters::setTrunkFlexibility));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.TREE_GENERATOR_PROPERTY_BRANCH_FLEXIBILITY, 2F, 0.01F, Integer.MAX_VALUE, parameters,
                    TreeParameters::getBranchFlexibility,
                    TreeParameters::setBranchFlexibility));
            result.add(new SimpleProperty<>(INTEGER, PluginMessages.TREE_GENERATOR_PROPERTY_SEED, parameters,
                    TreeParameters::getSeed,
                    TreeParameters::setSeed));
            result.add(new SimpleProperty<>(INTEGER, PluginMessages.TREE_GENERATOR_PROPERTY_TEXTURE_U_REPEAT, 1F, 0F, Integer.MAX_VALUE, parameters,
                    TreeParameters::getTextureURepeat,
                    TreeParameters::setTextureURepeat));
        }

        return result;
    }

    @Override
    @FxThread
    protected void buildForImpl(@NotNull final Object object, @Nullable final Object parent,
                                @NotNull final VBox container, @NotNull final ChangeConsumer changeConsumer) {
        super.buildForImpl(object, parent, container, changeConsumer);

        if (object instanceof MaterialParameters) {

            final MaterialParameters parameters = (MaterialParameters) object;
            final Material material = parameters.getMaterial();

            final MaterialKeyPropertyControl<ChangeConsumer, MaterialParameters> materialControl =
                    new MaterialKeyPropertyControl<>((MaterialKey) material.getKey(), Messages.MODEL_PROPERTY_MATERIAL, changeConsumer);
            materialControl.setApplyHandler(MATERIAL_APPLY_HANDLER);
            materialControl.setSyncHandler(MATERIAL_SYNC_HANDLER);
            materialControl.setEditObject(parameters);

            FXUtils.addToPane(materialControl, container);
        }
    }
}
