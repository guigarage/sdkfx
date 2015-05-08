package com.guigarage.sdk.form;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by hendrikebbers on 08.05.15.
 */
public class FormEditor {

    private BooleanProperty disabled;

    private StringProperty title;

    private BooleanProperty visible;

    public FormEditor(BooleanProperty disabled, StringProperty title, BooleanProperty visible) {
        this.disabled = disabled;
        this.title = title;
        this.visible = visible;
    }

    public boolean getDisabled() {
        return disabled.get();
    }

    public BooleanProperty disabledProperty() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled.set(disabled);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public boolean getVisible() {
        return visible.get();
    }

    public BooleanProperty visibleProperty() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible.set(visible);
    }
}
