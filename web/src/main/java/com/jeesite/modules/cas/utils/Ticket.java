package com.jeesite.modules.cas.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.subject.Subject;

import com.jeesite.common.cache.CacheUtils;
import com.jeesite.common.codec.Md5Utils;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils; 

public class Ticket {
	static Map<String,List<Map>> _CacheUtils = new HashMap();
	public Ticket(String t){
		this.ticketid = t;
		System.out.println("ticket:"+t);
	}

	Date previousLastTimeUsed;
	 
	Date lastTimeUsed ;
	
	int countOfUses;
	
	String ticketid;
	

	public static Ticket grantTicketGrantingTicket(ServletRequest request,Subject currentUser){
		List<Map> list = (List<Map>) CacheUtils.get("casTic");
		if(list == null){
			list = new ArrayList();

			CacheUtils.put("casTic", list);
		}
		Map mp = getTicket(UserUtils.getUser());
		
		Ticket ret = null;
		
		if(mp == null){
			ret = new Ticket("cas"+Md5Utils.md5(System.currentTimeMillis() + "" + Math.random()));
			ret.setCountOfUses(1); 
			ret.setPreviousLastTimeUsed(Calendar.getInstance().getTime()); 
			ret.setLastTimeUsed(Calendar.getInstance().getTime());
			mp = new HashMap();
			mp.put("subject", currentUser);
			mp.put("tic", ret);
			mp.put("USER",UserUtils.getUser());
			
			list.add(mp);
		}else{
			ret = (Ticket) mp.get("tic");
			ret.setPreviousLastTimeUsed(Calendar.getInstance().getTime()); 
			ret.setLastTimeUsed(Calendar.getInstance().getTime());
		}
	
		return ret;
	}
	
	public static Map getTicket(User user){
		
		List<Map> list =_CacheUtils.get("casTic");
		if(list == null) {
			list = new ArrayList();
			_CacheUtils.put("casTic", list);
		}
		
		for (Map map : list) {
			User usert = (User) map.get("user");
			if(usert.getLoginCode().equals(user.getLoginCode())){
				return map;
			}
		}
		return null;
	}
	
	public static Map getTicket(String ticket){
		List<Map> list =_CacheUtils.get("casTic");
		if(list == null) {
			list = new ArrayList();
			_CacheUtils.put("casTic", list);
		}
		for (Map map : list) {
			Ticket t = (Ticket) map.get("tic");
			if(t.getTicketid().equals(ticket)){
				return map;
			}
		}
		return null;
	}
	
	//EhCache
	public static void expire(User user){
		Map mp = getTicket(user);
		if(mp != null){
			List<Map> list = (List<Map>) CacheUtils.get("casTic");
			list.remove(mp);
			Subject s = (Subject) mp.get("subject");
			
			s.logout();
		}
		
	}
	@Override
	public String toString() {
		return this.ticketid;
	}
	
	public Date getPreviousLastTimeUsed() {
		return previousLastTimeUsed;
	}
	public void setPreviousLastTimeUsed(Date previousLastTimeUsed) {
		this.previousLastTimeUsed = previousLastTimeUsed;
	}
	public Date getLastTimeUsed() {
		return lastTimeUsed;
	}
	public void setLastTimeUsed(Date lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}
	public int getCountOfUses() {
		return countOfUses;
	}
	public void setCountOfUses(int countOfUses) {
		this.countOfUses = countOfUses;
	}
	public String getTicketid() {
		return ticketid;
	}
}
