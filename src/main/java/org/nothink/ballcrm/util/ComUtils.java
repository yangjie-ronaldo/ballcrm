package org.nothink.ballcrm.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ComUtils {

    public static Map<String, Object> getResp(int code,String msg,Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }

    public static String encodeByMd5(String string){
        try {
            // 确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            Base64.Encoder base64Encoder = Base64.getEncoder();
            // 加密字符串
            return base64Encoder.encodeToString(md5.digest(string.getBytes("utf-8")));
        }catch (Exception e){
            return null;
        }
    }

}
