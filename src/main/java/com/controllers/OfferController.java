package com.controllers;

import com.detailsrequestmodels.CharacteristicDetailsRequestModel;
import com.detailsrequestmodels.OfferDetailsRequestModel;
import com.entities.Category;
import com.entities.Characteristic;
import com.entities.Offer;
import com.services.CategoryService;
import com.services.CharacteristicService;
import com.services.OfferService;
import com.transfers.OfferTransfer;
import lombok.extern.log4j.Log4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/offer", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j
public class OfferController {

    private final OfferService offerService;

    private final CategoryService categoryService;

    private final CharacteristicService characteristicService;

    public OfferController(OfferService offerService, CategoryService categoryService, CharacteristicService characteristicService) {
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.characteristicService = characteristicService;
    }

    @PostMapping()
    public void addOffer(@RequestBody OfferDetailsRequestModel offerDRM) {
        Offer offer = new Offer();
        offer.setName(offerDRM.getName());
        offer.setPrice(offerDRM.getPrice());
        List<Characteristic> characteristicList = new ArrayList<>();
        offer.setCharacteristics(characteristicList);
        for (CharacteristicDetailsRequestModel c : offerDRM.getCharacteristicDRM()) {
            if (characteristicService.containInBase(c.getName())) {
                Characteristic characteristic = characteristicService.findCharacteristicByName(c.getName());
                offer.getCharacteristics().add(characteristic);
                characteristic.getOffers().add(offer);
                characteristicService.saveCharacteristic(characteristic);
            } else {
                Characteristic characteristic = new Characteristic();
                List<Offer> offerList = new ArrayList<>();
                characteristic.setOffers(offerList);
                characteristic.setName(c.getName());
                characteristic.setDescription(c.getDescription());
                offer.getCharacteristics().add(characteristic);
                characteristic.getOffers().add(offer);
                characteristicService.saveCharacteristic(characteristic);
            }
        }
        offerService.saveOffer(offer);
    }

    @PutMapping(value = "/{offerId}/category/{categoryId}")
    public void addCategoryForOffer(@PathVariable Integer offerId, @PathVariable Integer categoryId) {
        Offer offer = offerService.findOfferById(offerId);
        Category category = categoryService.findCategoryById(categoryId);
        if (offer == null) log.error("Offer is not in the database");
        if (category == null) log.error("Category is not in the database");
        offer.setCategory(category);
        offerService.saveOffer(offer);
    }

    @PutMapping(value = "/{offerId}/characteristic/{characteristicId}")
    public void addCharacteristicForOffer(@PathVariable Integer offerId, @PathVariable Integer characteristicId) {
        Offer offer = offerService.findOfferById(offerId);
        if (offer == null) log.error("Offer is not in the database");
        Characteristic characteristic = characteristicService.findCharacteristicById(characteristicId);
        if (characteristic == null) log.error("Characteristic is not in the database");
        offer.getCharacteristics().add(characteristic);
        characteristic.getOffers().add(offer);
        offerService.saveOffer(offer);
        characteristicService.saveCharacteristic(characteristic);
    }

    @GetMapping()
    @ResponseBody
    public List<OfferTransfer> findAllOffers() {
        List<OfferTransfer> offerTransferListRes = new ArrayList<>();
        for (Offer o : offerService.findAllOffer()) {
            offerTransferListRes.add(new OfferTransfer(o));
        }
        return offerTransferListRes;
    }

    @GetMapping(value = "/{offerId}")
    @ResponseBody
    public OfferTransfer findOfferById(@PathVariable Integer offerId) {
        Offer offer = offerService.findOfferById(offerId);
        if (offer == null) log.error("Offer is not in the database");
        return new OfferTransfer(offer);
    }

    @DeleteMapping(value = "/{offerId}")
    public void deleteOffer(@PathVariable Integer offerId) {
        Offer offer = offerService.findOfferById(offerId);
        offerService.deleteOffer(offer);
    }

    @PutMapping("/{offerId}")
    public void updateOffer(@PathVariable Integer offerId, @RequestBody OfferDetailsRequestModel offerDRM) {
        Offer offer = offerService.findOfferById(offerId);
        offer.setName(offerDRM.getName());
        offer.setPrice(offerDRM.getPrice());
        for (CharacteristicDetailsRequestModel c : offerDRM.getCharacteristicDRM()) {
            if (characteristicService.containInBase(c.getName())) {
                Characteristic characteristic = characteristicService.findCharacteristicByName(c.getName());
                offer.getCharacteristics().add(characteristic);
                characteristic.getOffers().add(offer);
                characteristicService.saveCharacteristic(characteristic);
            } else {
                Characteristic characteristic = new Characteristic();
                List<Offer> offerList = new ArrayList<>();
                characteristic.setOffers(offerList);
                characteristic.setName(c.getName());
                characteristic.setDescription(c.getDescription());
                offer.getCharacteristics().add(characteristic);
                characteristic.getOffers().add(offer);
                characteristicService.saveCharacteristic(characteristic);
            }
        }
        offerService.saveOffer(offer);
    }

}
