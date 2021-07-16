package br.com.sicredi.pauta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ScPautaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScPautaApplication.class, args);
	}

}
