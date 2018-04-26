package com.ss.editor.tree.generator.tree.node;

import static com.ss.rlib.common.util.ObjectUtils.notNull;
import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.tree.action.DeleteRootAction;
import com.ss.editor.ui.control.tree.NodeTree;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation root parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class RootParametersTreeNode extends ParametersTreeNode<BranchParameters> {

    /**
     * The name.
     */
    @NotNull
    private String name;

    public RootParametersTreeNode(@NotNull BranchParameters element, long objectId) {
        super(element, objectId);
        this.name = PluginMessages.TREE_GENERATOR_EDITOR_NODE_ROOT;
    }

    @Override
    @FxThread
    public void fillContextMenu(@NotNull NodeTree<?> nodeTree, @NotNull ObservableList<MenuItem> items) {
        super.fillContextMenu(nodeTree, items);

        var branchParameters = getElement();
        var parent = notNull((TreeParameters) branchParameters.getParent());
        var roots = parent.getRoots();

        if (roots[0] != branchParameters) {
            items.add(new DeleteRootAction(nodeTree, this));
        }
    }

    @Override
    @FxThread
    public void setName(@NotNull String name) {
        this.name = name;
    }

    @Override
    @FromAnyThread
    public @NotNull String getName() {
        return name;
    }
}
