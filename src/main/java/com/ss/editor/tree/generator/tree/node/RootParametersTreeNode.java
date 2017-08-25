package com.ss.editor.tree.generator.tree.node;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
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

    @NotNull
    private String name;

    public RootParametersTreeNode(@NotNull final BranchParameters element, final long objectId) {
        super(element, objectId);
        this.name = "Root";
    }

    @Override
    public void fillContextMenu(@NotNull final NodeTree<?> nodeTree, @NotNull final ObservableList<MenuItem> items) {
        super.fillContextMenu(nodeTree, items);

        final BranchParameters branchParameters = getElement();
        final TreeParameters parent = (TreeParameters) branchParameters.getParent();
        final BranchParameters[] roots = parent.getRoots();

        if (roots[0] != branchParameters) {
            items.add(new DeleteRootAction(nodeTree, this));
        }
    }

    @Override
    public void setName(@NotNull final String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }
}
