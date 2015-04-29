package com.guigarage.sdk.css;

import com.sun.javafx.css.converters.EnumConverter;
import javafx.css.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.control.SkinBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Helper class to create Styleable properties and CSSMetaData instances
 *
 * @author Hendrik Ebbers
 */
public class CssHelper {

    /**
     * Creates a List of CssMetaData instances that is merged by the given parameters
     * @param baseList A basic list. All elements of the list will be in the returned list
     * @param metaData An array of CssMetaData instances. All instances will be in the returned list
     * @return A list with all given CssMetaData instances
     */
    public static List<CssMetaData<? extends Styleable, ?>> createCssMetaDataList(List<CssMetaData<? extends Styleable, ?>> baseList, CssMetaData<? extends Styleable, ?>... metaData) {
        List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(baseList);
        styleables.addAll(Arrays.asList(metaData));
        return Collections.unmodifiableList(styleables);
    }

    /**
     * Creates a List of CssMetaData instances that is merged by the given parameters
     * @param metaData An array of CssMetaData instances. All instances will be in the returned list
     * @return A list with all given CssMetaData instances
     */
    public static List<CssMetaData<? extends Styleable, ?>> createCssMetaDataList(CssMetaData<? extends Styleable, ?>... metaData) {
        return createCssMetaDataList(new ArrayList<>(), metaData);
    }

    /**
     * Creates a StyleableObjectProperty instance that is parametrized by the given parameters. The returned property must be defined as a field in the given Styleable class. The property will be a <tt>SimpleStyleableObjectProperty</tt>
     * @param metaData The CssMetaData instance
     * @param styleable The styleable that contains the property field. Mostly this will be a JavaFX Control class
     * @param <S> Type of the Styleable
     * @param <V> value type of the property
     * @return a styleable property that can be used in a Styleable class like a Control
     *
     * @see javafx.css.SimpleStyleableObjectProperty
     */
    public static <S extends Styleable, V> StyleableObjectProperty<V> createProperty(DefaultPropertyBasedCssMetaData<S, V> metaData, S styleable) {
        return new SimpleStyleableObjectProperty<V>(metaData, styleable, metaData.getPropertyName(), metaData.getInitialValue(styleable));
    }

    /**
     * Creates a StyleableObjectProperty instance that is parametrized by the given parameters. The property will be a <tt>SimpleStyleableObjectProperty</tt>. The returned property must be defined as a field in the Skin class of the given Control class.
     * @param metaData The CssMetaData instance
     * @param skin The Skin that contains the property field.
     * @param <S> Type of the Control
     * @param <V> value type of the property
     * @return a styleable property that can be used in the Skin of a Control
     *
     * @see javafx.css.SimpleStyleableObjectProperty
     */
    public static <S extends Control, V> StyleableObjectProperty<V> createProperty(SkinPropertyBasedCssMetaData<S, V> metaData, SkinBase<S> skin) {
        return new SimpleStyleableObjectProperty<V>(metaData, skin, metaData.getPropertyName(), metaData.getInitialValue(skin.getSkinnable()));
    }

    /**
     * Creates a CssMetaData instance that can be used in a Styleable class.
     *
     * @param property name of the CSS property
     * @param converter the StyleConverter used to convert the CSS parsed value to a Java object.
     * @param propertyName Name of the property field in the Styleable class
     * @param defaultValue The default value of the corresponding StyleableProperty
     * @param <S> Type of the Styleable instance
     * @param <V> Value type of the property
     * @return the CssMetaData instance
     */
    public static <S extends Styleable, V> DefaultPropertyBasedCssMetaData<S, V> createMetaData(String property, StyleConverter<?, V> converter, String propertyName, V defaultValue) {
        return new DefaultPropertyBasedCssMetaData<S, V>(property, converter, propertyName, defaultValue);
    }

    /**
     * Creates a CssMetaData instance that can be used in the Skin of a Control.
     *
     * @param property name of the CSS property
     * @param converter the StyleConverter used to convert the CSS parsed value to a Java object.
     * @param propertyName Name of the property field in the Skin class
     * @param defaultValue The default value of the corresponding StyleableProperty
     * @param <S> Type of the Control
     * @param <V> Value type of the property
     * @return the CssMetaData instance
     */
    public static <S extends Control, V> SkinPropertyBasedCssMetaData<S, V> createSkinMetaData(String property, StyleConverter<?, V> converter, String propertyName, V defaultValue) {
        return new SkinPropertyBasedCssMetaData<S, V>(property, converter, propertyName, defaultValue);
    }

    public static <S extends Control, T extends VPos> SkinPropertyBasedCssMetaData<S, T> createSkinMetaDataForVPos(String property, String propertyName, T defaultValue) {
        return new SkinPropertyBasedCssMetaData<S, T>(property, new EnumConverter(VPos.class), propertyName, defaultValue);
    }

    public static <S extends Control, T extends HPos> SkinPropertyBasedCssMetaData<S, T> createSkinMetaDataForHPos(String property, String propertyName, T defaultValue) {
        return new SkinPropertyBasedCssMetaData<S, T>(property, new EnumConverter(HPos.class), propertyName, defaultValue);
    }

    public static <S extends Control, T extends Pos> SkinPropertyBasedCssMetaData<S, T> createSkinMetaDataForPos(String property, String propertyName, T defaultValue) {
        return new SkinPropertyBasedCssMetaData<S, T>(property, new EnumConverter(Pos.class), propertyName, defaultValue);
    }
}
