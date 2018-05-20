package com.ss.editor.tree.generator.tree.node;

import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.BranchesParameters;
import com.ss.editor.tree.generator.parameters.LodsParameters;
import com.ss.editor.tree.generator.parameters.RootsParameters;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.common.util.array.Array;
import com.ss.rlib.common.util.array.ArrayFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation tree parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class TreeParametersTreeNode extends ParametersTreeNode<TreeParameters> {

    public TreeParametersTreeNode(@NotNull TreeParameters element, long objectId) {
        super(element, objectId);
    }

    @Override
    @FromAnyThread
    public @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_NODE_TREE;
    }

    @Override
    @FxThread
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull NodeTree<?> nodeTree) {

        var treeParameters = getElement();

        var children = ArrayFactory.<TreeNode<?>>newArray(TreeNode.class);
        children.add(FACTORY_REGISTRY.createFor(new LodsParameters(treeParameters)));
        children.add(FACTORY_REGISTRY.createFor(new BranchesParameters(treeParameters)));
        children.add(FACTORY_REGISTRY.createFor(new RootsParameters(treeParameters)));

        return children;
    }

    @Override
    @FxThread
    public boolean hasChildren(@NotNull NodeTree<?> nodeTree) {
        return true;
    }
}
