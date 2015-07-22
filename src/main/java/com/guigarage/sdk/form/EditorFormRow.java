package com.guigarage.sdk.form;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class EditorFormRow<T extends Node> implements FormRow {

    private Label label;

    private T editor;

    private FormEditor formEditor;

    /**
     *
     * @param labelText The forms row label for this editor
     * @param editor e.g. new TextField(), new TextArea(), new CheckBox(), new ListView<>()
     */
    public EditorFormRow(String labelText, T editor) {
        this.editor = editor;
        this.label = createLabel(labelText, editor);

        label.disableProperty().bind(editor.disableProperty());
        label.visibleProperty().bind(editor.visibleProperty());

        StringProperty nameProperty = new SimpleStringProperty();
        nameProperty.addListener(e -> label.setText(nameProperty.get() + ":"));

        formEditor = new FormEditor(editor.disableProperty(), nameProperty, editor.visibleProperty());
    }

    private Label createLabel(String text, Node editor) {
        return createLabel(new SimpleStringProperty(text), editor);
    }

    private Label createLabel(StringProperty text, Node editor) {
        Label label = new Label();
        label.getStyleClass().add("form-label");
        text.addListener(e -> label.setText(text.get() + ":"));
        label.setText(text.get() + ":");
        label.setTextAlignment(TextAlignment.RIGHT);
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setLabelFor(editor);
        return label;
    }

    @Override
    public double getPrefLabelWidth() {
        return label.prefWidth(-1);
    }

    @Override
    public double layoutInParent(double startX, double startY, double width, double labelWidth, double rowSpacing, double columnSpacing) {
        if (label.isVisible()) {
            label.relocate(startX, startY);
            label.resize(labelWidth, label.prefHeight(labelWidth));

            double editorWidth = width - columnSpacing - labelWidth;
            editor.relocate(label.getLayoutX() + label.getWidth() + columnSpacing, startY);
            editor.resize(editorWidth, editor.prefHeight(editorWidth));

            return startY + Math.max(label.getHeight(), editor.getLayoutBounds().getHeight()) + rowSpacing;
        } else {
            return startY;
        }
    }

    @Override
    public List<Node> getNodes() {
        return FXCollections.observableArrayList(label, editor);
    }

    public Label getLabel() {
        return label;
    }

    public T getEditor() {
        return editor;
    }

    public FormEditor getFormEditor() {
        return formEditor;
    }

    public static EditorFormRow newEditorFormRow(String label, EditorType editorType) {
        EditorFormRow row = null;
        switch (editorType) {
            case TEXTFIELD:
                row = newTextFieldRow(label);
                break;
            case TEXTAREA:
                row = newTextAreaRow(label);
                break;
            case CHECKBOX:
                row = newCheckBoxRow(label);
                break;
            case COMBOBOX:
                row = newComboBox(label);
                break;
            case LIST:
                row = newListViewRow(label);
                break;
        }
        return row;
    }

    public static EditorFormRow<TextField> newTextFieldRow(String label) {
        return new EditorFormRow<>(label, new TextField());
    }

    public static EditorFormRow<TextArea> newTextAreaRow(String label) {
        return new EditorFormRow<>(label, new TextArea());
    }

    public static EditorFormRow<CheckBox> newCheckBoxRow(String label) {
        return new EditorFormRow<>(label, new CheckBox());
    }

    public static <A> EditorFormRow<ComboBox<A>> newComboBox(String label) {
        return new EditorFormRow<>(label, new ComboBox<>());
    }

    public static <A> EditorFormRow<ListView<A>> newListViewRow(String label) {
        return new EditorFormRow<>(label, new ListView<>());
    }
}
