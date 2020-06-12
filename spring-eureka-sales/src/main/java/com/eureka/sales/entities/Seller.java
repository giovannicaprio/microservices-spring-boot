package com.eureka.sales.entities;

import java.math.BigDecimal;

public class Seller {
    public String legalDocumentNumber;
    public String name;
    public BigDecimal salary;

    public Seller() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getLegalDocumentNumber() {
        return legalDocumentNumber;
    }

    public void setLegalDocumentNumber(String legalDocumentNumber) {
        this.legalDocumentNumber = legalDocumentNumber;
    }

   

    

}