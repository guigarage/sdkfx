package com.guigarage.sdk.container;

import com.guigarage.sdk.footer.InfoFooter;
import com.guigarage.sdk.action.Action;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public class BaseContainer extends Region {

    private ObjectProperty<ContainerToolbar> toolbar;

    private ObjectProperty<Node> centerNode;

    private ObjectProperty<Node> footerNode;

    private Line footerSeperator;

    public BaseContainer() {
        getStyleClass().add("base-container");

        footerSeperator = new Line();
        footerSeperator.getStyleClass().add("base-container-footer-seperator");

        toolbar = new SimpleObjectProperty<>();
        toolbar.addListener(e -> update());

        centerNode = new SimpleObjectProperty<>();
        centerNode.addListener(e -> update());

        footerNode = new SimpleObjectProperty<>();
        footerNode.addListener(e -> update());

        toolbar.setValue(new ContainerToolbar());
    }

    private void update() {
        getChildren().clear();

        if (toolbar.get() != null) {
            getChildren().add(toolbar.get());
        }
        if (centerNode.get() != null) {
            getChildren().add(centerNode.get());
        }
        if (footerNode.get() != null) {
            getChildren().add(footerNode.get());
        }
    }

    @Override
    protected void layoutChildren() {

        double toolbarPrefHeight = 0;
        if(toolbar.get() != null) {
            toolbarPrefHeight = toolbar.get().prefHeight(-1);
            double toolbarNodePrefWidth = toolbar.get().prefWidth(-1);
            toolbar.get().resize(getWidth(), toolbarPrefHeight);
            toolbar.get().relocate(0, 0);
        }

        double footerNodePrefHeight = 0;
        if(footerNode.get() != null) {

            footerNodePrefHeight = footerNode.get().prefHeight(-1);
            double footerNodePrefWidth = footerNode.get().prefWidth(-1);
            footerNode.get().resize(getWidth() - getPadding().getLeft() - getPadding().getRight(), footerNodePrefHeight);
            footerNode.get().relocate(getPadding().getLeft(), getHeight() - footerNodePrefHeight);

            if(!getChildren().contains(footerSeperator)) {
                getChildren().add(footerSeperator);
            }
            footerSeperator.setStartX(6);
            footerSeperator.setStartY(getHeight() - footerNodePrefHeight);
            footerSeperator.setEndX(getWidth() - 6);
            footerSeperator.setEndY(getHeight() - footerNodePrefHeight);

        } else {
            getChildren().remove(footerSeperator);
        }

        if(centerNode.get() != null) {
            centerNode.get().relocate(getPadding().getLeft(), toolbarPrefHeight + getPadding().getTop());
            centerNode.get().resize(getWidth() - getPadding().getLeft() - getPadding().getTop(), getHeight() - footerNodePrefHeight - toolbarPrefHeight - getPadding().getTop() - getPadding().getBottom());
        }
    }

    public ContainerToolbar getToolbar() {
        return toolbar.get();
    }

    public ObjectProperty<ContainerToolbar> toolbarProperty() {
        return toolbar;
    }

    public void setToolbar(ContainerToolbar toolbar) {
        this.toolbar.set(toolbar);
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

    public static BaseContainer build() {
        return new BaseContainer();
    }

    public BaseContainer withTitle(String title) {
        getToolbar().setTitle(title);
        return this;
    }

    public BaseContainer withContent(Node node) {
        setCenterNode(node);
        return this;
    }

    public BaseContainer withToolbarItem(Action action) {
        getToolbar().addAction(action);
        return this;
    }

    public BaseContainer withFooter(Node node) {
        setFooterNode(node);
        return this;
    }

    public BaseContainer withInfoFooter(String text) {
        setFooterNode(new InfoFooter().withText(text));
        return this;
    }
}
