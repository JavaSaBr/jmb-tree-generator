package com.ss.editor.tree.generator.editor;

import com.ss.editor.annotation.FxThread;
import com.ss.editor.config.DefaultSettingsProvider.Defaults;
import com.ss.editor.config.DefaultSettingsProvider.Preferences;
import com.ss.editor.ui.component.editor.state.impl.Editor3dWithEditorToolEditorState;

/**
 * The state of the {@link TreeGeneratorFileEditor}.
 *
 * @author JavaSaBr
 */
public class TreeGeneratorEditorState extends Editor3dWithEditorToolEditorState {

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
        this.enableLight = EDITOR_CONFIG.getBoolean(Preferences.PREF_CAMERA_LAMP,
                Defaults.PREF_DEFAULT_CAMERA_LIGHT);
    }

    /**
     * Sets enable light.
     *
     * @param enableLight true if the light is enabled.
     */
    @FxThread
    public void setEnableLight(boolean enableLight) {
        boolean changed = isEnableLight() != enableLight;
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
