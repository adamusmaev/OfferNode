package com.repository;

import com.entities.Characteristic;
import org.springframework.data.repository.CrudRepository;

public interface CharacteristicRepo extends CrudRepository<Characteristic, Integer>{
    Characteristic findCharacteristicByDescriptionAndAndName(String name, String description);
    Characteristic findCharacteristicByName(String name);
}
