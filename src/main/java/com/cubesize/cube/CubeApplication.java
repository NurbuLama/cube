package com.cubesize.cube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CubeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CubeApplication.class, args);
	}

}
