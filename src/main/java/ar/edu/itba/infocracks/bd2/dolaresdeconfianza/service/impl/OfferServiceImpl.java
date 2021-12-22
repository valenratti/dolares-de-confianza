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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OfferServiceImpl implements OfferService {

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
        log.info("Offer {} saved", offer);
        return offer;
    }

    @Override
    public List<ExploreOfferDTO> getOffers(UserEntity userEntity, String fromCurrency, String toCurrency, double maxRate, double minAmount, long distance, int trustLevel) {
        List<Offer> offerList = offerRepository.findOffersByCurrencyAndRateAndAmountAndDistance(
                fromCurrency, toCurrency, maxRate, minAmount,
                userEntity.getLocation().getX(), userEntity.getLocation().getY(), (double) distance);

        /* Chequeamos que se cumplan ambos trust level: el que puso el vendedor
            (el de la oferta) y el que pone el comprador (el parametro). */
        List<Offer> toBeRemoved = new ArrayList<>();
        for (Offer o: offerList) {
            int shortestPath = userNodeRepository.shortestPath(userEntity.getUsername(), o.getUser().getUsername());
            if( shortestPath > trustLevel || shortestPath > o.getTrustLevel() )
                toBeRemoved.add(o);
        }

        offerList.removeAll(toBeRemoved);
        return offerList
                .stream()
                .map((o) -> ExploreOfferDTO.of(o, userEntity))
                .collect(Collectors.toList());
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
        Optional<Offer> op = offerRepository.findById(offerId);
        Long userId;
        
        if(!op.isPresent())
            return -1;
        
        userId = op.get().getUser().getId();
        if(userId != userEntity.getId())
            throw new IllegalArgumentException("The offer you have tried to delete is not yours.");
        offerRepository.deleteById(offerId);
        
        return offerId;
    }
}
