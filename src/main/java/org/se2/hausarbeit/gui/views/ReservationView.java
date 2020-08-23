package org.se2.hausarbeit.gui.views;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;
import org.se2.hausarbeit.model.dto.OperationFeedback;
import org.se2.hausarbeit.model.dto.ReservationDTO;
import org.se2.hausarbeit.model.dto.ReservationsDTO;
import org.se2.hausarbeit.process.control.ReservationControl;
import org.se2.hausarbeit.services.util.PAGE;

import java.util.ArrayList;
import java.util.Set;

@Route(PAGE.RESERVATION)
@Theme(value = Material.class)
@CssImport("./styles/base.css")
public class ReservationView extends BaseView {
    private Button deleteButton;
    private Grid<ReservationDTO> grid;
    private ArrayList<ReservationDTO> gridData;
    private ReservationControl control;
    public ReservationView (){
        super();
        control = new ReservationControl();

        // Button
        deleteButton = new Button("löschen", new Icon(VaadinIcon.CLOSE_CIRCLE));
        deleteButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
        deleteButton.setVisible(false);
        deleteButton.addClickListener(click -> deleteReservation());
        HorizontalLayout upper = new HorizontalLayout(deleteButton);
        upper.setWidthFull();
        upper.setPadding(true);
        upper.setHeight("20%");
        upper.setAlignItems(FlexComponent.Alignment.CENTER);
        upper.setAlignItems(FlexComponent.Alignment.CENTER);

        // grid
        grid = new Grid<ReservationDTO>(ReservationDTO.class);
        grid.addSelectionListener(s -> select());
        grid.setSizeFull();

        gridData = this.loadReservation();

        grid.setItems(gridData);
        grid.removeAllColumns();
        grid.addColumn(ReservationDTO::getBrand).setWidth("15%").setHeader("Marke");
        grid.addColumn(ReservationDTO::getModel).setWidth("15%").setHeader("Modell");
        grid.addColumn(ReservationDTO::getYearOfProduction).setWidth("15%").setHeader("Baujahr");
        grid.addColumn(ReservationDTO::getDescription).setWidth("45%").setHeader("Beschreibung");
        grid.addClassName("transparent");

        VerticalLayout layout = new VerticalLayout(upper, grid);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.addClassName("reservation");
        this.setPageContent(layout, true, PAGE.RESERVATION);
    }

    private void select() {
        if(grid.getSelectedItems().size() == 1) this.deleteButton.setVisible(true);
        else this.deleteButton.setVisible(false);
    }

    private ArrayList<ReservationDTO> loadReservation() {
        ArrayList<ReservationDTO> result = new ArrayList<>();
        ReservationsDTO feedback = control.getReservations();
        if(feedback != null) {
            if (feedback.success) result = feedback.getResult();
            else {
                this.handelOperationFeedback(feedback);
                return new ArrayList<>();
            }
        }
        return result;
    }

    private void deleteReservation() {
        Set<ReservationDTO> selected = grid.getSelectedItems();
        if(selected.size() == 1) {
            ReservationDTO dto = selected.iterator().next();
            OperationFeedback feedback = control.deleteReservation(dto);
            if(feedback.success) {
                gridData.remove(dto);
                grid.setItems(gridData);
                deleteButton.setVisible(false);
            }
            this.handelOperationFeedback(feedback);
        }
    }
}
