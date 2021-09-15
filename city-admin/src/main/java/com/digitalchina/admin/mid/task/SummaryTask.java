package com.digitalchina.admin.mid.task;

import cn.hutool.core.date.DateUtil;
import com.digitalchina.admin.mid.entity.SignTask;
import com.digitalchina.admin.mid.service.SignTaskService;
import com.digitalchina.admin.mid.service.SignTreeVerService;
import com.digitalchina.modules.constant.enums.DateRateEnum;
import com.digitalchina.modules.constant.enums.TaskStatusEnum;
import com.digitalchina.modules.constant.enums.TaskTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

/**
 * <p>
 *  体征每日增加汇总任务
 * </p>
 * @author liuping
 * @since 2019-10-23
 */
@Component
public class SummaryTask {

    private static final String LOCK_SCRIPT = "local key, value, expiredTime = KEYS[1], ARGV[1], ARGV[2]; return redis.call('SET', key, value, 'NX', 'EX', expiredTime)";
    private static final String LOCK_KEY = "SummaryTask:lock";
    private static final String LOCK_VALUE = "ON";
    private static final String EXPIRED_TIME = "600";
    private static final String LOCK_SUCCESS = "OK";

    private static final Logger log = LoggerFactory.getLogger(SummaryTask.class);

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SignTaskService signTaskService;
    @Autowired
    private SignTreeVerService signTreeVerService;

    /**
     * 0:05:00开始每隔6小时进行一次调度
     */
    @Scheduled(cron = "0 5 0/6 * * ?")
    public void executeTask() {
        if (getLock()) {
            try {
                // do task
                Date now = new Date();
                String jobDate = DateUtil.formatDate(DateUtil.offsetHour(now,-1));
                SignTask signTask = new SignTask();
                signTask.setStarttime(jobDate);
                signTask.setEndtime(jobDate);
                signTask.setTid(signTreeVerService.getTid());
                signTask.setType(TaskTypeEnum.SUMMARY.getCode());
                signTask.setRate(DateRateEnum.DAY.getCode());
                signTask.setStatus(TaskStatusEnum.WAITING.getCode());
                signTask.setModt(now);
                signTaskService.save(signTask);
            } catch (Exception e) {
                log.error("体征每日增加汇总任务异常：{}",e.getMessage());
            } finally {
                // 放在在 finally 代码中，保证任务出现异常时（非程序终止）始终释放锁
                releaseLock();
            }
        } else {
            log.info("本应用实例未获得锁，体征每日增加汇总任务已跳过！");
        }
    }

    // 获取锁
    private boolean getLock() {
        // 获取锁并设置过期时间：SET key value NX EX expired
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(LOCK_SCRIPT);
        redisScript.setResultType(String.class);
        String result = redisTemplate.execute(redisScript, Collections.singletonList(LOCK_KEY),LOCK_VALUE, EXPIRED_TIME);
        return LOCK_SUCCESS.equals(result);
    }

    // 释放锁
    private void releaseLock() {
        // 释放“锁”：删除对应的 key
        redisTemplate.delete(LOCK_KEY);
    }
}
