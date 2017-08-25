package com.ss.editor.tree.generator.parameters;

import com.jme3.material.Material;
import com.simsilica.arboreal.Parameters;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of parameters to configure material of a tree.
 *
 * @author JavaSaBr
 */
public class MaterialParameters extends Parameters {

    /**
     * The material.
     */
    @NotNull
    private final Material material;

    /**
     * The material name.
     */
    @NotNull
    private final String name;

    public MaterialParameters(@NotNull final Material material, @NotNull final String name) {
        this.material = material;
        this.name = name;
    }

    /**
     * @return the material.
     */
    public @NotNull Material getMaterial() {
        return material;
    }

    /**
     * @return the material name.
     */
    public @NotNull String getName() {
        return name;
    }
}
