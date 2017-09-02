package com.ss.editor.tree.generator.parameters;

import com.jme3.asset.AssetManager;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.util.clone.Cloner;
import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.Parameters;
import com.simsilica.arboreal.TreeParameters;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * The object to present a project of tree generator.
 *
 * @author JavaSaBr
 */
public class ProjectParameters extends Parameters {

    /**
     * The tree parameters.
     */
    @NotNull
    private TreeParameters treeParameters;

    /**
     * The material parameters.
     */
    @NotNull
    private MaterialsParameters materialParameters;

    /**
     * The flag of showing wire.
     */
    private boolean showWire;

    public ProjectParameters() {
    }

    public ProjectParameters(@NotNull final AssetManager assetManager) {
        this.treeParameters = new TreeParameters();
        this.treeParameters.setParent(this);
        this.treeParameters.setGenerateLeaves(true);

        for (final BranchParameters branchParameters : treeParameters.getBranches()) {
            branchParameters.setParent(treeParameters);
        }

        for (final BranchParameters branchParameters : treeParameters.getRoots()) {
            branchParameters.setParent(treeParameters);
        }

        for (final LevelOfDetailParameters detailParameters : treeParameters.getLods()) {
            detailParameters.setParent(treeParameters);
        }
    }

    /**
     * Set material parameters.
     *
     * @param materialParameters the material parameters.
     */
    public void setMaterialParameters(@NotNull final MaterialsParameters materialParameters) {
        this.materialParameters = materialParameters;
        this.materialParameters.setParent(this);
    }

    /**
     * @return the tree parameters.
     */
    public @NotNull TreeParameters getTreeParameters() {
        return treeParameters;
    }

    /**
     * @return the material parameters.
     */
    public @NotNull MaterialsParameters getMaterialParameters() {
        return materialParameters;
    }

    /**
     * @return true if need to show wire.
     */
    public boolean isShowWire() {
        return showWire;
    }

    /**
     * @param showWire true if need to show wire.
     */
    public void setShowWire(final boolean showWire) {
        this.showWire = showWire;
    }

    @Override
    public void cloneFields(@NotNull final Cloner cloner, @NotNull final Object original) {
        materialParameters = cloner.clone(materialParameters);
        treeParameters = cloner.clone(treeParameters);
    }

    @Override
    public void write(@NotNull final JmeExporter ex) throws IOException {
        final OutputCapsule out = ex.getCapsule(this);
        out.write(treeParameters, "treeParameters", null);
        out.write(materialParameters, "materialsParameters", null);
        out.write(showWire, "showWire", false);
    }

    @Override
    public void read(@NotNull final JmeImporter im) throws IOException {
        final InputCapsule in = im.getCapsule(this);
        materialParameters = (MaterialsParameters) in.readSavable("materialsParameters", null);
        treeParameters = (TreeParameters) in.readSavable("treeParameters", null);
        showWire = in.readBoolean("showWire", false);
    }
}
