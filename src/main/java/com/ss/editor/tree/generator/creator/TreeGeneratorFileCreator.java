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
import com.ss.editor.ui.component.creator.FileCreatorDescription;
import com.ss.rlib.util.VarTable;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
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

    @NotNull
    private static final String PROP_PBR = "pbr";

    static {
        DESCRIPTION.setFileDescription(PluginMessages.TREE_GENERATOR_CREATOR_DESCRIPTION);
        DESCRIPTION.setConstructor(TreeGeneratorFileCreator::new);
    }

    @Override
    @FromAnyThread
    protected @NotNull Array<PropertyDefinition> getPropertyDefinitions() {

        final Array<PropertyDefinition> definitions = ArrayFactory.newArray(PropertyDefinition.class);
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
    protected void writeData(@NotNull final VarTable vars, @NotNull final Path resultFile) throws IOException {
        super.writeData(vars, resultFile);

        final boolean pbr = vars.getBoolean(PROP_PBR);
        final MaterialsParameters materialsParameters = new MaterialsParameters();

        if (pbr) {
            materialsParameters.loadPBR();
        } else {
            materialsParameters.loadDefault();
        }

        final ProjectParameters parameters = new ProjectParameters(EDITOR.getAssetManager());
        parameters.setMaterialParameters(materialsParameters);

        final BinaryExporter exporter = BinaryExporter.getInstance();

        try (final OutputStream out = Files.newOutputStream(resultFile)) {
            exporter.save(parameters, out);
        }
    }
}
