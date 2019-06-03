package org.nothink.ballcrm.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 异步任务类，在这个类中执行应用的异步任务
 */
@Service
public class ScheduledService {
    static int i=0;

    //@Scheduled(cron = "*/2 * * * * *")
    public void doTask() throws InterruptedException {

    }
}
