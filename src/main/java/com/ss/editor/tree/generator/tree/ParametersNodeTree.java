package com.ss.editor.tree.generator.tree;

import com.ss.editor.tree.generator.editor.ParametersChangeConsumer;
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
public class ParametersNodeTree extends NodeTree<ParametersChangeConsumer> {

    public ParametersNodeTree(@NotNull final Consumer<Object> selectionHandler,
                              @Nullable final ParametersChangeConsumer consumer) {
        super(selectionHandler, consumer);
    }

    @Override
    protected @NotNull NodeTreeCell<ParametersChangeConsumer, ?> createNodeTreeCell() {
        return new ParametersTreeCell(this);
    }
}
