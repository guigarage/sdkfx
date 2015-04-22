package com.guigarage.sdk.container;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public class Workbench extends StackPane {

    private ObjectProperty<Node> currentView;

    public Workbench() {
        getStyleClass().add("workbench");
        currentView = new SimpleObjectProperty<>();
        currentView.addListener(e -> {
            getChildren().clear();
            if (currentView.get() != null) {
                getChildren().add(currentView.get());
            }
        });
    }

    @Override
    protected void layoutChildren() {
        for (Node child : getChildren()) {
            child.relocate(0, 0);
            child.resize(getWidth(), getHeight());
        }
    }

    private void changeView(Node nextView) {
    }

    public Node getCurrentView() {
        return currentView.get();
    }

    public ObjectProperty<Node> currentViewProperty() {
        return currentView;
    }

    public void setCurrentView(Node currentView) {
        this.currentView.set(currentView);
    }

    public void setCurrentView(String fxml) {
    }
}
