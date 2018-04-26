package com.ss.editor.tree.generator.tree.node;

import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.RootsParameters;
import com.ss.editor.tree.generator.tree.action.CreateRootAction;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.common.util.array.Array;
import com.ss.rlib.common.util.array.ArrayFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation roots parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class RootsParametersTreeNode extends ParametersTreeNode<RootsParameters> {

    public RootsParametersTreeNode(@NotNull RootsParameters element, long objectId) {
        super(element, objectId);
    }

    @Override
    @FromAnyThread
    public @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_NODE_ROOTS;
    }

    @Override
    @FxThread
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull NodeTree<?> nodeTree) {

        var rootsParameters = getElement();
        var treeParameters = rootsParameters.getTreeParameters();

        var children = ArrayFactory.<TreeNode<?>>newArray(TreeNode.class);

        var roots = treeParameters.getRoots();
        for (var root : roots) {
            children.add(FACTORY_REGISTRY.createFor(root));
        }

        for (var i = 0; i < children.size(); i++) {
            var node = (BranchParametersTreeNode) children.get(i);
            node.setName(PluginMessages.TREE_GENERATOR_EDITOR_NODE_ROOT + " #" + i);
        }

        return children;
    }

    @Override
    @FxThread
    public void fillContextMenu(@NotNull NodeTree<?> nodeTree, @NotNull ObservableList<MenuItem> items) {
        super.fillContextMenu(nodeTree, items);
        items.add(new CreateRootAction(nodeTree, this));
    }

    @Override
    @FxThread
    public boolean hasChildren(@NotNull NodeTree<?> nodeTree) {
        return true;
    }
}
