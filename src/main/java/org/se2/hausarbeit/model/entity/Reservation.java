package org.se2.hausarbeit.model.entity;

public class Reservation {
    private Car car;
    private boolean isReserved = false;
    private int id = -1;

    public Reservation() {}
    public Reservation(Car car, boolean isReserved, int id) {
        this.car = car;
        this.isReserved = isReserved;
        this.id = id;
    }

    public Car getCar() {return car;}
    public void setCar(Car car) {this.car = car;}
    public boolean isReserved() {return isReserved;}
    public void setReserved(boolean reserved) {isReserved = reserved;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
}
