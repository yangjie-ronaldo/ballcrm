package org.nothink.ballcrm.async;

import net.sf.json.JSONObject;
import org.nothink.ballcrm.util.DateUtils;
import org.nothink.ballcrm.util.HttpRequest;
import org.nothink.ballcrm.util.WeChatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Date;

@Service
public class ScheduledService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(cron = "0 */20 * * * *")
    public void doTask() {
        logger.info("后台服务检测...正在运行中...当前时间：" + DateUtils.parseDateToStr(new Date(), DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
    }

    /**
     * 定时获取微信的access_token 每一小时获取一次 不用微信要把这个定时任务关了
     */
    //@Scheduled(fixedDelay = 60*60*1000)
    public void getAccess_token() {
        System.out.println("开始获取微信的access_token");
        String grant_type = "client_credential";
        String AppId = WeChatUtils.appID;
        String secret = WeChatUtils.appSecret;
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        String params = "grant_type=" + grant_type + "&appid=" + AppId + "&secret=" + secret;
        try {
            String message=HttpRequest.sendGet(url,params);
            System.out.println("返回的数据：" + message);
            JSONObject json = JSONObject.fromObject(message);
            String token = json.getString("access_token");
            if (!StringUtils.isEmpty(token)) {
                WeChatUtils.accessToken = token;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
