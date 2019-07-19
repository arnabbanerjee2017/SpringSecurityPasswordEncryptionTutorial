/*
 * Copyright (c) 2019, ARNAB BANERJEE. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted only for academic purposes.
 * 
 * For further queries / info: arnab.ban09@gmail.com
 */

package com.arnab.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arnab.spring.service.EmployeeService;

@RestController
@RequestMapping(value = "/office")
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;

	@GetMapping(value = "/test")
	public String testMethod() {
		return "Test successful!"
				+ "<br /> <h4><a href=\"/logout\">Logout?</a></h4>";
	}
	
	@GetMapping(value = "/employee")
	public String getEmployee() {
		return "I am employee"
				+ "<br /> <h4><a href=\"/office/admin\">ADMIN</a></h4>"
				+ "<br /> <h4><a href=\"/office/manager\">MANAGER</a></h4>"
				+ "<br /> <h4><a href=\"/logout\">Logout?</a></h4>";
	}
	
	@GetMapping(value = "/admin")
	public String getAdmin() {
		return "I am an Admin"
				+ "<br /> <h4><a href=\"/office/employee\">EMPLOYEE</a></h4>"
				+ "<br /> <h4><a href=\"/logout\">Logout?</a></h4>";
	}
	
	@GetMapping(value = "/manager")
	public String getManager() {
		return "I am a Manager"
				+ "<br /> <h4><a href=\"/office/employee\">EMPLOYEE</a></h4>"
				+ "<br /> <h4><a href=\"/logout\">Logout?</a></h4>";
	}
	
	@GetMapping(value = "/save")
	public String saveUser(@RequestParam("username") String username,
							@RequestParam("password") String password,
							@RequestParam("roles") String roles) {
		return service.saveUser(username, password, roles);
	}
	
}
