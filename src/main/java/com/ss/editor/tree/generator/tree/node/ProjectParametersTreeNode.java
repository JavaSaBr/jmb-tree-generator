package com.ss.editor.tree.generator.tree.node;

import com.ss.editor.annotation.FxThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.common.util.array.Array;
import com.ss.rlib.common.util.array.ArrayFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation project parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class ProjectParametersTreeNode extends ParametersTreeNode<ProjectParameters> {

    public ProjectParametersTreeNode(@NotNull ProjectParameters element, long objectId) {
        super(element, objectId);
    }

    @Override
    @FromAnyThread
    public @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_NODE_SETTINGS;
    }

    @Override
    @FxThread
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull NodeTree<?> nodeTree) {

        var projectParameters = getElement();

        var children = ArrayFactory.<TreeNode<?>>newArray(TreeNode.class);
        children.add(FACTORY_REGISTRY.createFor(projectParameters.getMaterialParameters()));
        children.add(FACTORY_REGISTRY.createFor(projectParameters.getTreeParameters()));

        return children;
    }

    @Override
    @FxThread
    public boolean hasChildren(@NotNull NodeTree<?> nodeTree) {
        return true;
    }
}
