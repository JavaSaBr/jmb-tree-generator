package com.ss.editor.tree.generator.tree.node;

import com.simsilica.arboreal.BranchParameters;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation branch parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class BranchParametersTreeNode extends ParametersTreeNode<BranchParameters> {

    @NotNull
    private String name;

    public BranchParametersTreeNode(@NotNull final BranchParameters element, final long objectId) {
        super(element, objectId);
        this.name = "Branch";
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
