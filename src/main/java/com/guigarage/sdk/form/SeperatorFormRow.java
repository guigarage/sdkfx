package com.guigarage.sdk.form;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.shape.Line;

import java.util.List;

public class SeperatorFormRow implements FormRow {

    private Line line;

    public SeperatorFormRow() {
        this.line = new Line();
        line.getStyleClass().add("form-seperator");
    }

    @Override
    public double getPrefLabelWidth() {
        return -1;
    }

    @Override
    public double layoutInParent(double startX, double startY, double width, double labelWidth, double rowSpacing, double columnSpacing) {
        line.setStartX(startX);
        line.setStartY(startY + 12);
        line.setEndX(startX + width);
        line.setEndY(startY + 12);

        return startY + 24 + rowSpacing;
    }

    @Override
    public List<Node> getNodes() {
        return FXCollections.observableArrayList(line);
    }
}
