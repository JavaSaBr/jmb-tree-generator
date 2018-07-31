package com.ss.editor.tree.generator.tree.operation;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.annotation.JmeThread;
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
    @JmeThread
    protected void redoInJme(@NotNull ChangeConsumer editor) {
        super.redoInJme(editor);
        index = treeParameters.removeBranch(removed);
    }

    @Override
    @FxThread
    protected void endRedoInFx(@NotNull ChangeConsumer editor) {
        super.endRedoInFx(editor);
        editor.notifyFxRemovedChild(branchesParameters, removed);
    }

    @Override
    @JmeThread
    protected void undoInJme(@NotNull ChangeConsumer editor) {
        super.undoInJme(editor);
        treeParameters.addBranch(removed, index);
    }

    @Override
    @FxThread
    protected void endUndoInFx(@NotNull ChangeConsumer editor) {
        super.endUndoInFx(editor);
        editor.notifyFxAddedChild(branchesParameters, removed, index, false);
    }
}
