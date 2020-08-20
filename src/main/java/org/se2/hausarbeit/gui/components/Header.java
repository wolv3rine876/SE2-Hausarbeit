package org.se2.hausarbeit.gui.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.se2.hausarbeit.services.util.PAGE;
import org.se2.hausarbeit.services.util.SESSIONATTRIBUT;

public class Header extends HorizontalLayout {
    public Header() {
        this.setWidthFull();
        // company logo
        H5 companyname = new H5("CarLook Ltd.");
        this.add(companyname);

        // logout button
        Button logout = new Button(new Icon(VaadinIcon.SIGN_OUT));
        logout.addClickListener(click -> {
           UI.getCurrent().getSession().setAttribute(SESSIONATTRIBUT.USER, null);
           UI.getCurrent().navigate(PAGE.WELCOME);
        });
        this.add(logout);
        // not signed in

        if(UI.getCurrent().getSession().getAttribute("user") == null) logout.setVisible(false);

        this.setAlignItems(Alignment.CENTER);
        this.setFlexGrow(1, companyname);
    }
}
