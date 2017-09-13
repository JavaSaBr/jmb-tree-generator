package com.ss.editor.tree.generator.tree.node;

import com.ss.editor.annotation.FXThread;
import com.ss.editor.tree.generator.parameters.MaterialParameters;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation material parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class MaterialParametersTreeNode extends ParametersTreeNode<MaterialParameters> {

    public MaterialParametersTreeNode(@NotNull final MaterialParameters element, final long objectId) {
        super(element, objectId);
    }

    @Override
    @FXThread
    public @NotNull String getName() {
        return getElement().getName();
    }

    @Override
    @FXThread
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull final NodeTree<?> nodeTree) {
        final TreeNode<?> node = FACTORY_REGISTRY.createFor(getElement().getMaterial());
        return ArrayFactory.asArray(node);
    }

    @Override
    @FXThread
    public boolean hasChildren(@NotNull final NodeTree<?> nodeTree) {
        return true;
    }
}
