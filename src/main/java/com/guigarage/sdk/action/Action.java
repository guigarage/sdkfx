package com.guigarage.sdk.action;

import com.guigarage.sdk.util.Callback;
import com.guigarage.sdk.util.FontAwesomeIcons;
import com.guigarage.sdk.util.FontBasedIcon;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public class Action {

    private StringProperty title;

    private ObjectProperty<FontBasedIcon> icon;

    private StringProperty tooltip;

    private ObjectProperty<Callback> callback;

    public Action() {
        this(FontAwesomeIcons.VOLUMNE_UP);
    }

    public Action(FontBasedIcon icon) {
        this.icon = new SimpleObjectProperty<>(icon);
        this.title = new SimpleStringProperty();
        this.tooltip = new SimpleStringProperty();
        this.callback = new SimpleObjectProperty<>();
    }

    public Action(String text) {
        this.icon = new SimpleObjectProperty<>();
        this.title = new SimpleStringProperty(text);
        this.tooltip = new SimpleStringProperty(text);
        this.callback = new SimpleObjectProperty<>();
    }

    public Action(FontBasedIcon icon, String text) {
        this.icon = new SimpleObjectProperty<>(icon);
        this.title = new SimpleStringProperty(text);
        this.tooltip = new SimpleStringProperty(text);
        this.callback = new SimpleObjectProperty<>();
    }

    public Action(FontBasedIcon icon, String text, Callback callback) {
        this.icon = new SimpleObjectProperty<>(icon);
        this.title = new SimpleStringProperty(text);
        this.tooltip = new SimpleStringProperty(text);
        this.callback = new SimpleObjectProperty<>(callback);
    }

    public Action(FontBasedIcon icon, Callback callback) {
        this.icon = new SimpleObjectProperty<>(icon);
        this.title = new SimpleStringProperty();
        this.tooltip = new SimpleStringProperty();
        this.callback = new SimpleObjectProperty<>(callback);
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

    public FontBasedIcon getIcon() {
        return icon.get();
    }

    public ObjectProperty<FontBasedIcon> iconProperty() {
        return icon;
    }

    public void setIcon(FontBasedIcon icon) {
        this.icon.set(icon);
    }

    public String getTooltip() {
        return tooltip.get();
    }

    public StringProperty tooltipProperty() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip.set(tooltip);
    }

    public Callback getCallback() {
        return callback.get();
    }

    public ObjectProperty<Callback> callbackProperty() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback.set(callback);
    }

    public Action withIcon(FontBasedIcon icon) {
        setIcon(icon);
        return this;
    }
}
