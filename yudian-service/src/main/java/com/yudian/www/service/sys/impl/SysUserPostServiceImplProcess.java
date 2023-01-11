package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.sys.SysUserPost;
import com.yudian.www.service.sys.ISysUserPostService;
import com.yudian.www.service.sys.ISysUserPostServiceProcess;
import com.yudian.www.service.sys.param.AeSysUserPostParam;
import com.yudian.www.service.sys.param.GetSysUserPostListParam;
import com.yudian.www.service.sys.vo.SysUserPostInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户与岗位关联表 服务实现类-流程
 *

 * @since 2022-02-12
 */
@Service
public class SysUserPostServiceImplProcess implements ISysUserPostServiceProcess {

    @Resource
    private ISysUserPostService sysUserPostService;

    /**
     * 添加
     *
     * @param aeSysUserPostParam
     */
    @Override
    public void sysUserPostAdd(AeSysUserPostParam aeSysUserPostParam) {
        aeSysUserPostParam.initParam();
        this.checkParam(aeSysUserPostParam);
        SysUserPost sysUserPost = new SysUserPost();
        BeanUtils.copyProperties(aeSysUserPostParam, sysUserPost);
        boolean save = sysUserPostService.save(sysUserPost);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void sysUserPostAddBatch(List<AeSysUserPostParam> aeSysUserPostParamList) {
        List<SysUserPost> sysUserPostList = aeSysUserPostParamList.stream()
                .map(aeSysUserPostParam -> {
                    SysUserPost sysUserPost = new SysUserPost();
                    BeanUtils.copyProperties(aeSysUserPostParam, sysUserPost);
                    return sysUserPost;
                }).collect(Collectors.toList());
        boolean save = sysUserPostService.saveBatch(sysUserPostList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeSysUserPostParam
     */
    @Override
    public void sysUserPostEdit(AeSysUserPostParam aeSysUserPostParam) {
        aeSysUserPostParam.initParam();
        this.checkParam(aeSysUserPostParam);
        SysUserPost sysUserPost = sysUserPostService.getById(aeSysUserPostParam.getUserId());
        if (null == sysUserPost) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeSysUserPostParam, sysUserPost);
        boolean update = sysUserPostService.updateById(sysUserPost);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param userIds
     */
    @Override
    public void sysUserPostRemove(Long[] userIds) {
        boolean remove = sysUserPostService.removeByIds(Arrays.asList(userIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getSysUserPostListParam
     * @return
     */
    @Override
    public TableRecordVo<SysUserPostInfoVo> getSysUserPostList(GetSysUserPostListParam getSysUserPostListParam) {
        LambdaQueryWrapper<SysUserPost> queryWrapper = new QueryWrapper<SysUserPost>().lambda();

        if (StringUtils.isNotBlank(getSysUserPostListParam.getOrderByColumn()) && StringUtils.isNotBlank(getSysUserPostListParam.getIsAsc())) {
            queryWrapper.last("order by " + getSysUserPostListParam.getOrderBy());
        }

        IPage<SysUserPost> page = sysUserPostService.page(new Page<>(getSysUserPostListParam.getPageNo(), getSysUserPostListParam.getPageSize()), queryWrapper);

        List<SysUserPost> records = page.getRecords();

        List<SysUserPostInfoVo> sysUserPostInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(sysUserPostInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param userId
     * @return
     */
    @Override
    public SysUserPostInfoVo getSysUserPostDetail(Long userId) {
        SysUserPost sysUserPost = sysUserPostService.getById(userId);
        if (null == sysUserPost) {
            throw new ServiceException("记录不存在");
        }
        List<SysUserPostInfoVo> sysUserPostInfoVos = this.entityToVo(Arrays.asList(sysUserPost));
        return sysUserPostInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<SysUserPostInfoVo> entityToVo(List<SysUserPost> records) {
        return records.stream()
                .map(sysUserPost -> {
                    SysUserPostInfoVo sysUserPostInfoVo = new SysUserPostInfoVo();
                    BeanUtils.copyProperties(sysUserPost, sysUserPostInfoVo);
                    return sysUserPostInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeSysUserPostParam
     */
    private void checkParam(AeSysUserPostParam aeSysUserPostParam) {

    }
}