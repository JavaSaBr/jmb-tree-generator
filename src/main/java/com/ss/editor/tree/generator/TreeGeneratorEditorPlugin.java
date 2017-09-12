package com.ss.editor.tree.generator;

import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.manager.FileIconManager;
import com.ss.editor.plugin.EditorPlugin;
import com.ss.editor.tree.generator.creator.TreeGeneratorFileCreator;
import com.ss.editor.tree.generator.editor.TreeGeneratorFileEditor;
import com.ss.editor.tree.generator.property.ParametersPropertyBuilder;
import com.ss.editor.tree.generator.tree.factory.ParametersTreeNodeFactory;
import com.ss.editor.ui.component.creator.FileCreatorRegistry;
import com.ss.editor.ui.component.editor.EditorRegistry;
import com.ss.editor.ui.control.property.builder.PropertyBuilderRegistry;
import com.ss.editor.ui.control.tree.node.TreeNodeFactoryRegistry;
import com.ss.rlib.plugin.PluginContainer;
import com.ss.rlib.plugin.annotation.PluginDescription;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of an editor plugin.
 *
 * @author JavaSaBr
 */
@PluginDescription(
        id = "com.ss.editor.tree.generator",
        version = "1.0.1",
        minAppVersion = "1.2.0",
        name = "SimArboreal Tree Generator",
        description = "A plugin to generate trees using the library SimArboreal."
)
public class TreeGeneratorEditorPlugin extends EditorPlugin {

    @NotNull
    public static final String PROJECT_EXTENSION = "j3sa";

    public TreeGeneratorEditorPlugin(@NotNull final PluginContainer pluginContainer) {
        super(pluginContainer);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull final FileCreatorRegistry registry) {
        super.register(registry);
        registry.register(TreeGeneratorFileCreator.DESCRIPTION);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull final EditorRegistry registry) {
        super.register(registry);
        registry.register(TreeGeneratorFileEditor.DESCRIPTION);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull final TreeNodeFactoryRegistry registry) {
        super.register(registry);
        registry.register(ParametersTreeNodeFactory.getInstance());
    }

    @Override
    @FromAnyThread
    public void register(@NotNull final PropertyBuilderRegistry registry) {
        super.register(registry);
        registry.register(ParametersPropertyBuilder.getInstance());
    }

    @Override
    @FromAnyThread
    public void register(@NotNull final FileIconManager iconManager) {
        iconManager.register((path, extension) -> {

            if (PROJECT_EXTENSION.equals(extension)) {
                return "com/ss/editor/tree/generator/icons/tree.svg";
            }

            return null;
        });
    }
}
