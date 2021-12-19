package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.config.GeometryConfig;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.FriendshipInvitation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class FriendshipInvitationDTO {
    private Long invitationId;
    private String username;
    private double distance;
    private LocalDateTime invitedAt;

    public static FriendshipInvitationDTO of(FriendshipInvitation invitation){
        return new FriendshipInvitationDTO(invitation.getId(), invitation.getInvitedBy().getUsername(),
                GeometryConfig.getDistanceInMeters(invitation.getInvitedUser().getLocation().getCoordinate(), invitation.getInvitedBy().getLocation().getCoordinate()),
                invitation.getInvitedAt());
    }
}
