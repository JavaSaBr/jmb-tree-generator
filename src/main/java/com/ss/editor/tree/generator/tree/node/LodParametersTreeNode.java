package com.ss.editor.tree.generator.tree.node;

import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FXThread;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.tree.action.DeleteLodAction;
import com.ss.editor.ui.control.tree.NodeTree;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The implementation of presentation level of details parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class LodParametersTreeNode extends ParametersTreeNode<LevelOfDetailParameters> {

    @NotNull
    private String name;

    public LodParametersTreeNode(@NotNull final LevelOfDetailParameters element, final long objectId) {
        super(element, objectId);
        this.name = PluginMessages.TREE_GENERATOR_EDITOR_NODE_LOD;
    }

    @Override
    @FXThread
    public void fillContextMenu(@NotNull final NodeTree<?> nodeTree, @NotNull final ObservableList<MenuItem> items) {
        super.fillContextMenu(nodeTree, items);

        final LevelOfDetailParameters levelOfDetailParameters = getElement();
        final TreeParameters parent = (TreeParameters) levelOfDetailParameters.getParent();
        final List<LevelOfDetailParameters> lods = parent.getLods();

        if (lods.get(0) != levelOfDetailParameters) {
            items.add(new DeleteLodAction(nodeTree, this));
        }
    }

    @Override
    @FXThread
    public void setName(@NotNull final String name) {
        this.name = name;
    }

    @Override
    @FXThread
    public @NotNull String getName() {
        return name;
    }
}
