package com.setvect.bokslmusic.context;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.setvect.bokslmusic.web.SessionCheckInterceptor;
import com.setvect.common.http.MultiFileCommonsMultipartResolver;

@Configuration
@ImportResource({ "classpath:/config/applicationDwr.xml" })
@ComponentScan(basePackages = "com.setvect.bokslmusic.web", useDefaultFilters = false, includeFilters = @Filter(type = ANNOTATION, value = Controller.class), excludeFilters = @Filter(type = ANNOTATION, value = Service.class))
public class WebContext {
	@Autowired
	private SessionCheckInterceptor sessionCheckerInterceptor;

	@Bean
	public SessionCheckInterceptor loginCheckInterceptor() {
		return new SessionCheckInterceptor();
	}

	@Bean
	public DefaultAnnotationHandlerMapping defaultAnnotationHandlerMapping() {
		DefaultAnnotationHandlerMapping bean = new DefaultAnnotationHandlerMapping();
		bean.setInterceptors(new Object[] { sessionCheckerInterceptor });
		return bean;
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/");
		bean.setSuffix(".jsp");
		return bean;
	}

	@Bean
	public MultiFileCommonsMultipartResolver multipartResolver() {
		MultiFileCommonsMultipartResolver bean = new MultiFileCommonsMultipartResolver();
		bean.setMaxUploadSize(10000000);
		return bean;
	}

}