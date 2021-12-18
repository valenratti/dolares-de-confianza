package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FriendshipInvitationDTO {
    private Long invitationId;
    private String username;
    private double distance;
    private LocalDateTime invitedAt;

    private static
}
