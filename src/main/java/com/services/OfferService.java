package com.services;

import com.entities.Offer;
import com.repository.OfferRepo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class OfferService {

    @Autowired
    private OfferRepo offerRepo;

    public Iterable<Offer> findAllOffer() {
        return offerRepo.findAll();
    }

    public void saveOffer(Offer offer) {
        offerRepo.save(offer);
    }

    public void deleteOffer(Offer offer) {
        Offer offerTmp = offerRepo.findById(offer.getId()).orElse(null);
        if (offerTmp == null) log.error("No offer in the database");
        else offerRepo.delete(offer);
    }
    public Offer findOfferById(Integer id)
    {
        return offerRepo.findById(id).orElse(null);
    }
}
