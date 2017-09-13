package com.ss.editor.tree.generator.tree.action;

import static com.ss.rlib.util.ObjectUtils.notNull;
import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FXThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.BranchesParameters;
import com.ss.editor.tree.generator.tree.operation.AddBranchOperation;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.action.AbstractNodeAction;
import com.ss.editor.ui.control.tree.node.TreeNode;
import org.jetbrains.annotations.NotNull;

/**
 * The action to delete a branch.
 *
 * @author JavaSaBr
 */
public class CreateBranchAction extends AbstractNodeAction<ChangeConsumer> {

    public CreateBranchAction(@NotNull final NodeTree<?> nodeTree, @NotNull final TreeNode<?> node) {
        super(nodeTree, node);
    }

    @Override
    @FXThread
    protected @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_ACTION_ADD_BRANCH;
    }

    @Override
    @FXThread
    protected void process() {
        super.process();

        final TreeNode<?> node = getNode();
        final BranchesParameters branchesParameters = (BranchesParameters) node.getElement();
        final TreeParameters treeParameters = branchesParameters.getTreeParameters();

        final BranchParameters newBranch = new BranchParameters();
        newBranch.setParent(treeParameters);

        final NodeTree<ChangeConsumer> nodeTree = getNodeTree();
        final ChangeConsumer changeConsumer = notNull(nodeTree.getChangeConsumer());
        changeConsumer.execute(new AddBranchOperation(treeParameters, branchesParameters, newBranch));
    }
}
