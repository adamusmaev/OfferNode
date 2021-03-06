package com.services;

import com.entities.Characteristic;
import com.repository.CharacteristicRepo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class CharacteristicService {

    @Autowired
    private CharacteristicRepo characteristicRepo;

    public Iterable<Characteristic> findAllCharacteristic() {
        return characteristicRepo.findAll();
    }

    public void saveCharacteristic(Characteristic characteristic) {
        characteristicRepo.save(characteristic);
    }

    public void deleteCharacteristic(Characteristic characteristic) {
        Characteristic characteristicTmp = characteristicRepo.findById(characteristic.getId()).orElse(null);
        if (characteristicTmp == null) log.error("–°haracteristics not in the database");
        else characteristicRepo.delete(characteristic);
    }

    public Characteristic findCharacteristicById(Integer id) {
        return characteristicRepo.findById(id).orElse(null);
    }

    public Characteristic findCharacteristicByNameAndDescription(String name, String description) {
        return characteristicRepo.findCharacteristicByDescriptionAndAndName(name, description);
    }

    public Characteristic findCharacteristicByName(String name){
        return characteristicRepo.findCharacteristicByName(name);
    }

    public boolean containInBase(String name){
        if (characteristicRepo.findCharacteristicByName(name) == null) return false;
        else return true;
    }
}
