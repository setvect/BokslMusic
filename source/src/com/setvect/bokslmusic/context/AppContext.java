package com.setvect.bokslmusic.context;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Configuration
@ImportResource({ "classpath:/config/applicationContext.xml" })
@ComponentScan(basePackages = "com.setvect.bokslmusic", useDefaultFilters = false)
public class AppContext {
}