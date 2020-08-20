package org.se2.hausarbeit.gui.components;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Footer extends HorizontalLayout {

    public Footer() {
        this.setWidthFull();
        //this.setHeight("5%");

        Anchor contactLink = new Anchor("", "Kontak");
        Anchor impressumLink = new Anchor("", "Impressum");
        Anchor aboutusLink = new Anchor("", "Über Uns");
        contactLink.getElement().getStyle().set("padding", "0");
        impressumLink.getElement().getStyle().set("padding", "0");
        aboutusLink.getElement().getStyle().set("padding", "0");

        VerticalLayout contact = new VerticalLayout(contactLink);
        contact.setAlignItems(Alignment.CENTER);
        contact.getElement().getStyle().set("padding", "0");
        VerticalLayout impressum = new VerticalLayout(impressumLink);
        impressum.setAlignItems(Alignment.CENTER);
        impressum.getElement().getStyle().set("padding", "0");
        VerticalLayout aboutus = new VerticalLayout(aboutusLink);
        aboutus.setAlignItems(Alignment.CENTER);
        aboutus.getElement().getStyle().set("padding", "0");

        add(contact);
        add(impressum);
        add(aboutus);

        this.setFlexGrow(1, contact);
        this.setFlexGrow(1, impressum);
        this.setFlexGrow(1, aboutus);

        //this.setPadding(true);
        //this.setSpacing(true);

        this.setAlignItems(Alignment.CENTER);
    }
}
