package com.arnab.spring.entity;

import java.util.Collection;

public class EmployeeUser {

	private String username;
	private String password;
	private Collection<String> roles;

	public EmployeeUser() {
		super();
	}

	public EmployeeUser(String username, String password, Collection<String> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<String> getRoles() {
		return roles;
	}

	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}

}
