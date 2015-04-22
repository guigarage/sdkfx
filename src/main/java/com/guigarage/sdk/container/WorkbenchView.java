package com.guigarage.sdk.container;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Region;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public class WorkbenchView extends Region {

    private ObjectProperty<Node> centerNode;

    private ObjectProperty<Node> footerNode;

    public WorkbenchView() {
        getStyleClass().add("workbench-view");
        centerNode = new SimpleObjectProperty<>();
        centerNode.addListener(e -> update());
        footerNode = new SimpleObjectProperty<>();
        footerNode.addListener(e -> update());
    }

    private void update() {
        getChildren().clear();

        if (centerNode.get() != null) {
            getChildren().add(centerNode.get());
        }
        if (footerNode.get() != null) {
            getChildren().add(footerNode.get());
        }
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        double footerNodePrefHeight = 0;
        if (footerNode.get() != null) {
            footerNodePrefHeight = footerNode.get().prefHeight(-1);
            footerNode.get().resize(getWidth(), footerNodePrefHeight);
            footerNode.get().relocate(0, getHeight() - footerNodePrefHeight);
        }
        if (centerNode.get() != null) {
            centerNode.get().relocate(0, 0);
            centerNode.get().resize(getWidth(), getHeight() - footerNodePrefHeight);
        }
    }

    public Node getCenterNode() {
        return centerNode.get();
    }

    public ObjectProperty<Node> centerNodeProperty() {
        return centerNode;
    }

    public void setCenterNode(Node centerNode) {
        this.centerNode.set(centerNode);
    }

    public Node getFooterNode() {
        return footerNode.get();
    }

    public ObjectProperty<Node> footerNodeProperty() {
        return footerNode;
    }

    public void setFooterNode(Node footerNode) {
        this.footerNode.set(footerNode);
    }

    public static WorkbenchView build() {
        return new WorkbenchView();
    }

}
