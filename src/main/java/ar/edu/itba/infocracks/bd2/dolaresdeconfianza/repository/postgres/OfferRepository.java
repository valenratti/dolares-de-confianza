package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.postgres;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
    @Query(value =
            "SELECT * FROM offers o WHERE o.from_currency LIKE ?1 AND o.to_currency LIKE ?2 " +
                    "AND o.min_rate <= ?3  AND o.max_rate >= ?4 AND o.min_amount <= ?5 AND o.max_amount >= ?6 " +
                    "ST_DWithin(cast((SELECT DISTINCT u.location FROM users u WHERE u.id = o.user) as geography), " +
                    "ST_SetSRID(ST_Point(?7, ?8), 4326), ?9)", nativeQuery = true)
    List<Offer> findOffersByCurrencyAndRateAndAmountAndDistance(
            String fromCurrency, String toCurrency, double minRate, double maxRate,
            double minAmount, double maxAmount, double locationX, double locationY, Double distance);
    // minRate y maxAmount no tienen mucho sentido
}
