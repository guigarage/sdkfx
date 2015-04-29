package com.guigarage.sdk.css;

import javafx.beans.property.Property;
import javafx.css.StyleConverter;
import javafx.css.StyleableProperty;
import javafx.scene.control.Control;

/**
 * A CssMetaData class that is bound to a specific property that is part of a Skin class of a Control.
 * @param <S> Type of the Control
 * @param <V> Value type of the property
 */
public class SkinPropertyBasedCssMetaData<S extends Control, V> extends AbstractPropertyBasedCssMetaData<S, V> {

    /**
     * Default Constructor
     *
     * @param property name of the CSS property
     * @param converter the StyleConverter used to convert the CSS parsed value to a Java object.
     * @param propertyName Name of the property field in the Skin class
     * @param defaultValue The default value of the corresponding StyleableProperty
     */
    public SkinPropertyBasedCssMetaData(String property, StyleConverter<?, V> converter, String propertyName, V defaultValue) {
        super(property, converter, propertyName, defaultValue);
    }

    protected <T extends Property<V> & StyleableProperty<V>> T getProperty(S styleable) {
        try {
            return (T) styleable.getSkin().getClass().getMethod(getPropertyName() + "Property").invoke(styleable.getSkin());
        } catch (Exception e) {
            throw new RuntimeException("Can't get StyleableProperty", e);
        }
    }
}