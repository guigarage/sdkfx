package com.guigarage.sdk.demos;

import com.guigarage.sdk.ApplicationFX;
import com.guigarage.sdk.footer.ActionFooter;
import com.guigarage.sdk.application.Application;
import com.guigarage.sdk.action.Action;
import com.guigarage.sdk.list.DefaultMedia;
import com.guigarage.sdk.list.Media;
import com.guigarage.sdk.list.MediaList;
import com.guigarage.sdk.list.SimpleMediaListCell;
import com.guigarage.sdk.util.Icon;
import com.guigarage.sdk.container.BaseContainer;
import com.guigarage.sdk.container.FormWorkbench;
import com.guigarage.sdk.container.WorkbenchView;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public class SimpleViewAppDemo1 {

    public static void main(String... args) {
        ApplicationFX.run(s -> {

            WorkbenchView view = new WorkbenchView();

            Application app = new Application();
            app.setTitle("MyApp");
            app.setBaseColor(Color.DARKORCHID);
            app.addToolbarItem(new Action(Icon.VOLUMNE_DOWN));
            app.addToolbarItem(new Action(Icon.VOLUMNE_UP));
            app.setWorkbench(view);

            app.addMenuEntry(new Action(Icon.CALENDAR, "Google Calendar", () -> showPersonList(view)));
            app.addMenuEntry(new Action(Icon.COGS, "System Settings"));
            app.addMenuEntry(new Action(Icon.MAIL, "Mail"));

            app.show(s);
        });
    }

    private static void showPersonList(WorkbenchView view) {
        MediaList<Media> list = new MediaList<>();

        list.getItems().add(new DefaultMedia("Test1", "Ich bin eine Beschreibung", new Image(SimpleViewAppDemo1.class.getResource("user-01.jpg").toExternalForm())));
        list.getItems().add(new DefaultMedia("Test2", "Ich bin eine Beschreibung", new Image(SimpleViewAppDemo1.class.getResource("user-02.jpg").toExternalForm())));
        list.getItems().add(new DefaultMedia("Test3", "Ich bin eine Beschreibung", new Image(SimpleViewAppDemo1.class.getResource("user-03.jpg").toExternalForm())));
        list.getItems().add(new DefaultMedia("Test4", "Ich bin eine Beschreibung", new Image(SimpleViewAppDemo1.class.getResource("user-04.jpg").toExternalForm())));
        list.getItems().add(new DefaultMedia("Test5", "Ich bin eine Beschreibung", new Image(SimpleViewAppDemo1.class.getResource("user-05.jpg").toExternalForm())));
        list.getItems().add(new DefaultMedia("Test6", "Ich bin eine Beschreibung", new Image(SimpleViewAppDemo1.class.getResource("user-06.jpg").toExternalForm())));
        list.getItems().add(new DefaultMedia("Test7", "Ich bin eine Beschreibung", new Image(SimpleViewAppDemo1.class.getResource("user-07.jpg").toExternalForm())));
        list.getItems().add(new DefaultMedia("Test8", "Ich bin eine Beschreibung", new Image(SimpleViewAppDemo1.class.getResource("user-08.jpg").toExternalForm())));
        list.getItems().add(new DefaultMedia("Test9", "Ich bin eine Beschreibung", new Image(SimpleViewAppDemo1.class.getResource("user-09.jpg").toExternalForm())));
        list.getItems().add(new DefaultMedia("Test10", "Ich bin eine Beschreibung", new Image(SimpleViewAppDemo1.class.getResource("user-10.jpg").toExternalForm())));
        list.getItems().add(new DefaultMedia("Test11", "Ich bin eine Beschreibung", new Image(SimpleViewAppDemo1.class.getResource("user-11.jpg").toExternalForm())));
        list.getItems().add(new DefaultMedia("Test12", "Ich bin eine Beschreibung", new Image(SimpleViewAppDemo1.class.getResource("user-12.jpg").toExternalForm())));
        list.getItems().add(new DefaultMedia("Test13", "Ich bin eine Beschreibung", new Image(SimpleViewAppDemo1.class.getResource("user-13.jpg").toExternalForm())));

        view.setCenterNode(list);

        ActionFooter footer = new ActionFooter();
        footer.addAction(new Action("Call"));
        footer.addAction(new Action(Icon.MAIL, "Send message"));
        view.setFooterNode(footer);
    }
}
