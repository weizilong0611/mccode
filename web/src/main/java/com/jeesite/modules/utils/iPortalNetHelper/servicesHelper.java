package com.jeesite.modules.utils.iPortalNetHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class servicesHelper extends HttpUtils {
    private String _url;
    private String _token;

    public servicesHelper(String url, String token) {
        this._url = url;
        this._token = token;
    }

    public String post(String SourceType, List<String> tags, JSONObject authorizeSetting, JSONObject metadata,
            JSONObject addedMapNames, JSONObject addedSceneNames) {
        String ret = "";
        String jsonStr = "{'type':'SUPERMAP_REST','tags':[],'authorizeSetting':[{'entityName':'GUEST','entityType':'USER','permissionType':'READ'}],'metadata':''}";
        JSONObject raw = JSONObject.parseObject(jsonStr);
        raw.put("type", SourceType);
        if (authorizeSetting != null) {
            raw.put("authorizeSetting", authorizeSetting);
        }
        if (tags != null) {
            raw.put("tags", tags.toString());
        }
        if (metadata != null) {
            raw.put("metadata", metadata);
        }
        Map<String, String> headers =new HashMap();
        headers.put("Content-Type", "application/json;charset=utf-8");
        Map<String, String> querys = new HashMap();
        querys.put("token", _token);
        try {
            HttpResponse httpresponse = this.doPost(_url, "/iportal/web/services.rjson", "method", headers, querys,raw.toJSONString());
            ret = EntityUtils.toString(httpresponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 
     * @param tags
     * @param linkage
     * @return
     */
    public String post_SUPERMAP_REST(List<String> tags, String linkage) {
        JSONObject metadata = JSONObject.parseObject(
                "{'mdContact':{'rpIndName':'','rpOrgName':'','rpPosName':'','rpCntInfo':{'cntAddress':{'delPoint':'','city':'','adminArea':'','postCode':'','country':'','eMailAdd':''},'voiceNum':'','faxNum':''}},'dataIdInfo':{'dataIdent':{'idCitation':{'resTitle':'map'},'idAbs':''}},'distInfo':{'onLineSrc':{'linkage':'http://139.219.5.93:8090/iserver/services/map-resi/rest'}}}");
        metadata.getJSONObject("distInfo").getJSONObject("onLineSrc").put("linkage", linkage);
        return post("SUPERMAP_REST", tags, null, metadata, null, null);
    }

    /**
     * 删除服务，支持批量删除服务
     * @param ids
     * @return
     */
    public String delete(List<String> ids) {
        String ret = "";
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json;charset=utf-8");
        Map<String, String> querys = new HashMap();
        querys.put("token", _token);
        querys.put("ids", ids.toString());
        try {
            HttpResponse httpresponse = this.doDelete(_url, "/iportal/web/services.rjson", "method", headers, querys);
            ret = EntityUtils.toString(httpresponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
