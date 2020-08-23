package org.se2.hausarbeit.process.control;

import com.vaadin.flow.component.UI;
import org.se2.hausarbeit.model.dao.ReservationDao;
import org.se2.hausarbeit.model.dto.OperationFeedback;
import org.se2.hausarbeit.model.dto.ReservationDTO;
import org.se2.hausarbeit.model.dto.SearchRequest;
import org.se2.hausarbeit.model.dto.ReservationsDTO;
import org.se2.hausarbeit.model.entity.Reservation;
import org.se2.hausarbeit.model.entity.User;
import org.se2.hausarbeit.process.exception.DatabaseException;
import org.se2.hausarbeit.services.util.SESSIONATTRIBUT;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SearchControl {

    public ReservationsDTO search(SearchRequest request) {
        ReservationsDTO result = null;
        try {
            int userId = ((User)UI.getCurrent().getSession().getAttribute(SESSIONATTRIBUT.USER)).getId();
            ArrayList<Reservation> reservations = new ReservationDao().search(request.getSearch(), userId);
            ArrayList<ReservationDTO> dtos = reservations.stream().map(r -> {
                ReservationDTO dto = new ReservationDTO();
                dto.setBrand(r.getCar().getBrand());
                dto.setModel(r.getCar().getModel());
                dto.setYearOfProduction(r.getCar().getYearOfProduction());
                dto.setDescription(r.getCar().getDescription());
                dto.setCarId(r.getCar().getId());
                dto.setReserved(r.isReserved());
                dto.setReservationId(r.getId());
                return dto;
            }).collect(Collectors.toCollection(ArrayList::new));
            result = new ReservationsDTO(true, null, dtos);
        }
        catch (DatabaseException e) {
            e.printStackTrace();
            result = new ReservationsDTO(false, "Fehler beim Ausführen der Datenbankanfrage");
        }
        return result;
    }
    public OperationFeedback addReservation(ReservationDTO dto) {
        OperationFeedback feedback = null;
        try {
            int userId = ((User)UI.getCurrent().getSession().getAttribute(SESSIONATTRIBUT.USER)).getId();
            new ReservationDao().addReservation(dto.getCarId(), userId);
            feedback = new OperationFeedback(true, null);
        }
        catch (DatabaseException e) {
            feedback = new OperationFeedback(false, e.getMessage());
        }
        return feedback;
    }
}
