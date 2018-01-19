package com.ss.editor.tree.generator.tree.node;

import com.ss.editor.annotation.FxThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.tree.generator.PluginMessages;
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
    @FromAnyThread
    public @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_NODE_MATERIALS;
    }

    @Override
    @FxThread
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull final NodeTree<?> nodeTree) {

        final MaterialsParameters parameters = getElement();

        final MaterialParameters treeMaterial =
                new MaterialParameters(parameters::setTreeMaterial, parameters::getTreeMaterial,
                        PluginMessages.TREE_GENERATOR_EDITOR_NODE_MATERIAL_TREE);

        final MaterialParameters leafMaterial =
                new MaterialParameters(parameters::setLeafMaterial, parameters::getLeafMaterial,
                        PluginMessages.TREE_GENERATOR_EDITOR_NODE_MATERIAL_LEAF);

        final MaterialParameters flatMaterial =
                new MaterialParameters(parameters::setFlatMaterial, parameters::getFlatMaterial,
                        PluginMessages.TREE_GENERATOR_EDITOR_NODE_MATERIAL_FLAT);

        final MaterialParameters impostorMaterial =
                new MaterialParameters(parameters::setImpostorMaterial, parameters::getImpostorMaterial,
                        PluginMessages.TREE_GENERATOR_EDITOR_NODE_MATERIAL_IMPOSTOR);

        final Array<TreeNode<?>> children = ArrayFactory.newArray(TreeNode.class);
        children.add(FACTORY_REGISTRY.createFor(treeMaterial));
        children.add(FACTORY_REGISTRY.createFor(leafMaterial));
        children.add(FACTORY_REGISTRY.createFor(flatMaterial));
        children.add(FACTORY_REGISTRY.createFor(impostorMaterial));

        return children;
    }

    @Override
    @FxThread
    public boolean hasChildren(@NotNull final NodeTree<?> nodeTree) {
        return true;
    }
}
