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
import com.ss.editor.ui.control.tree.node.factory.TreeNodeFactoryRegistry;
import com.ss.rlib.common.plugin.PluginContainer;
import com.ss.rlib.common.plugin.annotation.PluginDescription;
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
        version = "1.2.2",
        minAppVersion = "1.8.0",
        name = "SimArboreal Tree Generator",
        description = "Provides a new editor to generate trees based on the library 'SimArboreal'."
)
public class TreeGeneratorEditorPlugin extends EditorPlugin {

    @NotNull
    public static final String PROJECT_EXTENSION = "j3sa";

    @NotNull
    private static final String GRADLE_DEPENDENCIES;

    @NotNull
    private static final String MAVEN_DEPENDENCIES;

    static {
        var loader = TreeGeneratorEditorPlugin.class;
        GRADLE_DEPENDENCIES = FileUtils.read(loader.getResourceAsStream("/com/ss/editor/tree/generator/dependency/gradle.html"));
        MAVEN_DEPENDENCIES = FileUtils.read(loader.getResourceAsStream("/com/ss/editor/tree/generator/dependency/maven.html"));
    }

    public TreeGeneratorEditorPlugin(@NotNull PluginContainer pluginContainer) {
        super(pluginContainer);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull FileCreatorRegistry registry) {
        super.register(registry);
        registry.register(TreeGeneratorFileCreator.DESCRIPTION);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull EditorRegistry registry) {
        super.register(registry);
        registry.register(TreeGeneratorFileEditor.DESCRIPTION);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull TreeNodeFactoryRegistry registry) {
        super.register(registry);
        registry.register(ParametersTreeNodeFactory.getInstance());
    }

    @Override
    @FromAnyThread
    public void register(@NotNull PropertyBuilderRegistry registry) {
        super.register(registry);
        registry.register(ParametersPropertyBuilder.getInstance());
    }

    @Override
    @FromAnyThread
    public void register(@NotNull FileIconManager iconManager) {
        iconManager.register((path, extension) -> {

            if (PROJECT_EXTENSION.equals(extension)) {
                return "com/ss/editor/tree/generator/icons/tree.svg";
            }

            return null;
        });
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
