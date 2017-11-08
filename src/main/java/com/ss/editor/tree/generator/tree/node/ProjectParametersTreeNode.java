package com.ss.editor.tree.generator.tree.node;

import com.ss.editor.annotation.FXThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.tree.generator.PluginMessages;
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
    @FromAnyThread
    public @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_NODE_SETTINGS;
    }

    @Override
    @FXThread
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull final NodeTree<?> nodeTree) {

        final ProjectParameters element = getElement();

        final Array<TreeNode<?>> children = ArrayFactory.newArray(TreeNode.class);
        children.add(FACTORY_REGISTRY.createFor(element.getMaterialParameters()));
        children.add(FACTORY_REGISTRY.createFor(element.getTreeParameters()));

        return children;
    }

    @Override
    @FXThread
    public boolean hasChildren(@NotNull final NodeTree<?> nodeTree) {
        return true;
    }
}
