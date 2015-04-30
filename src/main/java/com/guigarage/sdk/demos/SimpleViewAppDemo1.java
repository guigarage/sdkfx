package com.guigarage.sdk.demos;

import com.guigarage.sdk.Application;
import com.guigarage.sdk.action.Action;
import com.guigarage.sdk.chat.ChatTimeline;
import com.guigarage.sdk.chat.DefaultChatMessage;
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

        Application app = new Application();
        app.setTitle("MyApp");
        app.setBaseColor(Color.DARKORCHID);
        app.addToolbarItem(new Action(Icon.VOLUMNE_DOWN));
        app.addToolbarItem(new Action(Icon.VOLUMNE_UP));

        app.addMenuEntry(new Action(Icon.CALENDAR, "Google Calendar", () -> showPersonList(app)));
        app.addMenuEntry(new Action(Icon.COGS, "System Settings"));
        app.addMenuEntry(new Action(Icon.MAIL, "Mail"));

        app.setMediaAsMenuHeader(new DefaultMedia("User4711", "Ich bin eine Beschreibung.", SimpleViewAppDemo1.class.getResource("user-13.jpg").toExternalForm()));

        app.setMenuFooter(new Action(Icon.COGS, "Configure"));

        app.show();
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
        footer.addAction(new Action(Icon.PHONE, "Call"));
        footer.addAction(new Action(Icon.MAIL, "Send message", () -> view.setCenterNode(createChatTimeline())));
        view.setFooterNode(footer);

        app.setWorkbench(view);
    }

    private static ChatTimeline<DefaultChatMessage> createChatTimeline() {
        ChatTimeline<DefaultChatMessage> timeline = new ChatTimeline<>();
        timeline.getItems().add(new DefaultChatMessage(true, "Hello"));
        timeline.getItems().add(new DefaultChatMessage(false, "How are you"));
        timeline.getItems().add(new DefaultChatMessage(true, "Fine, thanks. Do you want to go to the cinema today?"));
        timeline.getItems().add(new DefaultChatMessage(true, "Or having some drinks?"));
        timeline.getItems().add(new DefaultChatMessage(false, "Oh no, I already have a date this evening with Steve. We want to go to a club. If you want you can come with us."));
        timeline.getItems().add(new DefaultChatMessage(true, "Cool, when do you want to start?"));
        return timeline;
    }
}
