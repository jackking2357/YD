package com.yudian.www.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据权限类型
 * <p>
 * 语法支持 spel 模板表达式
 * <p>
 * 内置数据 user 当前用户 内容参考 SysUser
 * 如需扩展数据 可使用 {@link DataPermissionHelper} 操作
 */
@Getter
@AllArgsConstructor
public enum DataScopeType {

    /**
     * 全部 数据权限
     */
    ALL("1", "", ""),

    /**
     * 商家 数据权限
     */
    MERCHANT("2", " #{#merchantId} = #{#currMerchantId} ", ""),
    ;

    private final String code;

    /**
     * 语法 采用 spel 模板表达式
     */
    private final String sqlTemplate;

    /**
     * 不满足 sqlTemplate 则填充
     */
    private final String elseSql;

    public static DataScopeType findCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (DataScopeType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
