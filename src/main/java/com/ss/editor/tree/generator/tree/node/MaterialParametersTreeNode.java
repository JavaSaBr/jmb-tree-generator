package com.ss.editor.tree.generator.tree.node;

import com.ss.editor.tree.generator.parameters.MaterialParameters;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation material parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class MaterialParametersTreeNode extends ParametersTreeNode<MaterialParameters> {

    public MaterialParametersTreeNode(@NotNull final MaterialParameters element, final long objectId) {
        super(element, objectId);
    }

    @Override
    public @NotNull String getName() {
        return "Materials";
    }

    @Override
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull final NodeTree<?> nodeTree) {

        final @NotNull MaterialParameters element = getElement();

        final Array<TreeNode<?>> children = ArrayFactory.newArray(TreeNode.class);
        children.add(FACTORY_REGISTRY.createFor(element.getTreeMaterial()));
        children.add(FACTORY_REGISTRY.createFor(element.getLeafMaterial()));
        children.add(FACTORY_REGISTRY.createFor(element.getFlatMaterial()));
        children.add(FACTORY_REGISTRY.createFor(element.getImpostorMaterial()));

        return children;
    }

    @Override
    public boolean hasChildren(@NotNull final NodeTree<?> nodeTree) {
        return true;
    }
}
