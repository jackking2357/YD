package com.yudian.www.service.robot.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.robot.RobotAccelerator;
import com.yudian.www.service.robot.IRobotAcceleratorService;
import com.yudian.www.service.robot.IRobotAcceleratorServiceProcess;
import com.yudian.www.service.robot.param.AeRobotAcceleratorParam;
import com.yudian.www.service.robot.param.GetRobotAcceleratorListParam;
import com.yudian.www.service.robot.vo.RobotAcceleratorInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 机器人加速器 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class RobotAcceleratorServiceImplProcess implements IRobotAcceleratorServiceProcess {

    @Resource
    private IRobotAcceleratorService robotAcceleratorService;

    /**
     * 添加
     *
     * @param aeRobotAcceleratorParam
     */
    @Override
    public void robotAcceleratorAdd(AeRobotAcceleratorParam aeRobotAcceleratorParam) {
        aeRobotAcceleratorParam.initParam();
        this.checkParam(aeRobotAcceleratorParam);
        RobotAccelerator robotAccelerator = new RobotAccelerator();
        BeanUtils.copyProperties(aeRobotAcceleratorParam, robotAccelerator);
        robotAccelerator.setAcceleratorPhoto(JSON.toJSONString(aeRobotAcceleratorParam.getAcceleratorPhoto()));
        boolean save = robotAcceleratorService.save(robotAccelerator);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void robotAcceleratorAddBatch(List<AeRobotAcceleratorParam> aeRobotAcceleratorParamList) {
        List<RobotAccelerator> robotAcceleratorList = aeRobotAcceleratorParamList.stream()
                .map(aeRobotAcceleratorParam -> {
                    RobotAccelerator robotAccelerator = new RobotAccelerator();
                    BeanUtils.copyProperties(aeRobotAcceleratorParam, robotAccelerator);
                    return robotAccelerator;
                }).collect(Collectors.toList());
        boolean save = robotAcceleratorService.saveBatch(robotAcceleratorList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeRobotAcceleratorParam
     */
    @Override
    public void robotAcceleratorEdit(AeRobotAcceleratorParam aeRobotAcceleratorParam) {
        aeRobotAcceleratorParam.initParam();
        this.checkParam(aeRobotAcceleratorParam);
        RobotAccelerator robotAccelerator = robotAcceleratorService.getById(aeRobotAcceleratorParam.getRobotAcceleratorId());
        if (null == robotAccelerator) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeRobotAcceleratorParam, robotAccelerator);
        robotAccelerator.setAcceleratorPhoto(JSON.toJSONString(aeRobotAcceleratorParam.getAcceleratorPhoto()));
        boolean update = robotAcceleratorService.updateById(robotAccelerator);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param robotAcceleratorIds
     */
    @Override
    public void robotAcceleratorRemove(Long[] robotAcceleratorIds) {
        boolean remove = robotAcceleratorService.removeByIds(Arrays.asList(robotAcceleratorIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getRobotAcceleratorListParam
     * @return
     */
    @Override
    public TableRecordVo<RobotAcceleratorInfoVo> getRobotAcceleratorList(GetRobotAcceleratorListParam getRobotAcceleratorListParam) {
        LambdaQueryWrapper<RobotAccelerator> queryWrapper = new QueryWrapper<RobotAccelerator>().lambda();
        queryWrapper
                .ge(null != getRobotAcceleratorListParam.getStartDateTime(), RobotAccelerator::getCreateDatetime, getRobotAcceleratorListParam.getStartDateTime())
                .le(null != getRobotAcceleratorListParam.getEndDateTime(), RobotAccelerator::getCreateDatetime, getRobotAcceleratorListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getRobotAcceleratorListParam.getOrderByColumn()) && StringUtils.isNotBlank(getRobotAcceleratorListParam.getIsAsc())) {
            queryWrapper.last("order by " + getRobotAcceleratorListParam.getOrderBy());
        }

        IPage<RobotAccelerator> page = robotAcceleratorService.page(new Page<>(getRobotAcceleratorListParam.getPageNo(), getRobotAcceleratorListParam.getPageSize()), queryWrapper);

        List<RobotAccelerator> records = page.getRecords();

        List<RobotAcceleratorInfoVo> robotAcceleratorInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(robotAcceleratorInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param robotAcceleratorId
     * @return
     */
    @Override
    public RobotAcceleratorInfoVo getRobotAcceleratorDetail(Long robotAcceleratorId) {
        RobotAccelerator robotAccelerator = robotAcceleratorService.getById(robotAcceleratorId);
        if (null == robotAccelerator) {
            throw new ServiceException("记录不存在");
        }
        List<RobotAcceleratorInfoVo> robotAcceleratorInfoVos = this.entityToVo(Arrays.asList(robotAccelerator));
        return robotAcceleratorInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<RobotAcceleratorInfoVo> entityToVo(List<RobotAccelerator> records) {
        return records.stream()
                .map(robotAccelerator -> {
                    RobotAcceleratorInfoVo robotAcceleratorInfoVo = new RobotAcceleratorInfoVo();
                    BeanUtils.copyProperties(robotAccelerator, robotAcceleratorInfoVo);
                    return robotAcceleratorInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeRobotAcceleratorParam
     */
    private void checkParam(AeRobotAcceleratorParam aeRobotAcceleratorParam) {

    }
}