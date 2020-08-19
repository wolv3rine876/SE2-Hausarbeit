package org.se2.hausarbeit.gui.components;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Footer extends HorizontalLayout {

    public Footer() {
        this.setWidthFull();
        this.setHeight("5%");


        VerticalLayout contact = new VerticalLayout(new Anchor("", "Kontak"));
        contact.setAlignItems(Alignment.CENTER);
        VerticalLayout impressum = new VerticalLayout(new Anchor("", "Impressum"));
        impressum.setAlignItems(Alignment.CENTER);
        VerticalLayout aboutus = new VerticalLayout(new Anchor("", "Über Uns"));
        aboutus.setAlignItems(Alignment.CENTER);

        add(contact);
        add(impressum);
        add(aboutus);

        this.setFlexGrow(1, contact);
        this.setFlexGrow(1, impressum);
        this.setFlexGrow(1, aboutus);

        this.setPadding(true);
        this.setSpacing(true);

        this.setAlignItems(Alignment.CENTER);
    }
}
