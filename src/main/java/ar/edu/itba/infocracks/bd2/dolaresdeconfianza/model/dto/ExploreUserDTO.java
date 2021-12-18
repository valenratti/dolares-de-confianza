package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.config.GeometryConfig;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExploreUserDTO {
    private Long userId;
    private String username;
    private double distanceInMeters;

    public static ExploreUserDTO of(UserEntity exploringUser, UserEntity recommendedUser){
        return new ExploreUserDTO(recommendedUser.getId(), recommendedUser.getUsername(),
                GeometryConfig.getDistanceInMeters(exploringUser.getLocation().getCoordinate(), recommendedUser.getLocation().getCoordinate()));
    }
}
