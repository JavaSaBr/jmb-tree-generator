package com.ss.editor.tree.generator;

import com.ss.editor.annotation.BackgroundThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.manager.FileIconManager;
import com.ss.editor.manager.FileIconManager.IconFinder;
import com.ss.editor.plugin.EditorPlugin;
import com.ss.editor.tree.generator.creator.TreeGeneratorFileCreator;
import com.ss.editor.tree.generator.editor.TreeGeneratorFileEditor;
import com.ss.editor.tree.generator.property.ParametersPropertyBuilder;
import com.ss.editor.tree.generator.tree.factory.ParametersTreeNodeFactory;
import com.ss.editor.ui.component.creator.FileCreatorRegistry;
import com.ss.editor.ui.component.editor.EditorRegistry;
import com.ss.editor.ui.control.property.builder.PropertyBuilderRegistry;
import com.ss.editor.ui.control.tree.node.factory.TreeNodeFactoryRegistry;
import com.ss.rlib.common.plugin.PluginContainer;
import com.ss.rlib.common.plugin.annotation.PluginDescription;
import com.ss.rlib.common.plugin.extension.ExtensionPointManager;
import com.ss.rlib.common.util.FileUtils;
import com.ss.rlib.common.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

/**
 * The implementation of an editor plugin.
 *
 * @author JavaSaBr
 */
@PluginDescription(
        id = "com.ss.editor.tree.generator",
        version = "1.2.3",
        minAppVersion = "1.9.0",
        name = "SimArboreal Tree Generator",
        description = "Provides a new editor to generate trees based on the library 'SimArboreal'."
)
public class TreeGeneratorEditorPlugin extends EditorPlugin {

    private static final Class<?> CLASS = TreeGeneratorEditorPlugin.class;

    public static final String PROJECT_EXTENSION = "j3sa";

    private static final String GRADLE_DEPENDENCIES =
            FileUtils.readFromClasspath(CLASS, "/com/ss/editor/tree/generator/dependency/gradle.html");

    private static final String MAVEN_DEPENDENCIES =
            FileUtils.readFromClasspath(CLASS, "/com/ss/editor/tree/generator/dependency/maven.html");

    public TreeGeneratorEditorPlugin(@NotNull PluginContainer pluginContainer) {
        super(pluginContainer);
    }

    @Override
    @BackgroundThread
    public void register(@NotNull ExtensionPointManager manager) {
        super.register(manager);

        manager.getExtensionPoint(PropertyBuilderRegistry.EP_BUILDERS)
                .register(ParametersPropertyBuilder.getInstance());
        manager.getExtensionPoint(TreeNodeFactoryRegistry.EP_FACTORIES)
                .register(ParametersTreeNodeFactory.getInstance());
        manager.getExtensionPoint(FileCreatorRegistry.EP_DESCRIPTORS)
                .register(TreeGeneratorFileCreator.DESCRIPTOR);
        manager.getExtensionPoint(EditorRegistry.EP_DESCRIPTORS)
                .register(TreeGeneratorFileEditor.DESCRIPTOR);
        manager.getExtensionPoint(FileIconManager.EP_ICON_FINDERS)
                .register(makeIconFinder());
    }

    @FromAnyThread
    private @NotNull IconFinder makeIconFinder() {
        return (path, extension) -> {

            if (PROJECT_EXTENSION.equals(extension)) {
                return "com/ss/editor/tree/generator/icons/tree.svg";
            }

            return null;
        };
    }

    @Override
    @FromAnyThread
    public @Nullable URL getHomePageUrl() {
        return Utils.get("https://github.com/JavaSaBr/jmb-tree-generator", URL::new);
    }

    @Override
    @FromAnyThread
    public @Nullable String getUsedGradleDependencies() {
        return GRADLE_DEPENDENCIES;
    }

    @Override
    @FromAnyThread
    public @Nullable String getUsedMavenDependencies() {
        return MAVEN_DEPENDENCIES;
    }
}
