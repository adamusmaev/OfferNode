package com.transfers;

import com.entities.Characteristic;
import lombok.Data;

@Data
public class CharacteristicTransfer {

    private Integer id;
    private String name;
    private String description;

    public CharacteristicTransfer(Characteristic characteristic) {
        this.id = characteristic.getId();
        this.name = characteristic.getName();
        this.description = characteristic.getDescription();
    }
}
