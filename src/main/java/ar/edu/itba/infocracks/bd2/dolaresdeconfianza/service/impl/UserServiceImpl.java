package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service.impl;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.config.GeometryConfig;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.UserDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.exception.InvalidCredentialsException;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.exception.UserNotFoundException;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.InvitationResponse;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.ExploreUserDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.FriendshipInvitationDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.neo4j.UserNode;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.FriendshipInvitation;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.postgres.FriendshipInvitationRepository;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.postgres.UserEntityRepository;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.neo4j.UserNodeRepository;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security.AuthenticationResponse;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security.LoginForm;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security.SessionUtils;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserEntityRepository userEntityRepository;
    private final UserNodeRepository userNodeRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final FriendshipInvitationRepository friendshipInvitationRepository;
    private final SessionUtils sessionUtils;

    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserEntityRepository userEntityRepository, UserNodeRepository userNodeRepository, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, FriendshipInvitationRepository friendshipInvitationRepository, SessionUtils sessionUtils){
        this.encoder = passwordEncoder;
        this.userEntityRepository = userEntityRepository;
        this.userNodeRepository = userNodeRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.friendshipInvitationRepository = friendshipInvitationRepository;
        this.sessionUtils = sessionUtils;
    }

    @Override
    @Transactional
    public UserEntity save(String username, String email, String password, String firstName, String lastName,
                           double locationX, double locationY) {
        UserEntity user = userEntityRepository.save(new UserEntity(username, email, encoder.encode(password),
                firstName,lastName, GeometryConfig.pointFromCoordinates(locationX,locationY)));
        UserNode userNode = userNodeRepository.save(new UserNode(user.getId(),user.getUsername()));
        LOGGER.info("Saved {} to both databases", user);
        return user;
    }

    @Override
    public AuthenticationResponse authenticate(LoginForm authenticationRequest) throws InvalidCredentialsException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(token);
        }
        catch (AuthenticationException e){
            throw new InvalidCredentialsException();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = sessionUtils.generateToken(userDetails);

        return new AuthenticationResponse(jwt);
    }

    @Override
    public List<ExploreUserDTO> getSuggestedFriendshipsForUser(UserEntity exploringUser, Double distance) {
        return userEntityRepository.findNearbyUsers(exploringUser.getLocation().getX(),
                exploringUser.getLocation().getY(), distance)
                .stream()
                .filter(userEntity -> !userEntity.getId().equals(exploringUser.getId()))
                .map((recommendedUser) -> ExploreUserDTO.of(exploringUser, recommendedUser) )
                .collect(Collectors.toList());
    }

    @Override
    public FriendshipInvitationDTO sendFriendshipInvite(UserEntity invitedBy, Long userId) throws UserNotFoundException {
        UserEntity invitedUser = userEntityRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        FriendshipInvitation friendshipInvitation = new FriendshipInvitation(invitedBy, invitedUser, LocalDateTime.now(),
                null, null);
        friendshipInvitationRepository.save(friendshipInvitation);
        return FriendshipInvitationDTO.of(friendshipInvitation);
    }

    private void acceptFriendshipInvite(Long invitationId) throws UserNotFoundException {
        FriendshipInvitation friendshipInvitation = friendshipInvitationRepository.findById(invitationId)
                .orElseThrow(NoSuchElementException::new);
        UserEntity loggedUser = sessionUtils.getLoggedInUser();
        if(!friendshipInvitation.getInvitedUser().equals(loggedUser))
            throw new IllegalArgumentException("The invitation you have tried to accept is not for you.");
        friendshipInvitation.setAcceptedAt(LocalDateTime.now());
        friendshipInvitationRepository.save(friendshipInvitation);
        UserNode invitedUserNode = userNodeRepository.findById(loggedUser.getId())
                .orElseThrow(UserNotFoundException::new);

        UserNode invitorUserNode = userNodeRepository.findById(friendshipInvitation.getInvitedBy().getId())
                .orElseThrow(UserNotFoundException::new);
        invitedUserNode.getFriends().add(invitorUserNode);
        userNodeRepository.save(invitedUserNode);
        //TODO: Es necesario?
//        invitorUserNode.getFriends().add(invitedUserNode);
//        userNodeRepository.save(invitorUserNode);
    }

    private void declineFriendshipInvite(Long invitationId) throws UserNotFoundException {
        FriendshipInvitation friendshipInvitation = friendshipInvitationRepository.findById(invitationId)
                .orElseThrow(NoSuchElementException::new);
        UserEntity loggedUser = sessionUtils.getLoggedInUser();
        if(!friendshipInvitation.getInvitedUser().equals(loggedUser))
            throw new IllegalArgumentException("The invitation you have tried to accept is not for you.");
        friendshipInvitation.setDeniedAt(LocalDateTime.now());
        friendshipInvitationRepository.save(friendshipInvitation);
    }

    @Override
    @Transactional
    public void respondFriendshipInvite(Long invitationId, InvitationResponse response) throws UserNotFoundException {
        FriendshipInvitation friendshipInvitation = friendshipInvitationRepository.findById(invitationId)
                .orElseThrow(NoSuchElementException::new);
        UserEntity loggedUser = sessionUtils.getLoggedInUser();
        if(!friendshipInvitation.getInvitedUser().equals(loggedUser))
            throw new IllegalArgumentException("The invitation you have tried to accept is not for you.");
        switch (response){
            case ACCEPT:
                this.acceptFriendshipInvite(invitationId);
                break;
            case DECLINE:
                declineFriendshipInvite(invitationId);
            default:
                throw new IllegalArgumentException("You can only either accept or decline an invite.");
        }
    }

    @Override
    public List<FriendshipInvitationDTO> getFriendshipsInvites() {
        UserEntity loggedUser = sessionUtils.getLoggedInUser();
        return friendshipInvitationRepository.findAllByInvitedUser(loggedUser)
                .stream()
                .filter(obj -> obj.getAcceptedAt() == null && obj.getDeniedAt() == null)
                .map(FriendshipInvitationDTO::of)
                .collect(Collectors.toList());
    }

// TODO: por que no tiene @Override ?
    public UserDTO signUp(UserDTO user) {
        UserEntity userEntity = this.save(user.getUsername(), user.getEmail(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getLocationX(), user.getLocationY());
        return new UserDTO(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail(),
                userEntity.getFirstName(), userEntity.getLastName(), userEntity.getLocation().getX(),
                userEntity.getLocation().getY());
    }



}
