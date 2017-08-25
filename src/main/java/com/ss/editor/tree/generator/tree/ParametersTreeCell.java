package com.ss.editor.tree.generator.tree;

import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.ui.control.tree.NodeTreeCell;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of a cell for {@link ParametersNodeTree}.
 *
 * @author JavaSaBr
 */
public class ParametersTreeCell extends NodeTreeCell<ChangeConsumer, ParametersNodeTree> {

    public ParametersTreeCell(@NotNull final ParametersNodeTree nodeTree) {
        super(nodeTree);
    }
}
