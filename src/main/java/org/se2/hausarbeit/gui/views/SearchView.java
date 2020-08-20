package org.se2.hausarbeit.gui.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.se2.hausarbeit.services.util.PAGE;

@Route(PAGE.SEARCH)
public class SearchView extends BaseView {

    public SearchView() {
        VerticalLayout layout = new VerticalLayout();
        this.setPageContent(layout, true);
    }


}
