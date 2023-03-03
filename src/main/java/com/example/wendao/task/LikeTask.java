package com.example.wendao.task;

import com.example.wendao.service.LikeService;
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
 * @date: 2023/3/3 16:54
 * @version: 1.0
 */
@Component
public class LikeTask extends QuartzJobBean {
    @Autowired
    LikeService likeService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(LikeTask.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("LikeTask--------------------------{}", sdf.format(new Date()));
        likeService.transLikedCountFromRedis2DB();
    }
}
