package com.yudian.www.service.robot.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.robot.Robot;
import com.yudian.www.service.robot.param.AeRobotParam;
import com.yudian.www.service.robot.param.GetRobotListParam;
import com.yudian.www.service.robot.IRobotService;
import com.yudian.www.service.robot.IRobotServiceProcess;
import com.yudian.www.service.robot.vo.RobotInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* 机器人 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class RobotServiceImplProcess implements IRobotServiceProcess {

    @Resource
    private IRobotService robotService;

    /**
     * 添加
     * @param aeRobotParam
     */
    @Override
    public void robotAdd(AeRobotParam aeRobotParam) {
        aeRobotParam.initParam();
        this.checkParam(aeRobotParam);
        Robot robot = new Robot();
        BeanUtils.copyProperties(aeRobotParam, robot);
        robot.setRobotPhoto(JSON.toJSONString(aeRobotParam.getRobotPhoto()));
        boolean save = robotService.save(robot);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void robotAddBatch(List<AeRobotParam> aeRobotParamList) {
        List<Robot> robotList = aeRobotParamList.stream()
            .map(aeRobotParam -> {
                Robot robot = new Robot();
                BeanUtils.copyProperties(aeRobotParam, robot);
                robot.setRobotPhoto(JSON.toJSONString(aeRobotParam.getRobotPhoto()));
                return robot;
            }).collect(Collectors.toList());
        boolean save = robotService.saveBatch(robotList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     * @param aeRobotParam
     */
    @Override
    public void robotEdit(AeRobotParam aeRobotParam) {
        aeRobotParam.initParam();
        this.checkParam(aeRobotParam);
        Robot robot = robotService.getById(aeRobotParam.getRobotId());
        if (null == robot) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeRobotParam, robot);
        robot.setRobotPhoto(JSON.toJSONString(aeRobotParam.getRobotPhoto()));
        boolean update = robotService.updateById(robot);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     * @param robotIds
     */
    @Override
    public void robotRemove(Long[] robotIds) {
        boolean remove = robotService.removeByIds(Arrays.asList(robotIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     * @param getRobotListParam
     * @return
     */
    @Override
    public TableRecordVo<RobotInfoVo> getRobotList(GetRobotListParam getRobotListParam) {
        LambdaQueryWrapper<Robot> queryWrapper = new QueryWrapper<Robot>().lambda();
        queryWrapper
                .ge(null != getRobotListParam.getStartDateTime(), Robot::getCreateDatetime, getRobotListParam.getStartDateTime())
                .le(null != getRobotListParam.getEndDateTime(), Robot::getCreateDatetime, getRobotListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getRobotListParam.getOrderByColumn()) && StringUtils.isNotBlank(getRobotListParam.getIsAsc())) {
            queryWrapper.last("order by " + getRobotListParam.getOrderBy());
        }

        IPage<Robot> page = robotService.page(new Page<>(getRobotListParam.getPageNo(), getRobotListParam.getPageSize()), queryWrapper);

        List<Robot> records = page.getRecords();

        List<RobotInfoVo> robotInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(robotInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     * @param robotId
     * @return
     */
    @Override
    public RobotInfoVo getRobotDetail(Long robotId) {
        Robot robot = robotService.getById(robotId);
        if (null == robot) {
            throw new ServiceException("记录不存在");
        }
        List<RobotInfoVo> robotInfoVos = this.entityToVo(Arrays.asList(robot));
        return robotInfoVos.stream().findFirst().orElse(null);
    }

    /**
    * 实体类转VO
    *
    * @param records
    * @return
    */
    private List<RobotInfoVo> entityToVo(List<Robot> records) {
        return records.stream()
            .map(robot -> {
                RobotInfoVo robotInfoVo = new RobotInfoVo();
                BeanUtils.copyProperties(robot, robotInfoVo);
                return robotInfoVo;
            }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeRobotParam
     */
    private void checkParam(AeRobotParam aeRobotParam) {

    }
}