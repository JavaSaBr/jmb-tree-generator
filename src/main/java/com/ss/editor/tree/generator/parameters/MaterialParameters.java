package com.ss.editor.tree.generator.parameters;

import com.jme3.material.Material;
import com.simsilica.arboreal.Parameters;
import com.ss.editor.annotation.JmeThread;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The implementation of parameters to configure material of a tree.
 *
 * @author JavaSaBr
 */
public class MaterialParameters extends Parameters {

    /**
     * The material change handler.
     */
    @NotNull
    private final Consumer<@NotNull Material> changeHandler;

    /**
     * The material sync handler.
     */
    @NotNull
    private final Supplier<@NotNull Material> syncHandler;

    /**
     * The material name.
     */
    @NotNull
    private final String name;

    /**
     * The material.
     */
    @NotNull
    private Material material;

    public MaterialParameters(@NotNull final Consumer<@NotNull Material> changeHandler,
                              @NotNull final Supplier<@NotNull Material> syncHandler,
                              @NotNull final String name) {
        this.changeHandler = changeHandler;
        this.syncHandler = syncHandler;
        this.material = syncHandler.get();
        this.name = name;
    }

    /**
     * Get the material.
     *
     * @return the material.
     */
    @JmeThread
    public @NotNull Material getMaterial() {
        return material;
    }

    /**
     * Get the material.
     *
     * @param material the material.
     */
    @JmeThread
    public void setMaterial(@NotNull final Material material) {
        this.material = material;
    }

    /**
     * Get the material name.
     *
     * @return the material name.
     */
    @JmeThread
    public @NotNull String getName() {
        return name;
    }

    /**
     * Get the material change handler.
     *
     * @return the material change handler.
     */
    @JmeThread
    public @NotNull Consumer<@NotNull Material> getChangeHandler() {
        return changeHandler;
    }

    /**
     * Get the material sync handler.
     *
     * @return the material sync handler.
     */
    @JmeThread
    public @NotNull Supplier<@NotNull Material> getSyncHandler() {
        return syncHandler;
    }
}
