package com.detailsrequestmodels;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OfferDetailsRequestModel {

    private String name;

    private Float price;

    private List<CharacteristicDetailsRequestModel> characteristicDRM = new ArrayList<>();
}
