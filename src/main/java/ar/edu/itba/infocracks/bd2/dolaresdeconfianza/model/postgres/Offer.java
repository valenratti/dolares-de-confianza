package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "offer")
@Getter
@Setter
@ToString
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity user;

    @Column(name = "from_currency")
    private String fromCurrency;

    @Column(name = "to_currency")
    private String toCurrency;

    @Column(name = "from_to_rate")
    private double fromToRate;

    @Column(name = "min_amount")
    private double minAmount;

    @Column(name = "max_amount")
    private double maxAmount;

    @Column(name = "trust_level")
    private int trustLevel;

    public Offer() {
    }

    public Offer(UserEntity user, String fromCurrency, String toCurrency, double fromToRate, double minAmount, double maxAmount, int trustLevel) {
        this.user = user;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromToRate = fromToRate;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.trustLevel = trustLevel;
    }
}
