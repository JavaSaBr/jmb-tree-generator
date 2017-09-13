package com.ss.editor.tree.generator.tree.node;

import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FXThread;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.BranchesParameters;
import com.ss.editor.tree.generator.parameters.LodsParameters;
import com.ss.editor.tree.generator.parameters.RootsParameters;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation tree parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class TreeParametersTreeNode extends ParametersTreeNode<TreeParameters> {

    public TreeParametersTreeNode(@NotNull final TreeParameters element, final long objectId) {
        super(element, objectId);
    }

    @Override
    @FXThread
    public @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_NODE_TREE;
    }

    @Override
    @FXThread
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull final NodeTree<?> nodeTree) {

        final TreeParameters element = getElement();

        final Array<TreeNode<?>> children = ArrayFactory.newArray(TreeNode.class);
        children.add(FACTORY_REGISTRY.createFor(new LodsParameters(element)));
        children.add(FACTORY_REGISTRY.createFor(new BranchesParameters(element)));
        children.add(FACTORY_REGISTRY.createFor(new RootsParameters(element)));

        return children;
    }

    @Override
    @FXThread
    public boolean hasChildren(@NotNull final NodeTree<?> nodeTree) {
        return true;
    }
}
