package com.jeesite.modules.cas.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.cache.CacheUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.shiro.authc.FormToken;
import com.jeesite.common.web.BaseController;  
import com.jeesite.modules.cas.utils.Ticket;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping("")
public class CASController extends BaseController{

	@RequiresUser
	@RequestMapping(value="index",method = { RequestMethod.GET })
	public String index(HttpServletResponse response,String service,String ticket,String pgtUrl,String renew,Model model){
		
		User user = UserUtils.getUser();
		
		model.addAttribute(user);
		
		return  "modules/index";
	}
	
	@RequiresUser
	@RequestMapping(value="ds",method = { RequestMethod.GET })
	public String index(){
	
		return  "modules/cas/ds";
	}
	
	 
	@RequestMapping(value={"login",""},method = { RequestMethod.GET })		//,"login"
	public String login(String service,Model model,HttpServletRequest request){
		
		//service
		
		if(null == service)
			return "redirect:" + adminPath + "/index";
		
		model.addAttribute("service", service);
		Subject currentUser = SecurityUtils.getSubject();  
		if (!currentUser.isAuthenticated()) {  
			return "modules/cas/login";
		
		}else{ 
			Ticket tickect = com.jeesite.modules.cas.utils.Ticket.grantTicketGrantingTicket(request,currentUser);
			return "redirect:" + service + "?ticket=" + tickect.getTicketid();
		}
	}
	
	@RequestMapping(value="serviceValidate",method = { RequestMethod.GET })
	public String serviceValidate(HttpServletResponse response,String service,String ticket,String pgtUrl,String renew,Model model){
		if(StringUtils.isEmpty(ticket)){
			model.addAttribute("msg", "必须同时提供&#39;service&#39;和&#39;ticket&#39;参数");
			model.addAttribute("code","INVALID_REQUEST");
			return  "modules/cas/serviceValidateFailure";
		}
		
		
		Map mp = Ticket.getTicket(ticket);
		
	//	Ticket tic= (Ticket) mp.get("tic");
		
		response.setContentType("application/xml;charset=utf-8"); 
		if(StringUtils.isEmpty(ticket)){
			model.addAttribute("msg", "必须同时提供&#39;service&#39;和&#39;ticket&#39;参数");
			model.addAttribute("code","INVALID_REQUEST");
			return  "modules/cas/serviceValidateFailure";
		}else{
			
			User user = (User) mp.get("user"); 
			model.addAttribute("loginCode",user.getLoginCode());
			model.addAttribute("sex",user.getSex());
			model.addAttribute("userName",user.getUserName());
			model.addAttribute("email",user.getEmail());
			model.addAttribute("status",user.getStatus());
			model.addAttribute("phone",user.getPhone());
			model.addAttribute("userType",user.getUserType());
			model.addAttribute("password",user.getPassword());
			return "modules/cas/serviceValidateSuccess";
		}
		
		
	}
	
	
	//logout
	
	@RequestMapping("logout")
	public String logout(String ticket,String service){
		try{
			Ticket.expire(UserUtils.getUser());
		}catch(Exception err){
			
		}
		return "redirect:" + adminPath + "/logout";
		//return "modules/cas/login";
	}
	
	@RequestMapping("logoutcas")
	public String logoutcas(String ticket,String service){
		Ticket.expire(UserUtils.getUser());
		//return "redirect:" ;

		return "redirect:login" ;
	}
 
	@RequestMapping("ssoLogin")
	public Map ssoLogin(String uid,String pwd,String service){
		Map map = new HashMap();
		Subject currentUser = SecurityUtils.getSubject();  
		  if (!currentUser.isAuthenticated()) {  
			  try { 
				  FormToken ft = new FormToken();
				  
				  ft.setUsername(uid);
				  
				  ft.setPassword(pwd);
	    	
				  currentUser.login(ft);  
				  
				  map.put("succ",true);
				  
				  map.put("service",service);
				  
			  } catch (Exception err){ 
				  if(err instanceof org.apache.shiro.authc.UnknownAccountException){
					  map.put("msg", "账户不存在");
				  }else if(err instanceof org.apache.shiro.authc.IncorrectCredentialsException){
					  map.put("msg", "密码错误");
			  	  }else{
					  map.put("msg", err.getMessage());
				  }
				  map.put("succ", false);
				    
				  
			  }
		  }
		  return map; 
	}
}
