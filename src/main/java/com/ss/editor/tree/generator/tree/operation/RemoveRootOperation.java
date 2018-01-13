package com.ss.editor.tree.generator.tree.operation;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FxThread;
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

    public RemoveRootOperation(@NotNull final TreeParameters treeParameters,
                               @NotNull final RootsParameters rootsParameters,
                               @NotNull final BranchParameters removed) {
        this.treeParameters = treeParameters;
        this.rootsParameters = rootsParameters;
        this.removed = removed;
    }

    @Override
    @FxThread
    protected void redoImpl(@NotNull final ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJmeTask(() -> {
            index = treeParameters.removeRoot(removed);
            EXECUTOR_MANAGER.addFxTask(() -> editor.notifyFXRemovedChild(rootsParameters, removed));
        });
    }

    @Override
    @FxThread
    protected void undoImpl(@NotNull final ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJmeTask(() -> {
            treeParameters.addRoot(removed, index);
            EXECUTOR_MANAGER.addFxTask(() -> editor.notifyFXAddedChild(rootsParameters, removed, index, false));
        });
    }
}
