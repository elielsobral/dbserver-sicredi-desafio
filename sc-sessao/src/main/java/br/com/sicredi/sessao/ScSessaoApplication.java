package br.com.sicredi.sessao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ScSessaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScSessaoApplication.class, args);
	}

}
