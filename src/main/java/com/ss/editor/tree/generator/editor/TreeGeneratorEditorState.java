package com.ss.editor.tree.generator.editor;

import com.ss.editor.annotation.FxThread;
import com.ss.editor.ui.component.editor.state.impl.Editor3DWithEditorToolEditorState;

/**
 * The state of the {@link TreeGeneratorFileEditor}.
 *
 * @author JavaSaBr
 */
public class TreeGeneratorEditorState extends Editor3DWithEditorToolEditorState {

    /**
     * The constant serialVersionUID.
     */
    public static final long serialVersionUID = 2;

    /**
     * Is enabled light.
     */
    private volatile boolean enableLight;

    public TreeGeneratorEditorState() {
        this.cameraLocation.set(0, 2, 0);
        this.enableLight = EDITOR_CONFIG.isDefaultEditorCameraEnabled();
    }

    /**
     * Sets enable light.
     *
     * @param enableLight true if the light is enabled.
     */
    @FxThread
    public void setEnableLight(final boolean enableLight) {
        final boolean changed = isEnableLight() != enableLight;
        this.enableLight = enableLight;
        if (changed) notifyChange();
    }

    /**
     * Is enable light boolean.
     *
     * @return true if the light is enabled.
     */
    @FxThread
    public boolean isEnableLight() {
        return enableLight;
    }
}
