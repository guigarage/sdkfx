package com.guigarage.sdk.form;

import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.List;

/**
 * Created by hendrikebbers on 08.05.15.
 */
public interface FormRow {

    double getPrefLabelWidth();

    double layoutInParent(double startX, double startY, double width, double labelWidth, double rowSpacing, double columnSpacing);

    List<Node> getNodes();
}
