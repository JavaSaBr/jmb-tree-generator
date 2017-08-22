package com.ss.editor.tree.generator.tree.node;

import com.simsilica.arboreal.Parameters;
import com.ss.editor.ui.Icons;
import com.ss.editor.ui.control.tree.node.TreeNode;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The base implementation of presentation parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class ParametersTreeNode<T extends Parameters> extends TreeNode<T> {

    public ParametersTreeNode(@NotNull final T element, final long objectId) {
        super(element, objectId);
    }

    @Override
    public @Nullable Image getIcon() {
        return Icons.INFLUENCER_16;
    }
}
