package com.controllers;

import com.detailsrequestmodels.CharacteristicDetailsRequestModel;
import com.entities.Characteristic;
import com.services.CharacteristicService;
import com.transfers.CharacteristicTransfer;
import lombok.extern.log4j.Log4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/characteristic", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j
public class CharacteristicController {

    private final CharacteristicService characteristicService;

    public CharacteristicController(CharacteristicService characteristicService) {
        this.characteristicService = characteristicService;
    }

    @GetMapping()
    @ResponseBody
    public List<CharacteristicTransfer> findAllCharacteristic() {
        List<CharacteristicTransfer> characteristicTransfersRes = new ArrayList<>();
        for (Characteristic c : characteristicService.findAllCharacteristic()) {
            CharacteristicTransfer characteristicTmp = new CharacteristicTransfer(c);
            characteristicTransfersRes.add(characteristicTmp);
        }
        return characteristicTransfersRes;
    }

    @GetMapping("/{characteristicId}")
    public CharacteristicTransfer findCharacteristic(@PathVariable Integer characteristicId) {
        Characteristic characteristic = characteristicService.findCharacteristicById(characteristicId);
        return new CharacteristicTransfer(characteristic);
    }

    @DeleteMapping("/{characteristicId}")
    public void deleteCharacteristic(@PathVariable Integer characteristicId) {
        Characteristic characteristic = characteristicService.findCharacteristicById(characteristicId);
        if (characteristic.getOffers().isEmpty()) characteristicService.deleteCharacteristic(characteristic);
        else log.error("This characteristic has offer");
    }

    @PutMapping("/{characteristicId}")
    public void updateCharacteristic(@PathVariable Integer characteristicId, @RequestBody CharacteristicDetailsRequestModel characteristicDRM) {
        Characteristic characteristic = characteristicService.findCharacteristicById(characteristicId);
        characteristic.setDescription(characteristicDRM.getDescription());
        characteristicService.saveCharacteristic(characteristic);
    }

    @PostMapping()
    public void addCharacteristic(@RequestBody CharacteristicDetailsRequestModel characteristicDRM) {
        Characteristic characteristic = new Characteristic();
        characteristic.setName(characteristicDRM.getName());
        characteristic.setDescription(characteristicDRM.getDescription());
        characteristicService.saveCharacteristic(characteristic);
    }
}
