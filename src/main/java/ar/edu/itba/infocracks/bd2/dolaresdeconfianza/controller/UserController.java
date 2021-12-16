package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.controller;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.controller.dto.UserDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping()
    UserDTO register(@RequestBody UserDTO user){
        UserEntity userEntity = userService.save(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getLocationX(), user.getLocationY());
        return new UserDTO(userEntity.getId(), userEntity.getUsername(),userEntity.getFirstName(), userEntity.getLastName(), userEntity.getLocation().getX(),userEntity.getLocation().getY());
    }
}
