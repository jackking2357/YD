package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.sys.SysPost;
import com.yudian.www.service.sys.ISysPostService;
import com.yudian.www.service.sys.ISysPostServiceProcess;
import com.yudian.www.service.sys.param.AeSysPostParam;
import com.yudian.www.service.sys.param.GetSysPostListParam;
import com.yudian.www.service.sys.vo.SysPostInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 岗位信息表 服务实现类-流程
 *

 * @since 2022-02-12
 */
@Service
public class SysPostServiceImplProcess implements ISysPostServiceProcess {

    @Resource
    private ISysPostService sysPostService;

    /**
     * 添加
     *
     * @param aeSysPostParam
     */
    @Override
    public void sysPostAdd(AeSysPostParam aeSysPostParam) {
        aeSysPostParam.initParam();
        this.checkParam(aeSysPostParam);
        SysPost sysPost = new SysPost();
        BeanUtils.copyProperties(aeSysPostParam, sysPost);
        boolean save = sysPostService.save(sysPost);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void sysPostAddBatch(List<AeSysPostParam> aeSysPostParamList) {
        List<SysPost> sysPostList = aeSysPostParamList.stream()
                .map(aeSysPostParam -> {
                    SysPost sysPost = new SysPost();
                    BeanUtils.copyProperties(aeSysPostParam, sysPost);
                    return sysPost;
                }).collect(Collectors.toList());
        boolean save = sysPostService.saveBatch(sysPostList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeSysPostParam
     */
    @Override
    public void sysPostEdit(AeSysPostParam aeSysPostParam) {
        aeSysPostParam.initParam();
        this.checkParam(aeSysPostParam);
        SysPost sysPost = sysPostService.getById(aeSysPostParam.getPostId());
        if (null == sysPost) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeSysPostParam, sysPost);
        boolean update = sysPostService.updateById(sysPost);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param postIds
     */
    @Override
    public void sysPostRemove(Long[] postIds) {
        boolean remove = sysPostService.removeByIds(Arrays.asList(postIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getSysPostListParam
     * @return
     */
    @Override
    public TableRecordVo<SysPostInfoVo> getSysPostList(GetSysPostListParam getSysPostListParam) {
        Boolean postStatus = getSysPostListParam.getPostStatus();
        String postCode = getSysPostListParam.getPostCode();
        String postName = getSysPostListParam.getPostName();

        LambdaQueryWrapper<SysPost> queryWrapper = new QueryWrapper<SysPost>().lambda();
        queryWrapper.eq(null != postStatus, SysPost::getPostStatus, postStatus);
        queryWrapper.like(StringUtils.isNotBlank(postCode), SysPost::getPostCode, postCode);
        queryWrapper.like(StringUtils.isNotBlank(postName), SysPost::getPostName, postName);
        queryWrapper
                .ge(null != getSysPostListParam.getStartDateTime(), SysPost::getCreateDatetime, getSysPostListParam.getStartDateTime())
                .le(null != getSysPostListParam.getEndDateTime(), SysPost::getCreateDatetime, getSysPostListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getSysPostListParam.getOrderByColumn()) && StringUtils.isNotBlank(getSysPostListParam.getIsAsc())) {
            queryWrapper.last("order by " + getSysPostListParam.getOrderBy());
        }

        IPage<SysPost> page = sysPostService.page(new Page<>(getSysPostListParam.getPageNo(), getSysPostListParam.getPageSize()), queryWrapper);

        List<SysPost> records = page.getRecords();

        List<SysPostInfoVo> sysPostInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(sysPostInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param postId
     * @return
     */
    @Override
    public SysPostInfoVo getSysPostDetail(Long postId) {
        SysPost sysPost = sysPostService.getById(postId);
        if (null == sysPost) {
            throw new ServiceException("记录不存在");
        }
        List<SysPostInfoVo> sysPostInfoVos = this.entityToVo(Arrays.asList(sysPost));
        return sysPostInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<SysPostInfoVo> entityToVo(List<SysPost> records) {
        return records.stream()
                .map(sysPost -> {
                    SysPostInfoVo sysPostInfoVo = new SysPostInfoVo();
                    BeanUtils.copyProperties(sysPost, sysPostInfoVo);
                    return sysPostInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeSysPostParam
     */
    private void checkParam(AeSysPostParam aeSysPostParam) {

    }
}