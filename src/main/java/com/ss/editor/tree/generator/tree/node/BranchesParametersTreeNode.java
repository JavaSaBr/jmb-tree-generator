package com.ss.editor.tree.generator.tree.node;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FXThread;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.BranchesParameters;
import com.ss.editor.tree.generator.tree.action.CreateBranchAction;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation branches parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class BranchesParametersTreeNode extends ParametersTreeNode<BranchesParameters> {

    public BranchesParametersTreeNode(@NotNull final BranchesParameters element, final long objectId) {
        super(element, objectId);
    }

    @Override
    @FXThread
    public @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_NODE_BRANCHES;
    }

    @Override
    @FXThread
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull final NodeTree<?> nodeTree) {

        final BranchesParameters branchesParameters = getElement();
        final TreeParameters treeParameters = branchesParameters.getTreeParameters();

        final Array<TreeNode<?>> children = ArrayFactory.newArray(TreeNode.class);

        final BranchParameters[] branches = treeParameters.getBranches();
        for (final BranchParameters branch : branches) {
            children.add(FACTORY_REGISTRY.createFor(branch));
        }

        for (int i = 0; i < children.size(); i++) {
            final BranchParametersTreeNode node = (BranchParametersTreeNode) children.get(i);
            node.setName(PluginMessages.TREE_GENERATOR_EDITOR_NODE_BRANCH + " #" + i);
        }

        return children;
    }

    @Override
    @FXThread
    public void fillContextMenu(@NotNull final NodeTree<?> nodeTree, @NotNull final ObservableList<MenuItem> items) {
        super.fillContextMenu(nodeTree, items);
        items.add(new CreateBranchAction(nodeTree, this));
    }

    @Override
    @FXThread
    public boolean hasChildren(@NotNull final NodeTree<?> nodeTree) {
        return true;
    }
}
