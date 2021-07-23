package com.example.demo.security;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;

@Service
public class AppUserDetailsService implements UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static List<UserObject> users = new ArrayList(); 
    @Autowired
    private UserService userService;
    
	public AppUserDetailsService() {
//		users.add(new UserObject("admin", "123", "ADMIN"));
//		users.add(new UserObject("polinwei", "234", "USER"));
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				
        UserEntity appUser = userService.findByUserName(username);
        
        System.out.println("username="+username);
        System.out.println("appUser="+appUser);

        if (appUser == null) {
        	
            throw new UsernameNotFoundException(username + " not found");
            
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        UserDetails userDetails = User.builder()
                .username(appUser.getName())
//                .password("{noop}" + appUser.getUserPwd())
                .password(passwordEncoder.encode(appUser.getUserPwd()))		//未加密會返回error
//                .roles(appUser.getRole())
                .roles("user")
                .build();

        return userDetails;
        
	}
 
	private static class UserObject {
		
		private String name;
        private String password;
        private String role;
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        public UserObject(String name, String password, String role) {
            this.name = name;
            this.password = passwordEncoder.encode(password);
            this.role = role;
        }
        
    }
 
    private UserDetails toUserDetails(UserObject userObject) {      
  
        return User.withUsername(userObject.name)
                   .password(userObject.password)
                   .roles(userObject.role).build();
    }
    
}
