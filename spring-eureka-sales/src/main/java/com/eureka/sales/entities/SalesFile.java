package com.eureka.sales.entities;

import java.util.List;
import java.util.Map;

public class SalesFile {
    
    private Map<String, Object> sellers;
    private Map<String, Object> clients;
    private List<Sale> sales;
    private Long qtdClients;
    private Long qtdSellers;
    private Long idBestSale;
    private String worstSellerName;


	public Map<String, Object> getSellers() {
        return sellers;
    }

    public void setSellers(Map<String, Object> sellers) {
        this.sellers = sellers;
    }

    public Map<String, Object> getClients() {
        return clients;
    }

    public void setClients(Map<String, Object> clients) {
        this.clients = clients;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    

    public Long getQtdClients() {
        return qtdClients;
    }

    public void setQtdClients(Long qtdClients) {
        this.qtdClients = qtdClients;
    }

    public Long getQtdSellers() {
        return qtdSellers;
    }

    public void setQtdSellers(Long qtdSellers) {
        this.qtdSellers = qtdSellers;
    }

    public Long getIdBestSale() {
        return idBestSale;
    }

    public void setIdBestSale(Long idBestSale) {
        this.idBestSale = idBestSale;
    }

    public String getWorstSellerName() {
        return worstSellerName;
    }

    public void setWorstSellerName(String worstSellerName) {
        this.worstSellerName = worstSellerName;
    }


}