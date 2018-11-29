package com.flexion.codingchallenge.integration.sandbox;

import com.flexionmobile.codingchallenge.integration.Integration;
import com.flexionmobile.codingchallenge.integration.Purchase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main implementation of this project, accessing the sandbox REST API of Flexion.
 */
public class SandboxIntegration implements Integration {

    private static final Logger LOGGER = Logger.getLogger( SandboxIntegration.class.getName() );

    static final String ROOT_RESOURCE_URL = "http://sandbox.flexionmobile.com/javachallenge/rest";
    static final String BUY_ITEM_RESOURCE_URL_FORMAT = "/developer/%s/buy/%s";
    static final String GET_ALL_PURCHASES_RESOURCE_URL_FORMAT = "/developer/%s/all";
    static final String CONSUME_PURCHASE_RESOURCE_URL_FORMAT = "/developer/%s/consume/%s";

    private RestTemplate restTemplate = new RestTemplate();
    private String developerId;

    public SandboxIntegration(String developerId) {
        this.developerId = developerId;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    @Override
    public Purchase buy(String itemId) {
        String buyUrl = ROOT_RESOURCE_URL + String.format(BUY_ITEM_RESOURCE_URL_FORMAT, developerId, itemId);
        try {
            SandboxPurchase sandboxPurchase = restTemplate.postForObject(buyUrl, null, SandboxPurchase.class);
            return sandboxPurchase;
        } catch (RestClientException e) {
            LOGGER.log(Level.FINE, "REST exception while buying", e);
            handleRestException(e);
        }
        return null;
    }

    @Override
    public List<Purchase> getPurchases() {
        String getAllPurchasesUrl = ROOT_RESOURCE_URL + String.format(GET_ALL_PURCHASES_RESOURCE_URL_FORMAT, developerId);
        try {
            SandboxPurchases sandboxPurchases = restTemplate.getForObject(getAllPurchasesUrl, SandboxPurchases.class);
            if (sandboxPurchases == null) {
                // As per https://stackoverflow.com/a/47735749/3160869,
                // this may happen if the HTTP response is empty.
                throw new SandboxException("Invalid HTTP response from Sandbox REST service");
            }
            return sandboxPurchases.toListOfPurchases();
        } catch (RestClientException e) {
            LOGGER.log(Level.FINE, "REST exception while getting purchases", e);
            handleRestException(e);
        }
        return null;
    }

    @Override
    public void consume(Purchase purchase) {
        String purchaseId = purchase.getId();
        String consumeUrl = ROOT_RESOURCE_URL +
                String.format(CONSUME_PURCHASE_RESOURCE_URL_FORMAT, developerId, purchaseId);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(consumeUrl, null, String.class);
        } catch (RestClientException e) {
            LOGGER.log(Level.FINE, "REST exception while consuming", e);
            handleRestException(e);
        }
    }

    private void handleRestException(RestClientException e) {
        throw new SandboxException("Sandbox REST service error: " + e.getMessage());
    }
}
