package com.guigarage.sdk.util;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public class SimpleApplicationStarter extends Application {

    private static Consumer<Stage> starter;

    @Override
    public void start(Stage primaryStage) throws Exception {
        starter.accept(primaryStage);
    }

    public static void setStarter(Consumer<Stage> starter) {
        SimpleApplicationStarter.starter = starter;
    }
}
