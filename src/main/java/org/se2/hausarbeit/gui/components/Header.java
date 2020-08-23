package org.se2.hausarbeit.gui.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import org.se2.hausarbeit.services.util.PAGE;
import org.se2.hausarbeit.services.util.SESSIONATTRIBUT;

public class Header extends VerticalLayout {
    public Header(String page) {
        this.setWidthFull();

        // company logo
        H5 companyname = new H5("CarLook Ltd.");
        // logout button
        Button logout = new Button(new Icon(VaadinIcon.SIGN_OUT));
        logout.addClickListener(click -> {
           UI.getCurrent().getSession().setAttribute(SESSIONATTRIBUT.USER, null);
           UI.getCurrent().navigate(PAGE.WELCOME);
        });
        HorizontalLayout upper = new HorizontalLayout(companyname, logout);
        upper.setWidthFull();
        upper.setHeight("60%");
        upper.setAlignItems(Alignment.CENTER);
        upper.setFlexGrow(1, companyname);
        upper.setMargin(false);

        Tab search = new Tab("Suche");
        Tab reservation = new Tab("Reservierungen");
        Tabs tabs = new Tabs(search, reservation);
        if(page.equals(PAGE.RESERVATION)) {
            tabs.setSelectedTab(reservation);
        }
        else {
            tabs.setSelectedTab(search);
        }
        tabs.addSelectedChangeListener(click -> {
            if(search.isSelected()) {
                UI.getCurrent().navigate(PAGE.SEARCH);
            }
            else {
                UI.getCurrent().navigate(PAGE.RESERVATION);
            }
        });

        if(UI.getCurrent().getSession().getAttribute("user") == null) {
            logout.setVisible(false);
            tabs.setVisible(false);
        }
        this.add(upper, tabs);
        this.setAlignItems(Alignment.CENTER);
        this.setPadding(false);
    }
}
