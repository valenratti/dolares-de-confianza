package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.controller;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.controller.dto.UserDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.exception.InvalidCredentialsException;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security.LoginForm;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    UserDTO register(@RequestBody UserDTO user){
        return userService.signUp(user);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginForm authenticationRequest) throws Exception{
        try{
            return ResponseEntity.of(Optional.of(userService.authenticate(authenticationRequest)));
        } catch(InvalidCredentialsException e){
            return ResponseEntity.status(e.getStatus()).build();
        }
    }

}
