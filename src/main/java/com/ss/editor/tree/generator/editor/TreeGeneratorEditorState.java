package com.ss.editor.tree.generator.editor;

import com.ss.editor.ui.component.editor.state.impl.Editor3DWithEditorToolEditorState;

/**
 * The state of the {@link TreeGeneratorFileEditor}.
 *
 * @author JavaSaBr
 */
public class TreeGeneratorEditorState extends Editor3DWithEditorToolEditorState {

    public TreeGeneratorEditorState() {
        this.cameraLocation.set(0, 2, 0);
    }
}
