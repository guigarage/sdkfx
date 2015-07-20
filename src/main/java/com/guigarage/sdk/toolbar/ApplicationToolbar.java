package com.guigarage.sdk.toolbar;

import com.guigarage.sdk.util.Callback;
import com.guigarage.sdk.util.Icons;
import com.guigarage.sdk.util.MaterialDesignButton;
import javafx.scene.control.Button;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public class ApplicationToolbar extends BaseToolbar {

    private Button menuButton;

    public ApplicationToolbar() {
        getStyleClass().add("application-toolbar");

        menuButton = new MaterialDesignButton();
        menuButton.setId("application-menu-button");
        menuButton.setText(Icons.NAV.getText());
        menuButton.setOnMouseEntered(e -> {
            menuButton.setScaleX(1.1);
            menuButton.setScaleY(1.1);
        });
        menuButton.setOnMouseExited(e -> {
            menuButton.setScaleX(1.0);
            menuButton.setScaleY(1.0);
        });
        leftNodeProperty().setValue(menuButton);


    }

    public void setMenuButtonVisible(boolean visible) {
        menuButton.setVisible(visible);
    }

    public void setMenuButtonCallback(Callback callback) {
        menuButton.setOnAction(e -> callback.call());
    }

}
