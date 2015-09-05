package com.guigarage.sdk.animations;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by hendrikebbers on 02.06.15.
 */
public class FlyInFromBottomTransition {

    static Duration delay;

    public static void create(Duration duration, ListView list) {

        List<Node> cells = new ArrayList<>(list.lookupAll(".list-cell"));
        for(int i = 0; i< cells.size(); i++) {
            Duration delay = duration.divide(cells.size()).multiply(i);
            Duration cellDuration = duration.subtract(delay);
            Transition transition = create(cellDuration, list, cells.get(i));
            transition.setDelay(delay);
            transition.play();
        }

        list.lookupAll(".list-cell").forEach(cell -> {

        });
    }

    public static ParallelTransition create(Duration duration, ListView list, Node cell) {

        TranslateTransition translateTransition = new TranslateTransition(duration, cell);
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        translateTransition.fromYProperty().bind(list.heightProperty());
        translateTransition.setToY(0);

        FadeTransition fadeTransition = new FadeTransition(duration, cell);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        return new ParallelTransition(translateTransition, fadeTransition);
    }
}
