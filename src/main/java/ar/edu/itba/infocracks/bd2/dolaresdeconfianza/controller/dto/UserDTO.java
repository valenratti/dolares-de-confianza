package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.controller.dto;

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private double locationX;
    private double locationY;

    public UserDTO(){}

    public UserDTO(String username, String password, String firstName, String lastName, double locationX, double locationY) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public UserDTO(Long id, String username, String firstName, String lastName, double locationX, double locationY) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getLocationX() {
        return locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }
}
