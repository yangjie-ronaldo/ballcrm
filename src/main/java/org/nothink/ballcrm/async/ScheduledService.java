package org.nothink.ballcrm.async;

import org.nothink.ballcrm.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 异步任务类，在这个类中执行应用的异步任务
 */
@Service
public class ScheduledService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(cron = "0 */2 * * * *")
    public void doTask() throws InterruptedException {
        logger.info("后台服务检测...正在运行中...当前时间：" + DateUtils.parseDateToStr(new Date(), DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
    }
}
