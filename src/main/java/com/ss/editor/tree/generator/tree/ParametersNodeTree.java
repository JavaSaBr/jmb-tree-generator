package com.ss.editor.tree.generator.tree;

import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.NodeTreeCell;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * The implementation of {@link NodeTree} to show parameters structure.
 *
 * @author JavaSaBr
 */
public class ParametersNodeTree extends NodeTree<ChangeConsumer> {

    public ParametersNodeTree(@NotNull final Consumer<Object> selectionHandler,
                              @Nullable final ChangeConsumer consumer) {
        super(selectionHandler, consumer);
    }

    @Override
    protected @NotNull NodeTreeCell<ChangeConsumer, ?> createNodeTreeCell() {
        return new ParametersTreeCell(this);
    }
}
