package com.jeesite.modules.utils;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
 

public class DataUtils {

	   public static JSONObject  dom4j2Json(Element node){
		   JSONObject result = new JSONObject();
         // 当前节点的名称、文本内容和属性
         List<Attribute> listAttr = node.attributes();// 当前节点的所有属性的list
         for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
             result.put(attr.getName(), attr.getValue());
         }
         // 递归遍历当前节点所有的子节点
         List<Element> listElement = node.elements();// 所有一级子节点的list
         if (!listElement.isEmpty()) {
             for (Element e : listElement) {// 遍历所有一级子节点
                 if (e.attributes().isEmpty() && e.elements().isEmpty()) // 判断一级节点是否有属性和子节点
                     result.put(e.getName(), e.getTextTrim());// 沒有则将当前节点作为上级节点的属性对待
		   else {
                     if (!result.containsKey(e.getName())) // 判断父节点是否存在该一级节点名称的属性
                         result.put(e.getName(), new JSONArray());// 没有则创建
                     ((JSONArray) result.get(e.getName())).add(dom4j2Json(e));// 将该一级节点放入该节点名称的属性对应的值中
                 }
             }
         }
return result;
	    }
}
