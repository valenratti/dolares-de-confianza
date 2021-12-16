package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.postgres;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
}
