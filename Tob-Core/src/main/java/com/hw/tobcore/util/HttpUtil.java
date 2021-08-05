package com.hw.tobcore.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

public class HttpUtil {

    private Client client = null;

    private Client open() {
        client = ClientBuilder.newClient();
        return client;
    }

    private void close (){
        if(client != null){
            client.close();
        }
    }

    public String post(String targetUrl,String pathUrl, String data)throws Exception {
        open();
        if(client == null){
            throw new Exception("client not open");
        }

        String result = client.target(targetUrl).path(pathUrl)
                .request()
                .post(Entity.entity(data, "application/json"), String.class);

        close();
        return result;
    }

    public String get(String targetUrl,String pathUrl)throws Exception {
        open();
        if(client == null){
            throw new Exception("client not open");
        }
        String result = client.target(targetUrl).path(pathUrl)
                .request()
                .get( String.class);

        close();
        return result;
    }

    public static void main(String [] args) throws Exception {

        HttpUtil httpUtil = new HttpUtil();


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("liveId", "000000024a8ada232843025b");
        jsonObject.put("loginId", "110");
        jsonObject.put("role", "admin");
        jsonObject.put("version", "1_0_0");

        String result = httpUtil.post("http://localhost:3000","/api/v1/ios/socket/accessToken", JSON.toJSONString(jsonObject));
        System.out.println(result);
    }

}
