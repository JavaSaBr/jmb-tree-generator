package com.ss.editor.tree.generator.editor;

import static com.ss.rlib.util.ObjectUtils.notNull;
import com.jme3.export.binary.BinaryImporter;
import com.ss.editor.annotation.FXThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.plugin.api.editor.Advanced3DFileEditorWithSplitRightTool;
import com.ss.editor.tree.generator.TreeGeneratorEditorPlugin;
import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.editor.tree.generator.tree.ParametersNodeTree;
import com.ss.editor.ui.component.editor.EditorDescription;
import com.ss.editor.ui.component.editor.state.EditorState;
import com.ss.editor.ui.component.tab.EditorToolComponent;
import com.ss.editor.ui.css.CSSClasses;
import com.ss.rlib.ui.util.FXUtils;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The implementation of an editor to configure and generate Trees.
 *
 * @author JavaSaBr
 */
public class TreeGeneratorFileEditor extends
        Advanced3DFileEditorWithSplitRightTool<TreeGeneratorEditor3DState, TreeGeneratorEditorState> implements
        ParametersChangeConsumer {

    /**
     * The constant DESCRIPTION.
     */
    @NotNull
    public static final EditorDescription DESCRIPTION = new EditorDescription();

    @Nullable
    private ParametersNodeTree parametersTree;

    static {
        DESCRIPTION.setConstructor(TreeGeneratorFileEditor::new);
        DESCRIPTION.setEditorName("Tree Generator");
        DESCRIPTION.setEditorId(TreeGeneratorFileEditor.class.getSimpleName());
        DESCRIPTION.addExtension(TreeGeneratorEditorPlugin.PROJECT_EXTENSION);
    }

    @Nullable
    private ProjectParameters parameters;

    @Nullable
    private Consumer<Object> selectionHandler;

    @Override
    @FXThread
    protected @NotNull TreeGeneratorEditor3DState create3DEditorState() {
        return new TreeGeneratorEditor3DState(this);
    }

    @Override
    protected @Nullable Supplier<EditorState> getEditorStateFactory() {
        return TreeGeneratorEditorState::new;
    }

    @Override
    @FromAnyThread
    public @NotNull EditorDescription getDescription() {
        return DESCRIPTION;
    }

    @Override
    protected void createToolComponents(@NotNull final EditorToolComponent container, @NotNull final StackPane root) {
        super.createToolComponents(container, root);

        selectionHandler = this::selectFromTree;
        parametersTree = new ParametersNodeTree(selectionHandler, this);

        container.addComponent(buildSplitComponent(parametersTree, new Pane(), root), "Tree");

        FXUtils.addClassTo(parametersTree.getTreeView(), CSSClasses.TRANSPARENT_TREE_VIEW);
    }

    private void selectFromTree(final Object object) {
    }

    @Override
    @FXThread
    protected void doOpenFile(@NotNull final Path file) {
        super.doOpenFile(file);

        final BinaryImporter importer = BinaryImporter.getInstance();
        importer.setAssetManager(EDITOR.getAssetManager());

        try (final InputStream in = Files.newInputStream(file)) {
            parameters = (ProjectParameters) importer.load(in);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        getParametersTree().fill(parameters);
        getEditor3DState().open(parameters);
    }

    @Override
    @FXThread
    protected boolean needToolbar() {
        return true;
    }

    @FXThread
    private @NotNull ParametersNodeTree getParametersTree() {
        return notNull(parametersTree);
    }

    private @NotNull ProjectParameters getParameters() {
        return notNull(parameters);
    }
}
