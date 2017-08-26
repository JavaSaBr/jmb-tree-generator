package com.ss.editor.tree.generator.tree.operation;

import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FXThread;
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

    public AddLodOperation(@NotNull final TreeParameters treeParameters, @NotNull final LodsParameters lodsParameters,
                           @NotNull final LevelOfDetailParameters newLevel) {
        this.treeParameters = treeParameters;
        this.lodsParameters = lodsParameters;
        this.newLevel = newLevel;
    }

    @Override
    @FXThread
    protected void redoImpl(@NotNull final ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJMETask(() -> {
            treeParameters.addLod(newLevel, -1);
            EXECUTOR_MANAGER.addFXTask(() -> editor.notifyFXAddedChild(lodsParameters, newLevel, -1, true));
        });
    }

    @Override
    @FXThread
    protected void undoImpl(@NotNull final ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJMETask(() -> {
            treeParameters.removeLodLevel(newLevel);
            EXECUTOR_MANAGER.addFXTask(() -> editor.notifyFXRemovedChild(lodsParameters, newLevel));
        });
    }
}
