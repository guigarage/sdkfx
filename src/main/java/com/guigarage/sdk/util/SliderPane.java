package com.guigarage.sdk.util;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.RotateEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by hendrikebbers on 18.04.15.
 */
public class SliderPane extends Region {

    private ObjectProperty<Node> content;

    private ObjectProperty<Node> popover;

    private javafx.scene.layout.Pane contentWrapper;

    private javafx.scene.layout.Pane popoverWrapper;

    private ParallelTransition mouseExitTransition;

    private ParallelTransition mouseEnteredTransition;

    private DoubleProperty popupVisiblePercents;

    private double slideInDuration = 0.4;

    private double slideOutDuration = 0.4;

    private BooleanProperty pinned;

    private BooleanProperty popoverVisible;

    private Region contentClassPane;

    public SliderPane() {

        setMaxHeight(Double.MAX_VALUE - 1);

        getStyleClass().add("slider-pane");

        Rectangle clipRect = new Rectangle();
        clipRect.setX(0);
        clipRect.setY(0);
        clipRect.widthProperty().bind(widthProperty());
        clipRect.heightProperty().bind(heightProperty());

        setClip(clipRect);

        pinned = new SimpleBooleanProperty(true);
        pinned.addListener(e -> {
            requestLayout();
        });

        contentClassPane = new StackPane();
        contentClassPane.setId("slider-pane-glasspane");


        contentWrapper = new javafx.scene.layout.Pane() {

            @Override
            protected void layoutChildren() {
                super.layoutChildren();

                if (content.get() != null) {
                    content.get().resize(contentWrapper.getWidth(), contentWrapper.getHeight());
                    content.get().relocate(0, 0);
                }

                contentClassPane.relocate(0, 0);
                contentClassPane.resize(contentWrapper.getWidth(), contentWrapper.getHeight());
            }

            @Override
            protected double computePrefWidth(double height) {
                if (content.get() != null) {
                    return content.get().prefWidth(height);
                }
                return super.computePrefWidth(height);
            }
        };

        popoverWrapper = new javafx.scene.layout.Pane() {

            @Override
            protected void layoutChildren() {
                super.layoutChildren();

                if (popover.get() != null) {
                    popover.get().resize(popoverWrapper.getWidth() - popoverWrapper.getPadding().getLeft() - popoverWrapper.getPadding().getRight(), popoverWrapper.getHeight() - popoverWrapper.getPadding().getTop() - popoverWrapper.getPadding().getBottom());
                    popover.get().relocate(popoverWrapper.getPadding().getLeft(), popoverWrapper.getPadding().getTop());
                }
            }

            @Override
            protected double computePrefWidth(double height) {
                double popoverWidth = 0;
                if (popover.get() != null) {
                    return popoverWrapper.getPadding().getLeft() + popover.get().prefWidth(height) + popoverWrapper.getPadding().getRight();
                }
                return super.computePrefWidth(height);
            }
        };

        popoverWrapper.getStyleClass().add("slider-pane-popup");

        popupVisiblePercents = new SimpleDoubleProperty();
        popoverWrapper.translateXProperty().bind(popoverWrapper.widthProperty().multiply(popupVisiblePercents).negate());



        content = new SimpleObjectProperty<>();
        popover = new SimpleObjectProperty<>();

        InvalidationListener contentListener = e -> {
            popoverWrapper.getChildren().clear();
            contentWrapper.getChildren().clear();
            getChildren().clear();

            if (content.get() != null) {
                contentWrapper.getChildren().add(content.get());
                getChildren().addAll(contentWrapper, contentClassPane);
            }
            if (popover.get() != null) {
                popoverWrapper.getChildren().add(popover.get());
                getChildren().add(popoverWrapper);
            }
        };

        content.addListener(contentListener);

        popover.addListener(contentListener);

        pinned.addListener(e -> {
            if (pinned.get()) {
                if (mouseEnteredTransition != null) {
                    mouseEnteredTransition.stop();
                }
                if (mouseExitTransition != null) {
                    mouseExitTransition.stop();
                }
                popupVisiblePercents.unbind();
                popupVisiblePercents.set(0.0);
                if (popover.get() != null) {
                    popover.get().setOpacity(1.0);
                }
            }
        });


        popoverVisible = new SimpleBooleanProperty();
        popoverVisible.bind(popupVisiblePercents.isEqualTo(0));

        popoverWrapper.visibleProperty().bind(popupVisiblePercents.isEqualTo(1).not());

        contentClassPane.opacityProperty().bind(popupVisiblePercents.negate().add(1.0));
        contentClassPane.visibleProperty().bind(popupVisiblePercents.isEqualTo(1).not());

        contentClassPane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> hidePopover());
        contentClassPane.addEventFilter(TouchEvent.TOUCH_PRESSED, e -> hidePopover());

        addEventFilter(SwipeEvent.SWIPE_RIGHT, e -> showPopover());
        addEventFilter(SwipeEvent.SWIPE_LEFT, e -> hidePopover());

        //Erst einmal das Popup ausblenden:
        popupVisiblePercents.setValue(1);

    }

    public void showPopover() {
        popupVisiblePercents.unbind();
        if (mouseExitTransition != null) {
            mouseExitTransition.stop();
        }

        BindableTransition slideAnimation = new BindableTransition(Duration.seconds(slideInDuration));
        slideAnimation.setStartValue(popupVisiblePercents.doubleValue());
        slideAnimation.setEndValue(0.0);
        popupVisiblePercents.bind(slideAnimation.fractionProperty());

        mouseEnteredTransition = new ParallelTransition();
        mouseEnteredTransition.getChildren().add(slideAnimation);
        mouseEnteredTransition.setCycleCount(1);
        mouseEnteredTransition.setInterpolator(Interpolator.EASE_OUT);

        if (popover.get() != null) {
            FadeTransition fadeTransition2 = new FadeTransition(slideAnimation.getTotalDuration());
            fadeTransition2.setFromValue(popover.get().getOpacity());
            fadeTransition2.setToValue(1.0);
            fadeTransition2.setNode(popover.get());
            mouseEnteredTransition.getChildren().add(fadeTransition2);
        }

        mouseEnteredTransition.play();
    }

    public void hidePopover() {
        popupVisiblePercents.unbind();
        if (mouseEnteredTransition != null) {
            mouseEnteredTransition.stop();
        }

        BindableTransition slideAnimation = new BindableTransition(Duration.seconds(slideOutDuration));
        slideAnimation.setStartValue(popupVisiblePercents.doubleValue());
        popupVisiblePercents.bind(slideAnimation.fractionProperty());

        mouseExitTransition = new ParallelTransition();
        mouseExitTransition.getChildren().add(slideAnimation);
        mouseExitTransition.setCycleCount(1);
        mouseExitTransition.setInterpolator(Interpolator.EASE_IN);

        if (popover.get() != null) {
            FadeTransition fadeTransition2 = new FadeTransition(slideAnimation.getTotalDuration());
            fadeTransition2.setFromValue(popover.get().getOpacity());
            fadeTransition2.setToValue(0.0);
            fadeTransition2.setNode(popover.get());
            mouseExitTransition.getChildren().add(fadeTransition2);
        }

        mouseExitTransition.play();
    }

    public boolean getPinned() {
        return pinned.get();
    }

    public BooleanProperty pinnedProperty() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned.set(pinned);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        double prefWidth = popoverWrapper.prefWidth(getHeight());

        popoverWrapper.resize(Math.min(prefWidth, getWidth() - 64), getHeight());
        popoverWrapper.relocate(0, 0);

        Node contentNode = content.get();
        if (pinned.get()) {
            contentWrapper.resize(getWidth() - getPadding().getLeft() - getPadding().getRight() - popoverWrapper.getWidth(), getHeight() - getPadding().getTop() - getPadding().getBottom());
            contentWrapper.relocate(popoverWrapper.getWidth() + getPadding().getLeft(), getPadding().getTop());
        } else {
            contentWrapper.resize(getWidth() - getPadding().getLeft() - getPadding().getRight(), getHeight() - getPadding().getTop() - getPadding().getBottom());
            contentWrapper.relocate(getPadding().getLeft(), getPadding().getTop());

        }
    }

    @Override
    protected double computeMinWidth(double height) {
        if (content.get() != null) {
            return content.get().minWidth(height) + getPadding().getLeft() + getPadding().getRight();
        }
        return super.computeMinWidth(height);
    }

    @Override
    protected double computeMinHeight(double width) {
        if (content.get() != null) {
            return content.get().minHeight(width) + getPadding().getTop() + getPadding().getBottom();
        }
        return super.computeMinHeight(width);
    }

    @Override
    protected double computePrefWidth(double height) {
        if (!pinned.get()) {
            if (content.get() != null) {
                return content.get().prefWidth(height) + getPadding().getLeft() + getPadding().getRight();
            }
        } else {
            double contentWidth = 0;
            if (content.get() != null) {
                contentWidth = content.get().prefWidth(height) + getPadding().getLeft() + getPadding().getRight();
            }
            return popoverWrapper.getWidth() + contentWidth;
        }
        return super.computePrefWidth(height);
    }

    @Override
    protected double computePrefHeight(double width) {
        if (content.get() != null) {
            return content.get().prefHeight(width) + getPadding().getTop() + getPadding().getBottom();
        }
        return super.computePrefHeight(width);
    }

    @Override
    protected double computeMaxWidth(double height) {
        if (!pinned.get()) {
            if (content.get() != null) {
                return content.get().maxWidth(height) + getPadding().getLeft() + getPadding().getRight();
            }
        } else {
            double contentWidth = 0;
            if (content.get() != null) {
                contentWidth = content.get().maxWidth(height) + getPadding().getLeft() + getPadding().getRight();
            }
            return popoverWrapper.getWidth() + contentWidth;
        }
        return super.computeMaxWidth(height);
    }

    @Override
    protected double computeMaxHeight(double width) {
        if (content.get() != null) {
            return content.get().maxHeight(width) + getPadding().getTop() + getPadding().getBottom();
        }
        return super.computeMaxHeight(width);
    }

    public Node getContent() {
        return content.get();
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    public void setContent(Node content) {
        this.content.set(content);
    }

    public boolean isPopoverVisible() {
        return popoverVisible.get();
    }

    public BooleanProperty popoverVisibleProperty() {
        return popoverVisible;
    }

    public void setPopoverVisible(boolean popoverVisible) {
        this.popoverVisible.set(popoverVisible);
    }

    public Node getPopover() {
        return popover.get();
    }

    public ObjectProperty<Node> popoverProperty() {
        return popover;
    }

    public void setPopover(Node popover) {
        this.popover.set(popover);
    }
}
