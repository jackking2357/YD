package com.yudian.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "結果返回類")
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "版本", example = "V1.0.0")
    private String version = "V1.0.0";

    @ApiModelProperty(value = "狀態碼", example = "20000")
    private Integer statusCode;

    @ApiModelProperty(value = "狀態信息", example = "")
    private String statusMsg;

    @ApiModelProperty(value = "數據", example = "")
    private T data;

    public R() {
    }

    public R(T data) {
        this.data = data;
    }

    public R(String statusMsg, T data) {
        this.statusMsg = statusMsg;
        this.data = data;
    }

    public R(Integer statusCode, String statusMsg, T data) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.data = data;
    }

    public static <T> R<T> ok() {
        return base(null, 20000, "");
    }

    public static <T> R<T> ok(T data) {
        return base(data, 20000, "");
    }

    public static <T> R<T> ok(T data, String statusMsg) {
        return base(data, 20000, statusMsg);
    }

    public static <T> R<T> error() {
        return base(null, 50000, "");
    }

    public static <T> R<T> error(T data) {
        return base(data, 50000, "");
    }

    public static <T> R<T> error(T data, String statusMsg) {
        return base(data, 50000, statusMsg);
    }

    public static <T> R<T> error(T data, String statusMsg, Integer statusCode) {
        return base(data, statusCode, statusMsg);
    }

    public static <T> R<T> base(T data, Integer statusCode, String statusMsg) {
        return new R<>(statusCode, statusMsg, data);
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return this.statusMsg;
    }

    public R<T> setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
        return this;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "R [statusCode=" + this.statusCode + ", statusMsg=" + this.statusMsg + ", version=" + this.version + ", data=" + this.data + "]";
    }
}
