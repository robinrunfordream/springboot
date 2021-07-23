package com.example.demo.entity;

import lombok.Data;

@Data
public class UserLog {
	
    private Integer id;
    private String username;
    private String login_state;
    private String login_date;
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLogin_state() {
		return login_state;
	}
	public void setLogin_state(String login_state) {
		this.login_state = login_state;
	}
	public String getLogin_date() {
		return login_date;
	}
	public void setLogin_date(String login_date) {
		this.login_date = login_date;
	}
	
	@Override
	public String toString() {
		return "UserLog [id=" + id + ", username=" + username + ", login_state=" + login_state + ", login_date="
				+ login_date + "]";
	}

    
}
