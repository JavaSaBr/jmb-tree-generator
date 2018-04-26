package com.ss.editor.tree.generator.tree.node;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.tree.action.DeleteBranchAction;
import com.ss.editor.ui.control.tree.NodeTree;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation branch parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class BranchParametersTreeNode extends ParametersTreeNode<BranchParameters> {

    /**
     * The name.
     */
    @NotNull
    private String name;

    public BranchParametersTreeNode(@NotNull BranchParameters element, long objectId) {
        super(element, objectId);
        this.name = PluginMessages.TREE_GENERATOR_EDITOR_NODE_BRANCH;
    }

    @Override
    @FxThread
    public void fillContextMenu(@NotNull NodeTree<?> nodeTree, @NotNull ObservableList<MenuItem> items) {
        super.fillContextMenu(nodeTree, items);

        var branchParameters = getElement();
        var parent = (TreeParameters) branchParameters.getParent();
        var branches = parent.getBranches();

        if (branches[0] != branchParameters) {
            items.add(new DeleteBranchAction(nodeTree, this));
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
