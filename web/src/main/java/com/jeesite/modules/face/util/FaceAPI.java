package com.jeesite.modules.face.util;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.face.AipFace;

public class FaceAPI { 
	//设置APPID/AK/SK
    public static final String APP_ID = "22893207";
    public static final String API_KEY = "uDvk1o2RVue5H4LQFUf3YCwg";
    public static final String SECRET_KEY = "P2ghlBtzW5FIQ5WhguhI8UGgclqXfz6L";
    private static AipFace client = null;
    static String groupId = "group1";
    public static AipFace getClient() { 
    	if(null == client) {
    		client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);
    	}
    	return client; 
    }
    
    public static boolean save(String imgBase64,String loginName,String checkMyPassword) {
    	boolean ret = false; 
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info","{pwd:'"+checkMyPassword+"'}"); 
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("action_type", "REPLACE");
        
        String imageType = "BASE64"; 
         imgBase64 = imgBase64.substring(imgBase64.indexOf("base64,") +  7); 
        // 人脸注册
        org.json.JSONObject res = FaceAPI.getClient().addUser(imgBase64, imageType, groupId, loginName, options); 
    	return ret;
    }
    public static String getUserLoginCode(String imgBase64) {
        HashMap<String, String> options = new HashMap<String, String>();

        options.put("match_threshold", "70");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        
        // 调用接口
       
        String imageType = "BASE64";
        imgBase64 = imgBase64.substring(imgBase64.indexOf("base64,") +  7); 
        // 人脸检测
        org.json.JSONObject res = getClient().search(imgBase64, imageType,groupId, options);
        
        return res.getJSONObject("result").getJSONArray("user_list").getJSONObject(0).toString();
    }
    
    public static void main(String[] args) {
        // 初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);



        
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "age");
        options.put("max_face_num", "2");
        options.put("face_type", "LIVE");
        options.put("liveness_control", "LOW");

        // 调用接口
        String image = "取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
        String imageType = "BASE64";
        
        // 人脸检测
        org.json.JSONObject res = getClient().detect(image, imageType, options);
        System.out.println(res.toString(2));
        
    }
}
