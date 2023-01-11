package com.yudian.www.service.sys.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.common.entity.Constants;
import com.yudian.common.utils.AddressUtils;
import com.yudian.common.utils.ServletUtils;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.sys.SysLogininfor;
import com.yudian.www.mapper.sys.SysLogininforMapper;
import com.yudian.www.service.sys.ISysLogininforService;
import com.yudian.www.service.sys.ISysLogininforServiceProcess;
import com.yudian.www.service.sys.param.AeSysLogininforParam;
import com.yudian.www.service.sys.param.GetSysLogininforListParam;
import com.yudian.www.service.sys.vo.SysLogininforInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统访问记录 服务实现类-流程
 *

 * @since 2022-01-06
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysLogininforServiceImplProcess implements ISysLogininforServiceProcess {

    private final ISysLogininforService sysLogininforService;

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public void recordLogininfor(final String username, final String status, final String message, HttpServletRequest request, final Object... args) {
        final UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        final String ip = ServletUtils.getClientIP(request);
        String address = AddressUtils.getRealAddressByIP(ip);
        StringBuilder s = new StringBuilder();
        s.append(getBlock(ip));
        s.append(address);
        s.append(getBlock(username));
        s.append(getBlock(status));
        s.append(getBlock(message));
        // 打印信息到日志
        log.info(s.toString(), args);
        // 获取客户端操作系统
        String os = userAgent.getOs().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserName(username);
        logininfor.setIpaddr(ip);
        logininfor.setLoginLocation(address);
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setMsg(message);
        logininfor.setLoginTime(LocalDateTime.now());
        // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            logininfor.setLoginStatus(true);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            logininfor.setLoginStatus(false);
        }
        // 插入数据
        sysLogininforService.save(logininfor);
    }

    private String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }

    /**
     * 添加
     *
     * @param aeSysLogininforParam
     */
    @Override
    public void sysLogininforAdd(AeSysLogininforParam aeSysLogininforParam) {
        aeSysLogininforParam.initParam();
        this.checkParam(aeSysLogininforParam);
        SysLogininfor sysLogininfor = new SysLogininfor();
        BeanUtils.copyProperties(aeSysLogininforParam, sysLogininfor);
        boolean save = sysLogininforService.save(sysLogininfor);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void sysLogininforAddBatch(List<AeSysLogininforParam> aeSysLogininforParamList) {
        List<SysLogininfor> sysLogininforList = aeSysLogininforParamList.stream()
                .map(aeSysLogininforParam -> {
                    SysLogininfor sysLogininfor = new SysLogininfor();
                    BeanUtils.copyProperties(aeSysLogininforParam, sysLogininfor);
                    return sysLogininfor;
                }).collect(Collectors.toList());
        boolean save = sysLogininforService.saveBatch(sysLogininforList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeSysLogininforParam
     */
    @Override
    public void sysLogininforEdit(AeSysLogininforParam aeSysLogininforParam) {
        aeSysLogininforParam.initParam();
        this.checkParam(aeSysLogininforParam);
        SysLogininfor sysLogininfor = sysLogininforService.getById(aeSysLogininforParam.getInfoId());
        if (null == sysLogininfor) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeSysLogininforParam, sysLogininfor);
        boolean update = sysLogininforService.updateById(sysLogininfor);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param infoIds
     */
    @Override
    public void sysLogininforRemove(Long[] infoIds) {
        boolean remove = sysLogininforService.removeByIds(Arrays.asList(infoIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getSysLogininforListParam
     * @return
     */
    @Override
    public TableRecordVo<SysLogininforInfoVo> getSysLogininforList(GetSysLogininforListParam getSysLogininforListParam) {
        LambdaQueryWrapper<SysLogininfor> queryWrapper = new QueryWrapper<SysLogininfor>().lambda();
        queryWrapper
                .likeRight(StringUtils.isNotBlank(getSysLogininforListParam.getUserName()), SysLogininfor::getUserName, getSysLogininforListParam.getUserName())
                .likeRight(StringUtils.isNotBlank(getSysLogininforListParam.getIpaddr()), SysLogininfor::getIpaddr, getSysLogininforListParam.getIpaddr())
                .eq(null != getSysLogininforListParam.getLoginStatus(), SysLogininfor::getLoginStatus, getSysLogininforListParam.getLoginStatus())
                .ge(null != getSysLogininforListParam.getStartDateTime(), SysLogininfor::getLoginTime, getSysLogininforListParam.getStartDateTime())
                .le(null != getSysLogininforListParam.getEndDateTime(), SysLogininfor::getLoginTime, getSysLogininforListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getSysLogininforListParam.getOrderByColumn()) && StringUtils.isNotBlank(getSysLogininforListParam.getIsAsc())) {
            queryWrapper.last("order by " + getSysLogininforListParam.getOrderBy());
        }

        IPage<SysLogininfor> page = sysLogininforService.page(new Page<>(getSysLogininforListParam.getPageNo(), getSysLogininforListParam.getPageSize()), queryWrapper);

        List<SysLogininfor> records = page.getRecords();

        List<SysLogininforInfoVo> sysLogininforInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(sysLogininforInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param infoId
     * @return
     */
    @Override
    public SysLogininforInfoVo getSysLogininforDetail(Long infoId) {
        SysLogininfor sysLogininfor = sysLogininforService.getById(infoId);
        if (null == sysLogininfor) {
            throw new ServiceException("记录不存在");
        }
        List<SysLogininforInfoVo> sysLogininforInfoVos = this.entityToVo(Arrays.asList(sysLogininfor));
        return sysLogininforInfoVos.stream().findFirst().orElse(null);
    }

    @Override
    public void truncateTable() {
        SysLogininforMapper baseMapper = (SysLogininforMapper) sysLogininforService.getBaseMapper();
        baseMapper.truncateTable();
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<SysLogininforInfoVo> entityToVo(List<SysLogininfor> records) {
        return records.stream()
                .map(sysLogininfor -> {
                    SysLogininforInfoVo sysLogininforInfoVo = new SysLogininforInfoVo();
                    BeanUtils.copyProperties(sysLogininfor, sysLogininforInfoVo);
                    return sysLogininforInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeSysLogininforParam
     */
    private void checkParam(AeSysLogininforParam aeSysLogininforParam) {

    }
}