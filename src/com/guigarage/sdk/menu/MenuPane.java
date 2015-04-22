package com.guigarage.sdk.menu;

import com.guigarage.sdk.action.Action;
import com.guigarage.sdk.util.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hendrikebbers on 18.04.15.
 */
public class MenuPane extends VBox {

    private ObservableList<Action> entries;

    private Map<Action, Node> itemToNode;

    private Callback globalActionCallback;

    public MenuPane() {
        getStyleClass().add("menu-pane");
        setFillWidth(true);
        entries = FXCollections.observableArrayList();
        itemToNode = new HashMap<>();

        entries.addListener((ListChangeListener<Action>) c -> {
            while (c.next()) {
                if (!c.wasPermutated() && !c.wasUpdated()) {
                    for (Action removed : c.getRemoved()) {
                        Node child = itemToNode.remove(removed);
                        getChildren().remove(child);
                    }
                    for (Action added : c.getAddedSubList()) {
                        MenuEntry entry = new MenuEntry();
                        entry.setText(added.getTitle());
                        entry.setIcon(added.getIcon());
                        entry.setCallback(() -> {
                            if(added.getCallback() != null) {
                                added.getCallback().call();
                            }
                            if(globalActionCallback != null) {
                                globalActionCallback.call();
                            }
                        });
                        getChildren().add(entry);
                        itemToNode.put(added, entry);
                    }
                }
            }
        });
    }

    public void setGlobalActionCallback(Callback globalActionCallback) {
        this.globalActionCallback = globalActionCallback;
    }

    public void add(Action action) {
        entries.add(action);
    }

    public void remove(Action action) {
        entries.remove(action);
    }
}
