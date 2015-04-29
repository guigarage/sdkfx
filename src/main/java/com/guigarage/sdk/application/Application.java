package com.guigarage.sdk.application;

import com.guigarage.sdk.ApplicationFX;
import com.guigarage.sdk.action.Action;
import com.guigarage.sdk.toolbar.ApplicationToolbar;
import com.guigarage.sdk.container.Workbench;
import com.guigarage.sdk.menu.MenuPane;
import com.guigarage.sdk.util.Media;
import com.guigarage.sdk.util.SliderPane;
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

/**
 * Created by hendrikebbers on 12.03.15.
 */
public class Application extends VBox{

    private ObservableList<String> stylesheets;

    private ApplicationToolbar toolbar;

    private Workbench workbench;

    private SliderPane menuSlider;

    private ObjectProperty<Color> baseColor;

    private MenuPane menuPane;

    public Application() {
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
            if(menuSlider.isPopoverVisible()) {
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
            if(baseColor.get() != null) {
                setStyle("-fx-basic-color: rgba(" + (int) (baseColor.get().getRed() * 255) + ", " + (int) (baseColor.get().getGreen() * 255) + ", " + (int) (baseColor.get().getBlue() * 255) + ", " + (int) (baseColor.get().getOpacity() * 255) + ");");
            }
        });
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

    public Scene show(Stage stage) {
        Scene myScene = new Scene(this);
        myScene.getStylesheets().add(ApplicationFX.class.getResource("fonts.css").toExternalForm());
        myScene.getStylesheets().add(ApplicationFX.class.getResource("default-style.css").toExternalForm());
        stage.setScene(myScene);
        stage.show();
        return myScene;
    }
}
