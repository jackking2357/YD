package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.common.entity.Constants;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.base.TreeSelect;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.sys.SysDept;
import com.yudian.www.entity.sys.SysUser;
import com.yudian.www.service.sys.ISysDeptService;
import com.yudian.www.service.sys.ISysDeptServiceProcess;
import com.yudian.www.service.sys.ISysUserService;
import com.yudian.www.service.sys.ISysUserServiceProcess;
import com.yudian.www.service.sys.param.AeSysDeptParam;
import com.yudian.www.service.sys.param.GetSysDeptListParam;
import com.yudian.www.service.sys.vo.SysDeptInfoVo;
import com.yudian.www.service.sys.vo.SysUserInfoVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 部门表 服务实现类-流程
 *

 * @since 2022-02-12
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImplProcess implements ISysDeptServiceProcess {

    private final ISysDeptService sysDeptService;
    private final ISysUserService sysUserService;
    private final ISysUserServiceProcess sysUserServiceProcess;

    /**
     * 添加
     *
     * @param aeSysDeptParam
     */
    @Override
    public void sysDeptAdd(AeSysDeptParam aeSysDeptParam) {
        aeSysDeptParam.initParam();
        this.checkParam(aeSysDeptParam);
        String ancestors = aeSysDeptParam.getAncestors();

        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(aeSysDeptParam, sysDept);
        sysDept.setAncestors(ancestors);
        if (0 != sysDept.getParentId()) {
            sysDept.setAncestors(aeSysDeptParam.getAncestors() + "," + sysDept.getParentId());
        }
        boolean save = sysDeptService.save(sysDept);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void sysDeptAddBatch(List<AeSysDeptParam> aeSysDeptParamList) {
        List<SysDept> sysDeptList = aeSysDeptParamList.stream()
                .map(aeSysDeptParam -> {
                    SysDept sysDept = new SysDept();
                    BeanUtils.copyProperties(aeSysDeptParam, sysDept);
                    return sysDept;
                }).collect(Collectors.toList());
        boolean save = sysDeptService.saveBatch(sysDeptList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeSysDeptParam
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sysDeptEdit(AeSysDeptParam aeSysDeptParam) {
        aeSysDeptParam.initParam();
        SysDept sysDept = sysDeptService.getById(aeSysDeptParam.getDeptId());
        if (null == sysDept) {
            throw new ServiceException("记录不存在");
        }
        String oldAncestors = sysDept.getAncestors();
        this.checkParam(aeSysDeptParam);
        String ancestors = aeSysDeptParam.getAncestors();

        BeanUtils.copyProperties(aeSysDeptParam, sysDept);
        sysDept.setAncestors(ancestors);
        if (0 != sysDept.getParentId()) {
            sysDept.setAncestors(aeSysDeptParam.getAncestors() + "," + sysDept.getParentId());
        }

        boolean update = sysDeptService.updateById(sysDept);
        if (!update) {
            throw new ServiceException("编辑失败");
        }

        // 更新所有下级
        updateBatchChild(aeSysDeptParam, sysDept, oldAncestors);
    }

    private void updateBatchChild(AeSysDeptParam aeSysDeptParam, SysDept baseDesignerCategory, String oldAncestors) {
        Long sysDeptId = aeSysDeptParam.getDeptId();
        List<SysDept> sysDepts = sysDeptService.list(Wrappers.<SysDept>lambdaQuery()
                .and(sql -> {
                            // 第一个下级
                            sql.eq(SysDept::getParentId, sysDeptId)
                                    .or()
                                    // 所有下级
                                    .likeRight(SysDept::getAncestors, oldAncestors + "," + sysDeptId + ",");
                        }
                ));
        List<SysDept> sysDeptList = sysDepts.stream()
                .map(childSysDept -> {
                    String childAncestors = childSysDept.getAncestors();
                    String newAncestors = childAncestors.replaceAll(oldAncestors + "," + sysDeptId, baseDesignerCategory.getAncestors() + "," + sysDeptId);
                    childSysDept.setAncestors(newAncestors);
                    return childSysDept;
                }).collect(Collectors.toList());
        if (!sysDeptList.isEmpty()) {
            sysDeptService.updateBatchById(sysDeptList);
        }
    }

    /**
     * 删除
     *
     * @param deptIds
     */
    @Override
    public void sysDeptRemove(Long[] deptIds) {
        for (Long deptId : deptIds) {
            SysDept sysDept = sysDeptService.getById(deptId);
            if (null == sysDept) {
                throw new ServiceException("记录不存在");
            }
            if (1 == sysDeptService.list(Wrappers.<SysDept>lambdaQuery().eq(SysDept::getParentId, deptId).last(Constants.LIMIT1)).size()) {
                throw new ServiceException("存在下级部门,不允许删除");
            }
//            if (1 == sysUserService.list(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getDeptId, deptId).last(Constants.LIMIT1)).size()) {
//                throw new ServiceException("部门存在用户,不允许删除");
//            }
            boolean remove = sysDeptService.removeById(deptId);
            if (!remove) {
                throw new ServiceException("删除失败");
            }
        }
    }

    /**
     * 分页查询
     *
     * @param getSysDeptListParam
     * @return
     */
    @Override
    public TableRecordVo<SysDeptInfoVo> getSysDeptList(GetSysDeptListParam getSysDeptListParam) {
        Long parentId = getSysDeptListParam.getParentId();

        LambdaQueryWrapper<SysDept> queryWrapper = new QueryWrapper<SysDept>().lambda();

        queryWrapper
                .eq(null != getSysDeptListParam.getDeptStatus(), SysDept::getDeptStatus, getSysDeptListParam.getDeptStatus())
                .eq(null != parentId, SysDept::getParentId, parentId)
                .like(StringUtils.isNotBlank(getSysDeptListParam.getDeptName()), SysDept::getDeptName, getSysDeptListParam.getDeptName())
                .ge(null != getSysDeptListParam.getStartDateTime(), SysDept::getCreateDatetime, getSysDeptListParam.getStartDateTime())
                .le(null != getSysDeptListParam.getEndDateTime(), SysDept::getCreateDatetime, getSysDeptListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getSysDeptListParam.getOrderByColumn()) && StringUtils.isNotBlank(getSysDeptListParam.getIsAsc())) {
            queryWrapper.last("order by " + getSysDeptListParam.getOrderBy());
        }

        IPage<SysDept> page = sysDeptService.page(new Page<>(getSysDeptListParam.getPageNo(), getSysDeptListParam.getPageSize()), queryWrapper);

        List<SysDept> sysDeptList = page.getRecords();
        if (sysDeptList.isEmpty()) {
            return TableRecordVo.defaultValue();
        }

        if (null != getSysDeptListParam.getDeptStatus() || StringUtils.isNotBlank(getSysDeptListParam.getDeptName())) {
            String ancestors = sysDeptList.stream().map(SysDept::getAncestors).collect(Collectors.joining(","));
            // 所有上级id
            Set<Long> idSet = Stream.of(ancestors.split(",")).map(Long::parseLong).collect(Collectors.toSet());
            // 已经查到的id
            Set<Long> exitIdSet = sysDeptList.stream().map(SysDept::getDeptId).collect(Collectors.toSet());
            // 排除已经查到的 = 未查出来的
            Set<Long> noExitIdSet = idSet.stream().filter(id -> !exitIdSet.contains(id)).collect(Collectors.toSet());
            if (0 != noExitIdSet.size()) {
                sysDeptList.addAll(sysDeptService.listByIds(noExitIdSet));
            }
        }
        // 部门id
        List<Long> deptIds = sysDeptList.stream().map(SysDept::getDeptId).collect(Collectors.toList());
        List<SysUser> sysUserList = sysUserService.list(Wrappers.<SysUser>lambdaQuery()
                .in(SysUser::getDeptId, deptIds));
        List<SysUserInfoVo> sysUserInfoVos = sysUserServiceProcess.entityToVo(1, sysUserList);

        Map<Long, List<SysUserInfoVo>> deptSysUserMap = sysUserInfoVos.stream()
                .filter(sysUserInfoVo -> sysUserInfoVo.getDeptId() != null)
                .collect(Collectors.groupingBy(SysUserInfoVo::getDeptId));

        List<SysDeptInfoVo> sysDeptInfoVoList = sysDeptList.stream()
                .map(sysDept -> {
                    SysDeptInfoVo sysDeptInfoVo = new SysDeptInfoVo();
                    BeanUtils.copyProperties(sysDept, sysDeptInfoVo);
                    sysDeptInfoVo.setSysUserInfoVos(deptSysUserMap.get(sysDept.getDeptId()));
                    return sysDeptInfoVo;
                })
                .sorted(Comparator.comparing(SysDeptInfoVo::getDeptSort))
                .collect(Collectors.toList());
        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(sysDeptInfoVoList);
        tableRecordVo.setTotal(null);
        tableRecordVo.setPages(null);
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param deptId
     * @return
     */
    @Override
    public SysDeptInfoVo getSysDeptDetail(Long deptId) {
        SysDept sysDept = sysDeptService.getById(deptId);
        if (null == sysDept) {
            throw new ServiceException("记录不存在");
        }
        List<SysDeptInfoVo> sysDeptInfoVos = this.entityToVo(Arrays.asList(sysDept));
        return sysDeptInfoVos.stream().findFirst().orElse(null);
    }

    @Override
    public List<SysDept> getDeptList() {
        LambdaQueryWrapper<SysDept> queryWrapper = new QueryWrapper<SysDept>().lambda();
        List<SysDept> sysDeptList = sysDeptService.list(queryWrapper);
        return sysDeptList;
    }

    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> sysDeptList) {
        List<SysDept> deptTrees = buildTree(sysDeptList);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    private List<SysDept> buildTree(List<SysDept> depts) {
        List<SysDept> returnList = new ArrayList<>();
        Set<Long> tempSet = depts.stream().map(SysDept::getDeptId).collect(Collectors.toSet());
        for (SysDept dept : depts) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempSet.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t) {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        List<SysDept> tlist = new ArrayList<>();
        for (SysDept n : list) {
            if (null != n.getParentId() && n.getParentId().longValue() == t.getDeptId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<SysDeptInfoVo> entityToVo(List<SysDept> records) {
        return records.stream()
                .map(sysDept -> {
                    SysDeptInfoVo sysDeptInfoVo = new SysDeptInfoVo();
                    BeanUtils.copyProperties(sysDept, sysDeptInfoVo);
                    return sysDeptInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeSysDeptParam
     */
    private void checkParam(AeSysDeptParam aeSysDeptParam) {
        if (aeSysDeptParam.getParentId().equals(aeSysDeptParam.getDeptId())) {
            throw new ServiceException("无法选自己为父级");
        }

        Long deptId = aeSysDeptParam.getDeptId();
        Long parentId = aeSysDeptParam.getParentId();
        if (null != aeSysDeptParam.getDeptId()) {
            LambdaQueryWrapper<SysDept> queryWrapper = new QueryWrapper<SysDept>().lambda();
            queryWrapper.select(SysDept::getDeptId)
                    .and(sql -> {
                                // 第一个下级
                                sql.eq(SysDept::getParentId, deptId);
                                SysDept baseProjectCategory = sysDeptService.getById(deptId);
                                if (null != baseProjectCategory) {
                                    // 所有下级
                                    sql.or().likeRight(SysDept::getAncestors, baseProjectCategory.getAncestors() + "," + deptId + ",");
                                }
                            }
                    );
            Set<Long> idSet = sysDeptService.list(queryWrapper)
                    .stream()
                    .map(SysDept::getDeptId)
                    .collect(Collectors.toSet());
            if (idSet.contains(parentId)) {
                throw new ServiceException("不能把下级转成自己上级");
            }
        }

        String ancestors = "0";
        if (0 != aeSysDeptParam.getParentId()) {
            SysDept parentSysDept = sysDeptService.getById(aeSysDeptParam.getParentId());
            if (null == parentSysDept) {
                throw new ServiceException("父级不存在");
            }
            ancestors = parentSysDept.getAncestors();
        }
        aeSysDeptParam.setAncestors(ancestors);
    }
}