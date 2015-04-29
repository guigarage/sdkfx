package com.guigarage.sdk.util;

import javafx.scene.image.ImageView;

public class ResizeableImageView extends ImageView {

    @Override
    public double maxHeight(double width) {
        return Double.MAX_VALUE;
    }

    @Override
    public double maxWidth(double height) {
        return Double.MAX_VALUE;
    }

    @Override
    public double minHeight(double width) {
        return 0;
    }

    @Override
    public double minWidth(double height) {
        return 0;
    }
}
