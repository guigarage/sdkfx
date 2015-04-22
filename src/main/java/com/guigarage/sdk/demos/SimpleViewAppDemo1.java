package com.guigarage.sdk.demos;

import com.guigarage.sdk.ApplicationFX;
import com.guigarage.sdk.footer.ActionFooter;
import com.guigarage.sdk.application.Application;
import com.guigarage.sdk.action.Action;
import com.guigarage.sdk.util.Icon;
import com.guigarage.sdk.container.BaseContainer;
import com.guigarage.sdk.container.FormWorkbench;
import com.guigarage.sdk.container.WorkbenchView;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * Created by hendrikebbers on 12.03.15.
 */
public class SimpleViewAppDemo1 {

    public static void main(String... args) {
        ApplicationFX.run(s -> {

            //BaseContainer widget1 = BaseContainer.build().withTitle("Form 1").withContent(new Button("Test"));
            //BaseContainer widget2 = BaseContainer.build().withTitle("Form 2").withInfoFooter("Ich bin eine Information").withContent(new Button("Test"));
            //BaseContainer widget3 = BaseContainer.build().withTitle("Form 3").withToolbarItem(new Action(Icon.ANGLE_LEFT)).withToolbarItem(new Action(Icon.ANGLE_RIGHT)).withContent(new Button("Test"));

           // FormWorkbench workbench = new FormWorkbench();
            //.withChild(widget1);

            ActionFooter footer = new ActionFooter();
            footer.addAction(new Action("Save"));
            footer.addAction(new Action("Cancel"));

            WorkbenchView view = new WorkbenchView();
            //view.setCenterNode(workbench);
            view.setFooterNode(footer);





            Application app = new Application();
            app.setTitle("MyApp");
            app.setBaseColor(Color.DARKORCHID);
            app.addToolbarItem(new Action(Icon.VOLUMNE_DOWN));
            app.addToolbarItem(new Action(Icon.VOLUMNE_UP));
            app.setWorkbench(view);

            app.addMenuEntry(new Action(Icon.CALENDAR, "Google Calendar"));
            app.addMenuEntry(new Action(Icon.COGS, "System Settings"));
            app.addMenuEntry(new Action(Icon.MAIL, "Mail"));

            app.show(s);
        });
    }
}
