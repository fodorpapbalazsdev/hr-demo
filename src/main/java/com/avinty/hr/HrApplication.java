package com.avinty.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(EmbeddedTomcatConfiguration.class)
public class HrApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

}
