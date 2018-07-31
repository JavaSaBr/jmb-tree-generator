package com.ss.editor.tree.generator.tree.action;

import static com.ss.rlib.common.util.ObjectUtils.notNull;
import com.simsilica.arboreal.BranchParameters;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.BranchesParameters;
import com.ss.editor.tree.generator.tree.operation.RemoveBranchOperation;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.action.AbstractNodeAction;
import com.ss.editor.ui.control.tree.node.TreeNode;
import org.jetbrains.annotations.NotNull;

/**
 * The action to delete a branch.
 *
 * @author JavaSaBr
 */
public class DeleteBranchAction extends AbstractNodeAction<ChangeConsumer> {

    public DeleteBranchAction(@NotNull NodeTree<?> nodeTree, @NotNull TreeNode<?> node) {
        super(nodeTree, node);
    }

    @Override
    @FxThread
    protected @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_ACTION_DELETE;
    }

    @Override
    @FxThread
    protected void process() {
        super.process();

        var node = getNode();
        var parentNode = notNull(node.getParent());
        var branchParameters = (BranchParameters) node.getElement();
        var branchesParameters = (BranchesParameters) parentNode.getElement();
        var treeParameters = branchesParameters.getTreeParameters();

        getNodeTree().requireChangeConsumer()
                .execute(new RemoveBranchOperation(treeParameters, branchesParameters, branchParameters));
    }
}
