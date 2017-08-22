package com.ss.editor.tree.generator.tree.factory;

import static com.ss.rlib.util.ClassUtils.unsafeCast;
import com.ss.editor.tree.generator.parameters.MaterialParameters;
import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.editor.tree.generator.tree.node.MaterialParametersTreeNode;
import com.ss.editor.tree.generator.tree.node.ProjectParametersTreeNode;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.editor.ui.control.tree.node.TreeNodeFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The implementation of node factory to create parameters nodes.
 *
 * @author JavaSaBr
 */
public class ParametersTreeNodeFactory implements TreeNodeFactory {

    @NotNull
    private static final TreeNodeFactory INSTANCE = new ParametersTreeNodeFactory();

    public static @NotNull TreeNodeFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public <T, V extends TreeNode<T>> @Nullable V createFor(@Nullable final T element, final long objectId) {

        if (element instanceof ProjectParameters) {
            return unsafeCast(new ProjectParametersTreeNode((ProjectParameters) element, objectId));
        } else if (element instanceof MaterialParameters) {
            return unsafeCast(new MaterialParametersTreeNode((MaterialParameters) element, objectId));
        }

        return null;
    }
}
