package com.example.Integrator;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class IntegratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegratorApplication.class, args);
		String fileName = args.length > 0 ? args[0] : "properties.cfg";
		new Integrator().run(fileName);
	}

}
