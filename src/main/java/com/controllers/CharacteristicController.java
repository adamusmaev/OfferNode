package com.controllers;

import com.detailsrequestmodels.CharacteristicDetailsRequestModel;
import com.entities.Characteristic;
import com.services.CharacteristicService;
import com.transfers.CharacteristicTransfer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/characteristic", produces = MediaType.APPLICATION_JSON_VALUE)
public class CharacteristicController {

    private final CharacteristicService characteristicService;

    public CharacteristicController(CharacteristicService characteristicService) {
        this.characteristicService = characteristicService;
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public List<CharacteristicTransfer> findAllCharacteristic() {
        List<CharacteristicTransfer> characteristicTransfersRes = new ArrayList<>();
        for (Characteristic c : characteristicService.findAllCharacteristic()) {
            CharacteristicTransfer characteristicTmp = new CharacteristicTransfer(c);
            characteristicTransfersRes.add(characteristicTmp);
        }
        return characteristicTransfersRes;
    }

    @PostMapping(value = "/addition")
    public void addCharacteristic(@RequestBody CharacteristicDetailsRequestModel characteristicDRM){
        Characteristic characteristic = new Characteristic();
        characteristic.setName(characteristicDRM.getName());
        characteristic.setDescription(characteristicDRM.getDescription());
        characteristicService.saveCharacteristic(characteristic);
    }
}
