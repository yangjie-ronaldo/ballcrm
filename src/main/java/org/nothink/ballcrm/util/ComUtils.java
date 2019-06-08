package org.nothink.ballcrm.util;

import java.util.HashMap;
import java.util.Map;

public class ComUtils {

    public static Map<String, Object> getResp(String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "操作成功");
        map.put("code", 20000);
        map.put("data", null);
        if (status == null || status.equals("success")) {
            return map;
        } else {
            map.put("msg", "操作失败");
            map.put("code", 50000);
            return map;
        }
    }
}
