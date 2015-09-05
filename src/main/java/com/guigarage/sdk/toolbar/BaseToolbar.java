package com.guigarage.sdk.toolbar;

import com.guigarage.sdk.action.Action;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseToolbar extends Region {

    private ObjectProperty<Node> leftNode;

    private StringProperty title;

    private ObservableList<Action> actionItems;

    private HBox actionBox;

    protected Text titleText;

    private Map<Action, Node> itemToNode;

    private BooleanProperty large;

    private DoubleProperty headlineOffset;

    private DoubleProperty smallHeight;

    private DoubleProperty largeHeight;

    private Animation toLargeAnimation;

    private Animation toSmallAnimation;

    private PseudoClass largePseudoClass = PseudoClass.getPseudoClass("large");

    private ImageView backgroundImageView;

    private Line bottomLine;

    public BaseToolbar() {
        getStyleClass().add("base-toolbar");
        title = new SimpleStringProperty();
        actionItems = FXCollections.observableArrayList();

        actionBox = new HBox();
        actionBox.getStyleClass().add("action-box");
        getChildren().add(actionBox);

        itemToNode = new HashMap<>();

        actionItems.addListener((ListChangeListener<Action>) c -> {
            while (c.next()) {
                if (!c.wasPermutated() && !c.wasUpdated()) {
                    for (Action removed : c.getRemoved()) {
                        Node child = itemToNode.remove(removed);
                        actionBox.getChildren().remove(child);
                    }
                    for (Action added : c.getAddedSubList()) {
                        Button button = new Button();
                        button.setText(added.getIcon().getText());
                        button.setOnMouseEntered(e -> {
                            button.setScaleX(1.1);
                            button.setScaleY(1.1);
                        });
                        button.setOnMouseExited(e -> {
                            button.setScaleX(1.0);
                            button.setScaleY(1.0);
                        });
                        button.setOnAction(e -> {
                            if (added.getCallback() != null) {
                                added.getCallback().call();
                            }
                        });

                        actionBox.getChildren().add(button);
                        itemToNode.put(added, button);
                    }
                }
            }
        });

        titleText = new Text();
        titleText.getStyleClass().add("text");
        titleText.textProperty().bind(title);
        getChildren().add(titleText);

        leftNode = new SimpleObjectProperty<>();
        leftNode.addListener((obs, oldValue, newValue) -> {
            if (oldValue != null) {
                getChildren().remove(oldValue);
            }
            if (newValue != null) {
                getChildren().add(newValue);
            }
        });

        smallHeight = new SimpleDoubleProperty(64);
        largeHeight = new SimpleDoubleProperty(192);

        headlineOffset = new SimpleDoubleProperty(0);
        headlineOffset.bind(prefHeightProperty().subtract(smallHeight));

        large = new SimpleBooleanProperty(false);
        large.addListener(e -> {
            pseudoClassStateChanged(largePseudoClass, large.get());
            if (large.get()) {
                setPrefHeight(largeHeight.get());
            } else {
                setPrefHeight(smallHeight.get());
            }
        });
        setPrefHeight(smallHeight.get());
        setMaxHeight(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);

        backgroundImageView = new ImageView();
        backgroundImageView.getStyleClass().add("toolbar-background-image-view");
        backgroundImageView.setPreserveRatio(true);
        backgroundImageView.setSmooth(true);
        backgroundImageView.fitWidthProperty().bind(widthProperty());
        backgroundImageView.setOpacity(0);

        getChildren().add(backgroundImageView);

        bottomLine = new Line();
        bottomLine.setStroke(Color.BLACK);
        bottomLine.setStrokeWidth(2);
        bottomLine.setStartX(0);
        bottomLine.endXProperty().bind(widthProperty());
        bottomLine.startYProperty().bind(heightProperty());
        bottomLine.endYProperty().bind(heightProperty());
        getChildren().add(bottomLine);
    }

    public void setBackgroundImage(Image image) {
        backgroundImageView.setImage(image);
    }

    public void setBackgroundImage(String path) {
        backgroundImageView.setImage(new Image(path));
    }

    public Image getBackgroundImage() {
        return backgroundImageView.getImage();
    }

    public boolean isLarge() {
        return large.get();
    }

    private void stopAnimations() {
        if (toLargeAnimation != null) {
            toLargeAnimation.pause();
        }
        if (toSmallAnimation != null) {
            toSmallAnimation.pause();
        }
    }

    public void animateToSmallVersion() {
        if(isLarge()) {
            stopAnimations();
            final double currentHeight = getHeight();

            double resizeTime = 480;
            double fadeTime = 300;

            FadeTransition fadeTitleOutTransition = new FadeTransition(Duration.millis(fadeTime), titleText);
            fadeTitleOutTransition.setFromValue(titleText.getOpacity());
            fadeTitleOutTransition.setToValue(0);


            FadeTransition fadeTitleInTransition = new FadeTransition(Duration.millis(fadeTime), titleText);
            fadeTitleInTransition.setFromValue(0);
            fadeTitleInTransition.setToValue(1);
            fadeTitleInTransition.setDelay(Duration.millis(fadeTime + resizeTime));

            Transition resizeAnimation = new Transition() {
                {
                    setCycleDuration(Duration.millis(resizeTime));
                }

                @Override
                protected void interpolate(double frac) {
                    setPrefHeight(currentHeight + (smallHeight.get() - currentHeight) * frac);
                    backgroundImageView.setOpacity(1 - frac);
                }
            };
            resizeAnimation.setDelay(Duration.millis(fadeTime));
            resizeAnimation.setOnFinished(e -> large.setValue(false));

            toSmallAnimation = new ParallelTransition(fadeTitleOutTransition, resizeAnimation, fadeTitleInTransition);

            toSmallAnimation.play();
        }
    }

    public void animateToLargeVersion() {
        if(!isLarge()) {
            stopAnimations();
            final double currentHeight = getHeight();

            double resizeTime = 480;
            double fadeTime = 300;

            FadeTransition fadeTitleOutTransition = new FadeTransition(Duration.millis(fadeTime), titleText);
            fadeTitleOutTransition.setFromValue(titleText.getOpacity());
            fadeTitleOutTransition.setToValue(0);


            FadeTransition fadeTitleInTransition = new FadeTransition(Duration.millis(fadeTime), titleText);
            fadeTitleInTransition.setFromValue(0);
            fadeTitleInTransition.setToValue(1);
            fadeTitleInTransition.setDelay(Duration.millis(fadeTime + resizeTime));

            Transition resizeAnimation = new Transition() {
                {
                    setCycleDuration(Duration.millis(resizeTime));
                }

                @Override
                protected void interpolate(double frac) {
                    setPrefHeight(currentHeight + (largeHeight.get() - currentHeight) * frac);
                    backgroundImageView.setOpacity(frac);
                }
            };
            resizeAnimation.setDelay(Duration.millis(fadeTime));
            resizeAnimation.setOnFinished(e -> large.setValue(true));

            toLargeAnimation = new ParallelTransition(fadeTitleOutTransition, resizeAnimation, fadeTitleInTransition);

            toLargeAnimation.play();
        }
    }

    public void setLarge(boolean large) {
        stopAnimations();
        this.large.set(large);
        if(large) {
            backgroundImageView.setOpacity(1);
        } else {
            backgroundImageView.setOpacity(0);
        }
    }

    protected ObjectProperty<Node> leftNodeProperty() {
        return leftNode;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        backgroundImageView.toBack();
        backgroundImageView.relocate(0,0);
        backgroundImageView.resize(getWidth(), getHeight());

        double actionBoxPrefWidth = actionBox.prefWidth(-1);
        double actionBoxPrefHeight = actionBox.prefHeight(-1);
        double leftNodePrefHeight = 0;
        double leftNodePrefWidth = 0;

        if (leftNode.get() != null) {
            leftNodePrefHeight = leftNode.get().prefHeight(-1);
            leftNodePrefWidth = leftNode.get().prefWidth(-1);
        }
        double titleTextPrefWidth = titleText.prefWidth(-1);
        double titleTextPrefHeight = titleText.prefHeight(-1);

        double centerOfLine = Math.max(actionBoxPrefHeight, leftNodePrefHeight);
        centerOfLine = Math.max(centerOfLine, titleTextPrefHeight);
        centerOfLine = centerOfLine / 2 + getPadding().getTop();

        actionBox.resize(actionBoxPrefWidth, actionBoxPrefHeight);
        actionBox.relocate(getWidth() - getPadding().getRight() - actionBoxPrefWidth, centerOfLine - actionBoxPrefHeight / 2);

        if (leftNode.get() != null) {
            leftNode.get().resize(leftNode.get().prefWidth(-1), leftNodePrefHeight);
            leftNode.get().relocate(getPadding().getLeft(), centerOfLine - leftNodePrefHeight / 2);
        }

        titleText.resize(titleTextPrefWidth, titleTextPrefHeight);
        if(isLarge()) {
            titleText.relocate(getPadding().getLeft() + leftNodePrefWidth + 24, centerOfLine - titleTextPrefHeight / 2 + headlineOffset.get());
        } else {
            titleText.relocate((getWidth() - titleTextPrefWidth) / 2, centerOfLine - titleTextPrefHeight / 2 + headlineOffset.get());
        }
    }

    public double getLargeHeight() {
        return largeHeight.get();
    }

    public DoubleProperty largeHeightProperty() {
        return largeHeight;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void addAction(Action action) {
        actionItems.add(action);
    }
}
