package com.flexion.codingchallenge.integration.sandbox;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flexionmobile.codingchallenge.integration.Purchase;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SandboxPurchases {

    private List<SandboxPurchase> purchases;

    public SandboxPurchases() {
    }

    public SandboxPurchases(List<SandboxPurchase> purchases) {
        this.purchases = purchases;
    }

    public List<SandboxPurchase> getPurchases() {
        return purchases;
    }

    public List<Purchase> toListOfPurchases() {
        List<Purchase> purchaseList = new ArrayList<Purchase>(purchases.size());
        purchaseList.addAll(purchases);
        return purchaseList;
    }

    public void setPurchases(List<SandboxPurchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public String toString() {
        return "SandboxPurchases{" +
                "purchases=" + purchases +
                '}';
    }
}
