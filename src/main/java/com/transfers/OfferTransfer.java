package com.transfers;

import com.entities.Characteristic;
import com.entities.Offer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OfferTransfer {

    private Integer id;
    private String name;
    private Float price;
    private Integer paidType;
    private CategoryTransfer categoryTransfer;
    private List<CharacteristicTransfer> characteristicTransfers = new ArrayList<>();


    public OfferTransfer(Offer offer) {
        this.id = offer.getId();
        this.name = offer.getName();
        this.price = offer.getPrice();
        this.paidType= offer.getPaidType();
        if (!(offer.getCategory() == null)) this.categoryTransfer = new CategoryTransfer(offer.getCategory());
        if (!offer.getCharacteristics().isEmpty()) {
            for (Characteristic c : offer.getCharacteristics()) {
                characteristicTransfers.add(new CharacteristicTransfer(c));
            }
        }
    }
}
