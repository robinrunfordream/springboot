package com.example.demo.authentication;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import com.example.demo.entity.UserLog;
import com.example.demo.service.UserLogService;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/18 16:53
 * @Description: 登录失败回调处理
 */
@Component("AppsAuthenticationFailureHandler")
public class AppsAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserLogService userLogService;
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        logger.info("登录失败");
        
        String message = "";
		if(exception.getClass() == UsernameNotFoundException.class) {
			
			message = "帳號錯誤";
			
			
		} else if(exception.getClass() == BadCredentialsException.class) {
			message = "密碼錯誤";

		}
        
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		Date current = new Date();
		String date = dateFormat.format(current);
		
        UserLog userLog = new UserLog();
		userLog.setUsername(request.getParameter("username"));
		userLog.setLogin_state("01");
		userLog.setLogin_date(date);
		userLogService.save(userLog);
		
		System.out.println("[onAuthenticationFailure]:message="+message);
		
        super.setDefaultFailureUrl("/login");
        super.onAuthenticationFailure(request, response, exception);

    }
}