package com.dreamcar.auction.config;


import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@EnableWebMvc
@Configuration
@PropertySource("classpath:persistence-mysql.properties")
public class AuctionConfig implements WebMvcConfigurer {
	
	@Autowired
	private Environment env;
	private Logger logger = Logger.getLogger(getClass().getName());

    public AuctionConfig() {
        super();
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/anonymous.html");

        registry.addViewController("/login.html");
        registry.addViewController("/homepage.html");
        registry.addViewController("/admin/adminpage.html");
        registry.addViewController("/accessDenied");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/view/react/build/static/");

        registry.addResourceHandler("/*.js").addResourceLocations("/WEB-INF/view/react/build/");
        registry.addResourceHandler("/*.json").addResourceLocations("/WEB-INF/view/react/build/");
        registry.addResourceHandler("/*.ico").addResourceLocations("/WEB-INF/view/react/build/");
        registry.addResourceHandler("/index.html").addResourceLocations("/WEB-INF/view/react/build/index.html");
    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");

        return bean;
    }
    
    @Bean
    public DataSource securityDataSource() {
    	
    	// create connection pool
    	ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
    	
    	// set the jdbc driver class
    	try {
			securityDataSource.setDriverClass(env.getRequiredProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
    	
    	// log the connection properties
    	logger.info(">>> jdbc.url=" + env.getProperty("jdbc.url"));
    	logger.info(">>> jdbc.user=" + env.getProperty("jdbc.user"));
    	
    	// set database connection properties
    	securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
    	securityDataSource.setUser(env.getProperty("jdbc.user"));
    	securityDataSource.setPassword(env.getProperty("jdbc.password"));
    	
    	// set connection pool properties
    	securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
    	securityDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
    	securityDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
    	securityDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
    	
    	return securityDataSource;
    }
    
    // helper method for reading env property and converting to int
    
    private int getIntProperty(String propertyName) {
    	String propertyValue = env.getProperty(propertyName);
    	int intPropertyValue = Integer.parseInt(propertyValue);
    	
    	return intPropertyValue;
    }
}