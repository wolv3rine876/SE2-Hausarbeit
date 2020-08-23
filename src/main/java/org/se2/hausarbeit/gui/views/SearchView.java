package org.se2.hausarbeit.gui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;
import org.se2.hausarbeit.model.dto.OperationFeedback;
import org.se2.hausarbeit.model.dto.ReservationDTO;
import org.se2.hausarbeit.model.dto.SearchRequest;
import org.se2.hausarbeit.model.dto.ReservationsDTO;
import org.se2.hausarbeit.process.control.SearchControl;
import org.se2.hausarbeit.services.util.PAGE;

import java.util.ArrayList;
import java.util.Set;


@Route(PAGE.SEARCH)
@Theme(value = Material.class)
@CssImport("./styles/base.css")
public class SearchView extends BaseView {
    private TextField searchField;
    private Button reservationButton;
    private Grid<ReservationDTO> grid;
    private ArrayList<ReservationDTO> gridData;
    private SearchControl control;

    public SearchView() {
        control = new SearchControl();

        // Button
        reservationButton = new Button("Reservieren", new Icon(VaadinIcon.CHECK_CIRCLE));
        reservationButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
        reservationButton.setVisible(false);
        reservationButton.setDisableOnClick(true);
        reservationButton.addClickListener(click -> setReservation());
        Div div1 = new Div(reservationButton);
        div1.setWidth("25%");
        // search field
        searchField = new TextField("Suche");
        searchField.setClearButtonVisible(true);
        searchField.addValueChangeListener(event -> { search(); });
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.setWidth("50%");
        // spacer
        Div div2 = new Div();
        div2.setWidth("25%");

        HorizontalLayout upper = new HorizontalLayout(div1, searchField, div2);
        upper.setWidthFull();
        upper.setPadding(true);
        upper.setAlignItems(FlexComponent.Alignment.CENTER);
        upper.setAlignItems(FlexComponent.Alignment.CENTER);

        // grid
        grid = new Grid<ReservationDTO>(ReservationDTO.class);
        grid.addSelectionListener(s -> select());
        grid.setVisible(false);
        grid.setSizeFull();

        gridData = new ArrayList<>();

        grid.setItems(gridData);
        grid.removeAllColumns();
        grid.addColumn(ReservationDTO::getBrand).setWidth("15%").setHeader("Marke");
        grid.addColumn(ReservationDTO::getModel).setWidth("15%").setHeader("Modell");
        grid.addColumn(ReservationDTO::getYearOfProduction).setWidth("15%").setHeader("Baujahr");
        grid.addColumn(ReservationDTO::getDescription).setWidth("45%").setHeader("Beschreibung");
        grid.addClassName("transparent");

        VerticalLayout layout = new VerticalLayout(upper, grid);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.addClassName("search");

        this.setPageContent(layout, true, PAGE.SEARCH);
    }

    private void search() {
        grid.deselectAll();
        String input = searchField.getValue();
        if(input == null || input.equals("")) {
            if(reservationButton.isVisible()) reservationButton.setVisible(false);
            if(grid.isVisible()) grid.setVisible(false);
        }
        else {
            if(!grid.isVisible()) grid.setVisible(true);
            ReservationsDTO result = control.search(new SearchRequest(input));
            if(result.success) {
                gridData = result.getResult();
                grid.setItems(gridData);
            }
            else {
                this.handelOperationFeedback(result);
            }
        }
    }
    private void select() {
        Set<ReservationDTO> selected = grid.getSelectedItems();
        if(selected.size() == 0 && reservationButton.isVisible()) reservationButton.setVisible(false);
        else {
            if(!reservationButton.isVisible()) reservationButton.setVisible(true);
            ReservationDTO selection = selected.iterator().next();
            if(selection.isReserved() && reservationButton.isEnabled()) reservationButton.setEnabled(false);
            else if(!selection.isReserved() && !reservationButton.isEnabled()) reservationButton.setEnabled(true);
        }
    }
    private void setReservation() {
        Set<ReservationDTO> selected = grid.getSelectedItems();
        if(selected.size() == 1) {
            ReservationDTO dto = selected.iterator().next();
            OperationFeedback feedback = control.addReservation(dto);
            if(feedback.success) dto.setReserved(true);
            this.handelOperationFeedback(feedback);
        }
    }
}
