package com.ss.editor.tree.generator.tree.node;

import com.ss.editor.tree.generator.parameters.MaterialParameters;
import com.ss.editor.tree.generator.parameters.MaterialsParameters;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation materials parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class MaterialsParametersTreeNode extends ParametersTreeNode<MaterialsParameters> {

    public MaterialsParametersTreeNode(@NotNull final MaterialsParameters element, final long objectId) {
        super(element, objectId);
    }

    @Override
    public @NotNull String getName() {
        return "Materials";
    }

    @Override
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull final NodeTree<?> nodeTree) {

        final @NotNull MaterialsParameters materialsParameters = getElement();

        final Array<TreeNode<?>> children = ArrayFactory.newArray(TreeNode.class);
        children.add(FACTORY_REGISTRY.createFor(new MaterialParameters(materialsParameters.getTreeMaterial(), "Tree material")));
        children.add(FACTORY_REGISTRY.createFor(new MaterialParameters(materialsParameters.getLeafMaterial(), "Leaf material")));
        children.add(FACTORY_REGISTRY.createFor(new MaterialParameters(materialsParameters.getFlatMaterial(), "Flat material")));
        children.add(FACTORY_REGISTRY.createFor(new MaterialParameters(materialsParameters.getImpostorMaterial(), "Impostor material")));

        return children;
    }

    @Override
    public boolean hasChildren(@NotNull final NodeTree<?> nodeTree) {
        return true;
    }
}
