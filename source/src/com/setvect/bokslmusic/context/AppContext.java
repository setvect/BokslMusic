package com.setvect.bokslmusic.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:/config/applicationContext.xml" })
@ComponentScan(basePackages = "com.setvect.bokslmusic")
public class AppContext {
}