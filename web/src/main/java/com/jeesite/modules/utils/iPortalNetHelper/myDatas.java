package com.jeesite.modules.utils.iPortalNetHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class myDatas extends HttpUtils {
    private String _url;
    private String _token;
    public myDatas(String url, String token){
        this._url = url;
        this._token = token;
    }

    /**
     * 
     * @param type CSV|EXCEL 等,参考DataItemType
     * @param fileName
     * @param tags
     * @param description
     * @param authorizeSetting
     * @return
     */
    public String post(String type,String fileName,List<String> tags,String description,JSONObject authorizeSetting){
        String ret = "";
        String jsonStr = "{'fileName':'city0724.zip','authorizeSetting':[{'entityType':'USER','entityName':'GUEST','DataPermissionType':'DOWNLOAD'}],'type':'WORKSPACE'}";
        JSONObject raw = JSONObject.parseObject(jsonStr);
        raw.put("type", type);
        raw.put("fileName", fileName);
        if (tags != null) {
            raw.put("tags", tags.toString());
        }
        raw.put("description", description);
        if (tags != null) {
            raw.put("tags", tags.toString());
        }
        if (authorizeSetting != null) {
            raw.put("authorizeSetting", authorizeSetting);
        }
        Map<String, String> headers =new HashMap();
        headers.put("Content-Type", "application/json;charset=utf-8");
        Map<String, String> querys = new HashMap();
        querys.put("token", _token);
        try {
            HttpResponse httpresponse = this.doPost(_url, "/iportal/web/mycontent/datas.rjson", "method", headers, querys,raw.toJSONString());
            ret = EntityUtils.toString(httpresponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String post(String type,String fileName,List<String> tags){
        return this.post(type, fileName, tags,"",null);
    }
}