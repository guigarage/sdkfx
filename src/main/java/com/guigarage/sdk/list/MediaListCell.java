package com.guigarage.sdk.list;

import com.guigarage.sdk.css.CssHelper;
import com.guigarage.sdk.css.DefaultPropertyBasedCssMetaData;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class MediaListCell<T> extends StructuredListCell<T> {

    private Label titleLabel;

    private Label descriptionLabel;

    private VBox centerBox;

    private StyleableObjectProperty<Boolean> showDescription;

    private StringProperty title;

    private StringProperty description;

    private StyleableObjectProperty<Number> textSpacing;

    public MediaListCell() {
        getStyleClass().add("media-list-cell");
        title = new SimpleStringProperty();
        description = new SimpleStringProperty();
        textSpacing = CssHelper.createProperty(StyleableProperties.TEXT_SPACING, this);
        showDescription = CssHelper.createProperty(StyleableProperties.SHOW_DESCRIPTION, this);

        centerBox = new VBox();
        centerBox.spacingProperty().bind(textSpacing);

        titleLabel = new Label();
        titleLabel.getStyleClass().add("media-cell-title");
        titleLabel.textProperty().bind(title);
        VBox.setVgrow(titleLabel, Priority.NEVER);

        descriptionLabel = new Label();
        //TODO: CSS
        descriptionLabel.setWrapText(true);
        descriptionLabel.setTextOverrun(OverrunStyle.WORD_ELLIPSIS);
        descriptionLabel.getStyleClass().add("media-cell-description");
        descriptionLabel.textProperty().bind(description);
        VBox.setVgrow(titleLabel, Priority.ALWAYS);

        centerBox.getChildren().addAll(titleLabel, descriptionLabel);
        setCenterContent(centerBox);

        showDescription.addListener(e -> {
            descriptionLabel.setManaged(isShowDescription());
            descriptionLabel.setVisible(isShowDescription());
        });
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public Number getTextSpacing() {
        return textSpacing.get();
    }

    public void setTextSpacing(Number textSpacing) {
        this.textSpacing.set(textSpacing);
    }

    public ObjectProperty<Number> textSpacingProperty() {
        return textSpacing;
    }

    public boolean isShowDescription() {
        return showDescription.get();
    }

    public void setShowDescription(boolean showDescription) {
        this.showDescription.set(showDescription);
    }

    public StyleableObjectProperty<Boolean> showDescriptionProperty() {
        return showDescription;
    }

    private static class StyleableProperties {
        private static final DefaultPropertyBasedCssMetaData<MediaListCell, Boolean> SHOW_DESCRIPTION = CssHelper.createMetaData("-fx-show-description", StyleConverter.getBooleanConverter(), "showDescription", Boolean.FALSE);
        private static final DefaultPropertyBasedCssMetaData<MediaListCell, Number> TEXT_SPACING = CssHelper.createMetaData("-fx-text-spacing", StyleConverter.getSizeConverter(), "textSpacing", 0);
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES = CssHelper.createCssMetaDataList(StructuredListCell.getClassCssMetaData(), SHOW_DESCRIPTION, TEXT_SPACING);
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public Label getDescriptionLabel() {
        return descriptionLabel;
    }
}
