package com.guigarage.sdk.animations;

import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Point2D;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class MaximizeToTransition {

    public static Transition create(Duration duration, Region node, Region parentNode) {

        ScaleTransition transition = new ScaleTransition(duration, node);
        transition.setFromX(node.getScaleX());
        transition.setFromY(node.getScaleY());
        transition.toXProperty().bind(parentNode.widthProperty().divide(node.widthProperty()));
        transition.toYProperty().bind(parentNode.heightProperty().divide(node.heightProperty()));

        TranslateTransition translateTransition = new TranslateTransition(duration, node);
        translateTransition.setFromX(node.getTranslateX());
        translateTransition.setFromY(node.getTranslateY());

        DoubleBinding translateXBinding = Bindings.createDoubleBinding(() -> {
            Point2D topLeft = node.localToScene(0, 0);
            Point2D topLeftParent = parentNode.localToScene(0, 0);

            return topLeftParent.getX() - topLeft.getX();
        }, node.layoutBoundsProperty(), parentNode.layoutBoundsProperty());

        DoubleBinding translateYBinding = Bindings.createDoubleBinding(() -> {
            Point2D topLeft = node.localToScene(0, 0);
            Point2D topLeftParent = parentNode.localToScene(0, 0);

            return topLeftParent.getY() - topLeft.getY();
        }, node.layoutBoundsProperty(), parentNode.layoutBoundsProperty());

        translateTransition.toYProperty().bind(translateYBinding);

        ParallelTransition parallelTransition = new ParallelTransition(transition, translateTransition);


        return parallelTransition;
    }
}
