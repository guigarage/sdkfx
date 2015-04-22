package com.guigarage.sdk.container;

import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by hendrikebbers on 15.04.15.
 */
public class FormWorkbench extends VBox {

    public FormWorkbench() {
        getStyleClass().add("form-workbench");
        getChildren().addListener((ListChangeListener) e -> {
            getChildren().forEach(n -> VBox.setVgrow(n, Priority.ALWAYS));
        });
    }

    public FormWorkbench withChild(Node node) {
        getChildren().add(node);
        return this;
    }


}
