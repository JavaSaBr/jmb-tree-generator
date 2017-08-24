package com.ss.editor.tree.generator.property;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.LevelOfDetailParameters.ReductionType;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.tree.generator.editor.ParametersChangeConsumer;
import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.editor.ui.control.property.PropertyControl;
import com.ss.editor.ui.control.property.builder.PropertyBuilder;
import com.ss.editor.ui.control.property.builder.impl.AbstractPropertyBuilder;
import com.ss.editor.ui.control.property.impl.BooleanPropertyControl;
import com.ss.editor.ui.control.property.impl.EnumPropertyControl;
import com.ss.editor.ui.control.property.impl.FloatPropertyControl;
import com.ss.editor.ui.control.property.impl.IntegerPropertyControl;
import com.ss.rlib.ui.util.FXUtils;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The implementation of {@link PropertyBuilder} to build property controls for {@link com.simsilica.arboreal.Parameters} objects.
 *
 * @author JavaSaBr
 */
public class ParametersPropertyBuilder extends AbstractPropertyBuilder<ParametersChangeConsumer> {

    @NotNull
    private static final ParametersPropertyBuilder INSTANCE = new ParametersPropertyBuilder();

    public static @NotNull ParametersPropertyBuilder getInstance() {
        return INSTANCE;
    }

    protected ParametersPropertyBuilder() {
        super(ParametersChangeConsumer.class);
    }

    @Override
    protected void buildForImpl(@NotNull final Object object, @Nullable final Object parent,
                                @NotNull final VBox container, @NotNull final ParametersChangeConsumer changeConsumer) {
        super.buildForImpl(object, parent, container, changeConsumer);

        if (object instanceof ProjectParameters) {

            final ProjectParameters parameters = (ProjectParameters) object;
            final boolean showWire = parameters.isShowWire();

            final PropertyControl<ParametersChangeConsumer, ProjectParameters, Boolean> showWireControl =
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
        }
    }

    private void buildProperties(@NotNull final BranchParameters parameters, @NotNull final VBox container,
                                 @NotNull final ParametersChangeConsumer changeConsumer) {

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

        final PropertyControl<ParametersChangeConsumer, BranchParameters, Boolean> enabledControl =
                new BooleanPropertyControl<>(enabled, "Enabled", changeConsumer);
        enabledControl.setApplyHandler(BranchParameters::setEnabled);
        enabledControl.setSyncHandler(BranchParameters::isEnabled);
        enabledControl.setEditObject(parameters);

        final PropertyControl<ParametersChangeConsumer, BranchParameters, Boolean> inheritControl =
                new BooleanPropertyControl<>(inherit, "Inherit", changeConsumer);
        inheritControl.setApplyHandler(BranchParameters::setInherit);
        inheritControl.setSyncHandler(BranchParameters::isInherit);
        inheritControl.setEditObject(parameters);

        final PropertyControl<ParametersChangeConsumer, BranchParameters, Boolean> hasEndJointControl =
                new BooleanPropertyControl<>(hasEndJoint, "Has end joint", changeConsumer);
        hasEndJointControl.setApplyHandler(BranchParameters::setHasEndJoint);
        hasEndJointControl.setSyncHandler(BranchParameters::isHasEndJoint);
        hasEndJointControl.setEditObject(parameters);

        final FloatPropertyControl<ParametersChangeConsumer, BranchParameters> gravityControl =
                new FloatPropertyControl<>(gravity, "Gravity", changeConsumer);
        gravityControl.setApplyHandler(BranchParameters::setGravity);
        gravityControl.setSyncHandler(BranchParameters::getGravity);
        gravityControl.setEditObject(parameters);
        gravityControl.setScrollPower(0.3F);

        final FloatPropertyControl<ParametersChangeConsumer, BranchParameters> inclinationControl =
                new FloatPropertyControl<>(inclination, "Inclination", changeConsumer);
        inclinationControl.setApplyHandler(BranchParameters::setInclination);
        inclinationControl.setSyncHandler(BranchParameters::getInclination);
        inclinationControl.setEditObject(parameters);
        inclinationControl.setScrollPower(0.3F);

        final FloatPropertyControl<ParametersChangeConsumer, BranchParameters> lengthScaleControl =
                new FloatPropertyControl<>(lengthScale, "Length scale", changeConsumer);
        lengthScaleControl.setApplyHandler(BranchParameters::setLengthScale);
        lengthScaleControl.setSyncHandler(BranchParameters::getLengthScale);
        lengthScaleControl.setEditObject(parameters);
        lengthScaleControl.setScrollPower(0.3F);

        final FloatPropertyControl<ParametersChangeConsumer, BranchParameters> radiusScaleControl =
                new FloatPropertyControl<>(radiusScale, "Radius scale", changeConsumer);
        radiusScaleControl.setApplyHandler(BranchParameters::setRadiusScale);
        radiusScaleControl.setSyncHandler(BranchParameters::getRadiusScale);
        radiusScaleControl.setEditObject(parameters);
        radiusScaleControl.setScrollPower(0.3F);

        final FloatPropertyControl<ParametersChangeConsumer, BranchParameters> segmentVariationControl =
                new FloatPropertyControl<>(segmentVariation, "Segment variation", changeConsumer);
        segmentVariationControl.setApplyHandler(BranchParameters::setSegmentVariation);
        segmentVariationControl.setSyncHandler(BranchParameters::getSegmentVariation);
        segmentVariationControl.setEditObject(parameters);
        segmentVariationControl.setScrollPower(0.3F);
        segmentVariationControl.setMinMax(0F, 1F);

        final FloatPropertyControl<ParametersChangeConsumer, BranchParameters> sideJointStartAngleControl =
                new FloatPropertyControl<>(sideJointStartAngle, "Side joint start angle", changeConsumer);
        sideJointStartAngleControl.setApplyHandler(BranchParameters::setSideJointStartAngle);
        sideJointStartAngleControl.setSyncHandler(BranchParameters::getSideJointStartAngle);
        sideJointStartAngleControl.setEditObject(parameters);
        sideJointStartAngleControl.setScrollPower(0.3F);

        final FloatPropertyControl<ParametersChangeConsumer, BranchParameters> taperControl =
                new FloatPropertyControl<>(taper, "Taper", changeConsumer);
        taperControl.setApplyHandler(BranchParameters::setTaper);
        taperControl.setSyncHandler(BranchParameters::getTaper);
        taperControl.setEditObject(parameters);
        taperControl.setScrollPower(0.3F);

        final FloatPropertyControl<ParametersChangeConsumer, BranchParameters> tipRotationControl =
                new FloatPropertyControl<>(tipRotation, "Tip rotation", changeConsumer);
        tipRotationControl.setApplyHandler(BranchParameters::setTipRotation);
        tipRotationControl.setSyncHandler(BranchParameters::getTipRotation);
        tipRotationControl.setEditObject(parameters);
        tipRotationControl.setScrollPower(0.3F);

        final FloatPropertyControl<ParametersChangeConsumer, BranchParameters> twistControl =
                new FloatPropertyControl<>(twist, "Twist", changeConsumer);
        twistControl.setApplyHandler(BranchParameters::setTwist);
        twistControl.setSyncHandler(BranchParameters::getTwist);
        twistControl.setEditObject(parameters);
        twistControl.setScrollPower(0.3F);

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
    }

    private void buildProperties(@NotNull final LevelOfDetailParameters parameters, @NotNull final VBox container,
                                 @NotNull final ParametersChangeConsumer changeConsumer) {

        final ReductionType reduction = parameters.getReduction();

        final float distance = parameters.getDistance();
        final int branchDepth = parameters.getBranchDepth();
        final int maxRadialSegments = parameters.getMaxRadialSegments();
        final int rootDepth = parameters.getRootDepth();

        final PropertyControl<ParametersChangeConsumer, LevelOfDetailParameters, ReductionType> reductionControl =
                new EnumPropertyControl<>(reduction, "Reduction", changeConsumer);
        reductionControl.setApplyHandler(LevelOfDetailParameters::setReduction);
        reductionControl.setSyncHandler(LevelOfDetailParameters::getReduction);
        reductionControl.setEditObject(parameters);

        final FloatPropertyControl<ParametersChangeConsumer, LevelOfDetailParameters> distanceControl =
                new FloatPropertyControl<>(distance, "Distance", changeConsumer);
        distanceControl.setApplyHandler(LevelOfDetailParameters::setDistance);
        distanceControl.setSyncHandler(LevelOfDetailParameters::getDistance);
        distanceControl.setMinMax(0, Float.MAX_VALUE);
        distanceControl.setEditObject(parameters);

        final PropertyControl<ParametersChangeConsumer, LevelOfDetailParameters, Integer> branchDepthControl =
                new IntegerPropertyControl<>(branchDepth, "Branch depth", changeConsumer);
        branchDepthControl.setApplyHandler(LevelOfDetailParameters::setBranchDepth);
        branchDepthControl.setSyncHandler(LevelOfDetailParameters::getBranchDepth);
        branchDepthControl.setEditObject(parameters);

        final PropertyControl<ParametersChangeConsumer, LevelOfDetailParameters, Integer> maxRadialSegmentsControl =
                new IntegerPropertyControl<>(maxRadialSegments, "Max radial segments", changeConsumer);
        maxRadialSegmentsControl.setApplyHandler(LevelOfDetailParameters::setMaxRadialSegments);
        maxRadialSegmentsControl.setSyncHandler(LevelOfDetailParameters::getMaxRadialSegments);
        maxRadialSegmentsControl.setEditObject(parameters);

        final PropertyControl<ParametersChangeConsumer, LevelOfDetailParameters, Integer> rootDepthControl =
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

    private void buildProperties(@NotNull final TreeParameters treeParameters, @NotNull final VBox container,
                                 @NotNull final ParametersChangeConsumer changeConsumer) {

        final boolean generateLeaves = treeParameters.getGenerateLeaves();
        final float leafScale = treeParameters.getLeafScale();
        final float textureVScale = treeParameters.getTextureVScale();
        final float trunkHeight = treeParameters.getTrunkHeight();
        final float trunkRadius = treeParameters.getTrunkRadius();
        final float yOffset = treeParameters.getYOffset();
        final float baseScale = treeParameters.getBaseScale();
        final float flexHeight = treeParameters.getFlexHeight();
        final float branchFlexibility = treeParameters.getBranchFlexibility();
        final float trunkFlexibility = treeParameters.getTrunkFlexibility();
        final int seed = treeParameters.getSeed();
        final int textureURepeat = treeParameters.getTextureURepeat();

        final PropertyControl<ParametersChangeConsumer, TreeParameters, Boolean> generateLeavesControl =
                new BooleanPropertyControl<>(generateLeaves, "Generate Leaves", changeConsumer);
        generateLeavesControl.setApplyHandler(TreeParameters::setGenerateLeaves);
        generateLeavesControl.setSyncHandler(TreeParameters::getGenerateLeaves);
        generateLeavesControl.setEditObject(treeParameters);

        final FloatPropertyControl<ParametersChangeConsumer, TreeParameters> leafScaleControl =
                new FloatPropertyControl<>(leafScale, "Leaf scale", changeConsumer);
        leafScaleControl.setApplyHandler(TreeParameters::setLeafScale);
        leafScaleControl.setSyncHandler(TreeParameters::getLeafScale);
        leafScaleControl.setEditObject(treeParameters);
        leafScaleControl.setMinMax(0.01F, Integer.MAX_VALUE);
        leafScaleControl.setScrollPower(1F);

        final FloatPropertyControl<ParametersChangeConsumer, TreeParameters> textureVScaleControl =
                new FloatPropertyControl<>(textureVScale, "Texture V scale", changeConsumer);
        textureVScaleControl.setApplyHandler(TreeParameters::setTextureVScale);
        textureVScaleControl.setSyncHandler(TreeParameters::getTextureVScale);
        textureVScaleControl.setEditObject(treeParameters);
        textureVScaleControl.setMinMax(0.01F, Integer.MAX_VALUE);
        textureVScaleControl.setScrollPower(0.4F);

        final FloatPropertyControl<ParametersChangeConsumer, TreeParameters> trunkHeightControl =
                new FloatPropertyControl<>(trunkHeight, "Trunk height", changeConsumer);
        trunkHeightControl.setApplyHandler(TreeParameters::setTrunkHeight);
        trunkHeightControl.setSyncHandler(TreeParameters::getTrunkHeight);
        trunkHeightControl.setEditObject(treeParameters);
        trunkHeightControl.setMinMax(0.01F, Integer.MAX_VALUE);
        trunkHeightControl.setScrollPower(3F);

        final FloatPropertyControl<ParametersChangeConsumer, TreeParameters> trunkRadiusControl =
                new FloatPropertyControl<>(trunkRadius, "Trunk radius", changeConsumer);
        trunkRadiusControl.setApplyHandler(TreeParameters::setTrunkRadius);
        trunkRadiusControl.setSyncHandler(TreeParameters::getTrunkRadius);
        trunkRadiusControl.setEditObject(treeParameters);
        trunkRadiusControl.setMinMax(0.01F, Integer.MAX_VALUE);
        trunkRadiusControl.setScrollPower(0.5F);

        final PropertyControl<ParametersChangeConsumer, TreeParameters, Float> yOffsetControl =
                new FloatPropertyControl<>(yOffset, "Y offset", changeConsumer);
        yOffsetControl.setApplyHandler(TreeParameters::setYOffset);
        yOffsetControl.setSyncHandler(TreeParameters::getYOffset);
        yOffsetControl.setEditObject(treeParameters);

        final FloatPropertyControl<ParametersChangeConsumer, TreeParameters> baseScaleControl =
                new FloatPropertyControl<>(baseScale, "Base scale", changeConsumer);
        baseScaleControl.setApplyHandler(TreeParameters::setBaseScale);
        baseScaleControl.setSyncHandler(TreeParameters::getBaseScale);
        baseScaleControl.setEditObject(treeParameters);
        baseScaleControl.setMinMax(0.01F, Integer.MAX_VALUE);
        baseScaleControl.setScrollPower(0.5F);

        final FloatPropertyControl<ParametersChangeConsumer, TreeParameters> flexHeightControl =
                new FloatPropertyControl<>(flexHeight, "Flex height", changeConsumer);
        flexHeightControl.setApplyHandler(TreeParameters::setFlexHeight);
        flexHeightControl.setSyncHandler(TreeParameters::getFlexHeight);
        flexHeightControl.setEditObject(treeParameters);
        flexHeightControl.setMinMax(0.01F, Integer.MAX_VALUE);
        flexHeightControl.setScrollPower(2F);

        final FloatPropertyControl<ParametersChangeConsumer, TreeParameters> trunkFlexibilityControl =
                new FloatPropertyControl<>(trunkFlexibility, "Trunk flexibility", changeConsumer);
        trunkFlexibilityControl.setApplyHandler(TreeParameters::setTrunkFlexibility);
        trunkFlexibilityControl.setSyncHandler(TreeParameters::getTrunkFlexibility);
        trunkFlexibilityControl.setEditObject(treeParameters);
        trunkFlexibilityControl.setMinMax(0.01F, Integer.MAX_VALUE);
        trunkFlexibilityControl.setScrollPower(2F);

        final FloatPropertyControl<ParametersChangeConsumer, TreeParameters> branchFlexibilityControl =
                new FloatPropertyControl<>(branchFlexibility, "Branch flexibility", changeConsumer);
        branchFlexibilityControl.setApplyHandler(TreeParameters::setBranchFlexibility);
        branchFlexibilityControl.setSyncHandler(TreeParameters::getBranchFlexibility);
        branchFlexibilityControl.setEditObject(treeParameters);
        branchFlexibilityControl.setMinMax(0.01F, Integer.MAX_VALUE);
        branchFlexibilityControl.setScrollPower(2F);

        final PropertyControl<ParametersChangeConsumer, TreeParameters, Integer> seedControl =
                new IntegerPropertyControl<>(seed, "Seed", changeConsumer);
        seedControl.setApplyHandler(TreeParameters::setSeed);
        seedControl.setSyncHandler(TreeParameters::getSeed);
        seedControl.setEditObject(treeParameters);

        final IntegerPropertyControl<ParametersChangeConsumer, TreeParameters> textureURepeatControl =
                new IntegerPropertyControl<>(textureURepeat, "Texture U repeat", changeConsumer);
        textureURepeatControl.setApplyHandler(TreeParameters::setTextureURepeat);
        textureURepeatControl.setSyncHandler(TreeParameters::getTextureURepeat);
        textureURepeatControl.setEditObject(treeParameters);

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
