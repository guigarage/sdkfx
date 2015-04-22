package com.guigarage.sdk.util;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by hendrikebbers on 18.04.15.
 */
public class SliderPaneDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SliderPane pane = new SliderPane();
        pane.setContent(new Button("A"));
        pane.setPopover(new Button("B"));

        primaryStage.setScene(new Scene(pane));
        primaryStage.show();

    }

    public static void main(String... args) {
        launch(args);
    }
}
