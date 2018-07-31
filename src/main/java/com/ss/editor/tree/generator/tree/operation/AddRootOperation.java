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
 * The operation to add a new root.
 *
 * @author JavaSaBr
 */
public class AddRootOperation extends AbstractEditorOperation<ChangeConsumer> {

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
     * The new root.
     */
    @NotNull
    private final BranchParameters newRoot;

    public AddRootOperation(
            @NotNull TreeParameters treeParameters,
            @NotNull RootsParameters rootsParameters,
            @NotNull BranchParameters newRoot
    ) {
        this.treeParameters = treeParameters;
        this.rootsParameters = rootsParameters;
        this.newRoot = newRoot;
    }

    @Override
    @JmeThread
    protected void redoInJme(@NotNull ChangeConsumer editor) {
        super.redoInJme(editor);
        treeParameters.addRoot(newRoot, -1);
    }

    @Override
    @FxThread
    protected void endRedoInFx(@NotNull ChangeConsumer editor) {
        super.endRedoInFx(editor);
        editor.notifyFxAddedChild(rootsParameters, newRoot, -1, true);
    }

    @Override
    @JmeThread
    protected void undoInJme(@NotNull ChangeConsumer editor) {
        super.undoInJme(editor);
        treeParameters.removeRoot(newRoot);
    }

    @Override
    @FxThread
    protected void endUndoInFx(@NotNull ChangeConsumer editor) {
        super.endUndoInFx(editor);
        editor.notifyFxRemovedChild(rootsParameters, newRoot);
    }
}
