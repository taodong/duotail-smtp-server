package com.duotail.smtp.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.duotail.smtp")
public class SmtpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmtpServerApplication.class, args);
	}

}
