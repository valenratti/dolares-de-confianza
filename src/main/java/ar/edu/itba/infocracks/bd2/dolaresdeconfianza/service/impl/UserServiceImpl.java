package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service.impl;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.config.GeometryConfig;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.neo4j.UserNode;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.postgres.UserEntityRepository;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.neo4j.UserNodeRepository;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserEntityRepository userEntityRepository;
    private final UserNodeRepository userNodeRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserEntityRepository userEntityRepository, UserNodeRepository userNodeRepository){
        this.encoder = passwordEncoder;
        this.userEntityRepository = userEntityRepository;
        this.userNodeRepository = userNodeRepository;
    }

    @Override
    @Transactional
    public UserEntity save(String username, String password, String firstName, String lastName, double locationX, double locationY) {

        UserEntity user = userEntityRepository.save(new UserEntity(username,encoder.encode(password),firstName,lastName, GeometryConfig.pointFromCoordinates(locationX,locationY)));
        UserNode userNode = userNodeRepository.save(new UserNode(user.getId(),user.getUsername()));

        LOGGER.info("Saved {} to both databases", user);
        return user;
    }

}
