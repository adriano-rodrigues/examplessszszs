package com.monitoratec.tokenservice.vtswalletservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VtsWalletServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VtsWalletServiceApplication.class, args);
	}

}
