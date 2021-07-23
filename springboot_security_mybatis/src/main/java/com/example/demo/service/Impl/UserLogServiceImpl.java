package com.example.demo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.mapper.UserLogMapper;
import com.example.demo.entity.UserLog;
import com.example.demo.service.UserLogService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserLogServiceImpl implements UserLogService {

    @Autowired
    private UserLogMapper userLogMapper;
    
    // insert or update 回傳接口需修改為int或是void
    // add for userEntity 
	@Override
    public Integer save(UserLog userLog) {
        return userLogMapper.save(userLog.getUsername() , userLog.getLogin_state(), userLog.getLogin_date());
    }
    
}
