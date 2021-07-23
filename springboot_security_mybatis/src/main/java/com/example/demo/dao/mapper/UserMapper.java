package com.example.demo.dao.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserEntity;

@Repository
public interface UserMapper {

	List<UserEntity> queryAllUsers();
	
	Integer totalpage();
	
	List<UserEntity> pagePagination(Integer startRecord ,Integer pageSize);
    
	UserEntity findByUserName(String username);

	UserEntity findByName(String name);
	
	UserEntity findByUserEmail(String userEmail);
	
	Integer add(String username ,String userPwd,String name,String userEmail);
	
    
}
