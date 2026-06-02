package com.multigacha.torneo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
public class TorneoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TorneoApplication.class, args);
		System.out.println("aaa");
	}

}
