package com.ss.editor.tree.generator.tree.operation;

import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.annotation.JmeThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.model.undo.impl.AbstractEditorOperation;
import com.ss.editor.tree.generator.parameters.LodsParameters;
import org.jetbrains.annotations.NotNull;

/**
 * The operation to remove a level of details
 *
 * @author JavaSaBr
 */
public class RemoveLodOperation extends AbstractEditorOperation<ChangeConsumer> {

    /**
     * The tree parameters.
     */
    @NotNull
    private final TreeParameters treeParameters;

    /**
     * The lods parameters.
     */
    @NotNull
    private final LodsParameters lodsParameters;

    /**
     * The level of details to remove.
     */
    @NotNull
    private final LevelOfDetailParameters removed;

    /**
     * The prev index of the branch.
     */
    private int index;

    public RemoveLodOperation(
            @NotNull TreeParameters treeParameters,
            @NotNull LodsParameters lodsParameters,
            @NotNull LevelOfDetailParameters removed
    ) {
        this.treeParameters = treeParameters;
        this.lodsParameters = lodsParameters;
        this.removed = removed;
    }

    @Override
    @JmeThread
    protected void redoInJme(@NotNull ChangeConsumer editor) {
        super.redoInJme(editor);
        index = treeParameters.removeLodLevel(removed);
    }

    @Override
    @FxThread
    protected void endRedoInFx(@NotNull ChangeConsumer editor) {
        super.endRedoInFx(editor);
        editor.notifyFxRemovedChild(lodsParameters, removed);
    }

    @Override
    @JmeThread
    protected void undoInJme(@NotNull ChangeConsumer editor) {
        super.undoInJme(editor);
        treeParameters.addLodLevel(removed, index);
    }

    @Override
    @FxThread
    protected void endUndoInFx(@NotNull ChangeConsumer editor) {
        super.endUndoInFx(editor);
        editor.notifyFxAddedChild(lodsParameters, removed, index, false);
    }
}
