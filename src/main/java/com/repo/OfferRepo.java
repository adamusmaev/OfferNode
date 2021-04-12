package com.repo;

import com.entities.Offer;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface OfferRepo extends CrudRepository<Offer, Integer> {
}
