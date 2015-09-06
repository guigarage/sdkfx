package com.guigarage.sdk.demos;

import com.guigarage.sdk.Application;
import com.guigarage.sdk.action.Action;
import com.guigarage.sdk.chat.ChatTimeline;
import com.guigarage.sdk.chat.DefaultChatMessage;
import com.guigarage.sdk.container.WorkbenchView;
import com.guigarage.sdk.footer.ActionFooter;
import com.guigarage.sdk.form.EditorType;
import com.guigarage.sdk.form.FormLayout;
import com.guigarage.sdk.image.SimpleImageView;
import com.guigarage.sdk.list.MediaList;
import com.guigarage.sdk.overlay.Overlay;
import com.guigarage.sdk.table.MediaTable;
import com.guigarage.sdk.util.DefaultMedia;
import com.guigarage.sdk.util.FontAwesomeIcons;
import com.guigarage.sdk.util.Media;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SimpleViewAppDemo1 {

    public static void main(String... args) {

        Application app = new Application();
        app.setTitle("MyApp");
        app.setBaseColor(Color.DARKORCHID);
        app.addToolbarItem(new Action(FontAwesomeIcons.VOLUMNE_DOWN, () -> app.animateToolbarToLargeVersion()));
        app.addToolbarItem(new Action(FontAwesomeIcons.VOLUMNE_UP, () -> app.animateToolbarToSmallVersion()));

        app.setToolbarBackgroundImage(SimpleViewAppDemo1.class.getResource("toolbar-background.png").toExternalForm());

        app.addMenuEntry(new Action(FontAwesomeIcons.CALENDAR, "Show List", () -> showPersonList(app)));
        app.addMenuEntry(new Action(FontAwesomeIcons.CALENDAR, "Show Table", () -> showPersonTable(app)));
        app.addMenuEntry(new Action(FontAwesomeIcons.COGS, "System Settings", () -> showForm(app)));
        app.addMenuEntry(new Action(FontAwesomeIcons.MAIL, "Mail", () -> showImage(app)));

        app.addMenuEntry(new Action(FontAwesomeIcons.MAIL, "Toogle App Color", () -> {
            double random = Math.random();
            if(random < 0.1) {
                app.setBaseColor(Color.ORANGE);
            } else if(random < 0.2) {
                app.setBaseColor(Color.DARKORCHID);
            } else if(random < 0.3) {
                app.setBaseColor(Color.DARKRED);
            } else if(random < 0.4) {
                app.setBaseColor(Color.BLUEVIOLET);
            } else if(random < 0.5) {
                app.setBaseColor(Color.BLUE);
            } else if(random < 0.6) {
                app.setBaseColor(Color.PINK);
            } else if(random < 0.7) {
                app.setBaseColor(Color.PURPLE);
            } else if(random < 0.8) {
                app.setBaseColor(Color.GREEN);
            } else if(random < 0.9) {
                app.setBaseColor(Color.DARKGOLDENROD);
            } else {
                app.setBaseColor(Color.DARKTURQUOISE);
            }
        }));
        app.addMenuEntry(new Action(FontAwesomeIcons.MAIL, "Toogle Toolbar Size", () -> {
            if (app.isToolbarLarge()) {
                app.animateToolbarToSmallVersion();
            } else {
                app.animateToolbarToLargeVersion();
            }
        }));
        app.addMenuEntry(new Action(FontAwesomeIcons.MAIL, "Toogle Toolbar Image", () -> {
            if (app.getToolbarBackgroundImage() != null) {
                app.setToolbarBackgroundImage((Image) null);
            } else {
                app.setToolbarBackgroundImage(SimpleViewAppDemo1.class.getResource("toolbar-background.png").toExternalForm());
            }
        }));

        app.setMediaAsMenuHeader(new DefaultMedia("User4711", "Ich bin eine Beschreibung.", SimpleViewAppDemo1.class.getResource("user-13.jpg").toExternalForm()));

        app.setMenuFooter(new Action(FontAwesomeIcons.COGS, "Configure"));

        showImage(app);

        app.show();
    }

    private static void showForm(Application app) {
        FormLayout formLayout = new FormLayout();

        formLayout.addHeader("Ich bin eine Form");
        formLayout.addField("Name");
        formLayout.addField("Description", EditorType.TEXTAREA);

        formLayout.addSeperator();

        formLayout.addField("Gender", EditorType.COMBOBOX);
        formLayout.addField("Age");

        formLayout.addHeader("Adresse", "Bitte die Mail-Adresse angeben.");
        formLayout.addField("Mail");
        formLayout.addField("Mail2");
        formLayout.addField("Phone");
        formLayout.addField("Skype");
        formLayout.addActions(new Action("Save"), new Action("Cancel"));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(formLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        WorkbenchView view = new WorkbenchView();
        view.setCenterNode(scrollPane);


        app.setWorkbench(view);
        app.clearGlobalActions();
    }

    private static void showImage(Application app) {
        SimpleImageView imageView = new SimpleImageView();
        imageView.setImage(SimpleViewAppDemo1.class.getResource("pic.jpg").toExternalForm());

        imageView.setOverlay(new Overlay());

        WorkbenchView view = new WorkbenchView();
        view.setCenterNode(imageView);
        app.setWorkbench(view);

        app.clearGlobalActions();
        app.addGlobalAction(new Action(FontAwesomeIcons.NAV, () -> imageView.toggleOverlayVisibility()));
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
        footer.addAction(new Action(FontAwesomeIcons.PHONE, "Call"));
        footer.addAction(new Action(FontAwesomeIcons.MAIL, "Send message", () -> view.setCenterNode(createChatTimeline())));
        view.setFooterNode(footer);

        app.setWorkbench(view);

        app.clearGlobalActions();
        app.addGlobalAction(new Action(FontAwesomeIcons.VOLUMNE_DOWN));
        app.addGlobalAction(new Action(FontAwesomeIcons.VOLUMNE_UP));
    }

    private static void showPersonTable(Application app) {
        WorkbenchView view = new WorkbenchView();

        MediaTable table = new MediaTable();

        table.getItems().add(new DefaultMedia("Test01", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-01.jpg").toExternalForm()));
        table.getItems().add(new DefaultMedia("Test02", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-02.jpg").toExternalForm()));
        table.getItems().add(new DefaultMedia("Test03", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-03.jpg").toExternalForm()));
        table.getItems().add(new DefaultMedia("Test04", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-04.jpg").toExternalForm()));
        table.getItems().add(new DefaultMedia("Test05", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-05.jpg").toExternalForm()));
        table.getItems().add(new DefaultMedia("Test06", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-06.jpg").toExternalForm()));
        table.getItems().add(new DefaultMedia("Test07", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-07.jpg").toExternalForm()));
        table.getItems().add(new DefaultMedia("Test08", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-08.jpg").toExternalForm()));
        table.getItems().add(new DefaultMedia("Test09", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-09.jpg").toExternalForm()));
        table.getItems().add(new DefaultMedia("Test10", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-10.jpg").toExternalForm()));
        table.getItems().add(new DefaultMedia("Test11", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-11.jpg").toExternalForm()));
        table.getItems().add(new DefaultMedia("Test12", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-12.jpg").toExternalForm()));
        table.getItems().add(new DefaultMedia("Test13", "Ich bin eine Beschreibung", SimpleViewAppDemo1.class.getResource("user-13.jpg").toExternalForm()));

        view.setCenterNode(table);

        ActionFooter footer = new ActionFooter();
        footer.addAction(new Action(FontAwesomeIcons.PHONE, "Call"));
        footer.addAction(new Action(FontAwesomeIcons.MAIL, "Send message", () -> view.setCenterNode(createChatTimeline())));
        view.setFooterNode(footer);

        app.setWorkbench(view);

        app.clearGlobalActions();
        app.addGlobalAction(new Action(FontAwesomeIcons.VOLUMNE_DOWN));
        app.addGlobalAction(new Action(FontAwesomeIcons.VOLUMNE_UP));
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
