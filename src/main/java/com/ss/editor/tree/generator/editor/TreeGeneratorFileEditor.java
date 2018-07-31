package com.ss.editor.tree.generator.editor;

import static com.ss.rlib.common.util.ObjectUtils.notNull;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.export.binary.BinaryImporter;
import com.jme3.scene.Node;
import com.simsilica.arboreal.BranchParameters;
import com.simsilica.arboreal.LevelOfDetailParameters;
import com.ss.editor.FileExtensions;
import com.ss.editor.Messages;
import com.ss.editor.annotation.BackgroundThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.plugin.api.editor.Advanced3dFileEditorWithSplitRightTool;
import com.ss.editor.tree.generator.PluginMessages;
import com.ss.editor.tree.generator.TreeGeneratorEditorPlugin;
import com.ss.editor.tree.generator.parameters.MaterialParameters;
import com.ss.editor.tree.generator.parameters.ProjectParameters;
import com.ss.editor.tree.generator.property.ParametersPropertyEditor;
import com.ss.editor.ui.Icons;
import com.ss.editor.ui.component.asset.tree.context.menu.action.DeleteFileAction;
import com.ss.editor.ui.component.asset.tree.context.menu.action.NewFileAction;
import com.ss.editor.ui.component.asset.tree.context.menu.action.RenameFileAction;
import com.ss.editor.ui.component.editor.EditorDescriptor;
import com.ss.editor.ui.component.editor.state.EditorState;
import com.ss.editor.ui.component.tab.EditorToolComponent;
import com.ss.editor.ui.control.property.PropertyEditor;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.editor.ui.css.CssClasses;
import com.ss.editor.ui.util.DynamicIconSupport;
import com.ss.editor.ui.util.UiUtils;
import com.ss.editor.util.EditorUtil;
import com.ss.rlib.common.util.array.Array;
import com.ss.rlib.fx.util.FxUtils;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
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
    Advanced3dFileEditorWithSplitRightTool<TreeGeneratorEditor3dPart, TreeGeneratorEditorState> implements
        ChangeConsumer {

    private static final Predicate<Class<?>> ACTION_TESTER = type -> type == NewFileAction.class ||
            type == DeleteFileAction.class || type == RenameFileAction.class;

    public static final EditorDescriptor DESCRIPTOR = new EditorDescriptor(
            TreeGeneratorFileEditor::new,
            PluginMessages.TREE_GENERATOR_EDITOR_NAME,
            TreeGeneratorFileEditor.class.getSimpleName(),
            TreeGeneratorEditorPlugin.PROJECT_EXTENSION
    );

    /**
     * The light toggle.
     */
    @NotNull
    private final ToggleButton lightButton;

    /**
     * The parameters tree.
     */
    @NotNull
    private final NodeTree<ChangeConsumer> parametersTree;

    /**
     * The property editor.
     */
    @NotNull
    private final PropertyEditor<ChangeConsumer> propertyEditor;

    /**
     * The selection handler.
     */
    @NotNull
    private final Consumer<Array<Object>> selectionHandler;

    /**
     * The project parameters.
     */
    @Nullable
    private ProjectParameters parameters;

    public TreeGeneratorFileEditor() {
        this.selectionHandler = this::selectFromTree;
        this.parametersTree = new NodeTree<>(selectionHandler, this);
        this.propertyEditor = new ParametersPropertyEditor(this);
        this.lightButton = new ToggleButton();
    }

    @Override
    @FxThread
    protected @NotNull TreeGeneratorEditor3dPart create3dEditorPart() {
        return new TreeGeneratorEditor3dPart(this);
    }

    @Override
    protected @Nullable Supplier<EditorState> getEditorStateFactory() {
        return TreeGeneratorEditorState::new;
    }

    @Override
    @FromAnyThread
    public @NotNull EditorDescriptor getDescriptor() {
        return DESCRIPTOR;
    }

    @Override
    @FxThread
    protected void createToolComponents(@NotNull EditorToolComponent container, @NotNull StackPane root) {
        super.createToolComponents(container, root);

        propertyEditor.prefHeightProperty()
                .bind(root.heightProperty());

        container.addComponent(buildSplitComponent(parametersTree, propertyEditor, root),
                PluginMessages.TREE_GENERATOR_EDITOR_TREE_TOOL);

        FxUtils.addClass(parametersTree.getTreeView(), CssClasses.TRANSPARENT_TREE_VIEW);
    }

    /**
     * Handle selected objects from node tree.
     *
     * @param objects the selected objects.
     */
    @FxThread
    private void selectFromTree(@Nullable Array<Object> objects) {

        var object = objects.first();

        Object parent = null;
        Object element;

        if (object instanceof TreeNode<?>) {
            var treeNode = (TreeNode) object;
            var parentNode = treeNode.getParent();
            parent = parentNode == null ? null : parentNode.getElement();
            element = treeNode.getElement();
        } else {
            element = object;
        }

        propertyEditor.buildFor(element, parent);
    }


    @Override
    @FxThread
    protected void doOpenFile(@NotNull Path file) throws IOException {
        super.doOpenFile(file);

        var importer = BinaryImporter.getInstance();
        importer.setAssetManager(EditorUtil.getAssetManager());

        try (var in = Files.newInputStream(file)) {
            parameters = (ProjectParameters) importer.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        parametersTree.fill(parameters);
        editor3dPart.open(parameters);
    }

    @Override
    @BackgroundThread
    protected void doSave(@NotNull Path toStore) throws Throwable {
        super.doSave(toStore);

        var exporter = BinaryExporter.getInstance();

        try (var out = Files.newOutputStream(toStore)) {
            exporter.save(parameters, out);
        }
    }

    @Override
    @FxThread
    protected boolean needToolbar() {
        return true;
    }

    @Override
    @FxThread
    protected void createToolbar(@NotNull HBox container) {
        super.createToolbar(container);

        var exportAction = new Button();
        exportAction.setTooltip(new Tooltip(PluginMessages.TREE_GENERATOR_EDITOR_EXPORT_ACTION));
        exportAction.setOnAction(event -> export());
        exportAction.setGraphic(new ImageView(Icons.EXPORT_16));

        lightButton.setTooltip(new Tooltip(Messages.SCENE_FILE_EDITOR_ACTION_CAMERA_LIGHT));
        lightButton.setGraphic(new ImageView(Icons.LIGHT_16));
        lightButton.setSelected(true);
        lightButton.selectedProperty()
                .addListener((observable, oldValue, newValue) -> changeLight(newValue));

        FxUtils.addClass(exportAction, CssClasses.FLAT_BUTTON, CssClasses.FILE_EDITOR_TOOLBAR_BUTTON)
                .addClass(lightButton, CssClasses.FILE_EDITOR_TOOLBAR_BUTTON);

        DynamicIconSupport.addSupport(exportAction, lightButton);

        FxUtils.addChild(container, createSaveAction(), exportAction, lightButton);
    }

    /**
     * Open Save As Dialog to export the tree.
     */
    @FxThread
    private void export() {
        UiUtils.openSaveAsDialog(this::export, FileExtensions.JME_OBJECT, ACTION_TESTER);
    }

    /**
     * Export the tree to the file.
     *
     * @param path the file.
     */
    @FxThread
    private void export(@NotNull Path path) {
        UiUtils.incrementLoading();
        editor3dPart.generate(node -> exportGeneratedNode(path, node));
    }

    @FxThread
    private void exportGeneratedNode(@NotNull Path path, Node node) {

        var exporter = BinaryExporter.getInstance();

        try (var out = Files.newOutputStream(path)) {
            exporter.save(node, out);
        } catch (IOException e) {
            EditorUtil.handleException(LOGGER, this, e);
        }

        UiUtils.decrementLoading();
    }

    /**
     * Handle changing camera light visibility.
     */
    @FxThread
    private void changeLight(@NotNull Boolean newValue) {

        if (isIgnoreListeners()) {
            return;
        }

        editor3dPart.updateLightEnabled(newValue);

        if (editorState != null) {
            editorState.setEnableLight(newValue);
        }
    }

    /**
     * Get the project parameters.
     *
     * @return the project parameters.
     */
    @FxThread
    private @NotNull ProjectParameters getParameters() {
        return notNull(parameters);
    }

    @Override
    @FxThread
    protected void loadState() {
        super.loadState();

        var editorState = getEditorState();

        if (editorState != null) {
            lightButton.setSelected(editorState.isEnableLight());
        }
    }

    @Override
    @FxThread
    public void notifyFxChangeProperty(@NotNull Object object, @NotNull String propertyName) {

        if (object instanceof MaterialParameters) {
            parametersTree.refresh(object);
        }

        propertyEditor.syncFor(object);
        editor3dPart.generate();
    }

    @Override
    @FxThread
    public void notifyFxAddedChild(@NotNull Object parent, @NotNull Object added, int index, boolean needSelect) {

        if (added instanceof BranchParameters || added instanceof LevelOfDetailParameters) {
            parametersTree.refresh(parent);
        } else {
            parametersTree.notifyAdded(parent, added, index);
        }

        if (needSelect) {
            parametersTree.selectSingle(added);
        }

        editor3dPart.generate();
    }

    @Override
    @FxThread
    public void notifyFxRemovedChild(@NotNull Object parent, @NotNull Object removed) {

        if (removed instanceof BranchParameters || removed instanceof LevelOfDetailParameters) {
            parametersTree.refresh(parent);
        } else {
            parametersTree.notifyRemoved(parent, removed);
        }

        editor3dPart.generate();
    }
}
