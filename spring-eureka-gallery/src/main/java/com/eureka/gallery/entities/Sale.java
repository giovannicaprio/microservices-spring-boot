package com.eureka.gallery.entities;

package com.eureka.sales.entities;

import java.math.BigDecimal;
import java.util.Map;

public class Sale {
    public Long saleId;
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

    public String getSellerLegalDocumentNumber() {
        return sellerLegalDocumentNumber;
    }

    public void setSellerLegalDocumentNumber(String sellerLegalDocumentNumber) {
        this.sellerLegalDocumentNumber = sellerLegalDocumentNumber;
    }

    

}