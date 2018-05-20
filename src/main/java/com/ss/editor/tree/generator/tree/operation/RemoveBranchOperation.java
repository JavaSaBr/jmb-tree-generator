package com.ss.editor.tree.generator.tree.operation;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.model.undo.impl.AbstractEditorOperation;
import com.ss.editor.tree.generator.parameters.BranchesParameters;
import org.jetbrains.annotations.NotNull;

/**
 * The operation to remove a branch.
 *
 * @author JavaSaBr
 */
public class RemoveBranchOperation extends AbstractEditorOperation<ChangeConsumer> {

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
     * The branch to remove.
     */
    @NotNull
    private final BranchParameters removed;

    /**
     * The prev index of the branch.
     */
    private int index;

    public RemoveBranchOperation(
            @NotNull TreeParameters treeParameters,
            @NotNull BranchesParameters branchesParameters,
            @NotNull BranchParameters removed
    ) {
        this.treeParameters = treeParameters;
        this.branchesParameters = branchesParameters;
        this.removed = removed;
    }

    @Override
    @FxThread
    protected void redoImpl(@NotNull ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJmeTask(() -> {
            index = treeParameters.removeBranch(removed);
            EXECUTOR_MANAGER.addFxTask(() -> editor.notifyFxRemovedChild(branchesParameters, removed));
        });
    }

    @Override
    @FxThread
    protected void undoImpl(@NotNull ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJmeTask(() -> {
            treeParameters.addBranch(removed, index);
            EXECUTOR_MANAGER.addFxTask(() -> editor.notifyFxAddedChild(branchesParameters, removed, index, false));
        });
    }
}
