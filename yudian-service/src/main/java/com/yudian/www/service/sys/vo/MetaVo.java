package com.yudian.www.service.sys.vo;

import cn.hutool.core.lang.Validator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("路由显示信息")
public class MetaVo {

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    @ApiModelProperty(value = "设置该路由在侧边栏和面包屑中展示的名字")
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    @ApiModelProperty(value = "设置该路由的图标，对应路径src/assets/icons/svg")
    private String icon;

    /**
     * 设置为true，则不会被 <keep-alive>缓存
     */
    @ApiModelProperty(value = "设置为true，则不会被 <keep-alive>缓存")
    private boolean noCache;

    /**
     * 内链地址（http(s)://开头）
     */
    @ApiModelProperty(value = "内链地址（http(s)://开头）")
    private String link;

    public MetaVo(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public MetaVo(String title, String icon, boolean noCache) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
    }

    public MetaVo(String title, String icon, String link) {
        this.title = title;
        this.icon = icon;
        this.link = link;
    }

    public MetaVo(String title, String icon, boolean noCache, String link) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
        if (Validator.isUrl(link)) {
            this.link = link;
        }
    }

}
