package com.guigarage.sdk;

import com.guigarage.sdk.action.Action;
import com.guigarage.sdk.actionbutton.GlobalActionButton;
import com.guigarage.sdk.container.Workbench;
import com.guigarage.sdk.menu.MenuPane;
import com.guigarage.sdk.toolbar.ApplicationToolbar;
import com.guigarage.sdk.util.Callback;
import com.guigarage.sdk.util.Media;
import com.guigarage.sdk.util.SliderPane;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public class Application extends VBox {

    private ObservableList<String> stylesheets;

    private ApplicationToolbar toolbar;

    private Workbench workbench;

    private SliderPane menuSlider;

    private ObjectProperty<Color> baseColor;

    private MenuPane menuPane;

    private ObjectProperty<Stage> stage;

    private ObjectProperty<Scene> scene;

    private ObjectProperty<Callback> stopCallback;

    private Lock javaFxStarterLock;

    private Condition javaFXStarterCondition;

    private GlobalActionButton globalActionButton;

    public Application() {
        stage = new SimpleObjectProperty<>();
        scene = new SimpleObjectProperty<>();
        stopCallback = new SimpleObjectProperty<>();

        javaFxStarterLock = new ReentrantLock();
        javaFXStarterCondition = javaFxStarterLock.newCondition();

        javaFxStarterLock.lock();
        try {
            Executors.newSingleThreadExecutor().execute(() -> {
                ApplicationStarter.run(s -> {
                    javaFxStarterLock.lock();
                    try {
                        stage.setValue(s);

                        initApplication();


                        javaFXStarterCondition.signalAll();
                    } finally {
                        javaFxStarterLock.unlock();
                    }
                }, () -> {
                    if(stopCallback.get() != null) {
                        stopCallback.get().call();
                    }
                });
            });
            try {
                javaFXStarterCondition.await();
            } catch (InterruptedException e) {
            }
        } finally {
            javaFxStarterLock.unlock();
        }
    }

    private void initApplication() {
        baseColor = new SimpleObjectProperty();
        stylesheets = FXCollections.observableArrayList();

        workbench = new Workbench();
        menuSlider = new SliderPane();
        menuSlider.setPinned(false);
        menuPane = new MenuPane();
        menuPane.setGlobalActionCallback(() -> menuSlider.hidePopover());
        menuSlider.setPopover(menuPane);
        menuSlider.setContent(workbench);

        toolbar = new ApplicationToolbar();
        toolbar.setMenuButtonCallback(() -> {
            if (menuSlider.isPopoverVisible()) {
                menuSlider.hidePopover();
            } else {
                menuSlider.showPopover();
            }
        });
        VBox.setVgrow(toolbar, Priority.NEVER);

        VBox menuBox = new VBox();
        menuBox.setPadding(new Insets(6));

        VBox.setVgrow(menuSlider, Priority.ALWAYS);

        setFillWidth(true);
        getChildren().addAll(toolbar, menuSlider);

        baseColor.addListener(e -> {
            if (baseColor.get() != null) {
                setStyle("-fx-basic-color: rgba(" + (int) (baseColor.get().getRed() * 255) + ", " + (int) (baseColor.get().getGreen() * 255) + ", " + (int) (baseColor.get().getBlue() * 255) + ", " + (int) (baseColor.get().getOpacity() * 255) + ");");
            }
        });

        globalActionButton = new GlobalActionButton();
        globalActionButton.setManaged(false);
        globalActionButton.disableProperty().bind(menuSlider.popoverVisibleProperty());
        getChildren().add(globalActionButton);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        globalActionButton.toFront();
        double actionButtonPrefWidth = globalActionButton.prefWidth(-1);
        double actionButtonPrefHeight = globalActionButton.prefHeight(-1);
        globalActionButton.resize(actionButtonPrefWidth, actionButtonPrefHeight);
        if(toolbar.isLarge()) {
            globalActionButton.relocate(getWidth() - getPadding().getRight() - 48 - actionButtonPrefWidth, toolbar.getHeight() - actionButtonPrefHeight / 2);
        } else {
            globalActionButton.relocate(getWidth() - getPadding().getRight() - 48 - actionButtonPrefWidth, toolbar.getHeight() + 48);
        }

    }

    public void removeGlobalAction(Action action) {
        globalActionButton.getActions().remove(action);
    }

    public void clearGlobalActions() {
        globalActionButton.getActions().clear();
    }

    public void addGlobalAction(Action action) {
        globalActionButton.getActions().add(action);
    }

    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    public void addToolbarItem(Action action) {
        toolbar.addAction(action);
    }

    public void setWorkbench(Node view) {
        workbench.setCurrentView(view);
    }

    public void addStylesheet(String stylesheet) {
        stylesheets.add(stylesheet);
    }

    public void setBaseColor(Color color) {
        baseColor.setValue(color);
    }

    public void addMenuEntry(Action action) {
        menuPane.add(action);
    }

    public void setMenuHeader(Node menuHeader) {
        menuPane.setHeader(menuHeader);
    }

    public void setMenuFooter(Node menuFooter) {
        menuPane.setFooter(menuFooter);
    }

    public void setMediaAsMenuHeader(Media media) {
        menuPane.setHeader(media);
    }

    public void setMenuFooter(Action action) {
        menuPane.setActionAsFooter(action);
    }

    public void setMenuButtonVisible(boolean visible) {
        toolbar.setMenuButtonVisible(visible);
    }

    public void setToolbarLarge(boolean large) {
        toolbar.setLarge(large);
    }

    public boolean isToolbarLarge() {
        return toolbar.isLarge();
    }

    public void animateToolbarToLargeVersion() {
        toolbar.animateToLargeVersion();
    }

    public void animateToolbarToSmallVersion() {
        toolbar.animateToSmallVersion();
    }

    public void setToolbarBackgroundImage(Image image) {
        toolbar.setBackgroundImage(image);
    }

    public void setToolbarBackgroundImage(String path) {
        toolbar.setBackgroundImage(path);
    }

    public void show() {
        Runnable r = () -> {
            Scene myScene = new Scene(this);
            scene.setValue(myScene);
            myScene.getStylesheets().add(ApplicationStarter.class.getResource("fonts.css").toExternalForm());
            myScene.getStylesheets().add(ApplicationStarter.class.getResource("default-style.css").toExternalForm());
            stage.get().setScene(scene.get());
            stage.get().show();
        };
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(r);
        } else {
            r.run();
        }
    }

    public Callback getStopCallback() {
        return stopCallback.get();
    }

    public ObjectProperty<Callback> stopCallbackProperty() {
        return stopCallback;
    }

    public void setStopCallback(Callback stopCallback) {
        this.stopCallback.set(stopCallback);
    }

    public Image getToolbarBackgroundImage() {
        return toolbar.getBackgroundImage();
    }
}
