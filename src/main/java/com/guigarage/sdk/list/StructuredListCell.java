package com.guigarage.sdk.list;

import com.guigarage.sdk.css.CssHelper;
import com.guigarage.sdk.css.DefaultPropertyBasedCssMetaData;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class StructuredListCell<T> extends ListCell<T> {

    private ObjectProperty<Node> leftContent;

    private ObjectProperty<Node> centerContent;

    private ObjectProperty<Node> rightContent;

    public StructuredListCell() {
        getStyleClass().add("structured-list-cell");

        leftContent = new SimpleObjectProperty<>();
        centerContent = new SimpleObjectProperty<>();
        rightContent = new SimpleObjectProperty<>();

        addEventHandler(MouseEvent.MOUSE_CLICKED, e -> simpleSelect());
    }

    public Node getLeftContent() {
        return leftContent.get();
    }

    public void setLeftContent(Node leftContent) {
        this.leftContent.set(leftContent);
    }

    public ObjectProperty<Node> leftContentProperty() {
        return leftContent;
    }

    public Node getCenterContent() {
        return centerContent.get();
    }

    public void setCenterContent(Node centerContent) {
        this.centerContent.set(centerContent);
    }

    public ObjectProperty<Node> centerContentProperty() {
        return centerContent;
    }

    public Node getRightContent() {
        return rightContent.get();
    }

    public void setRightContent(Node rightContent) {
        this.rightContent.set(rightContent);
    }

    public ObjectProperty<Node> rightContentProperty() {
        return rightContent;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new StructuredListCellSkin(this);
    }

    private void simpleSelect() {
        ListView lv = getListView();
        int index = getIndex();
        MultipleSelectionModel sm = lv.getSelectionModel();
        lv.getSelectionModel().clearAndSelect(index);
    }

    private static class StyleableProperties {
        private static final DefaultPropertyBasedCssMetaData<StructuredListCell, Number> SPACING = CssHelper.createMetaData("-fx-spacing", StyleConverter.getSizeConverter(), "spacing", 0);
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES = CssHelper.createCssMetaDataList(StructuredListCell.getClassCssMetaData(), SPACING);
    }

}
