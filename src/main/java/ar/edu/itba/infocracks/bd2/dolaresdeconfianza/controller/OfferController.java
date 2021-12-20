package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.controller;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.ExploreOfferDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.dto.OfferDTO;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security.SessionUtils;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.service.OfferService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;
    private final SessionUtils sessionUtils;

    @PostMapping("/create")
    @ApiOperation("Create a new offer")
    public OfferDTO createOffer(@RequestBody OfferDTO offer) {
        UserEntity userEntity = sessionUtils.getLoggedInUser();
        return offerService.createOffer(userEntity, offer);
    }

    @GetMapping("/explore")
    @ApiOperation("Explore offers")
    public List<ExploreOfferDTO> getOffers(@RequestParam(defaultValue = "USD") String from_currency,
                                           @RequestParam(defaultValue = "ARS") String to_currency,
                                           @RequestParam double minRate, @RequestParam double maxRate,
                                           @RequestParam(defaultValue = "0.0") double minAmount,
                                           @RequestParam double maxAmount, @RequestParam(defaultValue = "10") int trustLevel,
                                           @RequestParam long distance) {
        UserEntity userEntity = sessionUtils.getLoggedInUser();
        return offerService.getOffers(userEntity, from_currency, to_currency, minRate, maxRate, minAmount, maxAmount, distance, trustLevel);
    }

    @DeleteMapping("/delete")
    @ApiOperation("Delete an offer")
    public long deleteOffer(@PathVariable long offerId) {
        UserEntity userEntity = sessionUtils.getLoggedInUser();
        return offerService.deleteOffer(userEntity, offerId);
    }
}
