package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.www.base.LinkTableUtils;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.sys.*;
import com.yudian.www.service.sys.*;
import com.yudian.www.service.sys.param.AeMerchantParam;
import com.yudian.www.service.sys.param.AeSysUserParam;
import com.yudian.www.service.sys.param.GetSysUserListParam;
import com.yudian.www.service.sys.vo.SysMerchantInfoVo;
import com.yudian.www.service.sys.vo.SysUserInfoVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户信息表 服务实现类-流程
 *

 * @since 2022-01-06
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImplProcess implements ISysUserServiceProcess {

    private final ISysUserService sysUserService;
    private final ISysRoleService sysRoleService;
    private final ISysPostService sysPostService;
    private final ISysDeptService sysDeptService;
    private final ISysUserRoleService sysUserRoleService;
    private final ISysUserPostService sysUserPostService;
    private final LinkTableUtils linkTableUtils;

    /**
     * 添加
     *
     * @param aeSysUserParam
     */
    @Override
    public void sysUserAdd(AeSysUserParam aeSysUserParam) {
        aeSysUserParam.initParam();
        this.checkParam(aeSysUserParam);
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(aeSysUserParam, sysUser);
        sysUser.setUserType(1);

        boolean save = sysUserService.save(sysUser);
        if (!save) {
            throw new ServiceException("保存失败");
        }

        saveBathUserRole(aeSysUserParam.getRoleIds(), sysUser);
        saveBathUserPost(aeSysUserParam.getPostIds(), sysUser);
    }

    /**
     * 批量保存用户和岗位关系记录
     *
     * @param postIds
     * @param sysUser
     */
    private void saveBathUserPost(List<Long> postIds, SysUser sysUser) {
        List<SysUserPost> sysUserPosts = postIds
                .stream()
                .map(postId -> {
                    SysUserPost sysUserPost = new SysUserPost();
                    sysUserPost.setUserId(sysUser.getUserId());
                    sysUserPost.setPostId(postId);
                    return sysUserPost;
                }).collect(Collectors.toList());
        if (0 != sysUserPosts.size()) {
            sysUserPostService.saveBatch(sysUserPosts);
        }
    }

    /**
     * 批量保存用户和角色关系记录
     *
     * @param roleIds
     * @param sysUser
     */
    private void saveBathUserRole(Set<Long> roleIds, SysUser sysUser) {
        List<SysUserRole> sysUserRoles = roleIds
                .stream()
                .map(roleId -> {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(sysUser.getUserId());
                    sysUserRole.setRoleId(roleId);
                    return sysUserRole;
                }).collect(Collectors.toList());
        if (0 != sysUserRoles.size()) {
            sysUserRoleService.saveBatch(sysUserRoles);
        }
    }

    @Override
    public void sysUserAddBatch(List<AeSysUserParam> aeSysUserParamList) {
        List<SysUser> sysUserList = aeSysUserParamList.stream()
                .map(aeSysUserParam -> {
                    SysUser sysUser = new SysUser();
                    BeanUtils.copyProperties(aeSysUserParam, sysUser);
                    return sysUser;
                }).collect(Collectors.toList());
        boolean save = sysUserService.saveBatch(sysUserList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeSysUserParam
     */
    @Override
    public void sysUserEdit(AeSysUserParam aeSysUserParam) {
        aeSysUserParam.initParam();
        this.checkParam(aeSysUserParam);
        SysUser sysUser = sysUserService.getById(aeSysUserParam.getUserId());
        if (null == sysUser) {
            throw new ServiceException("记录不存在");
        }
        String password = sysUser.getPassword();
        Integer userType = sysUser.getUserType();
        BeanUtils.copyProperties(aeSysUserParam, sysUser);
        sysUser.setPassword(password);
        sysUser.setUserType(userType);
        boolean update = sysUserService.updateById(sysUser);
        if (!update) {
            throw new ServiceException("编辑失败");
        }

        // 先删除原先绑定的角色id
        sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getUserId()));
        saveBathUserRole(aeSysUserParam.getRoleIds(), sysUser);

        sysUserPostService.remove(Wrappers.<SysUserPost>lambdaQuery().eq(SysUserPost::getUserId, sysUser.getUserId()));
        saveBathUserPost(aeSysUserParam.getPostIds(), sysUser);
    }


    /**
     * 删除
     *
     * @param userType 账户类型；1=系统账号；2=商家账号
     * @param userIds
     */
    @Override
    public void sysUserRemove(Integer userType, Long[] userIds) {
        Long loginUserId = LoginUtils.getUserId();
        Set<Long> userIdSet = Stream.of(userIds).filter(Objects::nonNull).collect(Collectors.toSet());
        if (userIdSet.isEmpty()) {
            return;
        }
        if (userIdSet.contains(1L)) {
            SysUser sysUser = sysUserService.getById(1);
            if (null != sysUser) {
                throw new ServiceException("无法删除初始账号：" + sysUser.getUserName());
            }
        }
        if (userIdSet.contains(loginUserId)) {
            SysUser sysUser = sysUserService.getById(loginUserId);
            if (null != sysUser) {
                throw new ServiceException("无法删除自己：" + sysUser.getUserName());
            }
        }
        boolean remove = sysUserService.remove(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUserType, userType)
                .in(SysUser::getUserId, Arrays.asList(userIds)));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getSysUserListParam
     * @return
     */
    @Override
    public TableRecordVo getSysUserList(Integer userType, GetSysUserListParam getSysUserListParam) {
        String roleId = getSysUserListParam.getRoleId();
        Set<Long> userIdSet = new HashSet<>();
        if (StringUtils.isNotBlank(roleId)) {
            userIdSet.addAll(sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery()
                    .eq(SysUserRole::getRoleId, roleId))
                    .stream()
                    .map(SysUserRole::getUserId)
                    .collect(Collectors.toSet()));
        }

        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        queryWrapper
                .in(!userIdSet.isEmpty(), SysUser::getUserId, userIdSet)
                .eq(null != getSysUserListParam.getDeptId() && 1 != getSysUserListParam.getDeptId(), SysUser::getDeptId, getSysUserListParam.getDeptId())
                .eq(null != userType, SysUser::getUserType, userType)
                .eq(null != getSysUserListParam.getUserStatus(), SysUser::getUserStatus, getSysUserListParam.getUserStatus())
                .like(StringUtils.isNotBlank(getSysUserListParam.getUserName()), SysUser::getUserName, getSysUserListParam.getUserName())
                .like(StringUtils.isNotBlank(getSysUserListParam.getUserNickname()), SysUser::getUserNickname, getSysUserListParam.getUserNickname())
                .like(StringUtils.isNotBlank(getSysUserListParam.getUserAddress()), SysUser::getUserAddress, getSysUserListParam.getUserAddress())
                .like(StringUtils.isNotBlank(getSysUserListParam.getUserEmail()), SysUser::getUserEmail, getSysUserListParam.getUserEmail())
                .like(StringUtils.isNotBlank(getSysUserListParam.getUserTel()), SysUser::getUserTel, getSysUserListParam.getUserTel())
                .ge(null != getSysUserListParam.getStartDateTime(), SysUser::getCreateDatetime, getSysUserListParam.getStartDateTime())
                .le(null != getSysUserListParam.getEndDateTime(), SysUser::getCreateDatetime, getSysUserListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getSysUserListParam.getOrderByColumn()) && StringUtils.isNotBlank(getSysUserListParam.getIsAsc())) {
            queryWrapper.last("order by " + getSysUserListParam.getOrderBy());
        }

        IPage<SysUser> page = sysUserService.page(new Page<>(getSysUserListParam.getPageNo(), getSysUserListParam.getPageSize()), queryWrapper);

        List<SysUser> records = page.getRecords();

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(this.entityToVo(userType, records));
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
    public Object getSysUserDetail(Integer userType, Long userId) {
        SysUser sysUser = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUserType, userType)
                .eq(SysUser::getUserId, userId));
        if (null == sysUser) {
            throw new ServiceException("记录不存在");
        }
        List sysUserInfoVos = this.entityToVo(userType, Arrays.asList(sysUser));
        return sysUserInfoVos.stream().findFirst().orElse(null);
    }

    @Override
    public void resetUserPwd(Integer userType, Long userId, String password) {
        SysUser sysUser = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUserType, userType)
                .eq(SysUser::getUserId, userId));
        if (null == sysUser) {
            throw new ServiceException("记录不存在");
        }
        if (sysUser.getPassword().equals(password)) {
            return;
        }
        sysUserService.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getPassword, password)
                .eq(SysUser::getUserId, userId));
    }


    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    @Override
    public List entityToVo(Integer userType, List<SysUser> records) {
        if (records.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Long, Set<Long>> userRoleIdSet = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery()
                .select(SysUserRole::getRoleId, SysUserRole::getUserId)
                .in(SysUserRole::getUserId, records.stream().map(SysUser::getUserId).collect(Collectors.toSet())))
                .stream()
                .collect(Collectors.groupingBy(SysUserRole::getUserId,
                        Collectors.collectingAndThen(Collectors.toList(), sysUserRoles -> sysUserRoles
                                .stream()
                                .map(SysUserRole::getRoleId)
                                .collect(Collectors.toSet())
                        )
                ));

        Map<Long, String> roleNameMap = new HashMap<>();
        if (!userRoleIdSet.values().isEmpty()) {
            roleNameMap.putAll(sysRoleService.listByIds(userRoleIdSet.values().stream().flatMap(Collection::stream).collect(Collectors.toList()))
                    .stream()
                    .collect(Collectors.toMap(SysRole::getRoleId, SysRole::getRoleName)));
        }

        Map<Long, Set<Long>> userPostIdSet = sysUserPostService.list(Wrappers.<SysUserPost>lambdaQuery()
                .select(SysUserPost::getPostId, SysUserPost::getUserId)
                .in(SysUserPost::getUserId, records.stream().map(SysUser::getUserId).collect(Collectors.toSet())))
                .stream()
                .collect(Collectors.groupingBy(SysUserPost::getUserId,
                        Collectors.collectingAndThen(Collectors.toList(), sysUserPosts -> sysUserPosts
                                .stream()
                                .map(SysUserPost::getPostId)
                                .collect(Collectors.toSet())
                        )
                ));

        Map<Long, String> postNameMap = new HashMap<>();
        if (!userPostIdSet.values().isEmpty()) {
            postNameMap.putAll(sysPostService.listByIds(userPostIdSet.values().stream().flatMap(Collection::stream).collect(Collectors.toList()))
                    .stream()
                    .collect(Collectors.toMap(SysPost::getPostId, SysPost::getPostName)));
        }

        Set<Long> deptIdSet = records.stream().map(SysUser::getDeptId).collect(Collectors.toSet());
        Map<Long, String> deptNameMap = new HashMap<>();
        if (!deptIdSet.isEmpty()) {
            deptNameMap.putAll(sysDeptService.listByIds(deptIdSet)
                    .stream()
                    .collect(Collectors.toMap(SysDept::getDeptId, SysDept::getDeptName)));
        }

        List<Long> userIds = records.stream().map(SysUser::getUserId).collect(Collectors.toList());

        HashSet<Long> roleIds = new HashSet<>();
        HashSet<Long> postIds = new HashSet<>();
        if (1 == userType) {
            return records.stream()
                    .map(sysUser -> {
                        SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
                        BeanUtils.copyProperties(sysUser, sysUserInfoVo);
                        Set<Long> myRoleIds = userRoleIdSet.getOrDefault(sysUser.getUserId(), roleIds);
                        sysUserInfoVo.setRoleIds(myRoleIds);
                        if (!myRoleIds.isEmpty()) {
                            sysUserInfoVo.setRoleNames(myRoleIds.stream().map(roleNameMap::get).collect(Collectors.toList()));
                        }

                        Set<Long> myPostIds = userPostIdSet.getOrDefault(sysUser.getUserId(), postIds);
                        sysUserInfoVo.setPostIds(myPostIds);
                        if (!myPostIds.isEmpty()) {
                            sysUserInfoVo.setPostNames(myPostIds.stream().map(postNameMap::get).collect(Collectors.toList()));
                        }

                        sysUserInfoVo.setDeptName(deptNameMap.get(sysUser.getDeptId()));
                        return sysUserInfoVo;
                    }).collect(Collectors.toList());
        } else if (2 == userType) {
            return records.stream()
                    .map(sysUser -> {
                        SysMerchantInfoVo sysMerchantInfoVo = new SysMerchantInfoVo();
                        BeanUtils.copyProperties(sysUser, sysMerchantInfoVo);
                        Set<Long> myRoleIds = userRoleIdSet.getOrDefault(sysUser.getUserId(), roleIds);
                        sysMerchantInfoVo.setRoleIds(myRoleIds);
                        if (!myRoleIds.isEmpty()) {
                            sysMerchantInfoVo.setRoleNames(myRoleIds.stream().map(roleNameMap::get).collect(Collectors.toList()));
                        }

                        return sysMerchantInfoVo;
                    }).collect(Collectors.toList());
        }
        return new ArrayList();
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeSysUserParam
     */
    private void checkParam(AeSysUserParam aeSysUserParam) {
        SysUser userNameSysUser = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery()
                .ne(null != aeSysUserParam.getUserId(), SysUser::getUserId, aeSysUserParam.getUserId())
                .eq(SysUser::getUserName, aeSysUserParam.getUserName()));

        if (null != userNameSysUser && userNameSysUser.getUserName().equals(aeSysUserParam.getUserName())) {
            throw new ServiceException("登录账号已存在，请修改");
        }

        if (null != aeSysUserParam.getRoleIds() && 0 != aeSysUserParam.getRoleIds().size()) {
            List<SysRole> sysRoles = sysRoleService.listByIds(aeSysUserParam.getRoleIds());
            if (sysRoles.size() != aeSysUserParam.getRoleIds().size()) {
                throw new ServiceException("绑定角色不存在，请修改");
            }
        }
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeMerchantParam
     */
    private void checkParam(AeMerchantParam aeMerchantParam) {
        SysUser userNameSysUser = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery()
                .ne(null != aeMerchantParam.getUserId(), SysUser::getUserId, aeMerchantParam.getUserId())
                .eq(SysUser::getUserName, aeMerchantParam.getUserName()));

        if (null != userNameSysUser && userNameSysUser.getUserName().equals(aeMerchantParam.getUserName())) {
            throw new ServiceException("登录账号已存在，请修改");
        }

        if (null != aeMerchantParam.getRoleIds() && 0 != aeMerchantParam.getRoleIds().size()) {
            List<SysRole> sysRoles = sysRoleService.listByIds(aeMerchantParam.getRoleIds());
            if (sysRoles.size() != aeMerchantParam.getRoleIds().size()) {
                throw new ServiceException("绑定角色不存在，请修改");
            }
        }
    }
}