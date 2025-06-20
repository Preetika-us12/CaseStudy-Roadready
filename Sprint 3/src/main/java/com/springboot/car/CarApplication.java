package com.springboot.car;

import org.springframework.boot.SpringApplication;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableTransactionManagement
public class CarApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarApplication.class, args);
	}
	@Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }


}