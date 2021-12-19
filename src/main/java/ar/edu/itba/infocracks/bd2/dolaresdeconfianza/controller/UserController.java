package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.controller;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.controller.dto.UserDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.exception.InvalidCredentialsException;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.exception.UserNotFoundException;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.ExploreUserDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.InvitationResponseDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security.LoginForm;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security.SessionUtils;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
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
    @ApiOperation("Sign Up")
    UserDTO register(@RequestBody UserDTO user){
        return userService.signUp(user);
    }

    @PostMapping(value = "/authenticate")
    @ApiOperation("Authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginForm authenticationRequest) throws Exception{
        try{
            return ResponseEntity.of(Optional.of(userService.authenticate(authenticationRequest)));
        } catch(InvalidCredentialsException e){
            return ResponseEntity.status(e.getStatus()).build();
        }
    }

    @GetMapping("/explore")
    @ApiOperation("Explore Nearby Users")
    public List<ExploreUserDTO> getUsers(@RequestParam(defaultValue = "10000.0") Double distance) {
        UserEntity userEntity = sessionUtils.getLoggedInUser();
        return userService.getSuggestedFriendshipsForUser(userEntity,distance);
    }

    @PostMapping("/{userId}/invite")
    @ApiOperation("Send Friend Invitation")
    public ResponseEntity<?> sendFriendInvite(@PathVariable Long userId) throws UserNotFoundException {
        UserEntity userEntity = sessionUtils.getLoggedInUser();
        userService.sendFriendshipInvite(userEntity, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invite/{inviteId}")
    @ApiOperation("Respond Friend Invitation")
    public ResponseEntity<?> respondFriendInvite(@PathVariable Long inviteId, @RequestBody InvitationResponseDTO invitationResponse) throws UserNotFoundException {
        userService.respondFriendshipInvite(inviteId, invitationResponse.getResponse());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ApiOperation("See Friendship Invites")
    public ResponseEntity<?> getFriendshipInvites(){
        return ResponseEntity.of(Optional.of(userService.getFriendshipsInvites()));
    }


}
