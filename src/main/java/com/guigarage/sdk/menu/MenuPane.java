package com.guigarage.sdk.menu;

import com.guigarage.sdk.action.Action;
import com.guigarage.sdk.util.Media;
import com.guigarage.sdk.util.Callback;
import com.guigarage.sdk.util.RoundImageView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hendrikebbers on 18.04.15.
 */
public class MenuPane extends VBox {

    private ObservableList<Action> entries;

    private Map<Action, Node> itemToNode;

    private Callback globalActionCallback;

    private ObjectProperty<Node> header;

    private ObjectProperty<Node> footer;

    public MenuPane() {
        header = new SimpleObjectProperty<>();
        header.addListener((obs, oldV, newV) -> {
            if(oldV != null) {
                getChildren().remove(oldV);
            }
            if(newV != null) {
                getChildren().add(0, newV);
            }
        });
        footer = new SimpleObjectProperty<>();
        footer.addListener((obs, oldV, newV) -> {
            if(oldV != null) {
                getChildren().remove(oldV);
            }
            if(newV != null) {
                getChildren().add(newV);
            }
        });

        getStyleClass().add("menu-pane");
        setFillWidth(true);
        entries = FXCollections.observableArrayList();
        itemToNode = new HashMap<>();

        entries.addListener((ListChangeListener<Action>) c -> {
            while (c.next()) {
                if (!c.wasPermutated() && !c.wasUpdated()) {
                    for (Action removed : c.getRemoved()) {
                        Node child = itemToNode.remove(removed);
                        getChildren().remove(child);
                    }
                    for (Action added : c.getAddedSubList()) {
                        MenuEntry entry = createMenuEntry(added);
                        getChildren().add(entry);
                        itemToNode.put(added, entry);
                    }
                    if(footer.get() != null) {
                        footer.get().toFront();
                    }
                }
            }
        });
    }

    private MenuEntry createMenuEntry(Action action) {
        MenuEntry entry = new MenuEntry();
        entry.setText(action.getTitle());
        entry.setIcon(action.getIcon());
        entry.setCallback(() -> {
            if(action.getCallback() != null) {
                action.getCallback().call();
            }
            if(globalActionCallback != null) {
                globalActionCallback.call();
            }
        });
        return entry;
    }

    public void setHeader(Media media) {
        HBox mediaView = new HBox();
        mediaView.getStyleClass().add("menu-media-header");
        RoundImageView imageView = new RoundImageView();
        imageView.imageProperty().bind(media.imageProperty());
        VBox textBox = new VBox();
        Label titleLabel = new Label();
        titleLabel.getStyleClass().add("media-title");
        titleLabel.textProperty().bind(media.titleProperty());
        Label descriptionLabel = new Label();
        descriptionLabel.getStyleClass().add("media-description");
        descriptionLabel.setWrapText(true);
        descriptionLabel.textProperty().bind(media.descriptionProperty());
        textBox.getChildren().addAll(titleLabel, descriptionLabel);
        mediaView.getChildren().addAll(imageView, textBox);
        setHeader(mediaView);
    }

    public void setActionAsFooter(Action action) {
        setFooter(createMenuEntry(action));
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        if(footer.get() != null) {
            footer.get().relocate(getPadding().getLeft(), getHeight() - getPadding().getBottom() - footer.get().getBoundsInParent().getHeight());
        }
    }

    public void setGlobalActionCallback(Callback globalActionCallback) {
        this.globalActionCallback = globalActionCallback;
    }

    public Node getHeader() {
        return header.get();
    }

    public ObjectProperty<Node> headerProperty() {
        return header;
    }

    public void setHeader(Node header) {
        this.header.set(header);
    }

    public Node getFooter() {
        return footer.get();
    }

    public ObjectProperty<Node> footerProperty() {
        return footer;
    }

    public void setFooter(Node footer) {
        this.footer.set(footer);
    }

    public void add(Action action) {
        entries.add(action);
    }

    public void remove(Action action) {
        entries.remove(action);
    }
}
