package com.ss.editor.tree.generator.editor;

import static com.ss.editor.config.DefaultSettingsProvider.Defaults.PREF_DEFAULT_CAMERA_LIGHT;
import static com.ss.editor.config.DefaultSettingsProvider.Preferences.PREF_CAMERA_LAMP;
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
        this.enableLight = EDITOR_CONFIG.getBoolean(PREF_CAMERA_LAMP, PREF_DEFAULT_CAMERA_LIGHT);
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
