package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.controller;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.controller.dto.UserDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.exception.InvalidCredentialsException;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.ExploreUserDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security.LoginForm;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security.SessionUtils;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final SessionUtils sessionUtils;


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

    @RequestMapping("/explore")
    public List<ExploreUserDTO> getUsers(@RequestParam(defaultValue = "10000.0") Double distance) {
        UserEntity userEntity = sessionUtils.getLoggedInUser();
        return userService.getSuggestedFriendshipsForUser(userEntity,distance);
    }

    @SneakyThrows
    @PostMapping("/{userId}/invite")
    public ResponseEntity<?> sendFriendInvite(@PathVariable Long userId){
        UserEntity userEntity = sessionUtils.getLoggedInUser();
        userService.sendFriendshipInvite(userEntity, userId);
        return ResponseEntity.ok().build();
    }

}
