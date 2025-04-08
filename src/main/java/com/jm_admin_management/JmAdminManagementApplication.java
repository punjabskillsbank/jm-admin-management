package com.jm_admin_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.jm_admin_management", "com.common.entity"})
public class JmAdminManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(JmAdminManagementApplication.class, args);
	}

}
