package com.ss.editor.tree.generator.parameters;

import com.jme3.asset.AssetManager;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.util.clone.Cloner;
import com.simsilica.arboreal.Parameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.JmeThread;
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

    public ProjectParameters(@NotNull AssetManager assetManager) {
        this.treeParameters = new TreeParameters();
        this.treeParameters.setParent(this);
        this.treeParameters.setGenerateLeaves(true);

        for (var branchParameters : treeParameters.getBranches()) {
            branchParameters.setParent(treeParameters);
        }

        for (var branchParameters : treeParameters.getRoots()) {
            branchParameters.setParent(treeParameters);
        }

        for (var detailParameters : treeParameters.getLods()) {
            detailParameters.setParent(treeParameters);
        }
    }

    /**
     * Set material parameters.
     *
     * @param materialParameters the material parameters.
     */
    @JmeThread
    public void setMaterialParameters(@NotNull MaterialsParameters materialParameters) {
        this.materialParameters = materialParameters;
        this.materialParameters.setParent(this);
    }

    /**
     * Get the tree parameters.
     *
     * @return the tree parameters.
     */
    @JmeThread
    public @NotNull TreeParameters getTreeParameters() {
        return treeParameters;
    }

    /**
     * Get the material parameters.
     *
     * @return the material parameters.
     */
    @JmeThread
    public @NotNull MaterialsParameters getMaterialParameters() {
        return materialParameters;
    }

    /**
     * @return true if need to show wire.
     */
    @JmeThread
    public boolean isShowWire() {
        return showWire;
    }

    /**
     * @param showWire true if need to show wire.
     */
    @JmeThread
    public void setShowWire(boolean showWire) {
        this.showWire = showWire;
    }

    @Override
    @JmeThread
    public void cloneFields(@NotNull Cloner cloner, @NotNull Object original) {
        materialParameters = cloner.clone(materialParameters);
        treeParameters = cloner.clone(treeParameters);
    }

    @Override
    @JmeThread
    public void write(@NotNull final JmeExporter ex) throws IOException {
        var out = ex.getCapsule(this);
        out.write(treeParameters, "treeParameters", null);
        out.write(materialParameters, "materialsParameters", null);
        out.write(showWire, "showWire", false);
    }

    @Override
    @JmeThread
    public void read(@NotNull final JmeImporter im) throws IOException {
        var in = im.getCapsule(this);
        materialParameters = (MaterialsParameters) in.readSavable("materialsParameters", null);
        treeParameters = (TreeParameters) in.readSavable("treeParameters", null);
        showWire = in.readBoolean("showWire", false);
    }
}
