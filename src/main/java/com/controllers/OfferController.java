package com.controllers;

import com.detailsrequestmodels.CharacteristicDetailsRequestModel;
import com.detailsrequestmodels.OfferDetailsRequestModel;
import com.entities.Category;
import com.entities.Characteristic;
import com.entities.Offer;
import com.services.CategoryService;
import com.services.CharacteristicService;
import com.services.OfferService;
import com.transfers.OfferTransfer;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping(value = "/offer", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j
public class OfferController {

    private final OfferService offerService;

    private final CategoryService categoryService;

    private final CharacteristicService characteristicService;

    private final String httpGetCustomerField = "http://customer.jelastic.regruhosting.ru/customer/";

    private final String httpGetPaidType = "http://customer.jelastic.regruhosting.ru/paidtypes/";

    public OfferController(OfferService offerService, CategoryService categoryService, CharacteristicService characteristicService) {
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.characteristicService = characteristicService;
    }

    @PostMapping()
    public void addOffer(@RequestBody OfferDetailsRequestModel offerDRM) {
        Offer offer = new Offer();
        offer.setName(offerDRM.getName());
        offer.setPrice(offerDRM.getPrice());
        List<Characteristic> characteristicList = new ArrayList<>();
        offer.setCharacteristics(characteristicList);
        for (CharacteristicDetailsRequestModel c : offerDRM.getCharacteristicDRM()) {
            if (characteristicService.containInBase(c.getName())) {
                Characteristic characteristic = characteristicService.findCharacteristicByName(c.getName());
                offer.getCharacteristics().add(characteristic);
                characteristic.getOffers().add(offer);
                characteristicService.saveCharacteristic(characteristic);
            } else {
                Characteristic characteristic = new Characteristic();
                List<Offer> offerList = new ArrayList<>();
                characteristic.setOffers(offerList);
                characteristic.setName(c.getName());
                characteristic.setDescription(c.getDescription());
                offer.getCharacteristics().add(characteristic);
                characteristic.getOffers().add(offer);
                characteristicService.saveCharacteristic(characteristic);
            }
        }
        offerService.saveOffer(offer);
    }

    @PutMapping(value = "/{offerId}/category/{categoryId}")
    public void addCategoryForOffer(@PathVariable Integer offerId, @PathVariable Integer categoryId) {
        Offer offer = offerService.findOfferById(offerId);
        Category category = categoryService.findCategoryById(categoryId);
        if (offer == null) log.error("Offer is not in the database");
        if (category == null) log.error("Category is not in the database");
        offer.setCategory(category);
        offerService.saveOffer(offer);
    }

    @PutMapping(value = "/{offerId}/characteristic/{characteristicId}")
    public void addCharacteristicForOffer(@PathVariable Integer offerId, @PathVariable Integer characteristicId) {
        Offer offer = offerService.findOfferById(offerId);
        if (offer == null) log.error("Offer is not in the database");
        Characteristic characteristic = characteristicService.findCharacteristicById(characteristicId);
        if (characteristic == null) log.error("Characteristic is not in the database");
        offer.getCharacteristics().add(characteristic);
        characteristic.getOffers().add(offer);
        offerService.saveOffer(offer);
        characteristicService.saveCharacteristic(characteristic);
    }

    @GetMapping()
    @ResponseBody
    public List<OfferTransfer> findAllOffers() {
        List<OfferTransfer> offerTransferListRes = new ArrayList<>();
        for (Offer o : offerService.findAllOffer()) {
            offerTransferListRes.add(new OfferTransfer(o));
        }
        return offerTransferListRes;
    }

    @GetMapping(value = "/{offerId}")
    @ResponseBody
    public OfferTransfer findOfferById(@PathVariable Integer offerId) {
        Offer offer = offerService.findOfferById(offerId);
        if (offer == null) log.error("Offer is not in the database");
        return new OfferTransfer(offer);
    }

    @DeleteMapping(value = "/{offerId}")
    public void deleteOffer(@PathVariable Integer offerId) {
        Offer offer = offerService.findOfferById(offerId);
        offerService.deleteOffer(offer);
    }

    @PutMapping("/{offerId}")
    public void updateOffer(@PathVariable Integer offerId, @RequestBody OfferDetailsRequestModel offerDRM) {
        Offer offer = offerService.findOfferById(offerId);
        offer.setName(offerDRM.getName());
        offer.setPrice(offerDRM.getPrice());
        for (CharacteristicDetailsRequestModel c : offerDRM.getCharacteristicDRM()) {
            if (characteristicService.containInBase(c.getName())) {
                Characteristic characteristic = characteristicService.findCharacteristicByName(c.getName());
                offer.getCharacteristics().add(characteristic);
                characteristic.getOffers().add(offer);
                characteristicService.saveCharacteristic(characteristic);
            } else {
                Characteristic characteristic = new Characteristic();
                List<Offer> offerList = new ArrayList<>();
                characteristic.setOffers(offerList);
                characteristic.setName(c.getName());
                characteristic.setDescription(c.getDescription());
                offer.getCharacteristics().add(characteristic);
                characteristic.getOffers().add(offer);
                characteristicService.saveCharacteristic(characteristic);
            }
        }
        offerService.saveOffer(offer);
    }

    @PostMapping("/{offerId}/paidtype/{paidTypeId}")
    public void addPaidTypeForOffer(@PathVariable Integer offerId,
                                    @PathVariable Integer paidTypeId) throws URISyntaxException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(httpGetPaidType+paidTypeId);
        HttpResponse httpresponse = httpclient.execute(httpget);
        Scanner sc = new Scanner(httpresponse.getEntity().getContent());
        JSONObject jsonObject = new JSONObject(sc.nextLine());
        Offer offer = offerService.findOfferById(offerId);
        offer.setPaidType((Integer) jsonObject.get("id"));
        offerService.saveOffer(offer);
    }

    @GetMapping("/customer")
    public List<OfferTransfer> getCustomer(@RequestParam String token) throws IOException, URISyntaxException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpgetToken = new HttpGet(httpGetCustomerField + "id");
        URI uri = new URIBuilder(httpgetToken.getURI())
                        .addParameter("token", token)
                        .build();
        ((HttpRequestBase) httpgetToken).setURI(uri);
        HttpResponse httpresponse = httpclient.execute(httpgetToken);
        Scanner sc = new Scanner(httpresponse.getEntity().getContent());
        HttpGet httpGetCustomer = new HttpGet(httpGetCustomerField+sc.nextLine());
        httpresponse = httpclient.execute(httpGetCustomer);
        sc = new Scanner(httpresponse.getEntity().getContent());
        JSONObject jsonObjectCustomer = new JSONObject(sc.nextLine());
        JSONArray jsonArrayPaidTypes = new JSONArray(jsonObjectCustomer.get("paidTypeTransfers").toString());
        List<Integer> listPaidTypesId = new ArrayList<>();
        for (int i = 0; i < jsonArrayPaidTypes.length(); i++){
            JSONObject jsonObject = (JSONObject) jsonArrayPaidTypes.get(i);
            listPaidTypesId.add(jsonObject.getInt("id"));
        }
        List<OfferTransfer> offerTransferList = new ArrayList<>();
        for (int i = 0; i < listPaidTypesId.size(); i++){
            Iterable<Offer> offerCollection = offerService.findAllOfferByPaidTypes(listPaidTypesId.get(i));
            for (Offer o : offerCollection){
                offerTransferList.add(new OfferTransfer(o));
            }
        }
        return offerTransferList;
    }

}
