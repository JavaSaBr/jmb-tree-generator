package com.ss.editor.tree.generator.tree.action;

import static com.ss.rlib.util.ObjectUtils.notNull;
import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.LodsParameters;
import com.ss.editor.tree.generator.tree.operation.AddLodOperation;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.action.AbstractNodeAction;
import com.ss.editor.ui.control.tree.node.TreeNode;
import org.jetbrains.annotations.NotNull;

/**
 * The action to delete a branch.
 *
 * @author JavaSaBr
 */
public class CreateLodAction extends AbstractNodeAction<ChangeConsumer> {

    public CreateLodAction(@NotNull final NodeTree<?> nodeTree, @NotNull final TreeNode<?> node) {
        super(nodeTree, node);
    }

    @Override
    @FxThread
    protected @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_ACTION_ADD_LOD;
    }

    @Override
    @FxThread
    protected void process() {
        super.process();

        final TreeNode<?> node = getNode();
        final LodsParameters lodsParameters = (LodsParameters) node.getElement();
        final TreeParameters treeParameters = lodsParameters.getTreeParameters();

        final LevelOfDetailParameters newLod = new LevelOfDetailParameters();
        newLod.setParent(treeParameters);

        final NodeTree<ChangeConsumer> nodeTree = getNodeTree();
        final ChangeConsumer changeConsumer = notNull(nodeTree.getChangeConsumer());
        changeConsumer.execute(new AddLodOperation(treeParameters, lodsParameters, newLod));
    }
}
