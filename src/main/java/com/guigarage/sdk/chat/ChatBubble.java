package com.guigarage.sdk.chat;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * Created by hendrikebbers on 18.04.15.
 */
public class ChatBubble extends Region {

    private Rectangle mainBubble;

    private Label chatText;

    private Rectangle mySpeakerSign;

    private Rectangle oppositeSpeakerSign;

    double chatInsets = 12;

    private PseudoClass mePseudoClass = PseudoClass.getPseudoClass("me");

    private PseudoClass oppositePseudoClass = PseudoClass.getPseudoClass("opposite");

    private ObjectProperty<ChatMessage> message;

    public ChatBubble() {

        getStyleClass().addAll("chat-bubble-wrapper");


        message = new SimpleObjectProperty<>();

        mainBubble = new Rectangle();
        mainBubble.getStyleClass().addAll("chat-bubble", "chat-bubble-rect");
        mainBubble.setManaged(false);

        mySpeakerSign = new Rectangle();
        mySpeakerSign.getStyleClass().add("chat-bubble");
        mySpeakerSign.setRotate(45.0);
        mySpeakerSign.setManaged(false);

        oppositeSpeakerSign = new Rectangle();
        oppositeSpeakerSign.getStyleClass().add("chat-bubble");
        oppositeSpeakerSign.setRotate(45.0);
        oppositeSpeakerSign.setManaged(false);

        chatText = new Label();
        chatText.setWrapText(true);
        chatText.getStyleClass().add("chat-text");

        getChildren().addAll(mainBubble, mySpeakerSign, oppositeSpeakerSign, chatText);

        InvalidationListener sendByMeListener = e -> {
            if(message.get().sendByMeProperty().get()) {
                setChatPseudoClass(mePseudoClass, true);
                setChatPseudoClass(oppositePseudoClass, false);
            } else {
                setChatPseudoClass(mePseudoClass, false);
                setChatPseudoClass(oppositePseudoClass, true);
            }
        };

        message.addListener((obs, oldV, newV) -> {
            chatText.textProperty().unbind();
            mySpeakerSign.visibleProperty().unbind();
            oppositeSpeakerSign.visibleProperty().unbind();
            if(oldV != null) {
                oldV.sendByMeProperty().removeListener(sendByMeListener);
            }
            if(newV != null) {
                newV.sendByMeProperty().addListener(sendByMeListener);
                sendByMeListener.invalidated(newV.sendByMeProperty());
                chatText.textProperty().bind(newV.messageProperty());
                mySpeakerSign.visibleProperty().bind(newV.sendByMeProperty());
                oppositeSpeakerSign.visibleProperty().bind(newV.sendByMeProperty().not());
            }
        });

        setMaxHeight(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(0);
    }

    private void setChatPseudoClass(PseudoClass cls, boolean value) {
        mainBubble.pseudoClassStateChanged(cls, value);
        mySpeakerSign.pseudoClassStateChanged(cls, value);
        oppositeSpeakerSign.pseudoClassStateChanged(cls, value);
    }

    @Override
    protected void layoutChildren() {
        mainBubble.setX(0);
        mainBubble.setY(0);
        mainBubble.setWidth(getWidth());
        mainBubble.setHeight(getHeight() - 24);

        mySpeakerSign.setX(mainBubble.getWidth() - 24 - 18);
        // - 4 to hide round corners
        mySpeakerSign.setY(mainBubble.getHeight() - 12 - 4);
        mySpeakerSign.setWidth(24);
        mySpeakerSign.setHeight(24);

        oppositeSpeakerSign.setX(18);
        // - 4 to hide round corners
        oppositeSpeakerSign.setY(mainBubble.getHeight() - 12 - 4);
        oppositeSpeakerSign.setWidth(24);
        oppositeSpeakerSign.setHeight(24);

        chatText.relocate(mainBubble.getX() + chatInsets, mainBubble.getY() + chatInsets);
        chatText.resize(mainBubble.getWidth() - chatInsets - chatInsets, mainBubble.getHeight() - chatInsets - chatInsets);
    }

    @Override
    protected double computePrefHeight(double width) {
        double prefHeight = chatText.prefHeight(width - chatInsets - chatInsets) + chatInsets + chatInsets + 24;
        return prefHeight;
    }

    @Override
    public Orientation getContentBias() {
        return Orientation.HORIZONTAL;
    }

    public void setMessage(ChatMessage message) {
        this.message.setValue(message);
    }
}
