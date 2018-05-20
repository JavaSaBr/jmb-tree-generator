package com.ss.editor.tree.generator.tree.action;

import static com.ss.rlib.common.util.ObjectUtils.notNull;
import com.simsilica.arboreal.BranchParameters;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.RootsParameters;
import com.ss.editor.tree.generator.tree.operation.RemoveRootOperation;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.action.AbstractNodeAction;
import com.ss.editor.ui.control.tree.node.TreeNode;
import org.jetbrains.annotations.NotNull;

/**
 * The action to delete a branch.
 *
 * @author JavaSaBr
 */
public class DeleteRootAction extends AbstractNodeAction<ChangeConsumer> {

    public DeleteRootAction(@NotNull NodeTree<?> nodeTree, @NotNull TreeNode<?> node) {
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
        var root = (BranchParameters) node.getElement();
        var rootsParameters = (RootsParameters) parentNode.getElement();
        var treeParameters = rootsParameters.getTreeParameters();

        notNull(getNodeTree().getChangeConsumer())
                .execute(new RemoveRootOperation(treeParameters, rootsParameters, root));
    }
}
