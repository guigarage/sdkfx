package com.guigarage.sdk.actionbutton;

import com.guigarage.sdk.action.Action;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class GlobalActionButton extends HBox {

    private ObservableList<Action> actions;

    private Map<Action, Node> itemToNode;

    private Ellipse ellipse;

    public GlobalActionButton() {
        getStyleClass().add("global-action-button");
        actions = FXCollections.observableArrayList();
        itemToNode = new HashMap<>();

        actions.addListener((ListChangeListener<Action>) c -> {
            while (c.next()) {
                if (!c.wasPermutated() && !c.wasUpdated()) {
                    for (Action removed : c.getRemoved()) {
                        Node child = itemToNode.remove(removed);
                        getChildren().remove(child);
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
                        button.setOnAction(e -> {
                            if (added.getCallback() != null) {
                                added.getCallback().call();
                            }
                        });
                        getChildren().add(button);
                        itemToNode.put(added, button);
                    }
                }
            }
        });

        Rectangle background = new Rectangle();
        background.getStyleClass().add("global-action-button-background");
        background.setManaged(false);
        background.setX(0);
        background.setY(0);
        background.widthProperty().bind(widthProperty());
        background.heightProperty().bind(heightProperty());
        background.arcWidthProperty().bind(heightProperty());
        background.arcHeightProperty().bind(heightProperty());
        getChildren().add(background);

        minWidth(USE_PREF_SIZE);
        maxWidth(USE_PREF_SIZE);
        minHeight(USE_PREF_SIZE);
        maxHeight(USE_PREF_SIZE);
    }

    @Override
    protected double computePrefWidth(double height) {
        if(actions.size() == 0) {
            return 0;
        }
        if(actions.size() == 1) {
            return Math.max(super.computePrefWidth(-1), super.computePrefHeight(-1));
        } else {
            return super.computePrefWidth(-1);
        }
    }

    @Override
    protected double computePrefHeight(double width) {
        if(actions.size() == 0) {
            return 0;
        }
        if(actions.size() == 1) {
            return Math.max(super.computePrefWidth(-1), super.computePrefHeight(-1));
        } else {
            return super.computePrefHeight(-1);
        }
    }

    public ObservableList<Action> getActions() {
        return actions;
    }
}
