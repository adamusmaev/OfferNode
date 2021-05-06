package com.repository;

import com.entities.Offer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OfferRepo extends CrudRepository<Offer, Integer> {

    Iterable<Offer> findOffersByPaidType(Integer paidTypesId);
}
