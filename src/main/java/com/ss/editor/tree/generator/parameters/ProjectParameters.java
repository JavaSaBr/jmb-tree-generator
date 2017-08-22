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
    private MaterialParameters materialParameters;

    public ProjectParameters() {
    }

    public ProjectParameters(@NotNull final AssetManager assetManager) {
        this.materialParameters = new MaterialParameters(assetManager);
        this.materialParameters.setParent(this);
        this.treeParameters = new TreeParameters();
        this.treeParameters.setParent(this);
        this.treeParameters.setGenerateLeaves(true);

        for (final BranchParameters branchParameters : treeParameters.getEffectiveBranches()) {
            branchParameters.setParent(treeParameters);
        }

        for (final BranchParameters branchParameters : treeParameters.getEffectiveRoots()) {
            branchParameters.setParent(treeParameters);
        }

        for (final LevelOfDetailParameters detailParameters : treeParameters.getLods()) {
            detailParameters.setParent(treeParameters);
        }
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
    public @NotNull MaterialParameters getMaterialParameters() {
        return materialParameters;
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
        out.write(materialParameters, "materialParameters", null);
    }

    @Override
    public void read(@NotNull final JmeImporter im) throws IOException {
        final InputCapsule in = im.getCapsule(this);
        materialParameters = (MaterialParameters) in.readSavable("materialParameters", null);
        treeParameters = (TreeParameters) in.readSavable("treeParameters", null);
    }
}
