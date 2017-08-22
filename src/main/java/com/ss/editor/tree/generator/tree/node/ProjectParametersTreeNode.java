package com.ss.editor.tree.generator.tree.node;

import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation project parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class ProjectParametersTreeNode extends ParametersTreeNode<ProjectParameters> {

    public ProjectParametersTreeNode(@NotNull final ProjectParameters element, final long objectId) {
        super(element, objectId);
    }

    @Override
    public @NotNull String getName() {
        return "Main";
    }

    @Override
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull final NodeTree<?> nodeTree) {

        final ProjectParameters element = getElement();

        final Array<TreeNode<?>> children = ArrayFactory.newArray(TreeNode.class);
        children.add(FACTORY_REGISTRY.createFor(element.getMaterialParameters()));

        return children;
    }

    @Override
    public boolean hasChildren(@NotNull final NodeTree<?> nodeTree) {
        return true;
    }
}
