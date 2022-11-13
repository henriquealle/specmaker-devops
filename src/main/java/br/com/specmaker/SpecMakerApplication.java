package br.com.specmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SpecMakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpecMakerApplication.class, args);
	}

}
