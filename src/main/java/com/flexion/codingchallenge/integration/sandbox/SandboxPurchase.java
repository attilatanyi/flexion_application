package com.flexion.codingchallenge.integration.sandbox;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flexionmobile.codingchallenge.integration.Purchase;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SandboxPurchase implements Purchase {

    private boolean consumed;
    private String id;
    private String itemId;

    public SandboxPurchase() {
    }

    public SandboxPurchase(boolean consumed, String id, String itemId) {
        this.consumed = consumed;
        this.id = id;
        this.itemId = itemId;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public void setConsumed(boolean consumed) {
        this.consumed = consumed;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean getConsumed() {
        return consumed;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "SandboxPurchase{" +
                "consumed=" + consumed +
                ", id='" + id + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }

}
