package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "friendship_invitations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
