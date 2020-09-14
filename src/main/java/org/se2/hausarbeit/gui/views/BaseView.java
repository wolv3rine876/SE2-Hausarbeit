package org.se2.hausarbeit.gui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import org.se2.hausarbeit.gui.components.Footer;
import org.se2.hausarbeit.gui.components.Header;
import org.se2.hausarbeit.model.dto.OperationFeedback;
import org.se2.hausarbeit.services.util.PAGE;
import org.se2.hausarbeit.services.util.SESSIONATTRIBUT;

public abstract class BaseView extends AppLayout implements BeforeEnterObserver {
    private boolean checkLogin;
    void setPageContent(VerticalLayout layout, boolean checkLogin, String page) {
        this.checkLogin = checkLogin;
        this.addToNavbar(new Header(page));
        
        layout.setHeightFull();
        VerticalLayout content = new VerticalLayout(layout, new Footer());
        content.setSizeFull();
        content.getElement().getStyle().set("padding-left", "0");
        content.getElement().getStyle().set("padding-right", "0");
        this.setContent(content);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(this.checkLogin && UI.getCurrent().getSession().getAttribute(SESSIONATTRIBUT.USER) == null) {
            beforeEnterEvent.rerouteTo(PAGE.WELCOME);
        }
    }
    void handelOperationFeedback(OperationFeedback feedback) {
        if(feedback.message != null) {
            Notification notification = new Notification(feedback.message);
            notification.setDuration(3000);
            notification.removeThemeName("Material");
            ThemeList a = notification.getThemeNames();
            if(!feedback.success) notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            else notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.open();
        }
    }
}
