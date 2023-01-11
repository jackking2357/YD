package com.yudian.www.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import com.yudian.common.entity.Constants;
import com.yudian.common.entity.R;
import com.yudian.common.redis.utils.RedisUtils;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.controller.monitor.vo.SysUserOnlineVo;
import com.yudian.www.project.annotation.Log;
import com.yudian.www.project.annotation.enums.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 在线用户监控
 */
@Api(value = "在线用户监控", tags = {"在线用户监控管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController {

    private final RedisUtils redisUtils;

    @SaCheckPermission(value = {"monitor:online:list"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation("在线用户列表")
    @GetMapping("/list")
    public R<TableRecordVo<SysUserOnlineVo>> list(String ipaddr, String userName) {
        // 获取所有未过期的 token
        List<String> keys = StpUtil.searchTokenValue("", -1, 0, true);
        List<SysUserOnlineVo> userOnlineDTOList = new ArrayList<>();
        for (String key : keys) {
            String token = key.replace(Constants.LOGIN_TOKEN_KEY, "");
            // 如果已经过期则踢下线
            if (StpUtil.stpLogic.getTokenActivityTimeoutByToken(token) < 0) {
                continue;
            }
            userOnlineDTOList.add(redisUtils.get(Constants.ONLINE_TOKEN_KEY + token));
        }
        if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
            userOnlineDTOList = userOnlineDTOList.stream()
                    .filter(userOnline -> StringUtils.equals(ipaddr, userOnline.getIpaddr()) && StringUtils.equals(userName, userOnline.getUserName()))
                    .collect(Collectors.toList());
        } else if (StringUtils.isNotEmpty(ipaddr)) {
            userOnlineDTOList = userOnlineDTOList.stream()
                    .filter(userOnline -> StringUtils.equals(ipaddr, userOnline.getIpaddr()))
                    .collect(Collectors.toList());
        } else if (StringUtils.isNotEmpty(userName)) {
            userOnlineDTOList = userOnlineDTOList.stream()
                    .filter(userOnline -> StringUtils.equals(userName, userOnline.getUserName()))
                    .collect(Collectors.toList());
        }
        Collections.reverse(userOnlineDTOList);
        userOnlineDTOList.removeAll(Collections.singleton(null));
        TableRecordVo<SysUserOnlineVo> tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(userOnlineDTOList);
        tableRecordVo.setTotal((long) userOnlineDTOList.size());
        return R.ok(tableRecordVo, "获取成功");
    }

    /**
     * 强退用户
     */
    @ApiOperation("强退用户")
    @SaCheckPermission("monitor:online:forceLogout")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public R forceLogout(@PathVariable String tokenId) {
        try {
            StpUtil.kickoutByTokenValue(tokenId);
        } catch (NotLoginException e) {
        }
        return R.ok();
    }
}
