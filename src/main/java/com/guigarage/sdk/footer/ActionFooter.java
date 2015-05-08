package com.guigarage.sdk.footer;

import com.guigarage.sdk.action.Action;
import com.guigarage.sdk.menu.MenuEntry;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hendrikebbers on 15.04.15.
 */
public class ActionFooter extends Footer {

    private ObservableList<Action> actionItems;

    private Map<Action, Node> itemToNode;

    public ActionFooter() {
        getStyleClass().add("action-footer");

        itemToNode = new HashMap<>();
        actionItems = FXCollections.observableArrayList();
        actionItems.addListener((ListChangeListener<Action>)c -> {
            while (c.next()) {
                if (!c.wasPermutated() && !c.wasUpdated()) {
                    for (Action removed : c.getRemoved()) {
                        Node child = itemToNode.remove(removed);
                        getChildren().remove(child);
                    }
                    for (Action added : c.getAddedSubList()) {
                        Button button = new Button();
                        button.setText(added.getTitle());

                        button.setOnAction(e -> {
                            if(added.getCallback() != null) {
                                added.getCallback().call();
                            }
                        });

                        if(added.getIcon() != null) {
                            Label iconLabel = new Label();
                            iconLabel.getStyleClass().add("icon-label");
                            iconLabel.setText(added.getIcon().getText());
                            button.setGraphic(iconLabel);
                        }

                        if(!getChildren().isEmpty()) {
                            button.getStyleClass().add("not-first-button");
                        }
                        getChildren().add(button);
                        itemToNode.put(added, button);
                    }
                }
            }
        });
    }

    public void addAction(Action action) {
        actionItems.add(action);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        double widthPerAction = getWidth() / getChildren().size();

        double currentX = 0;

        for(Node child : getChildren()) {
            child.relocate(currentX, 0);
            child.resize(widthPerAction, getHeight());
            currentX = currentX + widthPerAction;
        }

    }
}
