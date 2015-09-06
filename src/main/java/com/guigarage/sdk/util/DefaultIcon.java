package com.guigarage.sdk.util;

/**
 * @author Brian Schlining
 * @since 2015-07-19T15:27:00
 */
public class DefaultIcon implements FontBasedIcon {

    private String text;

    public DefaultIcon(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return getText();
    }
}