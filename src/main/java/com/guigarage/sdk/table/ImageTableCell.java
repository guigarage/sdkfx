package com.guigarage.sdk.table;

import com.guigarage.sdk.util.Media;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ImageTableCell extends TableCell<Media, Image> {

    private ImageView imageView;

    public ImageTableCell() {
        imageView = new ImageView();
        setGraphic(imageView);
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(widthProperty());
    }

    @Override
    protected void updateItem(Image item, boolean empty) {
        super.updateItem(item, empty);
        imageView.setImage(item);
    }
}
