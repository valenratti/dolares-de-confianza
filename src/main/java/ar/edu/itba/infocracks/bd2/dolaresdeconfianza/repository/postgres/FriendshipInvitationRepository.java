package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.postgres;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.FriendshipInvitation;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipInvitationRepository extends JpaRepository<FriendshipInvitation,Long> {
    List<FriendshipInvitation> findAllByInvitedUser(UserEntity invitedUser);
}
