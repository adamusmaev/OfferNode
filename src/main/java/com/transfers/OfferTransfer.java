package com.transfers;

import com.entities.Category;
import com.entities.Characteristic;
import com.entities.Offer;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class OfferTransfer {

    private String name;

    private Float price;

    private CategoryTransfer categoryTransfer;

    private List<CharacteristicTransfer> characteristicTransfers = new ArrayList<>();

    public OfferTransfer(Offer offer) {
        this.name = offer.getName();
        this.price = offer.getPrice();
        this.categoryTransfer = new CategoryTransfer(offer.getCategory());
        if (!offer.getCharacteristics().isEmpty()) {
            for (Characteristic c : offer.getCharacteristics()) {
                characteristicTransfers.add(new CharacteristicTransfer(c));
            }
        }
    }
}
