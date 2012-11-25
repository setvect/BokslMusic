package com.setvect.bokslmusic.context;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.setvect.bokslmusic.vo.code.Code;
import com.setvect.bokslmusic.vo.music.Album;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
import com.setvect.bokslmusic.vo.music.PlayItem;
import com.setvect.bokslmusic.vo.music.PlayTime;

@Configuration
@ImportResource({ "classpath:/config/applicationContext.xml" })
@ComponentScan(basePackages = "com.setvect.bokslmusic", useDefaultFilters = false, includeFilters = { @Filter(type = ANNOTATION, value = Service.class) }, excludeFilters = @Filter(type = ANNOTATION, value = Controller.class))
@PropertySource("classpath:/config/config.properties")
public class AppContext {
	@Autowired
	private Environment env;

	@Autowired
	private AnnotationSessionFactoryBean sessionFactory;

	@Bean
	public PathMatcher antPathMater() {
		return new AntPathMatcher();
	}

	@Bean
	public AnnotationSessionFactoryBean sessionFactory() {
		AnnotationSessionFactoryBean sessionFactory = new AnnotationSessionFactoryBean();

		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.connection.driver_class",
				env.getProperty("com.setvect.bokslmusic.db.driver"));
		hibernateProperties.setProperty("hibernate.connection.url", env.getProperty("com.setvect.bokslmusic.db.url"));
		hibernateProperties.setProperty("hibernate.connection.username", env.getProperty("com.setvect.bokslmusic.db.user"));
		hibernateProperties.setProperty("hibernate.connection.password", env.getProperty("com.setvect.bokslmusic.db.passwd"));
		hibernateProperties.setProperty("hibernate.connection.poolsize", env.getProperty("com.setvect.bokslmusic.db.poolsize"));
		hibernateProperties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider");
		hibernateProperties.setProperty("hibernate.cache.use_query_cache", "true");
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		hibernateProperties.setProperty("current_session_context_class", "thread");
		hibernateProperties.setProperty("hibernate.show_sql", "false");
		hibernateProperties.setProperty("hibernate.format_sql", "true");
		hibernateProperties.setProperty("hibernate.connection.shutdown", "true");
		sessionFactory.setHibernateProperties(hibernateProperties);

		Class[] vo = new Class[] { MusicDirectory.class, MusicArticle.class, Album.class, PlayItem.class,
				PlayTime.class, Code.class };

		sessionFactory.setAnnotatedClasses(vo);

		return sessionFactory;
	}

	@Bean
	public HibernateTemplate hibernateTemplate() {
		HibernateTemplate bean = new HibernateTemplate();
		bean.setSessionFactory(sessionFactory.getObject());
		return bean;
	}

	@Bean
	public HibernateTransactionManager hibernateTxManager() {
		HibernateTransactionManager bean = new HibernateTransactionManager();
		bean.setSessionFactory(sessionFactory.getObject());
		return bean;
	}

}