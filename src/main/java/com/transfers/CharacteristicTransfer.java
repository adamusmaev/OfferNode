package com.transfers;

import com.entities.Characteristic;
import lombok.Data;

@Data
public class CharacteristicTransfer {

    private String name;

    private String description;

    public CharacteristicTransfer(Characteristic characteristic) {
        this.name = characteristic.getName();
        this.description = characteristic.getDescription();
    }
}
