package br.com.sicredi.associado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ScAssociadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScAssociadoApplication.class, args);
	}

}
