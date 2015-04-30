package com.guigarage.sdk;

import com.guigarage.sdk.action.Action;
import com.guigarage.sdk.container.Workbench;
import com.guigarage.sdk.menu.MenuPane;
import com.guigarage.sdk.toolbar.ApplicationToolbar;
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

    private Lock javaFxStarterLock;

    private Condition javaFXStarterCondition;

    public Application() {
        stage = new SimpleObjectProperty<>();
        scene = new SimpleObjectProperty<>();

        javaFxStarterLock = new ReentrantLock();
        javaFXStarterCondition = javaFxStarterLock.newCondition();

        javaFxStarterLock.lock();
        try {
            Executors.newSingleThreadExecutor().execute(() -> {
                ApplicationStarter.run(s -> {
                    javaFxStarterLock.lock();
                    try {
                        stage.setValue(s);

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


                        javaFXStarterCondition.signalAll();
                    } finally {
                        javaFxStarterLock.unlock();
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

    public void show() {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> {
                Scene myScene = new Scene(this);
                scene.setValue(myScene);
                myScene.getStylesheets().add(ApplicationStarter.class.getResource("fonts.css").toExternalForm());
                myScene.getStylesheets().add(ApplicationStarter.class.getResource("default-style.css").toExternalForm());
                stage.get().setScene(scene.get());
                stage.get().show();
            });
        } else {
            Scene myScene = new Scene(this);
            scene.setValue(myScene);
            myScene.getStylesheets().add(ApplicationStarter.class.getResource("fonts.css").toExternalForm());
            myScene.getStylesheets().add(ApplicationStarter.class.getResource("default-style.css").toExternalForm());
            stage.get().setScene(scene.get());
            stage.get().show();
        }
    }
}
