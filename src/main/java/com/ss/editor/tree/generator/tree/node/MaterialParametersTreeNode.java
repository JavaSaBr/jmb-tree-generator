package com.ss.editor.tree.generator.tree.node;

import com.ss.editor.annotation.FxThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.tree.generator.parameters.MaterialParameters;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.common.util.ObjectUtils;
import com.ss.rlib.common.util.array.Array;
import com.ss.rlib.common.util.array.ArrayFactory;
import org.jetbrains.annotations.NotNull;

import static com.ss.rlib.common.util.ObjectUtils.notNull;

/**
 * The implementation of presentation material parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class MaterialParametersTreeNode extends ParametersTreeNode<MaterialParameters> {

    public MaterialParametersTreeNode(@NotNull MaterialParameters element, long objectId) {
        super(element, objectId);
    }

    @Override
    @FromAnyThread
    public @NotNull String getName() {
        return getElement().getName();
    }

    @Override
    @FxThread
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull NodeTree<?> nodeTree) {
        return Array.of(notNull(FACTORY_REGISTRY.createFor(getElement().getMaterial())));
    }

    @Override
    @FxThread
    public boolean hasChildren(@NotNull NodeTree<?> nodeTree) {
        return true;
    }
}
