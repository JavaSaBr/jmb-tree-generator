package com.ss.editor.tree.generator.tree.node;

import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.BranchesParameters;
import com.ss.editor.tree.generator.tree.action.CreateBranchAction;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.common.util.array.Array;
import com.ss.rlib.common.util.array.ArrayFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation branches parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class BranchesParametersTreeNode extends ParametersTreeNode<BranchesParameters> {

    public BranchesParametersTreeNode(@NotNull BranchesParameters element, long objectId) {
        super(element, objectId);
    }

    @Override
    @FromAnyThread
    public @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_NODE_BRANCHES;
    }

    @Override
    @FxThread
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull NodeTree<?> nodeTree) {

        var branchesParameters = getElement();
        var treeParameters = branchesParameters.getTreeParameters();

        var children = Array.<TreeNode<?>>ofType(TreeNode.class);

        var branches = treeParameters.getBranches();
        for (var branch : branches) {
            children.add(FACTORY_REGISTRY.createFor(branch));
        }

        for (var i = 0; i < children.size(); i++) {
            var node = (BranchParametersTreeNode) children.get(i);
            node.setName(PluginMessages.TREE_GENERATOR_EDITOR_NODE_BRANCH + " #" + i);
        }

        return children;
    }

    @Override
    @FxThread
    public void fillContextMenu(@NotNull NodeTree<?> nodeTree, @NotNull ObservableList<MenuItem> items) {
        super.fillContextMenu(nodeTree, items);
        items.add(new CreateBranchAction(nodeTree, this));
    }

    @Override
    @FxThread
    public boolean hasChildren(@NotNull NodeTree<?> nodeTree) {
        return true;
    }
}
