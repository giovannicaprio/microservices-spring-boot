package com.eureka.gallery.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.eureka.gallery.entities.Report;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonController {

    public JsonController() {
    }

    public Set<String> fetchJsonFromFolder(String folderPath) throws Exception {
        Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(folderPath))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName().toString());
                }
            }
        } catch (Exception e) {
            throw new Exception("erro ao listar arquivos " + e);
        }
        return fileList;
    }

    public Report generateReport(String jsonFilePath) throws JsonParseException, JsonMappingException, IOException {
        Report report = new Report();
        ObjectMapper mapper = new ObjectMapper();

        //JSON file to Java object
        Report obj = mapper.readValue(new File(jsonFilePath), Report.class);
        report.setQtdClients(obj.getQtdClients());
        report.setQtdSellers(obj.getQtdSellers());
        report.setIdBestSale(obj.getIdBestSale());
        report.setWorstSellerName(obj.getWorstSellerName());

        return report;
    }



}