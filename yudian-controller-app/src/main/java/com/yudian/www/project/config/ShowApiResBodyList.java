package com.yudian.www.project.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * {
 * "mainLowPrice": 384,
 * "code": "au9999",
 * "mainStatus": "on",
 * "mainRaF": -0.19886869,
 * "mainHighPrice": 388.5,
 * "mainClosePrice": 387.2,
 * "mainOpenPrice": 387,
 * "mainRaFValue": -0.77001953,
 * "mainTime": "2022-08-19 14:09:56",
 * "mainSellPrice": 386.43,
 * "mainLatestPrice": 386.43,
 * "mainBuyPrice": 386.4
 * }
 */
@Data
public class ShowApiResBodyList {
    // 最低价
    @ApiModelProperty(value = "最低价")
    private Double mainLowPrice;
    @ApiModelProperty(value = "金属名称")
    private String name;
    @ApiModelProperty(value = "金属编码")
    private String code;
    // 市场状态：on表示开市，off表示闭市
    @ApiModelProperty(value = "市场状态：on表示开市，off表示闭市")
    private String mainStatus;
    // 涨跌幅
    @ApiModelProperty(value = "涨跌幅")
    private Double mainRaF;
    // 最高价
    @ApiModelProperty(value = "最高价")
    private Double mainHighPrice;
    // 收盘价
    @ApiModelProperty(value = "收盘价")
    private Double mainClosePrice;
    // 开盘价
    @ApiModelProperty(value = "开盘价")
    private Double mainOpenPrice;
    // 涨跌值
    @ApiModelProperty(value = "涨跌值")
    private Double mainRaFValue;
    // 时间
    @ApiModelProperty(value = "时间")
    private String mainTime;
    // 卖
    @ApiModelProperty(value = "卖")
    private Double mainSellPrice;
    // 最新价格
    @ApiModelProperty(value = "最新价格")
    private Double mainLatestPrice;
    // 买
    @ApiModelProperty(value = "买")
    private Double mainBuyPrice;
}
