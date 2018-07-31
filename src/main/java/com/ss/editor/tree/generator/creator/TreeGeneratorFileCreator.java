package com.ss.editor.tree.generator.creator;

import static com.ss.editor.extension.property.EditablePropertyType.BOOLEAN;
import com.jme3.export.binary.BinaryExporter;
import com.ss.editor.annotation.BackgroundThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.plugin.api.file.creator.GenericFileCreator;
import com.ss.editor.plugin.api.property.PropertyDefinition;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.TreeGeneratorEditorPlugin;
import com.ss.editor.tree.generator.parameters.MaterialsParameters;
import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.editor.ui.component.creator.FileCreatorDescriptor;
import com.ss.editor.util.EditorUtil;
import com.ss.rlib.common.util.VarTable;
import com.ss.rlib.common.util.array.Array;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The creator to create a file of a tree generator project.
 *
 * @author JavaSaBr
 */
public class TreeGeneratorFileCreator extends GenericFileCreator {

    public static final FileCreatorDescriptor DESCRIPTOR = new FileCreatorDescriptor(
            PluginMessages.TREE_GENERATOR_CREATOR_DESCRIPTION,
            TreeGeneratorFileCreator::new
    );

    private static final String PROP_PBR = "pbr";

    @Override
    @FromAnyThread
    protected @NotNull Array<PropertyDefinition> getPropertyDefinitions() {

        var definitions = Array.ofType(PropertyDefinition.class);
        definitions.add(new PropertyDefinition(BOOLEAN, "PBR", PROP_PBR, true));

        return definitions;
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
    protected void writeData(@NotNull VarTable vars, @NotNull Path resultFile) throws IOException {
        super.writeData(vars, resultFile);

        var pbr = vars.getBoolean(PROP_PBR);
        var materialsParameters = new MaterialsParameters();

        if (pbr) {
            materialsParameters.loadPBR();
        } else {
            materialsParameters.loadDefault();
        }

        var parameters = new ProjectParameters(EditorUtil.getAssetManager());
        parameters.setMaterialParameters(materialsParameters);

        var exporter = BinaryExporter.getInstance();

        try (var out = Files.newOutputStream(resultFile)) {
            exporter.save(parameters, out);
        }
    }
}
