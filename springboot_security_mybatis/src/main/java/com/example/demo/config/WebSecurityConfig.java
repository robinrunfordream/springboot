package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.demo.authentication.AppsAuthenticationFailureHandler;
import com.example.demo.authentication.AppsAuthenticationSuccessHandler;
import com.example.demo.security.AppUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	@Autowired
	AppUserDetailsService appUserDetailsService;   
	
	@Autowired
	AppsAuthenticationFailureHandler appsAuthenticationFailureHandler;
	
	@Autowired
	AppsAuthenticationSuccessHandler appsAuthenticationSuccessHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(appUserDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
 
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity.csrf().disable();

    	httpSecurity
	    	.authorizeRequests()					// 認證機制設置              
	    	.antMatchers("/", "/home","/addMemberPage","/accessPage","/errorPage").permitAll()	// 首頁,註冊頁,新增成功導向頁面,錯誤導向頁 不需認證
	    	.anyRequest().authenticated()			// 除了以上的 URL 外, 都需要認證才可以訪問
	    	.and()
	    	.formLogin()
            .loginPage("/login")					//自定義登錄頁面的url
            .usernameParameter("username")			//設定登錄賬號引數，與表單引數一致
            .passwordParameter("password")			//設定登錄密碼引數，與表單引數一致
//	    	.defaultSuccessUrl("/welcome_index")	//驗證成功跳轉url對應controller
//	    	.failureUrl("/login?error=true")
//	    	.successHandler(null)
	    	.successHandler(appsAuthenticationSuccessHandler)
	    	.failureHandler(appsAuthenticationFailureHandler)
	    	.permitAll()
            .and()
            //注銷(登出)
            .logout()
            .logoutUrl("/logout")
            .permitAll();
    	// disable page caching
    	httpSecurity
    		.headers()
    		.frameOptions().sameOrigin()
    		.cacheControl();
    }
    
	@Override
	public void configure(WebSecurity web) throws Exception {
		//allow anonymous resource requests 解決靜態資源被攔截的問題
		web.ignoring().antMatchers("/css/**","/js/**","/easyui/**");
	}

	
}
