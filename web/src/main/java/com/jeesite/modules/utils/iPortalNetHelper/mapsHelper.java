package com.jeesite.modules.utils.iPortalNetHelper;

import com.jeesite.modules.utils.HttpUtils;

public class mapsHelper extends HttpUtils {
    private String _url;
	private String _token;
    public mapsHelper(String url,String token){
        this._url=url;
        this._token=token;
    }

}