package com.bank.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StandardCharteredBankApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(StandardCharteredBankApplication.class, args);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
