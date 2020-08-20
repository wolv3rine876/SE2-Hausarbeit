package org.se2.hausarbeit.gui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import org.se2.hausarbeit.gui.components.Footer;
import org.se2.hausarbeit.gui.components.Header;
import org.se2.hausarbeit.model.entity.User;
import org.se2.hausarbeit.services.util.PAGE;
import org.se2.hausarbeit.services.util.SESSIONATTRIBUT;

public abstract class BaseView extends AppLayout implements BeforeEnterObserver {
    private boolean checkLogin;
    void setPageContent(VerticalLayout layout, boolean checkLogin) {
        this.checkLogin = checkLogin;
        this.addToNavbar(new Header());
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
}
