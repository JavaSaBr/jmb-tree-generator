package com.ss.editor.tree.generator.tree.node;

import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.tree.generator.parameters.LodsParameters;
import com.ss.editor.tree.generator.tree.action.CreateLodAction;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The implementation of presentation levels of details parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class LodsParametersTreeNode extends ParametersTreeNode<LodsParameters> {

    public LodsParametersTreeNode(@NotNull final LodsParameters element, final long objectId) {
        super(element, objectId);
    }

    @Override
    public @NotNull String getName() {
        return "Levels of Details";
    }

    @Override
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull final NodeTree<?> nodeTree) {

        final LodsParameters lodsParameters = getElement();
        final TreeParameters treeParameters = lodsParameters.getTreeParameters();

        final Array<TreeNode<?>> children = ArrayFactory.newArray(TreeNode.class);

        final List<LevelOfDetailParameters> lods = treeParameters.getLods();
        lods.forEach(lod -> children.add(FACTORY_REGISTRY.createFor(lod)));

        for (int i = 0; i < children.size(); i++) {
            final LodParametersTreeNode node = (LodParametersTreeNode) children.get(i);
            node.setName("Level #" + i);
        }

        return children;
    }

    @Override
    public void fillContextMenu(@NotNull final NodeTree<?> nodeTree, @NotNull final ObservableList<MenuItem> items) {
        super.fillContextMenu(nodeTree, items);
        items.add(new CreateLodAction(nodeTree, this));
    }

    @Override
    public boolean hasChildren(@NotNull final NodeTree<?> nodeTree) {
        return true;
    }
}
