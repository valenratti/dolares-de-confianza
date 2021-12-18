package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipInvitation {
    @Id
    private Long id;
    @ManyToOne
    private UserEntity invitedBy;
    @ManyToOne
    private UserEntity invitedUser;
    private LocalDateTime invitedAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime deniedAt;

    public FriendshipInvitation(UserEntity invitedBy, UserEntity invitedUser, LocalDateTime invitedAt, LocalDateTime acceptedAt, LocalDateTime deniedAt) {
        this.invitedBy = invitedBy;
        this.invitedUser = invitedUser;
        this.invitedAt = invitedAt;
        this.acceptedAt = acceptedAt;
        this.deniedAt = deniedAt;
    }
}
