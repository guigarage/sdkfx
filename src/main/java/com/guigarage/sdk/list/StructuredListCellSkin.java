package com.guigarage.sdk.list;

import com.guigarage.sdk.css.CssHelper;
import com.guigarage.sdk.css.SkinPropertyBasedCssMetaData;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Region;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class StructuredListCellSkin extends SkinBase<StructuredListCell> {

    private StyleableObjectProperty<VPos> leftContentAlignment;
    private StyleableObjectProperty<VPos> rightContentAlignment;
    private StyleableObjectProperty<Pos> centerContentAlignment;
    private StyleableObjectProperty<Number> spacing;
    private StyleableObjectProperty<HPos> componentForPrefHeight;

    protected StructuredListCellSkin(StructuredListCell control) {
        super(control);
        getSkinnable().leftContentProperty().addListener(e -> updateContent());
        getSkinnable().centerContentProperty().addListener(e -> updateContent());
        getSkinnable().rightContentProperty().addListener(e -> updateContent());
        updateContent();

        leftContentAlignment = CssHelper.createProperty(StyleableProperties.LEFT_CONTENT_ALIGNMENT, this);
        rightContentAlignment = CssHelper.createProperty(StyleableProperties.RIGHT_CONTENT_ALIGNMENT, this);
        centerContentAlignment = CssHelper.createProperty(StyleableProperties.CENTER_CONTENT_ALIGNMENT, this);
        spacing = CssHelper.createProperty(StyleableProperties.SPACING, this);
        componentForPrefHeight = CssHelper.createProperty(StyleableProperties.CONTENT_FOR_PREF_HEIGHT, this);

        getSkinnable().itemProperty().addListener(e -> {
            updateVisibility();
        });

        getSkinnable().setMaxHeight(Region.USE_PREF_SIZE);
        getSkinnable().setMinHeight(Region.USE_PREF_SIZE);
    }

    public Number getSpacing() {
        return spacing.get();
    }

    public void setSpacing(Number spacing) {
        this.spacing.set(spacing);
    }

    public StyleableObjectProperty<Number> spacingProperty() {
        return spacing;
    }

    public VPos getLeftContentAlignment() {
        return leftContentAlignment.get();
    }

    public void setLeftContentAlignment(VPos leftContentAlignment) {
        this.leftContentAlignment.set(leftContentAlignment);
    }

    public StyleableObjectProperty<VPos> leftContentAlignmentProperty() {
        return leftContentAlignment;
    }

    public VPos getRightContentAlignment() {
        return rightContentAlignment.get();
    }

    public void setRightContentAlignment(VPos rightContentAlignment) {
        this.rightContentAlignment.set(rightContentAlignment);
    }

    public StyleableObjectProperty<VPos> rightContentAlignmentProperty() {
        return rightContentAlignment;
    }

    public Pos getCenterContentAlignment() {
        return centerContentAlignment.get();
    }

    public void setCenterContentAlignment(Pos centerContentAlignment) {
        this.centerContentAlignment.set(centerContentAlignment);
    }

    public StyleableObjectProperty<Pos> centerContentAlignmentProperty() {
        return centerContentAlignment;
    }

    public HPos getComponentForPrefHeight() {
        return componentForPrefHeight.get();
    }

    public void setComponentForPrefHeight(HPos componentForPrefHeight) {
        this.componentForPrefHeight.set(componentForPrefHeight);
    }

    public StyleableObjectProperty<HPos> componentForPrefHeightProperty() {
        return componentForPrefHeight;
    }

    private void updateVisibility() {
        Consumer<Node> c = n -> n.setVisible((getSkinnable().getItem() != null));
        Optional.ofNullable(getSkinnable().getLeftContent()).ifPresent(c);
        Optional.ofNullable(getSkinnable().getCenterContent()).ifPresent(c);
        Optional.ofNullable(getSkinnable().getRightContent()).ifPresent(c);
    }

    private void updateContent() {
        getChildren().clear();
        if (getSkinnable().leftContentProperty().get() != null) {
            getChildren().add(getSkinnable().getLeftContent());
        }
        if (getSkinnable().centerContentProperty().get() != null) {
            getChildren().add(getSkinnable().getCenterContent());
        }
        if (getSkinnable().rightContentProperty().get() != null) {
            getChildren().add(getSkinnable().getRightContent());
        }
        updateVisibility();
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        double leftContentWidth = 0;
        if (getSkinnable().getLeftContent() != null) {
            Node leftContent = getSkinnable().getLeftContent();
            leftContentWidth = leftContent.prefWidth(contentHeight);
            double leftContentHeight = Math.min(leftContent.prefHeight(leftContentWidth), contentHeight);
            leftContent.resize(leftContentWidth, leftContentHeight);
            if (leftContentAlignment.get().equals(VPos.TOP)) {
                leftContent.relocate(contentX, contentY);
            } else if (leftContentAlignment.get().equals(VPos.CENTER)) {
                leftContent.relocate(contentX, contentY + (contentHeight - leftContentHeight) / 2);
            } else {
                leftContent.relocate(contentX, contentY + (contentHeight - leftContentHeight));
            }
        }

        double rightContentWidth = 0;
        if (getSkinnable().getRightContent() != null) {
            Node rightContent = getSkinnable().getRightContent();
            rightContentWidth = rightContent.prefWidth(contentHeight);
            double rightContentHeight = Math.min(rightContent.prefHeight(rightContentWidth), contentHeight);
            rightContent.resize(rightContentWidth, rightContentHeight);
            if (rightContentAlignment.get().equals(VPos.TOP)) {
                rightContent.relocate(contentX + contentWidth - rightContentWidth, contentY);
            } else if (rightContentAlignment.get().equals(VPos.CENTER)) {
                rightContent.relocate(contentX + contentWidth - rightContentWidth, contentY + (contentHeight - rightContentHeight) / 2);
            } else {
                rightContent.relocate(contentX + contentWidth - rightContentWidth, contentY + (contentHeight - rightContentHeight));
            }
        }

        if (getSkinnable().getCenterContent() != null) {
            Node centerContent = getSkinnable().getCenterContent();

            double maxWidthForCenterContent = contentWidth - leftContentWidth - rightContentWidth - spacing.get().doubleValue() * 2;
            double maxHeigthForCenterContent = contentHeight;

            double centerContentWidth = Math.min(centerContent.maxWidth(contentHeight), maxWidthForCenterContent);
            double centerContentHeight = Math.min(centerContent.maxHeight(centerContentWidth), maxHeigthForCenterContent);
            centerContent.resize(centerContentWidth, centerContentHeight);

            if (centerContentAlignment.get().equals(Pos.TOP_LEFT)) {
                centerContent.relocate(contentX + leftContentWidth + spacing.get().doubleValue(), contentY);
            } else if (centerContentAlignment.get().equals(Pos.TOP_CENTER)) {
                centerContent.relocate(contentX + leftContentWidth + spacing.get().doubleValue() + (maxWidthForCenterContent - centerContentWidth) / 2, contentY);
            } else if (centerContentAlignment.get().equals(Pos.TOP_RIGHT)) {
                centerContent.relocate(contentX + leftContentWidth + spacing.get().doubleValue() + (maxWidthForCenterContent - centerContentWidth), contentY);
            } else if (centerContentAlignment.get().equals(Pos.CENTER_LEFT)) {
                centerContent.relocate(contentX + leftContentWidth + spacing.get().doubleValue(), contentY + (maxHeigthForCenterContent - centerContentHeight) / 2);
            } else if (centerContentAlignment.get().equals(Pos.CENTER)) {
                centerContent.relocate(contentX + leftContentWidth + spacing.get().doubleValue() + (maxWidthForCenterContent - centerContentWidth) / 2, contentY + (maxHeigthForCenterContent - centerContentHeight) / 2);
            } else if (centerContentAlignment.get().equals(Pos.CENTER_RIGHT)) {
                centerContent.relocate(contentX + leftContentWidth + spacing.get().doubleValue() + (maxWidthForCenterContent - centerContentWidth), contentY + (maxHeigthForCenterContent - centerContentHeight) / 2);
            } else if (centerContentAlignment.get().equals(Pos.BOTTOM_LEFT)) {
                centerContent.relocate(contentX + leftContentWidth + spacing.get().doubleValue(), contentY + (maxHeigthForCenterContent - centerContentHeight));
            } else if (centerContentAlignment.get().equals(Pos.BOTTOM_CENTER)) {
                centerContent.relocate(contentX + leftContentWidth + spacing.get().doubleValue() + (maxWidthForCenterContent - centerContentWidth) / 2, contentY + (maxHeigthForCenterContent - centerContentHeight));
            } else {
                centerContent.relocate(contentX + leftContentWidth + spacing.get().doubleValue() + (maxWidthForCenterContent - centerContentWidth), contentY + (maxHeigthForCenterContent - centerContentHeight));
            }
        }
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double leftComponentMinWidth = Optional.ofNullable(getSkinnable().getLeftContent()).map(n -> n.minWidth(height)).orElse(0d);
        double rightComponentMinWidth = Optional.ofNullable(getSkinnable().getRightContent()).map(n -> n.minWidth(height)).orElse(0d);
        double centerComponentMinWidth = Optional.ofNullable(getSkinnable().getCenterContent()).map(n -> n.minWidth(height)).orElse(0d);
        return leftInset + leftComponentMinWidth + spacing.get().doubleValue() + centerComponentMinWidth + spacing.get().doubleValue() + rightComponentMinWidth + rightInset;
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double leftComponentMaxWidth = Optional.ofNullable(getSkinnable().getLeftContent()).map(n -> n.maxWidth(height)).orElse(0d);
        double rightComponentMaxWidth = Optional.ofNullable(getSkinnable().getRightContent()).map(n -> n.maxWidth(height)).orElse(0d);
        double centerComponentMaxWidth = Optional.ofNullable(getSkinnable().getCenterContent()).map(n -> n.maxWidth(height)).orElse(0d);
        return leftInset + leftComponentMaxWidth + spacing.get().doubleValue() + centerComponentMaxWidth + spacing.get().doubleValue() + rightComponentMaxWidth + rightInset;
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        double prefWidthOfLeftComponent = Optional.ofNullable(getSkinnable().getLeftContent()).map(n -> n.prefWidth(-1)).orElse(0d);
        double minWidthOfCenterComponent = Optional.ofNullable(getSkinnable().getCenterContent()).map(n -> n.minWidth(-1)).orElse(0d);
        double prefWidthOfRightComponent = Optional.ofNullable(getSkinnable().getRightContent()).map(n -> n.prefWidth(-1)).orElse(0d);

        if (componentForPrefHeight.get().equals(HPos.LEFT)) {
            if (getSkinnable().getLeftContent() == null) {
                return super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
            } else {
                double useableWidth = width - getSpacing().doubleValue() - minWidthOfCenterComponent - getSpacing().doubleValue() - prefWidthOfRightComponent;
                return Math.max(getSkinnable().getLeftContent().prefHeight(useableWidth) + topInset + bottomInset, super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset));
            }
        } else if (componentForPrefHeight.get().equals(HPos.RIGHT)) {
            if (getSkinnable().getRightContent() == null) {
                return super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
            } else {
                double useableWidth = width - prefWidthOfLeftComponent - getSpacing().doubleValue() - minWidthOfCenterComponent - getSpacing().doubleValue();
                return Math.max(getSkinnable().getRightContent().prefHeight(useableWidth) + topInset + bottomInset, super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset));
            }
        } else {
            if (getSkinnable().getCenterContent() == null) {
                return super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
            } else {
                double useableWidth = width - prefWidthOfLeftComponent - getSpacing().doubleValue() - getSpacing().doubleValue() - prefWidthOfRightComponent;

                //TODO: Hack but negative width isn't working if center contains a text with word wrap...
                if ((useableWidth <= 0)) {
                    return super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
                }
                return Math.max(getSkinnable().getCenterContent().prefHeight(useableWidth) + topInset + bottomInset, super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset));
            }
        }
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double leftComponentPrefWidth = Optional.ofNullable(getSkinnable().getLeftContent()).map(n -> n.prefWidth(height)).orElse(0d);
        double rightComponentPrefWidth = Optional.ofNullable(getSkinnable().getRightContent()).map(n -> n.prefWidth(height)).orElse(0d);
        double centerComponentMinWidth = Optional.ofNullable(getSkinnable().getCenterContent()).map(n -> n.minWidth(height)).orElse(0d);
        return leftInset + leftComponentPrefWidth + spacing.get().doubleValue() + centerComponentMinWidth + spacing.get().doubleValue() + rightComponentPrefWidth + rightInset;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    private static class StyleableProperties {
        private static final SkinPropertyBasedCssMetaData<StructuredListCell, VPos> LEFT_CONTENT_ALIGNMENT = CssHelper.createSkinMetaDataForVPos("-fx-left-alignment", "leftContentAlignment", VPos.TOP);
        private static final SkinPropertyBasedCssMetaData<StructuredListCell, VPos> RIGHT_CONTENT_ALIGNMENT = CssHelper.createSkinMetaDataForVPos("-fx-right-alignment", "rightContentAlignment", VPos.TOP);
        private static final SkinPropertyBasedCssMetaData<StructuredListCell, HPos> CONTENT_FOR_PREF_HEIGHT = CssHelper.createSkinMetaDataForHPos("-fx-height-rule", "componentForPrefHeight", HPos.CENTER);
        private static final SkinPropertyBasedCssMetaData<StructuredListCell, Number> SPACING = CssHelper.createSkinMetaData("-fx-spacing", StyleConverter.getSizeConverter(), "spacing", 0);
        private static final SkinPropertyBasedCssMetaData<StructuredListCell, Pos> CENTER_CONTENT_ALIGNMENT = CssHelper.createSkinMetaDataForPos("-fx-center-alignment", "centerContentAlignment", Pos.TOP_LEFT);

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES = CssHelper.createCssMetaDataList(StructuredListCell.getClassCssMetaData(), LEFT_CONTENT_ALIGNMENT, RIGHT_CONTENT_ALIGNMENT, CONTENT_FOR_PREF_HEIGHT, SPACING, CENTER_CONTENT_ALIGNMENT);
    }
}
