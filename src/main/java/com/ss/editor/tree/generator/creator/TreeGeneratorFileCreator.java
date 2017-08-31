package com.ss.editor.tree.generator.creator;

import com.jme3.export.binary.BinaryExporter;
import com.ss.editor.annotation.BackgroundThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.plugin.api.file.creator.GenericFileCreator;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.TreeGeneratorEditorPlugin;
import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.editor.ui.component.creator.FileCreatorDescription;
import com.ss.rlib.util.VarTable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The creator to create a file of a tree generator project.
 *
 * @author JavaSaBr
 */
public class TreeGeneratorFileCreator extends GenericFileCreator {

    /**
     * The constant DESCRIPTION.
     */
    @NotNull
    public static final FileCreatorDescription DESCRIPTION = new FileCreatorDescription();

    static {
        DESCRIPTION.setFileDescription(PluginMessages.TREE_GENERATOR_CREATOR_DESCRIPTION);
        DESCRIPTION.setConstructor(TreeGeneratorFileCreator::new);
    }

    @Override
    @FromAnyThread
    protected @NotNull String getTitleText() {
        return PluginMessages.TREE_GENERATOR_CREATOR_TITLE;
    }

    @Override
    @FromAnyThread
    protected @NotNull String getFileExtension() {
        return TreeGeneratorEditorPlugin.PROJECT_EXTENSION;
    }

    @Override
    @BackgroundThread
    protected void writeData(@NotNull final VarTable vars, @NotNull final Path resultFile) throws IOException {
        super.writeData(vars, resultFile);

        final BinaryExporter exporter = BinaryExporter.getInstance();

        try (final OutputStream out = Files.newOutputStream(resultFile)) {
            exporter.save(new ProjectParameters(EDITOR.getAssetManager()), out);
        }
    }
}
