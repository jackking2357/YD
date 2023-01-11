package com.yudian.www.service;

import com.yudian.common.utils.AddressUtils;
import com.yudian.www.entity.sys.SysOperLog;
import com.yudian.www.service.sys.ISysOperLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步工具类
 *
 */
@Component
@RequiredArgsConstructor
public class SysOperLogAsyncService {

    private final ISysOperLogService sysOperLogService;

    /**
     * 记录操作信息
     *
     * @param sysOperLog
     */
    @Async("threadPoolTaskExecutor")
    public void save(SysOperLog sysOperLog) {
        sysOperLog.setOperLocation(AddressUtils.getRealAddressByIP(sysOperLog.getOperIp()));
        sysOperLogService.save(sysOperLog);
    }
}
