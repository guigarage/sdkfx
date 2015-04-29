package com.guigarage.sdk.util;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RoundImageViewSkin extends SkinBase<RoundImageView> {

    private Circle circleClip;

    private Circle overlayCircle;

    private ImageView imageView;

    public RoundImageViewSkin(RoundImageView control) {
        super(control);

        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        circleClip = new Circle();
        imageView.setClip(circleClip);

        overlayCircle = new Circle();
        overlayCircle.getStyleClass().add("round-image-view-circle");
        overlayCircle.setFill(Color.TRANSPARENT);

        getChildren().add(imageView);
        getChildren().add(overlayCircle);

        imageView.imageProperty().bind(getSkinnable().imageProperty());

        getSkinnable().setMaxWidth(Region.USE_PREF_SIZE);
        getSkinnable().setMinWidth(Region.USE_PREF_SIZE);
        getSkinnable().setMaxHeight(Region.USE_PREF_SIZE);
        getSkinnable().setMinHeight(Region.USE_PREF_SIZE);
    }



    protected double computeSize() {
        return getSkinnable().defaultSizeProperty().doubleValue();
    }

    @Override
    protected double computePrefHeight(double width, double topInset,
                                       double rightInset, double bottomInset, double leftInset) {
        return computeSize() + topInset + bottomInset;
    }

    @Override
    protected double computePrefWidth(double height, double topInset,
                                      double rightInset, double bottomInset, double leftInset) {
        return computeSize() + leftInset + topInset;
    }

    @Override
    protected void layoutChildren(double contentX, double contentY,
                                  double contentWidth, double contentHeight) {

        if (contentHeight > contentWidth) {
            contentY = contentY + (contentHeight - contentWidth) / 2;
            contentHeight = contentWidth;
        } else {
            contentX = contentX + (contentWidth - contentHeight) / 2;
            contentWidth = contentHeight;
        }

        overlayCircle.setCenterX(contentX + contentWidth / 2);
        overlayCircle.setCenterY(contentY + contentHeight / 2);
        overlayCircle.setRadius(contentHeight / 2);

        imageView.relocate(contentX, contentY);
        imageView.resize(contentWidth, contentHeight);

        if(imageView.getImage() != null) {
            Image currentImage = imageView.getImage();

            circleClip.setCenterX(contentWidth / 2);
            if(currentImage.getHeight() > currentImage.getWidth()) {
                imageView.setFitWidth(contentWidth);
                imageView.setFitHeight(-1);
                double factor = currentImage.getWidth() / contentWidth;
                imageView.setViewport(new Rectangle2D(0, (currentImage.getHeight() - contentHeight * factor) / 2, currentImage.getWidth(), currentImage.getHeight()));
            } else {
                imageView.setFitHeight(contentHeight);
                imageView.setFitWidth(-1);
                double factor = currentImage.getHeight() / contentHeight;
                imageView.setViewport(new Rectangle2D((currentImage.getWidth() - contentWidth * factor) / 2, 0, currentImage.getWidth(), currentImage.getHeight()));
            }
            circleClip.setCenterY(contentWidth / 2);
        circleClip.setRadius(contentWidth / 2);
        }
    }
}

