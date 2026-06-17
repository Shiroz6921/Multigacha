package com.multigacha.compra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class CompraApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompraApplication.class, args);
		System.out.println("aaa");
	}

}
