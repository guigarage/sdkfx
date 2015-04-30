package com.guigarage.sdk;

import com.guigarage.sdk.util.Callback;
import com.guigarage.sdk.util.SimpleApplicationStarter;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public class ApplicationStarter {

    public static void run(Consumer<Stage> runner) {
        SimpleApplicationStarter.setStarter(runner);
        SimpleApplicationStarter.launch(SimpleApplicationStarter.class);
    }

    public static void run(Consumer<Stage> runner, Callback stopCallback) {
        SimpleApplicationStarter.setStarter(runner);
        SimpleApplicationStarter.setStopCallback(stopCallback);
        SimpleApplicationStarter.launch(SimpleApplicationStarter.class);
    }
}
