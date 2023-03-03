package com.example.wendao.task;

import com.example.wendao.service.UserService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 16:53
 * @version: 1.0
 */
@Component
public class AchieveValueTask extends QuartzJobBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(AchieveValueTask.class);

    @Autowired
    UserService userService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("AchieveValueTask--------------------------{}", sdf.format(new Date()));
        userService.resetAchieveValue();
    }
}
