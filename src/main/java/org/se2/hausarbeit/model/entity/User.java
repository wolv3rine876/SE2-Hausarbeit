package org.se2.hausarbeit.model.entity;

public class User {
    private String name;
    private String lastname;
    private String role;
    private String email;
    private String password;
    private int id;

    public User() {}
    public User(String name, String lastname, String role, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.role = role;
        this.email = email;
        this.password = password;
    }
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getLastname() {return lastname;}
    public void setLastname(String lastname) {this.lastname = lastname;}
    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
}
