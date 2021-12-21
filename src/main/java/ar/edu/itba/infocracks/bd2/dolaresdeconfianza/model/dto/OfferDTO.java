package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferDTO {
    private Long id;
    private String fromCurrency;
    private String toCurrency;
    private double fromToRate; //price
    private double minAmount;
    private double maxAmount;
    private int trustLevel;

    public OfferDTO() {
    }

    public OfferDTO(String fromCurrency, String toCurrency, double fromToRate, double minAmount, double maxAmount, int trustLevel) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromToRate = fromToRate;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.trustLevel = trustLevel;
    }

    public OfferDTO(Long id, String fromCurrency, String toCurrency, double fromToRate, double minAmount, double maxAmount, int trustLevel) {
        this.id = id;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromToRate = fromToRate;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.trustLevel = trustLevel;
    }
}

