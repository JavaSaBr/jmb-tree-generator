package com.ss.editor.tree.generator.parameters;

import com.simsilica.arboreal.Parameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.JmeThread;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of parameters to configure branches of a tree.
 *
 * @author JavaSaBr
 */
public class BranchesParameters extends Parameters {

    /**
     * The tree parameters.
     */
    @NotNull
    private final TreeParameters treeParameters;

    public BranchesParameters(@NotNull final TreeParameters treeParameters) {
        this.treeParameters = treeParameters;
    }

    /**
     * @return the tree parameters.
     */
    @JmeThread
    public @NotNull TreeParameters getTreeParameters() {
        return treeParameters;
    }
}