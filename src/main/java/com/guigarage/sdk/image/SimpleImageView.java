package com.guigarage.sdk.image;

import com.guigarage.sdk.overlay.Overlay;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public class SimpleImageView extends Region {

    private ObjectProperty<Overlay> overlay;

    private ImageView imageView;

    //TODO: Ob man nun immer das ganze Bild sieht und dann möglicherweise einen Rand hat oder ob man nie einen Rand hat dafür aber vielleicht was vom Bild abgeschnitten ist.
    private BooleanProperty calcRatioByShortSite;

    public SimpleImageView() {
        getStyleClass().add("simple-image-view");

        imageView = new ImageView();
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);
        getChildren().add(imageView);

        overlay = new SimpleObjectProperty<>();
        overlay.addListener((observable, oldValue, newValue) -> {
            if(oldValue != null) {
                getChildren().remove(oldValue);
            }
            if(newValue != null) {
                getChildren().add(newValue);
            }
        });

        calcRatioByShortSite = new SimpleBooleanProperty(true);
        calcRatioByShortSite.addListener(e -> {
            imageView.fitWidthProperty().bind(widthProperty());
        });
        calcRatioByShortSite.setValue(false);
    }

    public void setImage(String url) {
        imageView.setImage(new Image(url));
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    public void setOverlay(Overlay overlay) {
        this.overlay.setValue(overlay);
    }

    public void hideOverlay() {
        if(overlay.get() != null) {
            overlay.get().hide();
        }
    }

    public void showOverlay() {
        if(overlay.get() != null) {
            overlay.get().show();
        }
    }

    public boolean isOverlayHidden() {
        if(overlay.get() != null) {
            return overlay.get().isHidden();
        }
        return false;
    }

    public void toggleOverlayVisibility() {
        if(overlay.get() != null) {
            overlay.get().toggleVisibility();
        }
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        imageView.relocate(0, 0);
        imageView.resize(getWidth(), getHeight());

        if(overlay.get() != null) {
            double height = overlay.get().prefHeight(getWidth());
            overlay.get().relocate(0, getHeight() - height);
            overlay.get().resize(getWidth(), height);
        }
    }
}
