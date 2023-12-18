package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;


@Controller
public class LoginController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Authentication auth;
	
	@Autowired 
	UserDetailsService userDetailsService;
	
    @Autowired
    private UserService userService;
    
	@GetMapping(value = {"/","/home"})
    public String home(){
        return "home";
    }
	
	@RequestMapping("/login")
	public String loginForm(HttpServletRequest request, Model model) {
		
		auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (!auth.getPrincipal().equals("anonymousUser")) {
			return "welcome_index";
		}
		
			return "login";
	}
	
	
  
	@RequestMapping("/welcome_index")
	public String authHome(Model model) {
		
		auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("username", auth.getName())
				.addAttribute("roles", "");			//role權限未做
		
		return "welcome_index";
	}
	
    /**
     * 讀取user表全部的人員
     *
     * @return
     */
//    @RequestMapping("/list")
//    @ResponseBody
//    public List<UserEntity> list() {
//        List<UserEntity> datagridList = userService.queryAllUsers();
//        System.out.println("datagridList="+datagridList);
//        return datagridList;
//    }
	
    
    /**
     *用户信息列表
     */
	 @GetMapping(value = "/list")
	 @ResponseBody
	 public Map getStuinforList(HttpServletRequest request){
	     
		 int page = Integer.parseInt(request.getParameter("page"));
	     int pageSzie = Integer.parseInt(request.getParameter("rows"));//pageSzie
	     int startRecord = (page-1) * pageSzie + 1;
	     
	     System.out.println("totalpage="+userService.totalpage());
	     System.out.println("pageini="+userService.pagePagination(startRecord,pageSzie));
	     
	     int total = userService.totalpage();
	     List pagination = userService.pagePagination(startRecord,pageSzie);
	     Map resultMap = new HashMap();
	     resultMap.put("total",total-1);
	     resultMap.put("rows",pagination);
	     
	     return resultMap;
	 }
	
	
    @RequestMapping("/addMemberPage")
    public String addMemberPage(Model model) throws Exception{
    	
    	System.out.println("addMemberPage");
    	
    	return "addMemberPage";
    }
    
	//新增
    @PostMapping("/addMemberPage")
    @ResponseBody
    public ModelAndView userAdd(
    		@RequestParam("username") String username,
    		@RequestParam("password") String password,
    		@RequestParam("password_double") String password_double,
    		@RequestParam("name") String name,
    		@RequestParam("email") String email
	) {
    	
    	ModelAndView mav = null;
    	try {
    		
    		UserEntity UserEntity = new UserEntity();
    		UserEntity.setName(username);
    		UserEntity.setUserPwd(password);
    		UserEntity.setUsername(name);
    		UserEntity.setUserEmail(email);
    		
    		logger.debug(username);
    		
    		//回傳訊息
    		String errorMsg = "";
    		//檢核
    		String checkUserName = "";
    		String checkName = "";
    		String checkEmail = "";
    		//password,password_double
    		String checkP_w_d = "";
    		
        	if( null != userService.findByUserName(username) ) {
        		checkUserName = userService.findByUserName(username).getUsername();
        	}
        	
        	if( null != userService.findByName(name) ) {
        		checkName = userService.findByName(name).getName();
        	}
        	
        	if( null != userService.findByUserEmail(email) ) {
        		checkEmail = userService.findByUserEmail(email).getUserEmail();
        	}
    		
    		if( !"".equals(checkUserName) ) {
    			if(!"".equals(errorMsg)) { errorMsg +="、"; }
    			errorMsg += "帳號";
    		}
    		
    		if( !"".equals(checkName) ) {
    			if(!"".equals(errorMsg)) { errorMsg +="、"; }
    			errorMsg += "姓名";
    		}
    		
    		if( !"".equals(checkEmail) ) {
    			if(!"".equals(errorMsg)) { errorMsg +="、"; }
    			errorMsg += "Email";
    		}
    		
    		System.out.println("errorMsg="+errorMsg);
    		
    		if(!password.equals(password_double)) {
    			checkP_w_d = "密碼欄位與再次輸入密碼欄位不一致";
    		}

    		if( "".equals(errorMsg) && "".equals(checkP_w_d) ) {
        		mav = new ModelAndView("/accessPage");
        		int i = userService.add(UserEntity);
        		
    		}else {
    			
    			String returnString = "";
    			
    			if( errorMsg.length() > 0 ) {
        			returnString = "系統已有重複的" + errorMsg + "，請確認後再新增";
    			}
    			
    			mav = new ModelAndView("/addMemberPage");
    			
    			System.out.println("returnString="+returnString);
    			
    			mav.addObject("errorMsg", returnString);
    			mav.addObject("checkPwd", checkP_w_d);
    		}
    		
    		return mav;

    	}catch(Exception e) {
    		mav = new ModelAndView("/errorPage");
    		System.out.println(e.getMessage());
    		return mav;
    	}
    	
    	
    }
}

