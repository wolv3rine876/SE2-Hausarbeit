package org.se2.hausarbeit.gui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.swing.text.html.parser.ContentModel;

public class Header extends HorizontalLayout {

    public Header() {

        this.setWidthFull();
        this.setHeight("10%");

        // company logo
        H3 companyname = new H3("CarLook Ltd.");
        this.add(companyname);

        // logout button
        Button logout = new Button(new Icon(VaadinIcon.SIGN_OUT));
        this.add(logout);
        // not signed in
        if(!true) logout.setVisible(false);

        this.setAlignItems(Alignment.CENTER);
        this.setFlexGrow(1, companyname);
        this.setPadding(true);
        this.setSpacing(true);

    }
}
