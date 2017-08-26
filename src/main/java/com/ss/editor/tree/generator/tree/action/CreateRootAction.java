package com.ss.editor.tree.generator.tree.action;

import static com.ss.rlib.util.ObjectUtils.notNull;
import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FXThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.RootsParameters;
import com.ss.editor.tree.generator.tree.operation.AddRootOperation;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.action.AbstractNodeAction;
import com.ss.editor.ui.control.tree.node.TreeNode;
import org.jetbrains.annotations.NotNull;

/**
 * The action to delete a branch.
 *
 * @author JavaSaBr
 */
public class CreateRootAction extends AbstractNodeAction<ChangeConsumer> {

    public CreateRootAction(@NotNull final NodeTree<?> nodeTree, @NotNull final TreeNode<?> node) {
        super(nodeTree, node);
    }

    @Override
    protected @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_ACTION_ADD_ROOT;
    }

    @Override
    @FXThread
    protected void process() {
        super.process();

        final TreeNode<?> node = getNode();
        final RootsParameters rootsParameters = (RootsParameters) node.getElement();
        final TreeParameters treeParameters = rootsParameters.getTreeParameters();

        final BranchParameters newRoot = new BranchParameters();
        newRoot.setParent(treeParameters);

        final NodeTree<ChangeConsumer> nodeTree = getNodeTree();
        final ChangeConsumer changeConsumer = notNull(nodeTree.getChangeConsumer());
        changeConsumer.execute(new AddRootOperation(treeParameters, rootsParameters, newRoot));
    }
}
