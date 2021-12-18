package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.postgres;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
    @Query(value="SELECT * FROM users WHERE ST_DWithin(cast(users.location as geography),ST_SetSRID(ST_Point(?1, ?2),4326), ?3);", nativeQuery = true)
    List<UserEntity> findNearbyUsers(double locationX, double locationY, Double distance);
}
