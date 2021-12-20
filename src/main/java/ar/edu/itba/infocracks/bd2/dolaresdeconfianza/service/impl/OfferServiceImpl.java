package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service.impl;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.ExploreOfferDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.OfferDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.Offer;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.neo4j.UserNodeRepository;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.postgres.OfferRepository;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.postgres.UserEntityRepository;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security.SessionUtils;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final OfferRepository offerRepository;
    private final UserEntityRepository userEntityRepository;
    private final UserNodeRepository userNodeRepository;
    private final SessionUtils sessionUtils;

    public OfferServiceImpl(OfferRepository offerRepository, UserEntityRepository userEntityRepository, UserNodeRepository userNodeRepository, SessionUtils sessionUtils) {
        this.offerRepository = offerRepository;
        this.userEntityRepository = userEntityRepository;
        this.userNodeRepository = userNodeRepository;
        this.sessionUtils = sessionUtils;
    }

    @Transactional
    public Offer save(UserEntity userEntity, String fromCurrency, String toCurrency, double fromToRate,
                      double minAmount, double maxAmount, int trustLevel) {
        Offer offer = offerRepository.save(new Offer(userEntity, fromCurrency, toCurrency, fromToRate,
                minAmount, maxAmount, trustLevel));
        LOGGER.info("Offer {} saved", offer);
        return offer;
    }

    @Override
    public List<ExploreOfferDTO> getOffers(UserEntity userEntity, String from_currency, String to_currency, double minRate, double maxRate, double minAmount, double maxAmount, long distance, int trustLevel) {
        /* TODO: Chequear que se cumplan ambos trust level: el que puso el vendedor
            (el de la oferta) y el que pone el comprador (el parametro). */
        offerRepository.findOffersByCurrencyAndRateAndAmountAndDistance(
                from_currency, to_currency, minRate, maxRate, minAmount, maxAmount,
                userEntity.getLocation().getX(), userEntity.getLocation().getY(), distance);
        return null;
    }

    @Override
    public OfferDTO createOffer(UserEntity userEntity, OfferDTO offerDTO) {
        Offer offer = this.save(userEntity, offerDTO.getFromCurrency(), offerDTO.getToCurrency(),
                offerDTO.getFromToRate(), offerDTO.getMinAmount(), offerDTO.getMaxAmount(), offerDTO.getTrustLevel());
        return new OfferDTO(offer.getId(), offer.getFromCurrency(), offer.getToCurrency(), offer.getFromToRate(),
                offer.getMinAmount(), offer.getMaxAmount(), offer.getTrustLevel());
    }

    @Override
    public long deleteOffer(UserEntity userEntity, long offerId) {
        // TODO: chequear si la oferta le pertence
        offerRepository.deleteById(offerId);
        return offerId;
    }
}
