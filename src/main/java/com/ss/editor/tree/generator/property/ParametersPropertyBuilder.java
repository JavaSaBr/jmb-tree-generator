package com.ss.editor.tree.generator.property;

import com.jme3.asset.MaterialKey;
import com.jme3.material.Material;
import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.LevelOfDetailParameters.ReductionType;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.tree.generator.parameters.MaterialParameters;
import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.editor.ui.control.property.PropertyControl;
import com.ss.editor.ui.control.property.builder.PropertyBuilder;
import com.ss.editor.ui.control.property.builder.impl.AbstractPropertyBuilder;
import com.ss.editor.ui.control.property.impl.*;
import com.ss.rlib.ui.util.FXUtils;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The implementation of {@link PropertyBuilder} to build property controls for {@link com.simsilica.arboreal.Parameters} objects.
 *
 * @author JavaSaBr
 */
public class ParametersPropertyBuilder extends AbstractPropertyBuilder<ChangeConsumer> {

    @NotNull
    private static final ParametersPropertyBuilder INSTANCE = new ParametersPropertyBuilder();

    public static @NotNull ParametersPropertyBuilder getInstance() {
        return INSTANCE;
    }

    protected ParametersPropertyBuilder() {
        super(ChangeConsumer.class);
    }

    @Override
    protected void buildForImpl(@NotNull final Object object, @Nullable final Object parent,
                                @NotNull final VBox container, @NotNull final ChangeConsumer changeConsumer) {
        super.buildForImpl(object, parent, container, changeConsumer);

        if (object instanceof ProjectParameters) {

            final ProjectParameters parameters = (ProjectParameters) object;
            final boolean showWire = parameters.isShowWire();

            final PropertyControl<ChangeConsumer, ProjectParameters, Boolean> showWireControl =
                    new BooleanPropertyControl<>(showWire, "Show wire", changeConsumer);
            showWireControl.setApplyHandler(ProjectParameters::setShowWire);
            showWireControl.setSyncHandler(ProjectParameters::isShowWire);
            showWireControl.setEditObject(parameters);

            FXUtils.addToPane(showWireControl, container);

        } else if (object instanceof TreeParameters) {
            buildProperties((TreeParameters) object, container, changeConsumer);
        } else if (object instanceof LevelOfDetailParameters) {
            buildProperties((LevelOfDetailParameters) object, container, changeConsumer);
        } else if (object instanceof BranchParameters) {
            buildProperties((BranchParameters) object, container, changeConsumer);
        } else if (object instanceof MaterialParameters) {

            final MaterialParameters parameters = (MaterialParameters) object;
            final Material material = parameters.getMaterial();

            final MaterialKeyPropertyControl<ChangeConsumer, MaterialParameters> materialControl =
                    new MaterialKeyPropertyControl<>((MaterialKey) material.getKey(), "Material", changeConsumer);

            FXUtils.addToPane(materialControl, container);
        }
    }

    private void buildProperties(@NotNull final BranchParameters parameters, @NotNull final VBox container,
                                 @NotNull final ChangeConsumer changeConsumer) {

        final boolean enabled = parameters.isEnabled();
        final boolean hasEndJoint = parameters.isHasEndJoint();
        final boolean inherit = parameters.isInherit();

        final float gravity = parameters.getGravity();
        final float inclination = parameters.getInclination();
        final float lengthScale = parameters.getLengthScale();
        final float radiusScale = parameters.getRadiusScale();
        final float segmentVariation = parameters.getSegmentVariation();
        final float sideJointStartAngle = parameters.getSideJointStartAngle();
        final float taper = parameters.getTaper();
        final float tipRotation = parameters.getTipRotation();
        final float twist = parameters.getTwist();

        final int lengthSegments = parameters.getLengthSegments();
        final int radialSegments = parameters.getRadialSegments();
        final int sideJointCount = parameters.getSideJointCount();

        final PropertyControl<ChangeConsumer, BranchParameters, Boolean> enabledControl =
                new BooleanPropertyControl<>(enabled, "Enabled", changeConsumer);
        enabledControl.setApplyHandler(BranchParameters::setEnabled);
        enabledControl.setSyncHandler(BranchParameters::isEnabled);
        enabledControl.setEditObject(parameters);

        final PropertyControl<ChangeConsumer, BranchParameters, Boolean> inheritControl =
                new BooleanPropertyControl<>(inherit, "Inherit", changeConsumer);
        inheritControl.setApplyHandler(BranchParameters::setInherit);
        inheritControl.setSyncHandler(BranchParameters::isInherit);
        inheritControl.setEditObject(parameters);

        final PropertyControl<ChangeConsumer, BranchParameters, Boolean> hasEndJointControl =
                new BooleanPropertyControl<>(hasEndJoint, "Has end joint", changeConsumer);
        hasEndJointControl.setApplyHandler(BranchParameters::setHasEndJoint);
        hasEndJointControl.setSyncHandler(BranchParameters::isHasEndJoint);
        hasEndJointControl.setEditObject(parameters);

        final FloatPropertyControl<ChangeConsumer, BranchParameters> gravityControl =
                new FloatPropertyControl<>(gravity, "Gravity", changeConsumer);
        gravityControl.setApplyHandler(BranchParameters::setGravity);
        gravityControl.setSyncHandler(BranchParameters::getGravity);
        gravityControl.setEditObject(parameters);
        gravityControl.setScrollPower(0.3F);

        final FloatPropertyControl<ChangeConsumer, BranchParameters> inclinationControl =
                new FloatPropertyControl<>(inclination, "Inclination", changeConsumer);
        inclinationControl.setApplyHandler(BranchParameters::setInclination);
        inclinationControl.setSyncHandler(BranchParameters::getInclination);
        inclinationControl.setEditObject(parameters);
        inclinationControl.setScrollPower(0.3F);

        final FloatPropertyControl<ChangeConsumer, BranchParameters> lengthScaleControl =
                new FloatPropertyControl<>(lengthScale, "Length scale", changeConsumer);
        lengthScaleControl.setApplyHandler(BranchParameters::setLengthScale);
        lengthScaleControl.setSyncHandler(BranchParameters::getLengthScale);
        lengthScaleControl.setEditObject(parameters);
        lengthScaleControl.setScrollPower(0.3F);

        final FloatPropertyControl<ChangeConsumer, BranchParameters> radiusScaleControl =
                new FloatPropertyControl<>(radiusScale, "Radius scale", changeConsumer);
        radiusScaleControl.setApplyHandler(BranchParameters::setRadiusScale);
        radiusScaleControl.setSyncHandler(BranchParameters::getRadiusScale);
        radiusScaleControl.setEditObject(parameters);
        radiusScaleControl.setScrollPower(0.3F);

        final FloatPropertyControl<ChangeConsumer, BranchParameters> segmentVariationControl =
                new FloatPropertyControl<>(segmentVariation, "Segment variation", changeConsumer);
        segmentVariationControl.setApplyHandler(BranchParameters::setSegmentVariation);
        segmentVariationControl.setSyncHandler(BranchParameters::getSegmentVariation);
        segmentVariationControl.setEditObject(parameters);
        segmentVariationControl.setScrollPower(0.3F);
        segmentVariationControl.setMinMax(0F, 1F);

        final FloatPropertyControl<ChangeConsumer, BranchParameters> sideJointStartAngleControl =
                new FloatPropertyControl<>(sideJointStartAngle, "Side joint start angle", changeConsumer);
        sideJointStartAngleControl.setApplyHandler(BranchParameters::setSideJointStartAngle);
        sideJointStartAngleControl.setSyncHandler(BranchParameters::getSideJointStartAngle);
        sideJointStartAngleControl.setEditObject(parameters);
        sideJointStartAngleControl.setScrollPower(0.3F);

        final FloatPropertyControl<ChangeConsumer, BranchParameters> taperControl =
                new FloatPropertyControl<>(taper, "Taper", changeConsumer);
        taperControl.setApplyHandler(BranchParameters::setTaper);
        taperControl.setSyncHandler(BranchParameters::getTaper);
        taperControl.setEditObject(parameters);
        taperControl.setScrollPower(0.3F);

        final FloatPropertyControl<ChangeConsumer, BranchParameters> tipRotationControl =
                new FloatPropertyControl<>(tipRotation, "Tip rotation", changeConsumer);
        tipRotationControl.setApplyHandler(BranchParameters::setTipRotation);
        tipRotationControl.setSyncHandler(BranchParameters::getTipRotation);
        tipRotationControl.setEditObject(parameters);
        tipRotationControl.setScrollPower(0.3F);

        final FloatPropertyControl<ChangeConsumer, BranchParameters> twistControl =
                new FloatPropertyControl<>(twist, "Twist", changeConsumer);
        twistControl.setApplyHandler(BranchParameters::setTwist);
        twistControl.setSyncHandler(BranchParameters::getTwist);
        twistControl.setEditObject(parameters);
        twistControl.setScrollPower(0.3F);

        final IntegerPropertyControl<ChangeConsumer, BranchParameters> lengthSegmentsControl =
                new IntegerPropertyControl<>(lengthSegments, "Length segments", changeConsumer);
        lengthSegmentsControl.setApplyHandler(BranchParameters::setLengthSegments);
        lengthSegmentsControl.setSyncHandler(BranchParameters::getLengthSegments);
        lengthSegmentsControl.setEditObject(parameters);
        lengthSegmentsControl.setMinMax(0, 100);

        final IntegerPropertyControl<ChangeConsumer, BranchParameters> radialSegmentsControl =
                new IntegerPropertyControl<>(radialSegments, "Radial segments", changeConsumer);
        radialSegmentsControl.setApplyHandler(BranchParameters::setRadialSegments);
        radialSegmentsControl.setSyncHandler(BranchParameters::getRadialSegments);
        radialSegmentsControl.setEditObject(parameters);
        radialSegmentsControl.setMinMax(0, 100);

        final IntegerPropertyControl<ChangeConsumer, BranchParameters> sideJointCountControl =
                new IntegerPropertyControl<>(sideJointCount, "Side joint count", changeConsumer);
        sideJointCountControl.setApplyHandler(BranchParameters::setSideJointCount);
        sideJointCountControl.setSyncHandler(BranchParameters::getSideJointCount);
        sideJointCountControl.setEditObject(parameters);
        sideJointCountControl.setMinMax(0, 100);

        FXUtils.addToPane(enabledControl, container);
        FXUtils.addToPane(inheritControl, container);
        FXUtils.addToPane(hasEndJointControl, container);
        FXUtils.addToPane(gravityControl, container);
        FXUtils.addToPane(inclinationControl, container);
        FXUtils.addToPane(lengthScaleControl, container);
        FXUtils.addToPane(radiusScaleControl, container);
        FXUtils.addToPane(segmentVariationControl, container);
        FXUtils.addToPane(sideJointStartAngleControl, container);
        FXUtils.addToPane(taperControl, container);
        FXUtils.addToPane(tipRotationControl, container);
        FXUtils.addToPane(twistControl, container);
        FXUtils.addToPane(lengthSegmentsControl, container);
        FXUtils.addToPane(radialSegmentsControl, container);
        FXUtils.addToPane(sideJointCountControl, container);
    }

    private void buildProperties(@NotNull final LevelOfDetailParameters parameters, @NotNull final VBox container,
                                 @NotNull final ChangeConsumer changeConsumer) {

        final ReductionType reduction = parameters.getReduction();

        final float distance = parameters.getDistance();
        final int branchDepth = parameters.getBranchDepth();
        final int maxRadialSegments = parameters.getMaxRadialSegments();
        final int rootDepth = parameters.getRootDepth();

        final PropertyControl<ChangeConsumer, LevelOfDetailParameters, ReductionType> reductionControl =
                new EnumPropertyControl<>(reduction, "Reduction", changeConsumer);
        reductionControl.setApplyHandler(LevelOfDetailParameters::setReduction);
        reductionControl.setSyncHandler(LevelOfDetailParameters::getReduction);
        reductionControl.setEditObject(parameters);

        final FloatPropertyControl<ChangeConsumer, LevelOfDetailParameters> distanceControl =
                new FloatPropertyControl<>(distance, "Distance", changeConsumer);
        distanceControl.setApplyHandler(LevelOfDetailParameters::setDistance);
        distanceControl.setSyncHandler(LevelOfDetailParameters::getDistance);
        distanceControl.setMinMax(0, Float.MAX_VALUE);
        distanceControl.setEditObject(parameters);

        final PropertyControl<ChangeConsumer, LevelOfDetailParameters, Integer> branchDepthControl =
                new IntegerPropertyControl<>(branchDepth, "Branch depth", changeConsumer);
        branchDepthControl.setApplyHandler(LevelOfDetailParameters::setBranchDepth);
        branchDepthControl.setSyncHandler(LevelOfDetailParameters::getBranchDepth);
        branchDepthControl.setEditObject(parameters);

        final PropertyControl<ChangeConsumer, LevelOfDetailParameters, Integer> maxRadialSegmentsControl =
                new IntegerPropertyControl<>(maxRadialSegments, "Max radial segments", changeConsumer);
        maxRadialSegmentsControl.setApplyHandler(LevelOfDetailParameters::setMaxRadialSegments);
        maxRadialSegmentsControl.setSyncHandler(LevelOfDetailParameters::getMaxRadialSegments);
        maxRadialSegmentsControl.setEditObject(parameters);

        final PropertyControl<ChangeConsumer, LevelOfDetailParameters, Integer> rootDepthControl =
                new IntegerPropertyControl<>(rootDepth, "Root depth", changeConsumer);
        rootDepthControl.setApplyHandler(LevelOfDetailParameters::setRootDepth);
        rootDepthControl.setSyncHandler(LevelOfDetailParameters::getRootDepth);
        rootDepthControl.setEditObject(parameters);

        FXUtils.addToPane(reductionControl, container);
        FXUtils.addToPane(distanceControl, container);
        FXUtils.addToPane(branchDepthControl, container);
        FXUtils.addToPane(maxRadialSegmentsControl, container);
        FXUtils.addToPane(rootDepthControl, container);
    }

    private void buildProperties(@NotNull final TreeParameters parameters, @NotNull final VBox container,
                                 @NotNull final ChangeConsumer changeConsumer) {

        final boolean generateLeaves = parameters.getGenerateLeaves();
        final float leafScale = parameters.getLeafScale();
        final float textureVScale = parameters.getTextureVScale();
        final float trunkHeight = parameters.getTrunkHeight();
        final float trunkRadius = parameters.getTrunkRadius();
        final float yOffset = parameters.getYOffset();
        final float baseScale = parameters.getBaseScale();
        final float flexHeight = parameters.getFlexHeight();
        final float branchFlexibility = parameters.getBranchFlexibility();
        final float trunkFlexibility = parameters.getTrunkFlexibility();
        final int seed = parameters.getSeed();
        final int textureURepeat = parameters.getTextureURepeat();

        final PropertyControl<ChangeConsumer, TreeParameters, Boolean> generateLeavesControl =
                new BooleanPropertyControl<>(generateLeaves, "Generate Leaves", changeConsumer);
        generateLeavesControl.setApplyHandler(TreeParameters::setGenerateLeaves);
        generateLeavesControl.setSyncHandler(TreeParameters::getGenerateLeaves);
        generateLeavesControl.setEditObject(parameters);

        final FloatPropertyControl<ChangeConsumer, TreeParameters> leafScaleControl =
                new FloatPropertyControl<>(leafScale, "Leaf scale", changeConsumer);
        leafScaleControl.setApplyHandler(TreeParameters::setLeafScale);
        leafScaleControl.setSyncHandler(TreeParameters::getLeafScale);
        leafScaleControl.setEditObject(parameters);
        leafScaleControl.setMinMax(0.01F, Integer.MAX_VALUE);
        leafScaleControl.setScrollPower(1F);

        final FloatPropertyControl<ChangeConsumer, TreeParameters> textureVScaleControl =
                new FloatPropertyControl<>(textureVScale, "Texture V scale", changeConsumer);
        textureVScaleControl.setApplyHandler(TreeParameters::setTextureVScale);
        textureVScaleControl.setSyncHandler(TreeParameters::getTextureVScale);
        textureVScaleControl.setEditObject(parameters);
        textureVScaleControl.setMinMax(0.01F, Integer.MAX_VALUE);
        textureVScaleControl.setScrollPower(0.4F);

        final FloatPropertyControl<ChangeConsumer, TreeParameters> trunkHeightControl =
                new FloatPropertyControl<>(trunkHeight, "Trunk height", changeConsumer);
        trunkHeightControl.setApplyHandler(TreeParameters::setTrunkHeight);
        trunkHeightControl.setSyncHandler(TreeParameters::getTrunkHeight);
        trunkHeightControl.setEditObject(parameters);
        trunkHeightControl.setMinMax(0.01F, Integer.MAX_VALUE);
        trunkHeightControl.setScrollPower(3F);

        final FloatPropertyControl<ChangeConsumer, TreeParameters> trunkRadiusControl =
                new FloatPropertyControl<>(trunkRadius, "Trunk radius", changeConsumer);
        trunkRadiusControl.setApplyHandler(TreeParameters::setTrunkRadius);
        trunkRadiusControl.setSyncHandler(TreeParameters::getTrunkRadius);
        trunkRadiusControl.setEditObject(parameters);
        trunkRadiusControl.setMinMax(0.01F, Integer.MAX_VALUE);
        trunkRadiusControl.setScrollPower(0.5F);

        final PropertyControl<ChangeConsumer, TreeParameters, Float> yOffsetControl =
                new FloatPropertyControl<>(yOffset, "Y offset", changeConsumer);
        yOffsetControl.setApplyHandler(TreeParameters::setYOffset);
        yOffsetControl.setSyncHandler(TreeParameters::getYOffset);
        yOffsetControl.setEditObject(parameters);

        final FloatPropertyControl<ChangeConsumer, TreeParameters> baseScaleControl =
                new FloatPropertyControl<>(baseScale, "Base scale", changeConsumer);
        baseScaleControl.setApplyHandler(TreeParameters::setBaseScale);
        baseScaleControl.setSyncHandler(TreeParameters::getBaseScale);
        baseScaleControl.setEditObject(parameters);
        baseScaleControl.setMinMax(0.01F, Integer.MAX_VALUE);
        baseScaleControl.setScrollPower(0.5F);

        final FloatPropertyControl<ChangeConsumer, TreeParameters> flexHeightControl =
                new FloatPropertyControl<>(flexHeight, "Flex height", changeConsumer);
        flexHeightControl.setApplyHandler(TreeParameters::setFlexHeight);
        flexHeightControl.setSyncHandler(TreeParameters::getFlexHeight);
        flexHeightControl.setEditObject(parameters);
        flexHeightControl.setMinMax(0.01F, Integer.MAX_VALUE);
        flexHeightControl.setScrollPower(2F);

        final FloatPropertyControl<ChangeConsumer, TreeParameters> trunkFlexibilityControl =
                new FloatPropertyControl<>(trunkFlexibility, "Trunk flexibility", changeConsumer);
        trunkFlexibilityControl.setApplyHandler(TreeParameters::setTrunkFlexibility);
        trunkFlexibilityControl.setSyncHandler(TreeParameters::getTrunkFlexibility);
        trunkFlexibilityControl.setEditObject(parameters);
        trunkFlexibilityControl.setMinMax(0.01F, Integer.MAX_VALUE);
        trunkFlexibilityControl.setScrollPower(2F);

        final FloatPropertyControl<ChangeConsumer, TreeParameters> branchFlexibilityControl =
                new FloatPropertyControl<>(branchFlexibility, "Branch flexibility", changeConsumer);
        branchFlexibilityControl.setApplyHandler(TreeParameters::setBranchFlexibility);
        branchFlexibilityControl.setSyncHandler(TreeParameters::getBranchFlexibility);
        branchFlexibilityControl.setEditObject(parameters);
        branchFlexibilityControl.setMinMax(0.01F, Integer.MAX_VALUE);
        branchFlexibilityControl.setScrollPower(2F);

        final PropertyControl<ChangeConsumer, TreeParameters, Integer> seedControl =
                new IntegerPropertyControl<>(seed, "Seed", changeConsumer);
        seedControl.setApplyHandler(TreeParameters::setSeed);
        seedControl.setSyncHandler(TreeParameters::getSeed);
        seedControl.setEditObject(parameters);

        final IntegerPropertyControl<ChangeConsumer, TreeParameters> textureURepeatControl =
                new IntegerPropertyControl<>(textureURepeat, "Texture U repeat", changeConsumer);
        textureURepeatControl.setApplyHandler(TreeParameters::setTextureURepeat);
        textureURepeatControl.setSyncHandler(TreeParameters::getTextureURepeat);
        textureURepeatControl.setEditObject(parameters);
        textureURepeatControl.setMinMax(0, Integer.MAX_VALUE);

        FXUtils.addToPane(generateLeavesControl, container);
        FXUtils.addToPane(leafScaleControl, container);
        FXUtils.addToPane(textureVScaleControl, container);
        FXUtils.addToPane(trunkHeightControl, container);
        FXUtils.addToPane(trunkRadiusControl, container);
        FXUtils.addToPane(yOffsetControl, container);
        FXUtils.addToPane(baseScaleControl, container);
        FXUtils.addToPane(flexHeightControl, container);
        FXUtils.addToPane(trunkFlexibilityControl, container);
        FXUtils.addToPane(branchFlexibilityControl, container);
        FXUtils.addToPane(seedControl, container);
        FXUtils.addToPane(textureURepeatControl, container);
    }
}
