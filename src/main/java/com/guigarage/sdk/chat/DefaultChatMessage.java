package com.guigarage.sdk.chat;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by hendrikebbers on 30.04.15.
 */
public class DefaultChatMessage implements ChatMessage {

    private BooleanProperty sendByMe;

    private StringProperty message;

    public DefaultChatMessage() {
        this(false, null);
    }

    public DefaultChatMessage(boolean sendByMe, String message) {
        this.sendByMe = new SimpleBooleanProperty(sendByMe);
        this.message = new SimpleStringProperty(message);
    }

    public boolean getSendByMe() {
        return sendByMe.get();
    }

    public void setSendByMe(boolean sendByMe) {
        this.sendByMe.set(sendByMe);
    }

    public String getMessage() {
        return message.get();
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    @Override
    public BooleanProperty sendByMeProperty() {
        return sendByMe;
    }

    @Override
    public StringProperty messageProperty() {
        return message;
    }
}
