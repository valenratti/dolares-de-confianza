package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
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

    public UserDTO(Long id, String username, String email, String firstName, String lastName, double locationX, double locationY) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.locationX = locationX;
        this.locationY = locationY;
    }
}
