package org.nothink.ballcrm.util;

import org.springframework.util.DigestUtils;

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
        if(string==null)
            return "";
        return DigestUtils.md5DigestAsHex(string.getBytes());
    }

    public static void main(String[] args){
        String pass=DigestUtils.md5DigestAsHex("123".getBytes());
        System.out.println(pass);
    }
}
