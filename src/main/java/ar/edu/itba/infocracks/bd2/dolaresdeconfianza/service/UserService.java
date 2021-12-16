package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;

public interface UserService {
    UserEntity save(String username, String password, String firstName, String lastName, double locationX, double locationY);
}
