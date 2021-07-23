package com.example.demo.dao.mapper;

import org.springframework.stereotype.Repository;


@Repository
public interface UserLogMapper {

	
	Integer save(String username , String login_state, String login_date);
	
    
}
