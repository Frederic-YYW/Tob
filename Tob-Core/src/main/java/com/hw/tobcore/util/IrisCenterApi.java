package com.hw.tobcore.util;

import com.alibaba.fastjson.JSON;
import com.iristar.center.exception.NetworkErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

/**
 * Created by zhouyu on 24/7/2019.
 */
public class IrisCenterApi {

    protected static Logger log = LoggerFactory.getLogger(IrisCenterApi.class);
    private static Client client = null;


    /**
     *
     * "bcjr_csrq" : "1988-10-12",
     "bcjr_gj" : "156",
     "bcjr_hjdz" : "北京市海淀区中关村东路95号中国科学院自动化所",
     "bcjr_jzdz" : "不需填写",
     "bcjr_mz" : "26",
     "bcjr_rylb" : "025",
     "bcjr_sjhm1" : "12345678901",
     "bcjr_sjhm2" : "12345678901",
     "bcjr_xb" : "2",
     "bcjr_xm" : "王新皓",
     "bcjr_zjhm" : "130321198810127315",
     "bcjr_zjlxdm" : "111",
     "bcjr_zjqfjg" : "北京市公安局海淀分局",
     "bcjr_zjyxqx" : "20150317",
     "cjbh" : "HM13000000000020190708123456",
     "cjbz" : "无特殊说明，该字段不填写",
     "cjcd" : "99",
     "cjr_gmsfhm" : "130101190001012873",
     "cjr_xm" : "王五",
     "cjtphs" : "2.032",
     "client_id" : "hongshi",
     "client_secret" : "hongshi",
     "dzmc" : "天津",
     "hmcjsbxhdm" : "03",
     */
    public static String Post(String url,Object object) throws NetworkErrorException{

        try{
            client = ClientBuilder.newClient();
//            client.property(ClientProperties.CONNECT_TIMEOUT, 3000);
            String targetHost = StringUtils.trim(ConfigHelper.getString("iris.center.host"));

            String result = client.target(targetHost).path(StringUtils.trim(url))
                    .request()
                    .post(Entity.entity(JSON.toJSONString(object), "application/json"), String.class);

            client.close();
            return result;
        }catch (Exception e) {
            log.error("Post iris center api error: " + e.getMessage());
            throw new NetworkErrorException(e);
        }
    }

    public static String Get(String url) throws NetworkErrorException {

        try{
            client = ClientBuilder.newClient();
            String targetHost = ConfigHelper.getString("iris.center.host");

            String result = client.target(targetHost).path(StringUtils.trim(url)).queryParam("id","0443a811-7fab-4678-9a4a-4643de401d1e")
                    .request()
                    .get( String.class);
            client.close();

            return result;
        }catch (Exception e) {
            log.error("Get iris center api error: " + e.getMessage());
            throw new NetworkErrorException(e);
        }
    }

    public static String regIris(Object params) throws NetworkErrorException{
        String regUrl = ConfigHelper.getString("iris.center.reg.url");
        return IrisCenterApi.Post(regUrl, params);
    }

    public static String inspectionIris(Object params) throws NetworkErrorException{
        String regUrl = ConfigHelper.getString("iris.center.insp.url");
        return IrisCenterApi.Post(regUrl, params);
    }

    public static String checkIris(Object params) throws NetworkErrorException{
        String regUrl = ConfigHelper.getString("iris.center.check.url");
        return IrisCenterApi.Post(regUrl, params);
    }

    public static String codeTree(Object params) throws NetworkErrorException{
        String regUrl = ConfigHelper.getString("iris.center.codeTree.url");
        return IrisCenterApi.Post(regUrl, params);
    }



}
