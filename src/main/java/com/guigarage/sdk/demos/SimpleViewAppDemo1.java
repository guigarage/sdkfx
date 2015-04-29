package com.guigarage.sdk.demos;

import com.guigarage.sdk.ApplicationFX;
import com.guigarage.sdk.action.Action;
import com.guigarage.sdk.application.Application;
import com.guigarage.sdk.container.WorkbenchView;
import com.guigarage.sdk.footer.ActionFooter;
import com.guigarage.sdk.list.MediaList;
import com.guigarage.sdk.util.DefaultMedia;
import com.guigarage.sdk.util.Icon;
import com.guigarage.sdk.util.Media;
import javafx.scene.paint.Color;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public class SimpleViewAppDemo1 {

    public static void main(String... args) {
        ApplicationFX.run(s -> {

            Application app = new Application();
            app.setTitle("MyApp");
            //app.setBaseColor(Color.DARKORCHID);
            app.addToolbarItem(new Action(Icon.VOLUMNE_DOWN));
            app.addToolbarItem(new Action(Icon.VOLUMNE_UP));

            app.addMenuEntry(new Action(Icon.CALENDAR, "Google Calendar", () -> showPersonList(app)));
            app.addMenuEntry(new Action(Icon.COGS, "System Settings"));
            app.addMenuEntry(new Action(Icon.MAIL, "Mail"));

            app.setMediaAsMenuHeader(new DefaultMedia("User4711", "Ich bin eine Beschreibung.", SimpleViewAppDemo1.class.getResource("user-01.jpg").toExternalForm()));

            app.setMenuFooter(new Action(Icon.COGS, "Configure"));

            app.show(s);
        });
    }

    private static void showPersonList(Application app) {
        WorkbenchView view = new WorkbenchView();

        MediaList<Media> list = new MediaList<>();

        list.getItems().add(new DefaultMedia("Test01", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-01.jpg").toExternalForm()));
        list.getItems().add(new DefaultMedia("Test02", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-02.jpg").toExternalForm()));
        list.getItems().add(new DefaultMedia("Test03", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-03.jpg").toExternalForm()));
        list.getItems().add(new DefaultMedia("Test04", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-04.jpg").toExternalForm()));
        list.getItems().add(new DefaultMedia("Test05", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-05.jpg").toExternalForm()));
        list.getItems().add(new DefaultMedia("Test06", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-06.jpg").toExternalForm()));
        list.getItems().add(new DefaultMedia("Test07", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-07.jpg").toExternalForm()));
        list.getItems().add(new DefaultMedia("Test08", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-08.jpg").toExternalForm()));
        list.getItems().add(new DefaultMedia("Test09", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-09.jpg").toExternalForm()));
        list.getItems().add(new DefaultMedia("Test10", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-10.jpg").toExternalForm()));
        list.getItems().add(new DefaultMedia("Test11", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-11.jpg").toExternalForm()));
        list.getItems().add(new DefaultMedia("Test12", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-12.jpg").toExternalForm()));
        list.getItems().add(new DefaultMedia("Test13", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-13.jpg").toExternalForm()));

        view.setCenterNode(list);

        ActionFooter footer = new ActionFooter();
        footer.addAction(new Action("Call"));
        footer.addAction(new Action(Icon.MAIL, "Send message"));
        view.setFooterNode(footer);

        app.setWorkbench(view);
    }
}
