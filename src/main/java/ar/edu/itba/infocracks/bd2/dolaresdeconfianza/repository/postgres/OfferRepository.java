package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.postgres;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
    // TODO: Query
    @Query()
    List<Offer> findOffersByCurrencyAndRateAndAmountAndDistance(
            String from_currency, String to_currency, double minRate, double maxRate,
            double minAmount, double maxAmount, double locationX, double locationY, Double distance);
}
