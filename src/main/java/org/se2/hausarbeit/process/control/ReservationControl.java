package org.se2.hausarbeit.process.control;

import com.vaadin.flow.component.UI;
import org.se2.hausarbeit.model.dao.ReservationDao;
import org.se2.hausarbeit.model.dto.OperationFeedback;
import org.se2.hausarbeit.model.dto.ReservationDTO;
import org.se2.hausarbeit.model.dto.ReservationsDTO;
import org.se2.hausarbeit.model.entity.User;
import org.se2.hausarbeit.process.exception.DatabaseException;
import org.se2.hausarbeit.services.util.SESSIONATTRIBUT;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReservationControl {

    public ReservationsDTO getReservations() {
        ReservationsDTO result = null;
        try {
            User user = ((User) UI.getCurrent().getSession().getAttribute(SESSIONATTRIBUT.USER));
            if (user != null) {
                ArrayList<ReservationDTO> dtos = new ReservationDao().getReservationsForUser(user.getId()).stream().map(r -> {
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
        }
        catch (DatabaseException e) {
            e.printStackTrace();
            result = new ReservationsDTO(false, e.getMessage());
        }
        return result;
    }
    public OperationFeedback deleteReservation(ReservationDTO dto) {
        OperationFeedback result = null;
        try {
            new ReservationDao().deleteReservation(dto.getReservationId());
            result = new OperationFeedback(true, null);
        }
        catch (DatabaseException ex){
            ex.printStackTrace();
            result = new OperationFeedback(false, ex.getMessage());
        }
        return result;
    }
}
