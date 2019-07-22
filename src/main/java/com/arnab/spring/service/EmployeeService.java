/*
 * Copyright (c) 2019, ARNAB BANERJEE. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted only for academic purposes.
 * 
 * For further queries / info: arnab.ban09@gmail.com
 */

package com.arnab.spring.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arnab.spring.entity.EmployeeUser;
import com.arnab.spring.repo.EmployeeRepo;

@Service
public class EmployeeService implements UserDetailsService {

	@Autowired
	private EmployeeRepo repo;
	
	@Transactional
	public String saveUser(String username, String password, String roles) {
		return repo.saveUser(username, password, roles);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EmployeeUser user = repo.getEmployeeUser(username);
		return new User(username, user.getPassword(), mapRoles(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRoles(Collection<String> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
	}
	
}
