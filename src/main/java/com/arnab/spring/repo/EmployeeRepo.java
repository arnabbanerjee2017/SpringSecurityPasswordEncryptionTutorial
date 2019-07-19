package com.arnab.spring.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.arnab.spring.entity.EmployeeUser;

@Repository
public class EmployeeRepo {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public String saveUser(String username, String password, String rolesCombined) {
		String[] roles = rolesCombined.split("-");
		String encodedPassword = passwordEncoder.encode(password);
		String userSql = "INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('" + username + "', '{bcrypt}" + encodedPassword + "', 1)";
		jdbcTemplate.update(userSql);
		for(String role: roles) {
			String roleSql = "INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('" + username + "', '" + role + "')";
			jdbcTemplate.update(roleSql);
		}
		return "SUCCESS";
	}
	
	public EmployeeUser getEmployeeUser(String username) {
		String userSql = "SELECT PASSWORD FROM USERS WHERE USERNAME = '" + username + "' AND ENABLED=1";		
		List<EmployeeUser> users = jdbcTemplate.query(userSql, new RowMapper<EmployeeUser>() {
			@Override
			public EmployeeUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				EmployeeUser user = new EmployeeUser();
				user.setUsername(username);
				user.setPassword(rs.getString("PASSWORD"));
				return user;
			}			
		});
		EmployeeUser user = users.get(0);
		String roleSql = "SELECT AUTHORITY FROM AUTHORITIES WHERE USERNAME = '" + username + "'";
		List<String> roles = jdbcTemplate.query(roleSql, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("AUTHORITY");
			}			
		});
		user.setRoles(roles);
		return user;
	}
	
}
