package com.guigarage.sdk.menu;

import com.guigarage.sdk.util.Callback;
import com.guigarage.sdk.util.FontBasedIcon;
import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Region;

import java.util.Optional;

/**
 * Created by hendrikebbers on 18.04.15.
 */
public class MenuEntry extends Region {

    private DoubleProperty iconWidth;

    private DoubleProperty iconTextGapWidth;

    private StringProperty text;

    private ObjectProperty<FontBasedIcon> icon;

    private ObjectProperty<Callback> callback;

    private Label textLabel;

    private Label iconLabel;

    private ObjectProperty<Node> rightNode;

    private PseudoClass hightlightPseudoclass = PseudoClass.getPseudoClass("highlighted");

    public MenuEntry() {
        getStyleClass().add("menu-entry");
        iconWidth = new SimpleDoubleProperty(24.0);
        iconWidth.addListener(e -> requestLayout());
        iconTextGapWidth = new SimpleDoubleProperty(12.0);
        iconTextGapWidth.addListener(e -> requestLayout());
        text = new SimpleStringProperty();
        icon = new SimpleObjectProperty<>();
        callback = new SimpleObjectProperty<>();

        textLabel = new Label();
        textLabel.getStyleClass().add("menu-entry-text");
        textLabel.textProperty().bind(text);
        getChildren().add(textLabel);

        iconLabel = new Label();
        iconLabel.getStyleClass().add("menu-entry-icon");
        icon.addListener(e -> {
            if (icon.get() != null) {
                iconLabel.setText(icon.get().getText());
            } else {
                iconLabel.setText("");
            }
        });
        getChildren().add(iconLabel);

        rightNode = new SimpleObjectProperty<>();
        rightNode.addListener((obs, oldValue, newValue) -> {
            if(oldValue != null) {
                getChildren().remove(oldValue);
            }
            if(newValue != null) {
                getChildren().add(newValue);
            }
        });

        addEventFilter(MouseEvent.MOUSE_CLICKED, e -> Optional.ofNullable(callback.get()).ifPresent(c -> c.call()));
        addEventFilter(TouchEvent.TOUCH_PRESSED, e -> Optional.ofNullable(callback.get()).ifPresent(c -> c.call()));

        addEventFilter(MouseEvent.MOUSE_ENTERED, e -> pseudoClassStateChanged(hightlightPseudoclass, true));
        addEventFilter(MouseEvent.MOUSE_EXITED, e -> pseudoClassStateChanged(hightlightPseudoclass, false));

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(Double.MAX_VALUE - 1);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
    }

    @Override
    protected void layoutChildren() {
        iconLabel.resize(iconWidth.get(), iconWidth.get());
        iconLabel.relocate(getPadding().getLeft(), getHeight() / 2 - iconWidth.get() / 2);

        textLabel.resize(textLabel.prefWidth(-1), textLabel.prefHeight(-1));
        textLabel.relocate(getPadding().getLeft() + iconWidth.get() + iconTextGapWidth.get(), getHeight() / 2 - textLabel.getHeight() / 2);

        if(rightNode.get() != null) {
            double rightNodePrefWidth = rightNode.get().prefWidth(getHeight());
            rightNode.get().resize(rightNodePrefWidth, rightNode.get().prefHeight(-1));
            textLabel.relocate(getWidth() - getPadding().getRight() - rightNodePrefWidth, getHeight() / 2 - textLabel.getHeight() / 2);
        }
     }

    @Override
    protected double computePrefHeight(double width) {
        double heightByIcon = iconWidth.get();
        double heightByLabel = textLabel.prefHeight(-1);
        double heightByRightComp = 0;
        if(rightNode.get() != null) {
            heightByRightComp = rightNode.get().prefHeight(-1);
        }
        return getPadding().getTop() + getPadding().getBottom() + Math.max(heightByIcon, Math.max(heightByLabel, heightByRightComp));
    }

    @Override
    protected double computePrefWidth(double height) {
        double rightCompWidth = 0;
        if(rightNode.get() != null) {
            rightCompWidth = rightNode.get().prefWidth(height);
        }
        return getPadding().getLeft() + iconWidth.get() + iconTextGapWidth.get() + textLabel.prefWidth(height) + iconTextGapWidth.get() + rightCompWidth + getPadding().getRight();
    }

    public Node getRightNode() {
        return rightNode.get();
    }

    public ObjectProperty<Node> rightNodeProperty() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode.set(rightNode);
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

    public FontBasedIcon getIcon() {
        return icon.get();
    }

    public ObjectProperty<FontBasedIcon> iconProperty() {
        return icon;
    }

    public void setIcon(FontBasedIcon icon) {
        this.icon.set(icon);
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
}
