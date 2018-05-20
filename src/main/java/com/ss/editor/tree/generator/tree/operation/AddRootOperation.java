package com.ss.editor.tree.generator.tree.operation;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FxThread;
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
    @FxThread
    protected void redoImpl(@NotNull ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJmeTask(() -> {
            treeParameters.addRoot(newRoot, -1);
            EXECUTOR_MANAGER.addFxTask(() -> editor.notifyFxAddedChild(rootsParameters, newRoot, -1, true));
        });
    }

    @Override
    @FxThread
    protected void undoImpl(@NotNull ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJmeTask(() -> {
            treeParameters.removeRoot(newRoot);
            EXECUTOR_MANAGER.addFxTask(() -> editor.notifyFxRemovedChild(rootsParameters, newRoot));
        });
    }
}
