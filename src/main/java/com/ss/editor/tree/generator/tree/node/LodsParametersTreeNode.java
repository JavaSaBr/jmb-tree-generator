package com.ss.editor.tree.generator.tree.node;

import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.LodsParameters;
import com.ss.editor.tree.generator.tree.action.CreateLodAction;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.common.util.array.Array;
import com.ss.rlib.common.util.array.ArrayFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation levels of details parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class LodsParametersTreeNode extends ParametersTreeNode<LodsParameters> {

    public LodsParametersTreeNode(@NotNull LodsParameters element, long objectId) {
        super(element, objectId);
    }

    @Override
    @FromAnyThread
    public @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_NODE_LODS;
    }

    @Override
    @FxThread
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull NodeTree<?> nodeTree) {

        var lodsParameters = getElement();
        var treeParameters = lodsParameters.getTreeParameters();

        var children = ArrayFactory.<TreeNode<?>>newArray(TreeNode.class);

        var lods = treeParameters.getLods();
        lods.forEach(lod -> children.add(FACTORY_REGISTRY.createFor(lod)));

        for (int i = 0; i < children.size(); i++) {
            var node = (LodParametersTreeNode) children.get(i);
            node.setName(PluginMessages.TREE_GENERATOR_EDITOR_NODE_LOD + " #" + i);
        }

        return children;
    }

    @Override
    @FxThread
    public void fillContextMenu(@NotNull NodeTree<?> nodeTree, @NotNull ObservableList<MenuItem> items) {
        super.fillContextMenu(nodeTree, items);
        items.add(new CreateLodAction(nodeTree, this));
    }

    @Override
    @FxThread
    public boolean hasChildren(@NotNull NodeTree<?> nodeTree) {
        return true;
    }
}
