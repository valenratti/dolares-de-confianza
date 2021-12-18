package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service;

public interface OfferService {
    public OfferDTO createOffer();
    public List<OfferDTO> findNearbyOffers();
    public List<OfferDTO> findOffersByCurrency();

}
