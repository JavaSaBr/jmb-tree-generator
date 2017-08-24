package com.ss.editor.tree.generator.tree.node;

import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.TreeParameters;
import com.ss.editor.tree.generator.parameters.BranchesParameters;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation branches parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class BranchesParametersTreeNode extends ParametersTreeNode<BranchesParameters> {

    public BranchesParametersTreeNode(@NotNull final BranchesParameters element, final long objectId) {
        super(element, objectId);
    }

    @Override
    public @NotNull String getName() {
        return "Branches";
    }

    @Override
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull final NodeTree<?> nodeTree) {

        final BranchesParameters branchesParameters = getElement();
        final TreeParameters treeParameters = branchesParameters.getTreeParameters();

        final Array<TreeNode<?>> children = ArrayFactory.newArray(TreeNode.class);

        final BranchParameters[] branches = treeParameters.getBranches();
        for (final BranchParameters branch : branches) {
            children.add(FACTORY_REGISTRY.createFor(branch));
        }

        for (int i = 0; i < children.size(); i++) {
            final BranchParametersTreeNode node = (BranchParametersTreeNode) children.get(i);
            node.setName("Branch #" + i);
        }

        return children;
    }

    @Override
    public boolean hasChildren(@NotNull final NodeTree<?> nodeTree) {
        return true;
    }
}
