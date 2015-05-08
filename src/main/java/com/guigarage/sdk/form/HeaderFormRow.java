package com.guigarage.sdk.form;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class HeaderFormRow implements FormRow {

    private VBox layout;

    public HeaderFormRow(String title) {
        this(title, null);
    }

    public HeaderFormRow(String title, String description) {
        this.layout = new VBox();

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("form-header-title");

        Label descriptionLabel = new Label(description);
        descriptionLabel.getStyleClass().add("form-header-description");

        layout.getChildren().addAll(titleLabel, descriptionLabel);
    }

    @Override
    public double getPrefLabelWidth() {
        return -1;
    }

    @Override
    public double layoutInParent(double startX, double startY, double width, double labelWidth, double rowSpacing, double columnSpacing) {
        layout.relocate(startX, startY);
        double layoutHeight = layout.prefHeight(width);
        layout.resize(width, layoutHeight);
        return startY + layoutHeight + rowSpacing;
    }

    @Override
    public List<Node> getNodes() {
        return FXCollections.observableArrayList(layout);
    }
}
