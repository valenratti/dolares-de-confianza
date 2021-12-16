package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.postgres;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
}
