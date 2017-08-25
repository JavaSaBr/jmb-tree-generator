package com.ss.editor.tree.generator.tree.operation;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FXThread;
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

    @NotNull
    private final TreeParameters treeParameters;

    @NotNull
    private final BranchesParameters branchesParameters;

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
    @FXThread
    protected void redoImpl(@NotNull final ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJMETask(() -> {
            treeParameters.addBranch(newBranch, -1);
            EXECUTOR_MANAGER.addFXTask(() -> editor.notifyFXAddedChild(branchesParameters, newBranch, -1, true));
        });
    }

    @Override
    @FXThread
    protected void undoImpl(@NotNull final ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJMETask(() -> {
            treeParameters.removeBranch(newBranch);
            EXECUTOR_MANAGER.addFXTask(() -> editor.notifyFXRemovedChild(branchesParameters, newBranch));
        });
    }
}
