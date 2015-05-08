package com.guigarage.sdk.form;

import com.guigarage.sdk.action.Action;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.List;

public class ActionFormRow implements FormRow {

    private HBox layout;

    public ActionFormRow(Action... actions) {
        layout = new HBox();
        layout.getStyleClass().add("form-action-box");
        for(Action action : actions) {
            Button button = new Button(action.getTitle());
            button.setOnAction(e -> action.getCallback().call());
            layout.getChildren().add(button);
        }
    }

    @Override
    public double getPrefLabelWidth() {
        return -1;
    }

    @Override
    public double layoutInParent(double startX, double startY, double width, double labelWidth, double rowSpacing, double columnSpacing) {
        layout.relocate(startX, startY);
        double prefHeight = layout.prefHeight(width);
        layout.resize(width, prefHeight);
        return startY + prefHeight + rowSpacing;
    }

    @Override
    public List<Node> getNodes() {
        return FXCollections.observableArrayList(layout);
    }
}
