package com.ss.editor.tree.generator.property;

import com.jme3.material.Material;
import com.ss.editor.annotation.FXThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.ui.control.property.PropertyEditor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The property editor.
 *
 * @author JavaSaBr
 */
public class ParametersPropertyEditor extends PropertyEditor<ChangeConsumer> {

    public ParametersPropertyEditor(@NotNull final ChangeConsumer changeConsumer) {
        super(changeConsumer);
    }

    @Override
    @FXThread
    protected boolean canEdit(@NotNull final Object object, @Nullable final Object parent) {

        if (object instanceof Material) {
            return ((Material) object).getKey() == null;
        }

        return super.canEdit(object, parent);
    }
}
