package com.guigarage.sdk.css;

import javafx.beans.property.Property;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;

/**
 * Abstract CssMetaData class that is bound to a specific property that can be accessed by a Styleable instance.
 *
 * @author Hendrik Ebbers
 * @param <S> Type of the Styleable instance
 * @param <V> Value type of the property
 */
public abstract class AbstractPropertyBasedCssMetaData<S extends Styleable, V> extends CssMetaData<S, V> {

    private String propertyName;

    /**
     * Default Constructor
     *
     * @param property name of the CSS property
     * @param converter the StyleConverter used to convert the CSS parsed value to a Java object.
     * @param propertyName Name of the property field
     * @param defaultValue The default value of the corresponding StyleableProperty
     */
    public AbstractPropertyBasedCssMetaData(String property, StyleConverter<?, V> converter, String propertyName, V defaultValue) {
        super(property, converter, defaultValue);
        this.propertyName = propertyName;
    }

    protected abstract <T extends Property<V> & StyleableProperty<V>> T getProperty(S styleable);

    /**
     * Returns the field name of the property
     * @return name of the property
     */
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public boolean isSettable(S styleable) {
        Property<V> property = getProperty(styleable);
        return property == null || !property.isBound();
    }

    @Override
    public StyleableProperty<V> getStyleableProperty(S styleable) {
        return (StyleableProperty<V>) getProperty(styleable);
    }

}
