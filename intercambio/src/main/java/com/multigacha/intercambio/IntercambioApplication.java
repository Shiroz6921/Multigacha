package com.multigacha.intercambio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class IntercambioApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntercambioApplication.class, args);
		System.out.println("sss");
	}

}
