package com.gorankadir.se.security;

import javax.sql.DataSource;

import org.postgresql.Driver;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("hello");
		registry.addViewController("/hello").setViewName("hello");
		//registry.addViewController("/profile").setViewName("profile");
		registry.addViewController("/cars").setViewName("cars");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/admin").setViewName("admin");
		registry.addViewController("/membership").setViewName("registerformula");
		registry.addViewController("/403").setViewName("403");
		registry.addViewController("/admin/showcars").setViewName("showcars");
	}

	@Bean(name="authDataSource")
    public DataSource getAuthDatasource() {
    	PGSimpleDataSource result = new PGSimpleDataSource();
    	result.setUrl("jdbc:postgresql://localhost/LeasingCar");
    	result.setUser("edu");
    	result.setPassword("admin");
    	result.setLogLevel(Driver.DEBUG);
    	return result;
    }

}