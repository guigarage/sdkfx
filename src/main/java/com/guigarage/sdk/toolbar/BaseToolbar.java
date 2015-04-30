package com.guigarage.sdk.toolbar;

import com.guigarage.sdk.action.Action;
import com.guigarage.sdk.util.Icon;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public abstract class BaseToolbar extends Region {

    private ObjectProperty<Node> leftNode;

    private StringProperty title;

    private ObservableList<Action> actionItems;

    private HBox actionBox;

    protected Text titleText;

    private Map<Action, Node> itemToNode;

    public BaseToolbar() {
        getStyleClass().add("base-toolbar");
        title = new SimpleStringProperty();
        actionItems = FXCollections.observableArrayList();

        actionBox = new HBox();
        actionBox.getStyleClass().add("action-box");
        getChildren().add(actionBox);

        itemToNode = new HashMap<>();

        actionItems.addListener((ListChangeListener<Action>) c -> {
            while (c.next()) {
                if (!c.wasPermutated() && !c.wasUpdated()) {
                    for (Action removed : c.getRemoved()) {
                        Node child = itemToNode.remove(removed);
                        actionBox.getChildren().remove(child);
                    }
                    for (Action added : c.getAddedSubList()) {
                        Button button = new Button();
                        button.setText(added.getIcon().getText());
                        button.setOnMouseEntered(e -> {
                            button.setScaleX(1.1);
                            button.setScaleY(1.1);
                        });
                        button.setOnMouseExited(e -> {
                            button.setScaleX(1.0);
                            button.setScaleY(1.0);
                        });

                        actionBox.getChildren().add(button);
                        itemToNode.put(added, button);
                    }
                }
            }
        });

        titleText = new Text();
        titleText.getStyleClass().add("text");
        titleText.textProperty().bind(title);
        getChildren().add(titleText);

        leftNode = new SimpleObjectProperty<>();
        leftNode.addListener((obs, oldValue, newValue) -> {
            if(oldValue != null) {
                getChildren().remove(oldValue);
            }
            if(newValue != null) {
                getChildren().add(newValue);
            }
        });

    }

    protected ObjectProperty<Node> leftNodeProperty() {
        return leftNode;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        double actionBoxPrefWidth = actionBox.prefWidth(-1);
        double actionBoxPrefHeight = actionBox.prefHeight(-1);
        actionBox.resize(actionBoxPrefWidth, actionBoxPrefHeight);
        actionBox.relocate(getWidth() - getPadding().getRight() - actionBoxPrefWidth, getPadding().getTop() + (getHeight() - getPadding().getTop() - actionBoxPrefHeight) / 2);

        if(leftNode.get() != null) {
            double leftNodeHeight = leftNode.get().prefHeight(-1);
            leftNode.get().resize(leftNode.get().prefWidth(-1), leftNodeHeight);
            leftNode.get().relocate(getPadding().getLeft(), getHeight() / 2 - leftNodeHeight / 2);
        }

        double titleTextPrefWidth = titleText.prefWidth(-1);
        double titleTextPrefHeight = titleText.prefHeight(-1);
        titleText.resize(titleTextPrefWidth, titleTextPrefHeight);
        titleText.relocate((getWidth() - titleTextPrefWidth) / 2, getPadding().getTop() + (getHeight() - getPadding().getTop() - titleTextPrefHeight) / 2);
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

    public void addAction(Action action) {
        actionItems.add(action);
    }
}
