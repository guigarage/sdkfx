package com.guigarage.sdk.util;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public enum Icon {
    VOLUMNE_UP("\uf028"), VOLUMNE_DOWN("\uf027"), ANGLE_LEFT("\uf104"), ANGLE_RIGHT("\uf105"), NAV("\uf0c9"), COGS("\uf085"), MAIL("\uf003"), CALENDAR("\uf073");

    private String text;

    Icon(String text) {
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
