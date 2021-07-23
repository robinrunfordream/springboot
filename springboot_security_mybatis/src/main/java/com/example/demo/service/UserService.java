package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.UserEntity;

public interface UserService {
	
    List<UserEntity> queryAllUsers();
    
    Integer totalpage();
	
	List<UserEntity> pagePagination(Integer startRecord ,Integer pageSize);
    
    UserEntity findByUserName(String username);
    
    UserEntity findByName(String name);
    
    UserEntity findByUserEmail(String userEmail);
    
    Integer add(UserEntity userEntity);
    
}