package com.refdata;

import org.springframework.boot.SpringApplication;

public class TestRefDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(RefDataServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
