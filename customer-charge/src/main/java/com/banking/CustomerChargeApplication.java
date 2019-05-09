package com.banking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CustomerChargeApplication implements CommandLineRunner { 
	
	@Autowired
    private Chargesdata chargesdata;
	
	@Autowired
	private Fxdata fxdata;
	
	@Autowired
	private Countrycurr countrycurr;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println(chargesdata.getNormalcharges());
		System.out.println(fxdata.getFxcharges());
		System.out.println(countrycurr.getMappinglist());
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerChargeApplication.class, args);
	}

}
