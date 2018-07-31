package com.ss.editor.tree.generator.tree.operation;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.annotation.JmeThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.model.undo.impl.AbstractEditorOperation;
import com.ss.editor.tree.generator.parameters.RootsParameters;
import org.jetbrains.annotations.NotNull;

/**
 * The operation to remove a root.
 *
 * @author JavaSaBr
 */
public class RemoveRootOperation extends AbstractEditorOperation<ChangeConsumer> {

    /**
     * The tree parameters.
     */
    @NotNull
    private final TreeParameters treeParameters;

    /**
     * The roots parameters.
     */
    @NotNull
    private final RootsParameters rootsParameters;

    /**
     * The root to remove.
     */
    @NotNull
    private final BranchParameters removed;

    /**
     * The prev index of the branch.
     */
    private int index;

    public RemoveRootOperation(
            @NotNull TreeParameters treeParameters,
            @NotNull RootsParameters rootsParameters,
            @NotNull BranchParameters removed
    ) {
        this.treeParameters = treeParameters;
        this.rootsParameters = rootsParameters;
        this.removed = removed;
    }

    @Override
    @JmeThread
    protected void redoInJme(@NotNull ChangeConsumer editor) {
        super.redoInJme(editor);
        index = treeParameters.removeRoot(removed);
    }

    @Override
    @FxThread
    protected void endRedoInFx(@NotNull ChangeConsumer editor) {
        super.endRedoInFx(editor);
        editor.notifyFxRemovedChild(rootsParameters, removed);
    }

    @Override
    @JmeThread
    protected void undoInJme(@NotNull ChangeConsumer editor) {
        super.undoInJme(editor);
        treeParameters.addRoot(removed, index);
    }

    @Override
    @FxThread
    protected void endUndoInFx(@NotNull ChangeConsumer editor) {
        super.endUndoInFx(editor);
        editor.notifyFxAddedChild(rootsParameters, removed, index, false);
    }
}
