package com.guigarage.sdk.chat;

import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;

/**
 * Created by hendrikebbers on 30.04.15.
 */
public class ChatCell<T extends ChatMessage> extends ListCell<T> {

    private ChatBubble bubble;

    public ChatCell() {
        bubble = new ChatBubble();
        setGraphic(bubble);
        setText("");
        getStyleClass().add("chat-cell");

        listViewProperty().addListener(e -> {
            bubble.minWidthProperty().unbind();
            if(listViewProperty().get() != null) {
                bubble.minWidthProperty().bind(listViewProperty().get().widthProperty().subtract(32));
                bubble.prefWidthProperty().bind(listViewProperty().get().widthProperty().subtract(32));
                bubble.maxWidthProperty().bind(listViewProperty().get().widthProperty().subtract(32));
            }
        });

    }

    @Override
    public Orientation getContentBias() {
        return Orientation.HORIZONTAL;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if(item == null || empty) {
            bubble.setVisible(false);
        } else {
            bubble.setVisible(true);
            bubble.setMessage(item);
        }
    }
}
