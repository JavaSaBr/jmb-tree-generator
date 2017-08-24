package com.ss.editor.tree.generator.tree.node;

import com.simsilica.arboreal.LevelOfDetailParameters;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of presentation level of details parameters node in the {@link ParametersTreeNode}
 *
 * @author JavaSaBr
 */
public class LodParametersTreeNode extends ParametersTreeNode<LevelOfDetailParameters> {

    @NotNull
    private String name;

    public LodParametersTreeNode(@NotNull final LevelOfDetailParameters element, final long objectId) {
        super(element, objectId);
        this.name = "Level of Details";
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
