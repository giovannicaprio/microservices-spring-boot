package com.eureka.sales;

import java.io.IOException;

import com.eureka.sales.controller.WatchServiceSales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableEurekaClient 		// Enable eureka client.
@EnableCircuitBreaker 	// Enable circuit breakers	
public class SpringEurekaSalesApp {

	public static void main(String[] args){
		try {
			System.out.println("Starting WatchServiceSales to monitor folder");
			new WatchServiceSales().doProcess();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpringApplication.run(SpringEurekaSalesApp.class, args);
	}

}

@Configuration
class RestTemplateConfig {
	
	// Create a bean for restTemplate to call services
	@Bean
	@LoadBalanced		// Load balance between service instances running at different ports.
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
