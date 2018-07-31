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
 * The operation to add a new level of details.
 *
 * @author JavaSaBr
 */
public class AddLodOperation extends AbstractEditorOperation<ChangeConsumer> {

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
     * The new level of details.
     */
    @NotNull
    private final LevelOfDetailParameters newLevel;

    public AddLodOperation(
            @NotNull TreeParameters treeParameters,
            @NotNull LodsParameters lodsParameters,
            @NotNull LevelOfDetailParameters newLevel
    ) {
        this.treeParameters = treeParameters;
        this.lodsParameters = lodsParameters;
        this.newLevel = newLevel;
    }

    @Override
    @JmeThread
    protected void redoInJme(@NotNull ChangeConsumer editor) {
        super.redoInJme(editor);
        treeParameters.addLodLevel(newLevel, -1);
    }

    @Override
    @FxThread
    protected void endRedoInFx(@NotNull ChangeConsumer editor) {
        super.endRedoInFx(editor);
        editor.notifyFxAddedChild(lodsParameters, newLevel, -1, true);
    }

    @Override
    @JmeThread
    protected void undoInJme(@NotNull ChangeConsumer editor) {
        super.undoInJme(editor);
        treeParameters.removeLodLevel(newLevel);
    }

    @Override
    @FxThread
    protected void endUndoInFx(@NotNull ChangeConsumer editor) {
        super.endUndoInFx(editor);
        editor.notifyFxRemovedChild(lodsParameters, newLevel);
    }
}
