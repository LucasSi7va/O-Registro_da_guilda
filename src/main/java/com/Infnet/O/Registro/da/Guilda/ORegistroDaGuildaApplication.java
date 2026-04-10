package com.Infnet.O.Registro.da.Guilda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ORegistroDaGuildaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ORegistroDaGuildaApplication.class, args);
	}

}
