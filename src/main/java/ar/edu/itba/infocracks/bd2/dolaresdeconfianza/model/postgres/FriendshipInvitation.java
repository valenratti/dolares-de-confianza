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
    private LocalDateTime acceptedAt;
    private LocalDateTime deniedAt;
}
