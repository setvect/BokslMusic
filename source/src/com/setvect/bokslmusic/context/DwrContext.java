package com.setvect.bokslmusic.context;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

@Configuration
@ImportResource({ "classpath:/config/applicationDwr.xml" })
@ComponentScan(basePackages = "com.setvect.bokslmusic.web", useDefaultFilters = false, includeFilters = @Filter(type = ANNOTATION, value = Controller.class), excludeFilters = @Filter(type = ANNOTATION, value = Service.class))
public class DwrContext {
	@Bean
	public SimpleUrlHandlerMapping dwrUrlHandlerMapping() {
		SimpleUrlHandlerMapping bean = new SimpleUrlHandlerMapping();
		Properties mappings = new Properties();
		mappings.setProperty("/engine.js", "dwrController");
		mappings.setProperty("/util.js", "dwrController");
		mappings.setProperty("/interface/**", "dwrController");
		mappings.setProperty("/call/**", "dwrController");
		mappings.setProperty("/*", "dwrController");
		bean.setMappings(mappings);

		return bean;
	}
}