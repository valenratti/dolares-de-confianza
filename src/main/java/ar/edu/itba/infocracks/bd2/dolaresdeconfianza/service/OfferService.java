package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.ExploreOfferDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.OfferDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;

import java.util.List;

public interface OfferService {

    List<ExploreOfferDTO> getOffers(UserEntity userEntity, String from_currency, String to_currency, double minRate, double maxRate, double minAmount, double maxAmount, long distance, int trustLevel);

    OfferDTO createOffer(UserEntity userEntity, OfferDTO offerDTO);

    long deleteOffer(UserEntity userEntity, long offerId);

//    public ExploreOfferDTO createOffer();
//    public List<ExploreOfferDTO> findNearbyOffers();
//    public List<ExploreOfferDTO> findOffersByCurrency();

}
