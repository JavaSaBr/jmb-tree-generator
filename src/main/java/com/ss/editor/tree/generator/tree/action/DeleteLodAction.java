package com.ss.editor.tree.generator.tree.action;

import static com.ss.rlib.util.ObjectUtils.notNull;
import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FXThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.LodsParameters;
import com.ss.editor.tree.generator.tree.operation.RemoveLodOperation;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.action.AbstractNodeAction;
import com.ss.editor.ui.control.tree.node.TreeNode;
import org.jetbrains.annotations.NotNull;

/**
 * The action to delete a branch.
 *
 * @author JavaSaBr
 */
public class DeleteLodAction extends AbstractNodeAction<ChangeConsumer> {

    public DeleteLodAction(@NotNull final NodeTree<?> nodeTree, @NotNull final TreeNode<?> node) {
        super(nodeTree, node);
    }

    @Override
    protected @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_ACTION_DELETE;
    }

    @Override
    @FXThread
    protected void process() {
        super.process();

        final TreeNode<?> node = getNode();
        final TreeNode<?> parentNode = node.getParent();
        final LevelOfDetailParameters lodParameters = (LevelOfDetailParameters) node.getElement();
        final LodsParameters lodsParameters = (LodsParameters) parentNode.getElement();
        final TreeParameters treeParameters = lodsParameters.getTreeParameters();

        final NodeTree<ChangeConsumer> nodeTree = getNodeTree();
        final ChangeConsumer changeConsumer = notNull(nodeTree.getChangeConsumer());
        changeConsumer.execute(new RemoveLodOperation(treeParameters, lodsParameters, lodParameters));
    }
}
