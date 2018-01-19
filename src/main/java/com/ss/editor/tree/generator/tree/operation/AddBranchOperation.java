package com.ss.editor.tree.generator.tree.operation;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.model.undo.impl.AbstractEditorOperation;
import com.ss.editor.tree.generator.parameters.BranchesParameters;
import org.jetbrains.annotations.NotNull;

/**
 * The operation to add a new branch.
 *
 * @author JavaSaBr
 */
public class AddBranchOperation extends AbstractEditorOperation<ChangeConsumer> {

    /**
     * The tree parameters.
     */
    @NotNull
    private final TreeParameters treeParameters;

    /**
     * The branches parameters.
     */
    @NotNull
    private final BranchesParameters branchesParameters;

    /**
     * The new branch.
     */
    @NotNull
    private final BranchParameters newBranch;

    public AddBranchOperation(@NotNull final TreeParameters treeParameters,
                              @NotNull final BranchesParameters branchesParameters,
                              @NotNull final BranchParameters newBranch) {
        this.treeParameters = treeParameters;
        this.branchesParameters = branchesParameters;
        this.newBranch = newBranch;
    }

    @Override
    @FxThread
    protected void redoImpl(@NotNull final ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJmeTask(() -> {
            treeParameters.addBranch(newBranch, -1);
            EXECUTOR_MANAGER.addFxTask(() -> editor.notifyFxAddedChild(branchesParameters, newBranch, -1, true));
        });
    }

    @Override
    @FxThread
    protected void undoImpl(@NotNull final ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJmeTask(() -> {
            treeParameters.removeBranch(newBranch);
            EXECUTOR_MANAGER.addFxTask(() -> editor.notifyFxRemovedChild(branchesParameters, newBranch));
        });
    }
}
