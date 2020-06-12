package com.eureka.sales.entities;

import java.math.BigDecimal;

public class Item {
    public Long itemId;
    public BigDecimal itemPrice;

    public Item(){
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    

}