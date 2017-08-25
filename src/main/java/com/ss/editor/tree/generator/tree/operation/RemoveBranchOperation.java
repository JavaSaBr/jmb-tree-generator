package com.ss.editor.tree.generator.tree.operation;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FXThread;
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

    @NotNull
    private final TreeParameters treeParameters;

    @NotNull
    private final BranchesParameters branchesParameters;

    @NotNull
    private final BranchParameters removed;

    /**
     * The prev index of the branch.
     */
    private int index;

    public RemoveBranchOperation(@NotNull final TreeParameters treeParameters,
                                 @NotNull final BranchesParameters branchesParameters,
                                 @NotNull final BranchParameters removed) {
        this.treeParameters = treeParameters;
        this.branchesParameters = branchesParameters;
        this.removed = removed;
    }

    @Override
    @FXThread
    protected void redoImpl(@NotNull final ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJMETask(() -> {
            index = treeParameters.removeBranch(removed);
            EXECUTOR_MANAGER.addFXTask(() -> editor.notifyFXRemovedChild(branchesParameters, removed));
        });
    }

    @Override
    @FXThread
    protected void undoImpl(@NotNull final ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJMETask(() -> {
            treeParameters.addBranch(removed, index);
            EXECUTOR_MANAGER.addFXTask(() -> editor.notifyFXAddedChild(branchesParameters, removed, index, false));
        });
    }
}
