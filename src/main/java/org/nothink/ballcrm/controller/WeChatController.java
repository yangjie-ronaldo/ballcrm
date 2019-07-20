package org.nothink.ballcrm.controller;

import net.sf.json.JSONObject;
import org.nothink.ballcrm.service.WeChatService;
import org.nothink.ballcrm.util.AesCbcUtil;
import org.nothink.ballcrm.util.HttpRequest;
import org.nothink.ballcrm.util.WeChatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WeChatController {


    @Autowired
    WeChatService weChatService;


    @ResponseBody
    @PostMapping("/decodeuser")
    public Map decodeUserInfo(String encryptedData, String iv, String code) {
        Map map = new HashMap();
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code不能为空");
            return map;
        }

        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = "wx91e39a17fe9f1a92";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "98f061988afbed7e81babcaa26900ded";
        //授权（必填）
        String grant_type = "authorization_code";


        // 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //System.out.println("拿回来的字符串："+sr);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //System.out.println("对象："+json);
        String errmsg=json.get("errmsg").toString();
        //出错直接返回
        if(errmsg!=null){
            map.put("status",40029);
            map.put("msg",errmsg);
            return map;
        }

        //成功则解析
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");

        //////////////// 2、对encryptedData加密数据进行AES解密其中包含这openid和unionid ////////////////
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            System.out.println("解密完："+result);
            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");

                JSONObject userInfoJSON = JSONObject.fromObject(result);
                Map userInfo = new HashMap();
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                map.put("userInfo", userInfo);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", 0);
        map.put("msg", "解密失败");
        return map;
    }

    /**
     * 微信公众号后台认证
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping("/wechat")
    @ResponseBody
    public String validate(@RequestParam(value = "signature") String signature,
                           @RequestParam(value = "timestamp") String timestamp,
                           @RequestParam(value = "nonce") String nonce,
                           @RequestParam(value = "echostr") String echostr) {
        //直接返true或false 验证成功
        return WeChatUtils.checkSignature(signature, timestamp, nonce) ? echostr : null;
    }

    /**
     * 此处是处理微信服务器的事件
     */
    @PostMapping("/wechat")
    @ResponseBody
    public String processMsg(HttpServletRequest request) {
        // 调用核心服务类接收处理请求
        return weChatService.processRequest(request);
    }

    @GetMapping("/wechat/sendmodelmsg")
    @ResponseBody
    public String sendModelMsg(){
        return weChatService.sendModelMsg(null);
    }

    @GetMapping("/wechat/createmenu")
    @ResponseBody
    public String createMenu(){
        return weChatService.createMenu();
    }

    @GetMapping("/wechat/getitemlist")
    @ResponseBody
    public String getItemList(){
        return weChatService.getItemList();
    }
}
