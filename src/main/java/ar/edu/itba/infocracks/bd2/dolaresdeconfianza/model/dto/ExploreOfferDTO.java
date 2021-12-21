package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.config.GeometryConfig;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.Offer;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExploreOfferDTO {
    private long offerId;
    private double fromToRate; //price
    private double minAmount;
    private double maxAmount;
    private int trustLevel;
    private double distanceInMeters;
    private String username;
    private String email; // TODO: y el phone number? o deberia devolver solo el userid?

    public static ExploreOfferDTO of(Offer o, UserEntity explorerUser){
        UserEntity u1 = o.getUser(); // El usuario que oferta
        int distance = GeometryConfig.getDistanceInMeters(u1.getLocation().getCoordinate(), explorerUser.getLocation().getCoordinate())

        return new ExploreOfferDTO(o.getId(), o.getFromToRate(), o.getMinAmount(), o.getMaxAmount(), o.getTrustLevel(), , u1.getUsername(), u1.getEmail());
    }
}
