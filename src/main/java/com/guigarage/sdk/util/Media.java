package com.guigarage.sdk.util;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public interface Media {

    StringProperty titleProperty();

    StringProperty descriptionProperty();

    ObjectProperty<Image> imageProperty();
}
