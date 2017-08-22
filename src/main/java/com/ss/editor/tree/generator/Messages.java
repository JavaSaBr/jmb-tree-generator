package com.ss.editor.tree.generator;

import com.ss.editor.plugin.api.messages.MessagesPluginFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

/**
 * The class with localised all plugin messages.
 *
 * @author JavaSaBr
 */
public class Messages {

    @NotNull
    private static final ResourceBundle RESOURCE_BUNDLE = MessagesPluginFactory.getResourceBundle(TreeGeneratorEditorPlugin.class,
            "com/ss/editor/tree/generator/messages/messages");

    public static final String FONT_GENERATOR_DESCRIPTION;
    public static final String FONT_GENERATOR_TITLE;
    public static final String FONT_GENERATOR_PROP_FONT;
    public static final String FONT_GENERATOR_PROP_IMAGE_SIZE;
    public static final String FONT_GENERATOR_PROP_FONT_SIZE;
    public static final String FONT_GENERATOR_PROP_FONT_STYLE;
    public static final String FONT_GENERATOR_PROP_PADDING_X;
    public static final String FONT_GENERATOR_PROP_PADDING_Y;
    public static final String FONT_GENERATOR_PROP_LETTER_SPACING;
    public static final String FONT_GENERATOR_PROP_FIRST_CHAR;
    public static final String FONT_GENERATOR_PROP_LAST_CHAR;

    static {
        FONT_GENERATOR_DESCRIPTION = RESOURCE_BUNDLE.getString("FontGeneratorDescription");
        FONT_GENERATOR_TITLE = RESOURCE_BUNDLE.getString("FontGeneratorTitle");
        FONT_GENERATOR_PROP_FONT = RESOURCE_BUNDLE.getString("FontGeneratorPropFont");
        FONT_GENERATOR_PROP_IMAGE_SIZE = RESOURCE_BUNDLE.getString("FontGeneratorPropImageSize");
        FONT_GENERATOR_PROP_FONT_SIZE = RESOURCE_BUNDLE.getString("FontGeneratorPropFontSize");
        FONT_GENERATOR_PROP_FONT_STYLE = RESOURCE_BUNDLE.getString("FontGeneratorPropFontStyle");
        FONT_GENERATOR_PROP_PADDING_X = RESOURCE_BUNDLE.getString("FontGeneratorPropPaddingX");
        FONT_GENERATOR_PROP_PADDING_Y = RESOURCE_BUNDLE.getString("FontGeneratorPropPaddingY");
        FONT_GENERATOR_PROP_LETTER_SPACING = RESOURCE_BUNDLE.getString("FontGeneratorPropLetterSpacing");
        FONT_GENERATOR_PROP_FIRST_CHAR = RESOURCE_BUNDLE.getString("FontGeneratorPropFirstChar");
        FONT_GENERATOR_PROP_LAST_CHAR = RESOURCE_BUNDLE.getString("FontGeneratorPropLastChar");
    }
}
