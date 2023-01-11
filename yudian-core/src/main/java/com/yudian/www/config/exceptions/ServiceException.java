package com.yudian.www.config.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    final Exception ex;

    final String msg;

    final Object data;

    final String statusMsg;

    final Integer statusCode;

    public ServiceException(String msg) {
        super(msg);
        this.ex = null;
        this.msg = msg;
        this.data = null;
        this.statusMsg = "50000";
        this.statusCode = 50000;
    }

    public ServiceException(String msg, Object data) {
        super(msg);
        this.ex = null;
        this.msg = msg;
        this.data = data;
        this.statusMsg = "50000";
        this.statusCode = 50000;
    }

    public ServiceException(String msg, Object data, String statusMsg) {
        super(msg);
        this.ex = null;
        this.msg = msg;
        this.data = data;
        this.statusMsg = statusMsg;
        this.statusCode = 50000;
    }

    public ServiceException(String msg, Object data, String statusMsg, Integer statusCode) {
        super(msg);
        this.ex = null;
        this.msg = msg;
        this.data = data;
        this.statusMsg = statusMsg;
        this.statusCode = statusCode;
    }

    public ServiceException(Exception ex) {
        super(ex);
        this.ex = ex;
        this.msg = ex.getMessage();
        this.data = null;
        this.statusMsg = "50000";
        this.statusCode = 50000;
    }
}
