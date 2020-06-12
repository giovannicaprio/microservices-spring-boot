package com.eureka.sales.entities;

public class Client {

    public String legalDocumentNumber;
    public String name;
    public String businessArea;

    public Client() {
    }

    public String getLegalDocumentNumber() {
        return legalDocumentNumber;
    }

    public void setLegalDocumentNumber(String legalDocumentNumber) {
        this.legalDocumentNumber = legalDocumentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    


}