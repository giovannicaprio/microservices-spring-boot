package com.eureka.sales.entities;

import java.math.BigDecimal;
import java.util.Map;

public class Sale {
    public Long saleId;
    public Map<Item, Integer> items;
    public String sellerName;
    public String sellerLegalDocumentNumber;
    

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }


    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }

    public BigDecimal getSaletTotalAmmout(){
        BigDecimal totalAmmout = new BigDecimal("0.0");
        for (Map.Entry<Item, Integer> it : items.entrySet()) {
            BigDecimal itemPrice = it.getKey().getItemPrice();
            Integer qtd = it.getValue();
            BigDecimal subTotal = itemPrice.multiply(new BigDecimal(qtd));
            totalAmmout = totalAmmout.add(subTotal);
        }

        return totalAmmout;      
    }

    public String getSellerLegalDocumentNumber() {
        return sellerLegalDocumentNumber;
    }

    public void setSellerLegalDocumentNumber(String sellerLegalDocumentNumber) {
        this.sellerLegalDocumentNumber = sellerLegalDocumentNumber;
    }

    

}