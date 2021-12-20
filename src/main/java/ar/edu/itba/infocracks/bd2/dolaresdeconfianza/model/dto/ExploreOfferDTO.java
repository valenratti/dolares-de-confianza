package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto;

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

    // TODO: Ver si hace falta
//    public static ExploreOfferDTO of(UserEntity exploringUser, UserEntity recommendedUser){
//        return new ExploreOfferDTO();
//    }
}
