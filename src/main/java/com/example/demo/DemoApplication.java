package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		MyProducerGateway myProducerGateway = context.getBean(MyProducerGateway.class);
		final User member = new User("test", "test", 1);
		myProducerGateway.sendEvent(member);
	}

}
