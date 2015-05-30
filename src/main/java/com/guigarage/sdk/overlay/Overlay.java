package com.guigarage.sdk.overlay;


import com.guigarage.sdk.action.Action;
import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.List;

/**
 * Created by hendrikebbers on 29.05.15.
 */
public class Overlay extends Region {

    //TODO: TITLE, DESCRIPTION, ACTIONS

    private List<Action> actions;

    private Label titleLabel;

    private Label descriptionLabel;

    private Line seperator;

    private Animation showAnimation;

    private Animation hideAnimation;

    private BooleanProperty hidden;

    public Overlay() {
        getStyleClass().add("overlay");

        titleLabel = new Label("Beschreibung");
        titleLabel.getStyleClass().addAll("overlay-content", "overlay-title");

        descriptionLabel = new Label("Ich bin die Beschreibung des aktuell angezeigten Datensatzes. Ich kann schon mal länger sein als so ein langweiliger Titel. Daher muss ich natürlich vernünftig umbrechen.");
        descriptionLabel.getStyleClass().addAll("overlay-content", "overlay-description");
        descriptionLabel.setWrapText(true);

        seperator = new Line();
        seperator.getStyleClass().addAll("overlay-content", "overlay-seperator");

        getChildren().addAll(titleLabel, descriptionLabel, seperator);

        hidden = new SimpleBooleanProperty(false);

    }

    public boolean isHidden() {
        return hidden.get();
    }

    public void toggleVisibility() {
        if(isHidden()) {
            show();
        } else {
            hide();
        }
    }

    private void stopAnimations() {
        if (showAnimation != null) {
            showAnimation.pause();
        }
        if (hideAnimation != null) {
            hideAnimation.pause();
        }
    }

    public void hide() {
        stopAnimations();

        hidden.setValue(true);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(360), this);
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        translateTransition.setFromY(getTranslateY());
        translateTransition.toYProperty().bind(heightProperty());

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(360), this);
        fadeTransition.setFromValue(getOpacity());
        fadeTransition.setToValue(0);

        hideAnimation = new ParallelTransition(translateTransition, fadeTransition);
        hideAnimation.play();
    }

    public void show() {
        stopAnimations();

        hidden.setValue(false);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(360), this);
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        translateTransition.setFromY(getTranslateY());
        translateTransition.setToY(0);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(360), this);
        fadeTransition.setFromValue(getOpacity());
        fadeTransition.setToValue(1.0);

        showAnimation = new ParallelTransition(translateTransition, fadeTransition);
        showAnimation.play();
    }

    @Override
    public Orientation getContentBias() {
        return Orientation.HORIZONTAL;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        titleLabel.relocate(getPadding().getLeft(), getPadding().getTop());
        double titleLabelHeight = titleLabel.prefHeight(getWidth() - getPadding().getLeft() - getPadding().getRight());
        titleLabel.resize(getWidth() - getPadding().getLeft() - getPadding().getRight(), titleLabelHeight);

        seperator.setStartX(getPadding().getLeft());
        seperator.setEndX(getWidth() - getPadding().getRight());
        seperator.setStartY(getPadding().getTop() + titleLabelHeight + 2);
        seperator.setEndY(getPadding().getTop() + titleLabelHeight + 6);

        descriptionLabel.relocate(getPadding().getLeft(), getPadding().getTop() + titleLabelHeight + 2 + 6);
        double descriptionLabelHeight = descriptionLabel.prefHeight(getWidth() - getPadding().getLeft() - getPadding().getRight());
        descriptionLabel.resize(getWidth() - getPadding().getLeft() - getPadding().getRight(), descriptionLabelHeight);
    }

    @Override
    protected double computePrefHeight(double width) {
        double widthWithPadding = width - getPadding().getLeft() - getPadding().getRight();
        return getPadding().getTop() + titleLabel.prefHeight(widthWithPadding) + 2 + seperator.prefHeight(widthWithPadding) + 6 + descriptionLabel.prefHeight(widthWithPadding) + getPadding().getBottom();
    }
}
