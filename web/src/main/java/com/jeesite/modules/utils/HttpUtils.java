package com.jeesite.modules.utils;

 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtils {
	private  String cookie = null;
	
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}


	static class SSLClient extends DefaultHttpClient {
	    public SSLClient() throws Exception{
	        super();
	        SSLContext ctx = SSLContext.getInstance("TLS");
	        X509TrustManager tm = new X509TrustManager() {
	                @Override
	                public void checkClientTrusted(X509Certificate[] chain,
	                        String authType) throws CertificateException {
	                }
	                @Override
	                public void checkServerTrusted(X509Certificate[] chain,
	                        String authType) throws CertificateException {
	                }
	                @Override
	                public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	        };
	        ctx.init(null, new TrustManager[]{tm}, null);
	        SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	        ClientConnectionManager ccm = this.getConnectionManager();
	        SchemeRegistry sr = ccm.getSchemeRegistry();
	        sr.register(new Scheme("https", 443, ssf));
	    }
	}
	 private static String asUrlParams(Map<String, String> source){
	        Iterator<String> it = source.keySet().iterator();
	        StringBuilder paramStr = new StringBuilder();
	        while (it.hasNext()){
	            String key = it.next();
	            //if (StringUtils.isBlank(source.get(key))){
	            if(source.get(key) == null ) {
	                continue;
	            }
	            //}
	            paramStr.append("&").append(key).append("=").append(source.get(key));
	        }
	        // 去掉第一个&
	        return paramStr.substring(1);
	    } 
	 public  String sendGet(String url,Map par) throws Exception {
		 if(!url.endsWith("?"))url += "?";
		
		// url = URLEncoder.encode(url);
		
		 CloseableHttpClient httpClient =  new HttpUtils.SSLClient() ;
	        StringBuilder entityStringBuilder = null;
	        try {
	        	List<NameValuePair> postPara = new ArrayList<NameValuePair>();
	        
	        	 Iterator<String> it = par.keySet().iterator();
	        	while (it.hasNext()){
		            String key = it.next();
		            //if (StringUtils.isBlank(source.get(key))){
		            if(par.get(key) == null ) {
		                continue;
		            }
		            //}
		            postPara.add(new BasicNameValuePair(key,URLEncoder.encode(par.get(key)+"")));
		            //sendstr.append("&").append(key).append("=").append(source.get(key));
		        }
	        	String sendstr = EntityUtils.toString(new UrlEncodedFormEntity(postPara,HTTP.UTF_8));
	        	 url += sendstr;
	        	
	        	HttpGet get = new HttpGet(url);
	        	if(null!= getCookie())
	        		get.addHeader("Cookie",getCookie());
	        	 System.out.println(url);
	            
	            CloseableHttpResponse httpResponse = null;
	            httpResponse = httpClient.execute(get);
	            try {
	                HttpEntity entity = httpResponse.getEntity();
	                entityStringBuilder = new StringBuilder();
	                if (null != entity) {
	                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"), 8 * 1024);
	                    String line = null;
	                    while ((line = bufferedReader.readLine()) != null) {
	                    	if(entityStringBuilder.length() > 0)entityStringBuilder.append("\n");
	                        entityStringBuilder.append(line);
	                    }
	                }
	            } finally {
	                httpResponse.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (httpClient != null) {
	                    httpClient.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return entityStringBuilder.toString();
	    }
 
	
	//post请求方法
    public  String sendPost(String url,String CHARSET, Map<String,Object> params) {
       String response = null;
       System.out.println(url);
       System.out.println(params);
       try {
           List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (String key : params.keySet()) {
                    pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
                }
            }
           CloseableHttpClient httpclient = null;
           CloseableHttpResponse httpresponse = null;
           try {
               httpclient = new HttpUtils.SSLClient() ;//HttpClients.createDefault();
               HttpPost httppost = new HttpPost(url);
           	if(null!=getClass())
           		httppost.addHeader("Cookie",getCookie());
              // StringEntity stringentity = new StringEntity(data);
               if (pairs != null && pairs.size() > 0) {
                   httppost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
               }
               httpresponse = httpclient.execute(httppost);
               response = EntityUtils
                       .toString(httpresponse.getEntity());
               System.out.println(response);
           } finally {
               if (httpclient != null) {
                   httpclient.close();
               }
               if (httpresponse != null) {
                   httpresponse.close();
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return response;
    }
    
    public String sendDenete(String url,String dat,HttpEntityEnclosingRequestBase method) {
    	return sendPost(url,dat);
    }
    public  String sendPost(String url,String dat) {
    	String method = "POST"; 
    	return sendPost(url,dat,method);
    }
    public  String sendPost(String url,String dat,String method) {
        String response = null; 
        try {
          
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
            try {
                httpclient = new HttpUtils.SSLClient() ;
                HttpPost httppost = new HttpPost(url) {
                	@Override
                	public String getMethod() {
                		return method;
                	}
                };
           
            	if(null!=getClass())
            		httppost.addHeader("Cookie",getCookie()); 
                    httppost.setEntity(new StringEntity(dat,ContentType.APPLICATION_FORM_URLENCODED)); 
                httpresponse = httpclient.execute(httppost);
                response = EntityUtils
                        .toString(httpresponse.getEntity());
                System.out.println(response);
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
     }
}
