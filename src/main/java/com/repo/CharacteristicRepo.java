package com.repo;

import com.entities.Characteristic;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface CharacteristicRepo extends CrudRepository<Characteristic, Integer>{
    Characteristic findCharacteristicByDescriptionAndAndName(String name, String description);
}
