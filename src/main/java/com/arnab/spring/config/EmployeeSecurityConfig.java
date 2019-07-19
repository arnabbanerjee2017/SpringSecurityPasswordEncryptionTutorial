/*
 * Copyright (c) 2019, ARNAB BANERJEE. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted only for academic purposes.
 * 
 * For further queries / info: arnab.ban09@gmail.com
 */

package com.arnab.spring.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.arnab.spring.service.EmployeeService;

@Configuration
@EnableWebSecurity	// This will enable Web Security or Spring Security
public class EmployeeSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private EmployeeService service;

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
	/**
	 * The below configure() method is used to configure the HTTP requests coming to this application.
	 * 1. First it applies that all requests must be authorized.
	 * 2. Then all requests to the root (/) must be authenticated.
	 * 3. Then it restricts the access to pages based on roles.
	 * 		a) Only EMPLOYEE, MANAGER, and ADMIN are permitted to access the path /office/employee
	 * 		b) Only ADMIN is permitted to access the path /office/admin
	 * 		c) Only MANAGER and ADMIN are permitted to access the path /office/manager
	 * 4. Then it applies that any request coming to this application must be authenticated.
	 * 5. Then it provides a way to login - a Login form and permits all requests via this login form.
	 * 6. Then it applies the logout logic and permits the session to logout.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception { 
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/office/save").anonymous()
			.antMatchers("/office/employee").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
			.antMatchers("/office/admin").hasAnyRole("ADMIN")
			.antMatchers("/office/manager").hasAnyRole("MANAGER", "ADMIN")
			.anyRequest().authenticated()
			.and()
			.formLogin().permitAll()
			.and()
			.logout().permitAll();
	}
	
	/**
	 * The below configure() method is used to configure a JDBC authentication provider.
	 * It just uses a DataSource for authentication. All the queries to check the credentials are done in back ground, out-of-the-box.
	 * We don't need to code anything more than this. We just need to configure the data source and which we have configured in
	 * EmployeeAppConfig.java. It will just use the data source and that's it.
	 * This is for the default users and authorities tables. For more information on this, please refer README.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Add users for in-memory authentication.
		auth.authenticationProvider(authenticationProvider());
		
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuth = new DaoAuthenticationProvider();
		daoAuth.setUserDetailsService(service);
		daoAuth.setPasswordEncoder(passwordEncoder());
		return daoAuth;
	}
}
