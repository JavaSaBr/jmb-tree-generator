package com.ss.editor.tree.generator.tree.operation;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FXThread;
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

    public AddRootOperation(@NotNull final TreeParameters treeParameters,
                            @NotNull final RootsParameters rootsParameters, @NotNull final BranchParameters newRoot) {
        this.treeParameters = treeParameters;
        this.rootsParameters = rootsParameters;
        this.newRoot = newRoot;
    }

    @Override
    @FXThread
    protected void redoImpl(@NotNull final ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJMETask(() -> {
            treeParameters.addRoot(newRoot, -1);
            EXECUTOR_MANAGER.addFXTask(() -> editor.notifyFXAddedChild(rootsParameters, newRoot, -1, true));
        });
    }

    @Override
    @FXThread
    protected void undoImpl(@NotNull final ChangeConsumer editor) {
        EXECUTOR_MANAGER.addJMETask(() -> {
            treeParameters.removeRoot(newRoot);
            EXECUTOR_MANAGER.addFXTask(() -> editor.notifyFXRemovedChild(rootsParameters, newRoot));
        });
    }
}
