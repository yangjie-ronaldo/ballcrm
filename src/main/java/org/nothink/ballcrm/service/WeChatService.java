package org.nothink.ballcrm.service;

import net.sf.json.JSONObject;
import org.nothink.ballcrm.entity.ArticleItem;
import org.nothink.ballcrm.util.WeChatUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class WeChatService {

    //处理微信服务器发过来的公众号事件
    public String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent;
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = WeChatUtils.parseXml(request);
            System.out.println("从微信服务器收到事件：\n" + requestMap);
            // 相关用户的openID
            String fromUserOpenId = requestMap.get(WeChatUtils.FromUserName);
            // 消息类型
            String msgType = requestMap.get(WeChatUtils.MsgType);
            String mes = null;
            // 文本消息
            if (msgType.equals(WeChatUtils.REQ_MESSAGE_TYPE_TEXT)) {
                mes = requestMap.get(WeChatUtils.Content);
                if (mes != null && ("2".equals(mes) || "图文消息".equals(mes))) {
                    //回复图文消息 此场景只能回一条
                    List<ArticleItem> items = new ArrayList<>();
                    ArticleItem item = new ArticleItem();
                    item.setTitle("百度搜一搜");
                    item.setDescription("这是一条图文消息，点开我可以进行百度搜索。试一试吧！");
                    item.setPicUrl("https://www.baidu.com/img/bd_logo1.png");
                    item.setUrl("http://www.baidu.com");
                    items.add(item);
                    respXml = WeChatUtils.sendArticleMsg(requestMap, items);
                } else if (mes != null && ("1".equals(mes) || "我的信息".equals(mes))) {
                    respXml = WeChatUtils.sendTextMsg(requestMap, "开发哥哥云龙.zhou还没开发到这来，请再等等哟！");
                } else if (mes != null && ("3".equals(mes) || "签到通知测试".equals(mes))) {
                    this.sendModelMsg(fromUserOpenId);
                    return "";
                } else if (mes != null && ("?".equals(mes) || "？".equals(mes))) {
                    respContent = "谢谢您的关注！请回复：\n" +
                            "1.我的信息\n" +
                            "2.图文消息\n" +
                            "3.签到通知测试\n" +
                            "?.本菜单";
                    respXml = WeChatUtils.sendTextMsg(requestMap, respContent);
                }
            }
            // 图片消息
            else if (msgType.equals(WeChatUtils.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
                respXml = WeChatUtils.sendTextMsg(requestMap, respContent);
            }
            // 语音消息
            else if (msgType.equals(WeChatUtils.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是语音消息！";
                respXml = WeChatUtils.sendTextMsg(requestMap, respContent);
            }
            // 视频消息
            else if (msgType.equals(WeChatUtils.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
                respXml = WeChatUtils.sendTextMsg(requestMap, respContent);
            }
            // 地理位置消息
            else if (msgType.equals(WeChatUtils.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
                respXml = WeChatUtils.sendTextMsg(requestMap, respContent);
            }
            // 链接消息
            else if (msgType.equals(WeChatUtils.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
                respXml = WeChatUtils.sendTextMsg(requestMap, respContent);
            }
            // 事件推送
            else if (msgType.equals(WeChatUtils.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get(WeChatUtils.Event);
                // 关注
                if (eventType.equals(WeChatUtils.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！回复：\n" +
                            "1.我的信息\n" +
                            "2.图文消息\n" +
                            "3.签到通知测试\n" +
                            "?.本菜单";
                    respXml = WeChatUtils.sendTextMsg(requestMap, respContent);
                }
                // 取消关注
                else if (eventType.equals(WeChatUtils.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                    respContent = "用户取消关注了";
                    return "";
                }
                // 扫描带参数二维码
                else if (eventType.equals(WeChatUtils.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                }
                // 上报地理位置
                else if (eventType.equals(WeChatUtils.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(WeChatUtils.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                    String eventKey=requestMap.get("EventKey");

                    if (eventKey!=null && "MENU_ABOUT".equals(eventKey)){
                        //简单的关于我们
                        respContent = "篮球+舞蹈=花式篮球\n" +
                                "一门课程，两份收获\n" +
                                "从喜欢，到持续喜欢，再到习惯的养成\n"+
                                "快快加入我们吧！";
                        respXml = WeChatUtils.sendTextMsg(requestMap, respContent);
                    } else if (eventKey!=null && "MENU_TUWEN".equals(eventKey)){
                        //图文消息
                        List<ArticleItem> items = new ArrayList<>();
                        ArticleItem item = new ArticleItem();
                        item.setTitle("★不败神话fk·1★，先睹为快");
                        item.setPicUrl("http://mmbiz.qpic.cn/mmbiz_jpg/qTz4qeDOSsMNGJ5kplb0j8l8MFibrWQZCy72TjvoxKj9V8WAWy3ibHYtiaCt1B05wnPFgUUV72W6ibRcdRUE436uZQ/0?wx_fmt=jpeg");
                        item.setUrl("http://mp.weixin.qq.com/s?__biz=MzU4Njg3Mjg0NQ==&mid=100000014&idx=1&sn=8204d20fe47352959054ff2b2af7da13&chksm=7df5e06c4a82697aa0f97706920e5a039afda938fd2f582324df09c03495bfcf2fd4907fc6fa#rd");
                        items.add(item);
                        ArticleItem item2 = new ArticleItem();
                        item2.setTitle("校区环境，最美篮球，冠军教练等你来！");
                        item2.setPicUrl("http://mmbiz.qpic.cn/mmbiz_jpg/qTz4qeDOSsNX58vye6hI2rAGU9N6L8EY6E8CbCVOl52w1G9fl9cGia2919qvkDiaoQ4Rg6B4AmqL3TiaBzbdEicmlg/0?wx_fmt=jpeg");
                        item2.setUrl("http://mp.weixin.qq.com/s?__biz=MzU4Njg3Mjg0NQ==&mid=100000003&idx=1&sn=c279ca72470f49284f28e617f5c07ac2&chksm=7df5e0614a826977b5697a5f405dee5b2648d6158646762aae73cf6b32fd88bb4fcad53b8111#rd");
                        items.add(item2);
                        respXml = WeChatUtils.sendArticleMsg(requestMap, items);
                    } else if (eventKey!=null && "MENU_JOIN".equals(eventKey)){
                        respContent = "相关功能建设中，敬请期待！";
                        respXml = WeChatUtils.sendTextMsg(requestMap, respContent);
                    }
                } else if (eventType.equals(WeChatUtils.EVENT_TYPE_TEMPLATESENDJOBFINISH)) {
                    System.out.println("模版发送成功！");
                    return "";
                }
            }
            mes = mes == null ? "用户消息为空" : mes;
            if (respXml == null)
                respXml = WeChatUtils.sendTextMsg(requestMap, mes);
            System.out.println("后台处理后回复给微信的字符串为：");
            System.out.println(respXml);
            return respXml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //发送模板消息接口，定死为签到提醒先
    public String sendModelMsg(String toOpenId) {
        Map<String, Object> msg = new HashMap<>();
        if (toOpenId == null) {
            toOpenId = "oDVbL1Ya6tjH6JObFiuTiamLjAzg";
        }
        msg.put("touser", toOpenId);
        msg.put("template_id", "3pc8-muKr7cpGL5FPR6d022ETg9_5IvAbdFBTaBDVXI");

        Map<String, Object> data = new HashMap<>();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("value", "课程类型");
        data.put("productType", valueMap);

        valueMap = new HashMap<>();
        valueMap.put("value", "小课包上课消费成功");
        valueMap.put("color", "#173177");
        data.put("name", valueMap);

        valueMap = new HashMap<>();
        valueMap.put("value", "会员卡号");
        valueMap.put("color", "#173177");
        data.put("accountType", valueMap);

        valueMap = new HashMap<>();
        valueMap.put("value", "疯疯柯基卡123");
        valueMap.put("color", "#173177");
        data.put("account", valueMap);

        valueMap = new HashMap<>();
        valueMap.put("value", new Date().getTime());
        valueMap.put("color", "#173177");
        data.put("time", valueMap);

        valueMap = new HashMap<>();
        valueMap.put("value", "这是一个发送签到消费模板的测试疯疯！");
        valueMap.put("color", "#173177");
        data.put("remark", valueMap);

        msg.put("data", data);

        JSONObject jsonObject = JSONObject.fromObject(msg);
        System.out.println("得到的json串：");
        String postBody = jsonObject.toString();
        String result = WeChatUtils.sendTemplateMsg(postBody);
        return result;
    }

    //创建公众号菜单
    public String createMenu(){
        Map<String,Object> menu1=new HashMap<>();
        Map<String,Object> button1=new HashMap<>();
        Map<String,Object> button2=new HashMap<>();
        button1.put("type","click");
        button1.put("name","简单介绍");
        button1.put("key","MENU_ABOUT");
        button2.put("type","click");
        button2.put("name","图文介绍");
        button2.put("key","MENU_TUWEN");
        ArrayList buttons=new ArrayList();
        buttons.add(button1);
        buttons.add(button2);
        menu1.put("name","关于我们");
        menu1.put("sub_button",buttons);

        Map<String,Object> menu2=new HashMap<>();
        Map<String,Object> button3=new HashMap<>();
        Map<String,Object> button4=new HashMap<>();
        button3.put("type","click");
        button3.put("name","加入我们");
        button3.put("key","MENU_JOIN");
        button4.put("type","view");
        button4.put("name","百度一下");
        button4.put("url","https://www.baidu.com/s?wd=fk1%E8%8A%B1%E5%BC%8F%E7%AF%AE%E7%90%83");
        ArrayList buttons2=new ArrayList();
        buttons2.add(button3);
        buttons2.add(button4);
        menu2.put("name","FK1俱乐部");
        menu2.put("sub_button",buttons2);

        ArrayList all=new ArrayList();
        all.add(menu1);
        all.add(menu2);
        Map<String,Object> out=new HashMap<>();
        out.put("button",all);
        JSONObject jsonMenu=JSONObject.fromObject(out);
        String resp=WeChatUtils.createMenu(jsonMenu.toString());
        return resp;
    }

    //拉取素材
    public String getItemList(){
        String type="news";
        Integer offset=0;
        Integer count=20;
        return WeChatUtils.getItemList(type,offset,count);
    }
}
