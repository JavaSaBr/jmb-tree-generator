package com.ss.editor.tree.generator.editor;

import static com.ss.rlib.util.ObjectUtils.notNull;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.export.binary.BinaryImporter;
import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.LevelOfDetailParameters;
import com.ss.editor.FileExtensions;
import com.ss.editor.Messages;
import com.ss.editor.annotation.BackgroundThread;
import com.ss.editor.annotation.FXThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.plugin.api.editor.Advanced3DFileEditorWithSplitRightTool;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.TreeGeneratorEditorPlugin;
import com.ss.editor.tree.generator.parameters.MaterialParameters;
import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.editor.tree.generator.property.ParametersPropertyEditor;
import com.ss.editor.ui.Icons;
import com.ss.editor.ui.component.asset.tree.context.menu.action.DeleteFileAction;
import com.ss.editor.ui.component.asset.tree.context.menu.action.NewFileAction;
import com.ss.editor.ui.component.asset.tree.context.menu.action.RenameFileAction;
import com.ss.editor.ui.component.editor.EditorDescription;
import com.ss.editor.ui.component.editor.state.EditorState;
import com.ss.editor.ui.component.tab.EditorToolComponent;
import com.ss.editor.ui.control.property.PropertyEditor;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.editor.ui.css.CSSClasses;
import com.ss.editor.ui.util.DynamicIconSupport;
import com.ss.editor.ui.util.UIUtils;
import com.ss.editor.util.EditorUtil;
import com.ss.rlib.ui.util.FXUtils;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * The implementation of an editor to configure and generate Trees.
 *
 * @author JavaSaBr
 */
public class TreeGeneratorFileEditor extends
        Advanced3DFileEditorWithSplitRightTool<TreeGeneratorEditor3DState, TreeGeneratorEditorState> implements
        ChangeConsumer {

    @NotNull
    private static final Predicate<Class<?>> ACTION_TESTER = type -> type == NewFileAction.class ||
            type == DeleteFileAction.class || type == RenameFileAction.class;

    /**
     * The constant DESCRIPTION.
     */
    @NotNull
    public static final EditorDescription DESCRIPTION = new EditorDescription();

    static {
        DESCRIPTION.setConstructor(TreeGeneratorFileEditor::new);
        DESCRIPTION.setEditorName(PluginMessages.TREE_GENERATOR_EDITOR_NAME);
        DESCRIPTION.setEditorId(TreeGeneratorFileEditor.class.getSimpleName());
        DESCRIPTION.addExtension(TreeGeneratorEditorPlugin.PROJECT_EXTENSION);
    }

    /**
     * The light toggle.
     */
    @Nullable
    private ToggleButton lightButton;

    /**
     * The parameters tree.
     */
    @Nullable
    private NodeTree<ChangeConsumer> parametersTree;

    /**
     * The project parameters.
     */
    @Nullable
    private ProjectParameters parameters;

    /**
     * The property editor.
     */
    @Nullable
    private PropertyEditor<ChangeConsumer> propertyEditor;

    /**
     * The selection handler.
     */
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
        parametersTree = new NodeTree<>(selectionHandler, this);
        propertyEditor = new ParametersPropertyEditor(this);
        propertyEditor.prefHeightProperty().bind(root.heightProperty());

        container.addComponent(buildSplitComponent(parametersTree, propertyEditor, root), PluginMessages.TREE_GENERATOR_EDITOR_TREE_TOOL);

        FXUtils.addClassTo(parametersTree.getTreeView(), CSSClasses.TRANSPARENT_TREE_VIEW);
    }

    /**
     * Handle selected object from node tree.
     *
     * @param object the selected object.
     */
    @FXThread
    private void selectFromTree(@Nullable final Object object) {

        Object parent = null;
        Object element;

        if (object instanceof TreeNode<?>) {
            final TreeNode treeNode = (TreeNode) object;
            final TreeNode parentNode = treeNode.getParent();
            parent = parentNode == null ? null : parentNode.getElement();
            element = treeNode.getElement();
        } else {
            element = object;
        }

        getPropertyEditor().buildFor(element, parent);
    }

    /**
     * @return the property editor.
     */
    @FXThread
    private @NotNull PropertyEditor<ChangeConsumer> getPropertyEditor() {
        return notNull(propertyEditor);
    }

    @Override
    @FXThread
    protected void doOpenFile(@NotNull final Path file) throws IOException {
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
    @BackgroundThread
    protected void doSave(@NotNull final Path toStore) throws IOException {
        super.doSave(toStore);

        final BinaryExporter exporter = BinaryExporter.getInstance();

        try (final OutputStream out = Files.newOutputStream(toStore)) {
            exporter.save(parameters, out);
        }
    }

    @Override
    @FXThread
    protected boolean needToolbar() {
        return true;
    }

    @Override
    @FXThread
    protected void createToolbar(@NotNull final HBox container) {
        super.createToolbar(container);

        final Button exportAction = new Button();
        exportAction.setTooltip(new Tooltip(PluginMessages.TREE_GENERATOR_EDITOR_EXPORT_ACTION));
        exportAction.setOnAction(event -> export());
        exportAction.setGraphic(new ImageView(Icons.EXPORT_16));

        lightButton = new ToggleButton();
        lightButton.setTooltip(new Tooltip(Messages.SCENE_FILE_EDITOR_ACTION_CAMERA_LIGHT));
        lightButton.setGraphic(new ImageView(Icons.LIGHT_16));
        lightButton.setSelected(true);
        lightButton.selectedProperty()
                .addListener((observable, oldValue, newValue) -> changeLight(newValue));

        FXUtils.addClassesTo(exportAction, CSSClasses.FLAT_BUTTON, CSSClasses.FILE_EDITOR_TOOLBAR_BUTTON);
        FXUtils.addClassesTo(lightButton, CSSClasses.FILE_EDITOR_TOOLBAR_BUTTON);
        DynamicIconSupport.addSupport(exportAction, lightButton);

        FXUtils.addToPane(createSaveAction(), container);
        FXUtils.addToPane(exportAction, lightButton, container);
    }

    /**
     * Open Save As Dialog to export the tree.
     */
    @FXThread
    private void export() {
        UIUtils.openSaveAsDialog(this::export, FileExtensions.JME_OBJECT, ACTION_TESTER);
    }

    /**
     * Export the tree to the file.
     *
     * @param path the file.
     */
    @FXThread
    private void export(@NotNull final Path path) {

        final BinaryExporter exporter = BinaryExporter.getInstance();

        try (final OutputStream out = Files.newOutputStream(path)) {
            exporter.save(getEditor3DState().getTreeNode(), out);
        } catch (final IOException e) {
            EditorUtil.handleException(LOGGER, this, e);
        }
    }

    /**
     * Handle changing camera light visibility.
     */
    @FXThread
    private void changeLight(@NotNull final Boolean newValue) {
        if (isIgnoreListeners()) return;

        final TreeGeneratorEditor3DState editor3DState = getEditor3DState();
        editor3DState.updateLightEnabled(newValue);

        if (editorState != null) editorState.setEnableLight(newValue);
    }

    /**
     * @return the parameters tree.
     */
    @FXThread
    private @NotNull NodeTree<ChangeConsumer> getParametersTree() {
        return notNull(parametersTree);
    }

    /**
     * @return the project parameters.
     */
    @FXThread
    private @NotNull ProjectParameters getParameters() {
        return notNull(parameters);
    }

    /**
     * @return the light toggle.
     */
    @FXThread
    private @NotNull ToggleButton getLightButton() {
        return notNull(lightButton);
    }

    @Override
    @FXThread
    protected void loadState() {
        super.loadState();

        final TreeGeneratorEditorState editorState = getEditorState();
        if (editorState != null) {
            getLightButton().setSelected(editorState.isEnableLight());
        }
    }

    @Override
    @FXThread
    public void notifyFXChangeProperty(@NotNull final Object object, @NotNull final String propertyName) {

        if (object instanceof MaterialParameters) {
            getParametersTree().refresh(object);
        }

        getPropertyEditor().syncFor(object);
        getEditor3DState().generate();
    }

    @Override
    @FXThread
    public void notifyFXAddedChild(@NotNull final Object parent, @NotNull final Object added, final int index,
                                   final boolean needSelect) {

        final NodeTree<ChangeConsumer> parametersTree = getParametersTree();

        if (added instanceof BranchParameters || added instanceof LevelOfDetailParameters) {
            parametersTree.refresh(parent);
        } else {
            parametersTree.notifyAdded(parent, added, index);
        }

        if (needSelect) {
            parametersTree.select(added);
        }

        getEditor3DState().generate();
    }

    @Override
    @FXThread
    public void notifyFXRemovedChild(@NotNull final Object parent, @NotNull final Object removed) {

        if (removed instanceof BranchParameters || removed instanceof LevelOfDetailParameters) {
            getParametersTree().refresh(parent);
        } else {
            getParametersTree().notifyRemoved(parent, removed);
        }

        getEditor3DState().generate();
    }
}
