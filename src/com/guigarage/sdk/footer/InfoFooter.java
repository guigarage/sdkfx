package com.guigarage.sdk.footer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

/**
 * Created by hendrikebbers on 15.04.15.
 */
public class InfoFooter extends Footer {

    private StringProperty text;

    private Label label;

    public InfoFooter() {
        this.text = new SimpleStringProperty();
    }

    public InfoFooter withText(String text) {
        return withText(new SimpleStringProperty(text));
    }

    public InfoFooter withText(StringProperty text) {
        textProperty().bind(text);
        label = new Label();
        getStyleClass().addAll("infoFooterText");
        label.textProperty().bind(textProperty());
        getChildren().add(label);
        return this;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        label.resize(getWidth(), getHeight());
        label.relocate(0,0);
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }
}
