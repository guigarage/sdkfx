package com.guigarage.sdk.table;

import com.guigarage.sdk.util.Media;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;

public class MediaTable extends TableView<Media> {

    private StringProperty imageColumnHeaderText;

    private StringProperty titleColumnHeaderText;

    private StringProperty descriptionColumnHeaderText;

    private BooleanProperty imageColumnHeaderVisible;

    private BooleanProperty titleColumnHeaderVisible;

    private BooleanProperty descriptionColumnHeaderVisible;

    public MediaTable() {
        imageColumnHeaderText = new SimpleStringProperty("Image");
        titleColumnHeaderText = new SimpleStringProperty("Title");
        descriptionColumnHeaderText = new SimpleStringProperty("Description");

        imageColumnHeaderVisible = new SimpleBooleanProperty(true);
        titleColumnHeaderVisible = new SimpleBooleanProperty(true);
        descriptionColumnHeaderVisible = new SimpleBooleanProperty(true);

        TableColumn<Media, Image> imageTableColumn = new TableColumn<>();
        imageTableColumn.setCellValueFactory(c -> c.getValue().imageProperty());
        imageTableColumn.textProperty().bind(imageColumnHeaderText);
        imageTableColumn.visibleProperty().bind(imageColumnHeaderVisible);
        imageTableColumn.setCellFactory(c -> new ImageTableCell());

        TableColumn<Media, String> titleTableColumn = new TableColumn<>();
        titleTableColumn.setCellValueFactory(c -> c.getValue().titleProperty());
        titleTableColumn.textProperty().bind(titleColumnHeaderText);
        titleTableColumn.visibleProperty().bind(titleColumnHeaderVisible);

        TableColumn<Media, String> descriptionTableColumn = new TableColumn<>();
        descriptionTableColumn.setCellValueFactory(c -> c.getValue().descriptionProperty());
        descriptionTableColumn.textProperty().bind(descriptionColumnHeaderText);
        descriptionTableColumn.visibleProperty().bind(descriptionColumnHeaderVisible);

        getColumns().addAll(imageTableColumn, titleTableColumn, descriptionTableColumn);
    }
}
