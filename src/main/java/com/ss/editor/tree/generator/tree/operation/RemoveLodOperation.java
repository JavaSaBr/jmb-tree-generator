package com.ss.editor.tree.generator.tree.operation;

import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FxThread;
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
    @FxThread
    protected void redoImpl(@NotNull ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJmeTask(() -> {
            index = treeParameters.removeLodLevel(removed);
            EXECUTOR_MANAGER.addFxTask(() -> editor.notifyFxRemovedChild(lodsParameters, removed));
        });
    }

    @Override
    @FxThread
    protected void undoImpl(@NotNull ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJmeTask(() -> {
            treeParameters.addLodLevel(removed, index);
            EXECUTOR_MANAGER.addFxTask(() -> editor.notifyFxAddedChild(lodsParameters, removed, index, false));
        });
    }
}
