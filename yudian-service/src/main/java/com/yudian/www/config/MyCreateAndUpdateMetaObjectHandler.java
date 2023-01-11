package com.yudian.www.config;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.www.base.BaseEntity;
import com.yudian.www.config.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;


@Slf4j
public class MyCreateAndUpdateMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
            if (null != baseEntity.getCreateBy()) {
                return;
            }
            if (!StpUtil.isLogin()) {
                return;
            }
            Long userId = LoginUtils.getUserId();
            if (null == userId) {
                throw new ServiceException("未能获取用户信息");
            }

            if (null == baseEntity.getCreateBy()) {
                baseEntity.setCreateBy(userId);
            }

            if (null == baseEntity.getLastUpdateBy()) {
                baseEntity.setLastUpdateBy(userId);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
            Long userId = LoginUtils.getUserId();
            if (null == userId) {
                throw new ServiceException("未能获取用户信息");
            }

            baseEntity.setLastUpdateBy(userId);
        }
    }
}
