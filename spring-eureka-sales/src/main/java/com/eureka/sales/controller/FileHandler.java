package com.eureka.sales.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.eureka.sales.entities.Client;
import com.eureka.sales.entities.Item;
import com.eureka.sales.entities.Sale;
import com.eureka.sales.entities.SalesFile;
import com.eureka.sales.entities.Seller;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileHandler {

    public FileHandler fileHandler() {
        return new FileHandler();
    }

    public Boolean readFile(final String filePath, final String fileName) throws IOException {
        final StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }

        try {
            this.processData(contentBuilder, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private Boolean processData(StringBuilder content, String fileName) throws Exception {
        SalesFile fileData = new SalesFile();
        Map<String, Object> sellers = new HashMap<String, Object>();
        Map<String, Object> clients = new HashMap<String, Object>();
        List<Sale> sales = new ArrayList<Sale>();

        String[] lines = content.toString().split("\n");
        Long qtdClients = 0L;
        Long qtdSellers = 0L;
        Long idBestSale = 0L;
        Map<String, BigDecimal> salesAmmountBySeller = new HashMap<String, BigDecimal>();

        //Long idWorstSale = 0L;
        
        // String worstSeller = "";
        BigDecimal biggerTotalAmmout = new BigDecimal("0.0");
        //BigDecimal lowestTotalAmmout = new BigDecimal("0.0");

        for (String line : lines) {
            String[] lineContent = line.split("ç");
            String id = lineContent[0];

            if (id.equals("001")) {
                Seller seller = new Seller();
                seller.setLegalDocumentNumber(lineContent[1]);
                seller.setName(lineContent[2]);
                seller.setSalary(new BigDecimal(lineContent[3]));

                // create lists of sellers to put in the doc
                sellers.put(seller.getLegalDocumentNumber(), seller);
                qtdSellers++;

            } else if (id.equals("002")) {
                Client client = new Client();
                client.setLegalDocumentNumber(lineContent[1]);
                client.setName(lineContent[2]);
                client.setBusinessArea(lineContent[3]);

                clients.put(client.getLegalDocumentNumber(), client);
                qtdClients++;

            } else if (id.equals("003")) {
                Sale sale = new Sale();
                sale.setSaleId(Long.parseLong(lineContent[1]));
                String sellerName = lineContent[3];
                sale.setSellerName(sellerName);
                Map<Item, Integer> items = new HashMap<Item, Integer>();

                String allItens = lineContent[2];
                allItens = allItens.replace("[", "").replace("]", "");
                Arrays.asList(allItens.split(",")).stream().forEach(saleItemStr -> {
                    if (saleItemStr.isEmpty()) {
                        System.out.println("isEmpty");
                        return;
                    }
                    final String[] saleAttr = saleItemStr.split("-");
                    if (saleAttr.length < 3) {
                        System.out.println("< 3>");
                        return;
                    }

                    Item item = new Item();
                    item.setItemId(Long.parseLong(saleAttr[0]));
                    item.setItemPrice(new BigDecimal(saleAttr[2]));
                    Integer qtdItem = Integer.parseInt(saleAttr[1]);
                    items.put(item, qtdItem);

                });
                sale.setItems(items);
                BigDecimal saleTotalAmmount = sale.getSaletTotalAmmout();
               
                if (saleTotalAmmount.compareTo(biggerTotalAmmout) == 1) {
                    biggerTotalAmmout = saleTotalAmmount;
                    idBestSale = sale.getSaleId();
                }

                sales.add(sale);

                //popula dicionário com vendas por vendedores
                if(salesAmmountBySeller.containsKey(sellerName)){
                    salesAmmountBySeller.put(sellerName, salesAmmountBySeller.get(sellerName).add(saleTotalAmmount));
                }else{
                    salesAmmountBySeller.put(sellerName, saleTotalAmmount);
                }                

            } else {
                throw new Exception("Informação inválida no arquivo ou arquivo corrompido");
            }

        }

        //worlst seller
        Map<String, BigDecimal> sorted = salesAmmountBySeller
        .entrySet()
        .stream()
        .sorted(Map.Entry.<String, BigDecimal> comparingByValue())
        .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));
        
        // create final document translated
        fileData.setSellers(sellers);
        fileData.setClients(clients);
        fileData.setSales(sales);
        fileData.setQtdClients(qtdClients);
        fileData.setQtdSellers(qtdSellers);
        fileData.setIdBestSale(idBestSale);
        fileData.setWorstSellerName(sorted.entrySet().iterator().next().getKey());

        //System.out.println(sorted.entrySet().iterator().next().getKey());
        //System.out.println(sorted.entrySet().iterator().next().getValue());

        ObjectMapper mapper = new ObjectMapper();

        /**
         * Convert Map to JSON and write to a file
         */
        try {
            System.out.println(":: GERANDO ARQUIVO CONSOLIDADO ....");
            mapper.writeValue(new File(System.getProperty("user.dir") + "/src/main/resources/data/out/" + fileName.replace(".txt", "") + ".json"), fileData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(":: ARQUIVO GERADO COM SUCESSO");
        return true;
    }
    

}