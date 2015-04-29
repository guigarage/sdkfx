package com.guigarage.sdk.util;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class DefaultMedia implements Media {

    private StringProperty title;

    private StringProperty description;

    private ObjectProperty<Image> image;

    public DefaultMedia() {
        this(null, null, (Image)null);
    }

    public DefaultMedia(String title) {
        this(title, null, (Image)null);
    }

    public DefaultMedia(String title, String description) {
        this(title, description, (Image)null);
    }

    public DefaultMedia(String title, Image image) {
        this(title, null, image);
    }

    public DefaultMedia(String title, String description, Image image) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.image = new SimpleObjectProperty<>(image);
    }

    public DefaultMedia(String title, String description, String imageUrl) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.image = new SimpleObjectProperty<>(new Image(imageUrl));
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Image getImage() {
        return image.get();
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    @Override
    public StringProperty titleProperty() {
        return title;
    }

    @Override
    public StringProperty descriptionProperty() {
        return description;
    }

    @Override
    public ObjectProperty<Image> imageProperty() {
        return image;
    }
}
