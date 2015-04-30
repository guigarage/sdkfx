package com.guigarage.sdk.chat;

import javafx.scene.control.ListView;

/**
 * Created by hendrikebbers on 30.04.15.
 */
public class ChatTimeline<T extends ChatMessage> extends ListView<T> {

    public ChatTimeline() {
        setCellFactory(e -> new ChatCell<T>());
    }
}
