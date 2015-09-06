package com.guigarage.sdk.util;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public enum FontAwesomeIcons implements FontBasedIcon {
    ANGLE_LEFT("\uf104"),
    ANGLE_RIGHT("\uf105"),
    CALENDAR("\uf073"),
    COGS("\uf085"),
    PLUS("\uf067"),
    MAIL("\uf003"),
    NAV("\uf0c9"),
    PHONE("\uf095"),
    SEARCH("\uf002"),
    VOLUMNE_DOWN("\uf027"),
    VOLUMNE_UP("\uf028");

    private String text;

    FontAwesomeIcons(String text) {
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