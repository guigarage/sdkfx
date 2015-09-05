package com.guigarage.sdk.form;

import com.guigarage.sdk.action.Action;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

public class FormLayout extends Region {

    private DoubleProperty labelEditorSpacing;

    private DoubleProperty rowSpacing;

    private List<FormRow> rows;

    private final static int ROW_MARGIN = 12;

    public FormLayout() {
        getStyleClass().addAll("form-layout");
        this.labelEditorSpacing = new SimpleDoubleProperty(12);
        this.rowSpacing = new SimpleDoubleProperty(8);
        this.rows = new ArrayList<>();
    }

    public void addSeperator() {
        add(new SeperatorFormRow());
    }

    public void addHeader(String title) {
        add(new HeaderFormRow(title));
    }

    public void addActions(Action... actions) {
        add(new ActionFormRow(actions));
    }

    public void addHeader(String title, String description) {
        add(new HeaderFormRow(title, description));
    }

    public FormEditor addField(String name) {
        return addField(name, EditorType.TEXTFIELD);
    }

    public FormEditor addField(String name, EditorType type) {
        EditorFormRow row = new EditorFormRow(name, type);
        add(row);
        return row.getFormEditor();
    }

    public void add(FormRow row) {
        getChildren().addAll(row.getNodes());
        rows.add(row);
    }

    @Override
    protected void layoutChildren() {
        double labelWidth = computeLabelWidth();
        double rowStartY = getPadding().getTop();

        for (FormRow row : rows) {
            rowStartY = row.layoutInParent(getPadding().getLeft(), rowStartY, getWidth() - getPadding().getLeft() - getPadding().getRight(), labelWidth, rowSpacing.get(), labelEditorSpacing.get()) + ROW_MARGIN;
        }
    }

    private double computeLabelWidth() {
        double width = 0;
        for (FormRow row : rows) {
            double prefLabelWidth = row.getPrefLabelWidth();
            if (prefLabelWidth > width) {
                width = prefLabelWidth;
            }
        }
        return width;
    }
}
