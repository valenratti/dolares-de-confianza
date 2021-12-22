package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.UserDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.exception.InvalidCredentialsException;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.exception.UserNotFoundException;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.InvitationResponse;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.ExploreUserDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.FriendshipInvitationDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security.AuthenticationResponse;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security.LoginForm;

import java.util.List;

public interface UserService {

    UserDTO signUp(UserDTO user);

    UserEntity save(String username, String email, String password, String firstName, String lastName, double locationX, double locationY);

    AuthenticationResponse authenticate(LoginForm form) throws InvalidCredentialsException;

    List<ExploreUserDTO> getSuggestedFriendshipsForUser(UserEntity exploringUser, Double distance);

    FriendshipInvitationDTO sendFriendshipInvite(UserEntity userEntity, Long userId) throws UserNotFoundException;

    void respondFriendshipInvite(Long invitationId, InvitationResponse response) throws UserNotFoundException;

    List<FriendshipInvitationDTO> getFriendshipsInvites();
}
