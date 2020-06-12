package com.eureka.sales.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.eureka.sales.entities.Client;
import com.eureka.sales.entities.Item;
import com.eureka.sales.entities.Sale;
import com.eureka.sales.entities.Seller;

public class FileHandler {

    public FileHandler fileHandler() {
        return new FileHandler();
    }

    public Boolean readFile(final String filePath) throws IOException {
        final StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.processData(contentBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // System.out.println(contentBuilder.toString());

        // return contentBuilder.toString();

        return true;
    }

    private void processData(StringBuilder content) throws Exception {
        String[] lines = content.toString().split("\n");
        Long qtdClients = 0L;
        Long qtdSellers = 0L;
        Long idBestSale = 0L;
        String worstSeller = "";
        BigDecimal biggerTotalAmmout = new BigDecimal("0.0");
        
        for (String line : lines) {
            String[] lineContent = line.split("ç");
            String id = lineContent[0];

            if(id.equals("001")){
                Seller seller = new Seller();
                seller.setLegalDocumentNumber(lineContent[1]);
                seller.setName(lineContent[2]);
                seller.setSalary(new BigDecimal(lineContent[3]));
                qtdSellers++;

            }else if(id.equals("002")){
                Client client = new Client();
                client.setLegalDocumentNumber(lineContent[1]);
                client.setName(lineContent[2]);
                client.setBusinessArea(lineContent[3]);
                qtdClients++;

            }else if(id.equals("003")){
                Sale sale = new Sale();
                sale.setSaleId(Long.parseLong(lineContent[1]));
                sale.setSellerName(lineContent[3]);
                Map<Item, Integer> items = new HashMap<Item, Integer>();

                String allItens = lineContent[2];
                allItens = allItens.replace("[", "").replace("]", "");
                Arrays.asList(allItens.split(",")).stream().forEach(saleItemStr -> {
                    if (saleItemStr.isEmpty()){
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
                if(saleTotalAmmount.compareTo(biggerTotalAmmout) == 1){
                    biggerTotalAmmout = saleTotalAmmount;
                    idBestSale = sale.getSaleId();
                } 

            }else{
                throw new Exception("Informação inválida no arquivo");
            }
            
        }

        System.out.println(":: GERANDO o arquivo de report...");
        //write to a new file 
        PrintWriter out = new PrintWriter(System.getProperty("user.dir") + "/src/main/resources/data/out/teste_dir.txt");
        BufferedWriter bwr = new BufferedWriter(out);
        //String tmp = contentBuilder.toString();
        //bwr.write(tmp.toCharArray());
        bwr.write("Quantidade de clientes: " + String.valueOf(qtdClients));
        bwr.write("Quantidade de vendedores: " + String.valueOf(qtdSellers));
        bwr.write("ID venda mais cara: " + idBestSale + " -> com o valor de: " +  biggerTotalAmmout);
        bwr.write("Pior vendedor: " + String.valueOf(qtdSellers));

        bwr.flush();
        bwr.close();
        out.close();

    }
    

}