package com.ss.editor.tree.generator.tree.node;

import com.ss.editor.annotation.FxThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.parameters.MaterialParameters;
import com.ss.editor.tree.generator.parameters.MaterialsParameters;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.common.util.array.Array;
import com.ss.rlib.common.util.array.ArrayFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation materials parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class MaterialsParametersTreeNode extends ParametersTreeNode<MaterialsParameters> {

    public MaterialsParametersTreeNode(@NotNull MaterialsParameters element, long objectId) {
        super(element, objectId);
    }

    @Override
    @FromAnyThread
    public @NotNull String getName() {
        return PluginMessages.TREE_GENERATOR_EDITOR_NODE_MATERIALS;
    }

    @Override
    @FxThread
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull NodeTree<?> nodeTree) {

        var parameters = getElement();

        var treeMaterial = new MaterialParameters(parameters::setTreeMaterial, parameters::getTreeMaterial,
                PluginMessages.TREE_GENERATOR_EDITOR_NODE_MATERIAL_TREE);
        var leafMaterial = new MaterialParameters(parameters::setLeafMaterial, parameters::getLeafMaterial,
                PluginMessages.TREE_GENERATOR_EDITOR_NODE_MATERIAL_LEAF);
        var flatMaterial = new MaterialParameters(parameters::setFlatMaterial, parameters::getFlatMaterial,
                PluginMessages.TREE_GENERATOR_EDITOR_NODE_MATERIAL_FLAT);
        var impostorMaterial = new MaterialParameters(parameters::setImpostorMaterial, parameters::getImpostorMaterial,
                PluginMessages.TREE_GENERATOR_EDITOR_NODE_MATERIAL_IMPOSTOR);

        var children = ArrayFactory.<TreeNode<?>>newArray(TreeNode.class);
        children.add(FACTORY_REGISTRY.createFor(treeMaterial));
        children.add(FACTORY_REGISTRY.createFor(leafMaterial));
        children.add(FACTORY_REGISTRY.createFor(flatMaterial));
        children.add(FACTORY_REGISTRY.createFor(impostorMaterial));

        return children;
    }

    @Override
    @FxThread
    public boolean hasChildren(@NotNull NodeTree<?> nodeTree) {
        return true;
    }
}
