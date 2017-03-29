package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuaration {
	@Bean
	MyComponent myComponent(){
		return new MyComponent();
	}
}
