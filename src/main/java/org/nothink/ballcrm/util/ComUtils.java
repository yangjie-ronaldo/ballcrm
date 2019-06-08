package org.nothink.ballcrm.util;

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
}
