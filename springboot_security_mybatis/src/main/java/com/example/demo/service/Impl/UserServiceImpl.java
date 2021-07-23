package com.example.demo.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.mapper.UserMapper;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserEntity> queryAllUsers() {
        return userMapper.queryAllUsers();
    }
    
    @Override
    public Integer totalpage() {
        return userMapper.totalpage();
    }
    
    @Override
    public List<UserEntity> pagePagination(Integer startRecord ,Integer pageSize){
    	
        return userMapper.pagePagination(startRecord , pageSize);
        
    }
    
    @Override
    public UserEntity findByUserName(String username) {
        return userMapper.findByUserName(username);
    }
    
    @Override
    public UserEntity findByName(String name) {
        return userMapper.findByName(name);
    }
    
    @Override
    public UserEntity findByUserEmail(String userEmail) {
        return userMapper.findByUserEmail(userEmail);
    }
    
    // insert or update 回傳接口需修改為int或是void
    // add for userEntity 
    @Override
    public Integer add(UserEntity userEntity) {
        return userMapper.add(userEntity.getUsername() ,userEntity.getUserPwd(),userEntity.getName(),userEntity.getUserEmail());
    }
    
}
