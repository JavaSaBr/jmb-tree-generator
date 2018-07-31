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

    public AddBranchOperation(
            @NotNull TreeParameters treeParameters,
            @NotNull BranchesParameters branchesParameters,
            @NotNull BranchParameters newBranch
    ) {
        this.treeParameters = treeParameters;
        this.branchesParameters = branchesParameters;
        this.newBranch = newBranch;
    }

    @Override
    @JmeThread
    protected void redoInJme(@NotNull ChangeConsumer editor) {
        super.redoInJme(editor);
        treeParameters.addBranch(newBranch, -1);
    }

    @Override
    @FxThread
    protected void endRedoInFx(@NotNull ChangeConsumer editor) {
        super.endRedoInFx(editor);
        editor.notifyFxAddedChild(branchesParameters, newBranch, -1, true);
    }

    @Override
    @JmeThread
    protected void undoInJme(@NotNull ChangeConsumer editor) {
        super.undoInJme(editor);
        treeParameters.removeBranch(newBranch);
    }

    @Override
    @FxThread
    protected void endUndoInFx(@NotNull ChangeConsumer editor) {
        super.endUndoInFx(editor);
        editor.notifyFxRemovedChild(branchesParameters, newBranch);
    }
}
