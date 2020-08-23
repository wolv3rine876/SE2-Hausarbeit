package org.se2.hausarbeit.model.dto;

import org.se2.hausarbeit.model.entity.Reservation;

import java.time.LocalDate;

public class ReservationDTO {
    private String brand;
    private String model;
    private LocalDate yearOfProduction;
    private String description;
    private int carId = -1;
    private boolean isReserved = false;
    private int reservationId = -1;

    public ReservationDTO() {}

    public String getBrand() {return brand;}
    public void setBrand(String brand) {this.brand = brand;}
    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}
    public LocalDate getYearOfProduction() {return yearOfProduction;}
    public void setYearOfProduction(LocalDate yearOfProduction) {this.yearOfProduction = yearOfProduction;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public int getCarId() {return carId;}
    public void setCarId(int carId) {this.carId = carId;}
    public boolean isReserved() {return isReserved;}
    public void setReserved(boolean reserved) {isReserved = reserved;}
    public int getReservationId() {return reservationId;}
    public void setReservationId(int reservationId) {this.reservationId = reservationId;}
}
