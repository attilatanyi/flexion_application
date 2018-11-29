package com.flexion.codingchallenge;

import com.flexion.codingchallenge.integration.sandbox.SandboxIntegration;
import com.flexion.codingchallenge.integration.sandbox.SandboxPurchases;
import com.flexion.codingchallenge.integration.sandbox.SandboxPurchase;
import com.flexionmobile.codingchallenge.integration.IntegrationTestRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Just a class to have something to run and play around with.
 * This is a solution of the Java challenge of Flexion.
 */
public class Main {

    public static final String DEVELOPER_ID = "Tanyi_Attila";

    public static void main(String[] args) {

        // Custom tests to play around with.
        runTestsForApiCalls();

        // Official tests.
        runTestsFromLibrary();
    }

    /**
     * Runs the official tests in the pluginapi.jar file.
     */
    private static void runTestsFromLibrary() {
        IntegrationTestRunner integrationTestRunner = new IntegrationTestRunner();
        SandboxIntegration sandboxIntegration = new SandboxIntegration(DEVELOPER_ID); // The main implementation of this project.
        try {
            integrationTestRunner.runTests(sandboxIntegration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs tests with some made up parameters.
     */
    private static void runTestsForApiCalls() {
        RestTemplate restTemplate = new RestTemplate();
        String rootResourceUrl = "http://sandbox.flexionmobile.com/javachallenge/rest";
        try {
            buyItem(restTemplate, rootResourceUrl, DEVELOPER_ID);
            String firstPurchaseId = getAllPurchases(restTemplate, rootResourceUrl, DEVELOPER_ID);
            if (firstPurchaseId != null) {
                consumePurchase(restTemplate, rootResourceUrl, DEVELOPER_ID, firstPurchaseId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void consumePurchase(RestTemplate restTemplate, String rootResourceUrl, String developerId,
                                        String purchaseId) {
        // Consume a purchase
        String consumePurchaseResourceUrl = "/developer/%s/consume/%s";
        String url = rootResourceUrl + String.format(consumePurchaseResourceUrl, developerId, purchaseId);
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        System.out.println();
        System.out.println(String.format("Consumed purchase %s from %s.", purchaseId, url));
        System.out.println();
    }

    private static String getAllPurchases(RestTemplate restTemplate, String rootResourceUrl, String developerId) {
        // Get all purchases
        String getAllPurchasesResourceUrl = "/developer/%s/all";
        String url = rootResourceUrl + String.format(getAllPurchasesResourceUrl, developerId);
        // ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        // System.out.println(String.format("Response from %s:\n%s", url, response));
        SandboxPurchases sandBoxPurchases = restTemplate.getForObject(url, SandboxPurchases.class);
        System.out.println();
        System.out.println("Current purchases: " + sandBoxPurchases);
        System.out.println();
        List<SandboxPurchase> purchases = sandBoxPurchases.getPurchases();
        if (purchases.size() > 0) {
            return purchases.get(0).getId();
        } else {
            return null;
        }
    }

    private static void buyItem(RestTemplate restTemplate, String rootResourceUrl, String developerId) {
        // Buy an item
        String itemId = "item1";
        String buyItemResourceUrl = "/developer/%s/buy/%s";
        String url = rootResourceUrl + String.format(buyItemResourceUrl, developerId, itemId);
        // ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        // System.out.println(String.format("Response from %s:\n%s", url, response));
        SandboxPurchase sandboxPurchase = restTemplate.postForObject(url, null, SandboxPurchase.class);
        System.out.println();
        System.out.println("Bought item: " + sandboxPurchase);
        System.out.println();
    }

}
