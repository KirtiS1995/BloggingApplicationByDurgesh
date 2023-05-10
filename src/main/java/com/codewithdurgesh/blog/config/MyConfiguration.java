package com.codewithdurgesh.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;


@Configuration
public class MyConfiguration {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
