package com.ss.editor.tree.generator.tree.node;

import static com.ss.rlib.common.util.ObjectUtils.notNull;
import com.simsilica.arboreal.LevelOfDetailParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.tree.action.DeleteLodAction;
import com.ss.editor.ui.control.tree.NodeTree;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation level of details parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class LodParametersTreeNode extends ParametersTreeNode<LevelOfDetailParameters> {

    /**
     * The name.
     */
    @NotNull
    private String name;

    public LodParametersTreeNode(@NotNull LevelOfDetailParameters element, long objectId) {
        super(element, objectId);
        this.name = PluginMessages.TREE_GENERATOR_EDITOR_NODE_LOD;
    }

    @Override
    @FxThread
    public void fillContextMenu(@NotNull NodeTree<?> nodeTree, @NotNull ObservableList<MenuItem> items) {
        super.fillContextMenu(nodeTree, items);

        var levelOfDetailParameters = getElement();
        var parent = notNull((TreeParameters) levelOfDetailParameters.getParent());
        var lods = parent.getLods();

        if (lods.get(0) != levelOfDetailParameters) {
            items.add(new DeleteLodAction(nodeTree, this));
        }
    }

    @Override
    @FxThread
    public void setName(@NotNull final String name) {
        this.name = name;
    }

    @Override
    @FromAnyThread
    public @NotNull String getName() {
        return name;
    }
}
