package com.eureka.gallery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



import com.eureka.gallery.entities.Report;

@RestController
@RequestMapping("/")
public class HomeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private Environment env;

	//private static final String reportsDir = System.getProperty("user.dir") + "/../../src/main/resources/data/out/";
	private static final String reportsDir = Paths.get(System.getProperty("user.dir") + "/spring-eureka-sales/src/main/resources/data/out/").toString();
	@RequestMapping("/reports")
	@ResponseBody
	public List<Report> getReports() throws Exception {
		//SalesDataPath salesDataPath = new SalesDataPath();

		// fetch all jsons from folder
		JsonController jsonController = new JsonController();
		List<Report> reports = new ArrayList<Report>();
		Report rep = new Report();
		rep.setIdBestSale(10L);
		reports.add(rep);
		
		try {
			Set<String> jsonsInFolder = jsonController.fetchJsonFromFolder(reportsDir);
			for(String jsonFile : jsonsInFolder){
				if(!jsonFile.equals(".DS_Store")){
					Report report = jsonController.generateReport(reportsDir + "/" + jsonFile);
					reports.add(report);
				}
			}

		} catch (IOException e) {
			throw new Exception("Erro ao gerar reports" +  e);
		}
		
		
		return reports;
	}
	@RequestMapping("/home")
	public String home() throws Exception {
		
		return "Hello from Gallery Service running at port: " + env.getProperty("local.server.port");
	}
  
	// a fallback method to be called if failure happened
	public String fallback(Throwable hystrixCommand) {
		return "erro inesperado";
	}
}