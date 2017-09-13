package com.ss.editor.tree.generator.parameters;

import com.jme3.material.Material;
import com.simsilica.arboreal.Parameters;
import com.ss.editor.annotation.JMEThread;
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
     * @return the material.
     */
    @JMEThread
    public @NotNull Material getMaterial() {
        return material;
    }

    /**
     * @param material the material.
     */
    @JMEThread
    public void setMaterial(@NotNull final Material material) {
        this.material = material;
    }

    /**
     * @return the material name.
     */
    @JMEThread
    public @NotNull String getName() {
        return name;
    }

    /**
     * @return the material change handler.
     */
    @JMEThread
    public @NotNull Consumer<@NotNull Material> getChangeHandler() {
        return changeHandler;
    }

    /**
     * @return the material sync handler.
     */
    @JMEThread
    public @NotNull Supplier<@NotNull Material> getSyncHandler() {
        return syncHandler;
    }
}
