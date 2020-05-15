package com.rentcompany.rentcollect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
//https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-many-mapping-example/
public class RentCollectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentCollectApplication.class, args);
	}

}
