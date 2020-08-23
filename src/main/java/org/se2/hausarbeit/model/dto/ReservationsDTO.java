package org.se2.hausarbeit.model.dto;

import java.util.ArrayList;

public class ReservationsDTO extends OperationFeedback {
    private ArrayList<ReservationDTO> result;

    public ReservationsDTO(boolean success, String message) {
        super(success, message);
    }
    public ReservationsDTO(boolean success, String message, ArrayList<ReservationDTO> result) {
        super(success, message);
        this.result = result;
    }

    public ArrayList<ReservationDTO> getResult() {return result;}
    public void setResult(ArrayList<ReservationDTO> result) {this.result = result;}
}
