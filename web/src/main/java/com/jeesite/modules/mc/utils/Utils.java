package com.jeesite.modules.mc.utils;

import java.util.ArrayList;
import java.util.List;

import com.jeesite.modules.sys.entity.Menu;

public class Utils {

    public static void makeTree(Menu parentMenu ,List<Menu> list) {
   
    	if(null == parentMenu.getChildList()) {
    		parentMenu.setChildList(new ArrayList());
    	}
    	for(int i=0;i<list.size();i++) {
    		if(parentMenu.getMenuCode().equals(list.get(i).getParentCode())) {
    			parentMenu.getChildList().add(list.get(i));
    			list.remove(i);
    			i--;
    		}
    	}
    	for (Menu menu : parentMenu.getChildList()) {
			makeTree(menu, list);
		} 
    }
    
    
}
