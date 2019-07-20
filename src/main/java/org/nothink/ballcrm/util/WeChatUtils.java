package org.nothink.ballcrm.util;

import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.nothink.ballcrm.entity.ArticleItem;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class WeChatUtils {
    //APPID
    public static final String appID = "wxb544ddf463f9a37e";  //fk1的appid
    //appsecret
    public static final String appSecret = "278f086880bd77a0a0a0330c5bbb39c4";  //fk1的secret
    // Token
    public static final String TOKEN = "fk1app";

    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    public static final String REQ_MESSAGE_TYPE_LINK = "link";
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    public static final String EVENT_TYPE_SCAN = "SCAN";
    public static final String EVENT_TYPE_LOCATION = "LOCATION";
    public static final String EVENT_TYPE_CLICK = "CLICK";
    public static final String EVENT_TYPE_TEMPLATESENDJOBFINISH="TEMPLATESENDJOBFINISH";
    public static final String FromUserName = "FromUserName";
    public static final String ToUserName = "ToUserName";
    public static final String MsgType = "MsgType";
    public static final String Content = "Content";
    public static final String Event = "Event";

    public static String accessToken="";

    //下面是微信公众平台请求的接口
    public static final String TemplateSendUrl="https://api.weixin.qq.com/cgi-bin/message/template/send";
    public static final String CreateMenu="https://api.weixin.qq.com/cgi-bin/menu/create";
    public static final String ItemList="https://api.weixin.qq.com/cgi-bin/material/batchget_material";

    //发送模板消息给用户
    public static String sendTemplateMsg(String postBody){
        String urlParams="?access_token="+accessToken;
        String out=HttpRequest.sendPost(TemplateSendUrl+urlParams,postBody);
        return out;
    }

    //创建菜单
    public static String createMenu(String postBody){
        String urlParams="?access_token="+accessToken;
        String out=HttpRequest.sendPost(CreateMenu+urlParams,postBody);
        return out;
    }

    //获取永久素材列表
    public static String getItemList(String type,Integer offset,Integer count){
        String urlParams="?access_token="+accessToken;
        JSONObject body=new JSONObject();
        body.put("type",type);
        body.put("offset",offset);
        body.put("count",count);
        String out=HttpRequest.sendPost(ItemList+urlParams,body.toString());
        return out;
    }

    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{TOKEN, timestamp, nonce};
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
//        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 回复文本消息
     *
     * @param requestMap
     * @param content
     * @return
     */
    public static String sendTextMsg(Map<String, String> requestMap, String content) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ToUserName", requestMap.get(WeChatUtils.FromUserName));
        map.put("FromUserName", requestMap.get(WeChatUtils.ToUserName));
        map.put("MsgType", WeChatUtils.RESP_MESSAGE_TYPE_TEXT);
        map.put("CreateTime", new Date().getTime());
        map.put("Content", content);
        return mapToXML(map);
    }

    /**
     * 回复图文消息
     *
     * @param requestMap
     * @param items
     * @return
     */
    public static String sendArticleMsg(Map<String, String> requestMap, List<ArticleItem> items) {
        if (items == null || items.size() < 1) {
            return "";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("ToUserName", requestMap.get(WeChatUtils.FromUserName));
        map.put("FromUserName", requestMap.get(WeChatUtils.ToUserName));
        map.put("MsgType", "news");
        map.put("CreateTime", new Date().getTime());
        List<Map<String, Object>> Articles = new ArrayList<>();
        for (ArticleItem itembean : items) {
            Map<String, Object> item = new HashMap<>();
            Map<String, Object> itemContent = new HashMap<>();
            itemContent.put("Title", itembean.getTitle());
            itemContent.put("Description", itembean.getDescription());
            itemContent.put("PicUrl", itembean.getPicUrl());
            itemContent.put("Url", itembean.getUrl());
            item.put("item", itemContent);
            Articles.add(item);
        }
        map.put("Articles", Articles);
        map.put("ArticleCount", Articles.size());
        return mapToXML(map);
    }


    /**
     * 解析微信发来的请求(xml格式)  一层元素结构  仅微信服务器发过来的xml使用
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked"})
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());
        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

    /**
     * 将map对象（可多级）转成xml格式
     * @param map
     * @return
     */
    public static String mapToXML(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        mapToXML2(map, sb);
        sb.append("</xml>");
        try {
            return sb.toString();
        } catch (Exception e) {
        }
        return null;
    }

    private static void mapToXML2(Map map, StringBuffer sb) {
        Set set = map.keySet();
        for (Iterator it = set.iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            Object value = map.get(key);
            if (null == value)
                value = "";
            if (value.getClass().getName().equals("java.util.ArrayList")) {
                ArrayList list = (ArrayList) map.get(key);
                sb.append("<" + key + ">");
                for (int i = 0; i < list.size(); i++) {
                    HashMap hm = (HashMap) list.get(i);
                    mapToXML2(hm, sb);
                }
                sb.append("</" + key + ">");
            } else {
                if (value instanceof HashMap) {
                    sb.append("<" + key + ">");
                    mapToXML2((HashMap) value, sb);
                    sb.append("</" + key + ">");
                } else {
                    sb.append("<" + key + "><![CDATA[" + value + "]]></" + key + ">");
                }
            }
        }
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }
}
