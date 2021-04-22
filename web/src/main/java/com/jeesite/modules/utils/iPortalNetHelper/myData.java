package com.jeesite.modules.utils.iPortalNetHelper;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class myData extends HttpUtils {
    private String _url;
    private String _token;
    public myData(String url, String token){
        this._url = url;
        this._token = token;
    }

    public String fileUpload(String childID,String filePath){
        String ret = "";
        Map<String, String> headers =new HashMap();
        headers.put("Content-Type", "application/json;charset=utf-8");
        Map<String, String> querys = new HashMap();
        querys.put("token", _token);
        try {
            byte[] file=toByteArray(filePath);
            HttpResponse httpresponse = this.doPost(_url, "/iportal/web/mycontent/datas/"+childID+"/upload.rjson", "method", headers, querys,file);
            ret = EntityUtils.toString(httpresponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(String filename) throws IOException {
 
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filename, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,
                    fc.size()).load();
            System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                // System.out.println("remain");
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}