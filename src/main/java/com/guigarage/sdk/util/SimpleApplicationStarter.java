package com.guigarage.sdk.util;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public class SimpleApplicationStarter extends Application {

    private static Consumer<Stage> starter;

    private static Callback stopCallback;

    @Override
    public void start(Stage primaryStage) throws Exception {
        starter.accept(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if(stopCallback != null) {
            stopCallback.call();
        }
    }

    public static void setStarter(Consumer<Stage> starter) {
        SimpleApplicationStarter.starter = starter;
    }

    public static void setStopCallback(Callback stopCallback) {
        SimpleApplicationStarter.stopCallback = stopCallback;
    }
}
