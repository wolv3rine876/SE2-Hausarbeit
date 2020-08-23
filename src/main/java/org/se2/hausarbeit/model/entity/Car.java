package org.se2.hausarbeit.model.entity;

import java.time.LocalDate;

public class Car {
    private String brand;
    private String model;
    private LocalDate yearOfProduction;
    private String description;
    private int id = -1;

    public Car() {}
    public Car(String brand, String model, LocalDate yearOfProduction, String description, int id) {
        this.brand = brand;
        this.model = model;
        this.yearOfProduction = yearOfProduction;
        this.description = description;
        this.id = id;
    }
    public String getBrand() {return brand;}
    public void setBrand(String brand) {this.brand = brand;}
    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}
    public LocalDate getYearOfProduction() {return yearOfProduction;}
    public void setYearOfProduction(LocalDate yearOfProduction) {this.yearOfProduction = yearOfProduction;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
}
