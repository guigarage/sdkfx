package com.guigarage.sdk.util;

import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;

/**
 * Created by hendrikebbers on 18.04.15.
 */
public class BindableTransition extends Transition{


    private DoubleProperty fraction;

    private double startValue;

    private double endValue;

    public BindableTransition(Duration duration) {
        fraction = new SimpleDoubleProperty();
        setCycleDuration(duration);
        startValue = 0;
        endValue = 1.0;
    }

    @Override
    protected final void interpolate(double frac) {
        fraction.set(startValue + frac * (endValue - startValue));
    }

    public ReadOnlyDoubleProperty fractionProperty() {
        return fraction;
    }

    public double getStartValue() {
        return startValue;
    }

    public void setStartValue(double startValue) {
        this.startValue = startValue;
    }

    public double getEndValue() {
        return endValue;
    }

    public void setEndValue(double endValue) {
        this.endValue = endValue;
    }
}
