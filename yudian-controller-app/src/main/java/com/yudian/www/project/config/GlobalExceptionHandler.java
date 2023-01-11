package com.yudian.www.project.config;

import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.yudian.common.entity.R;
import com.yudian.www.config.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public R handleException(HttpRequestMethodNotSupportedException e) {
        return R.error(null, "不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 用来拦截valid的校验
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R myMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return R.error(null, Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 参数转换错误
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R myHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        logger.error("参数转换错误：" + exception.getMessage());
        return R.error(null, "参数错误");
    }

    /**
     * 对方法参数校验异常处理方法(仅对于表单提交有效，对于以json格式提交将会失效)
     * 如果是表单类型的提交，则spring会采用表单数据的处理类进行处理（进行参数校验错误时会抛出BindException异常）
     */
    @ExceptionHandler(BindException.class)
    public R handlerBindException(BindException exception) {
        return R.error(null, Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 自定义的ServiceException
     */
    @ExceptionHandler(ServiceException.class)
    public R customException(ServiceException e) {
        return R.error(e.getData(), e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R notFount(RuntimeException e) {
        logger.error("运行时异常:", e);
        return R.error(null, "服务器网络拥堵，请稍后再试");
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return R.error(null, "服务器网络拥堵，请稍后再试");
    }


    // -----------------Sa-Token-----------------

    /**
     * 未登录异常
     */
    @ExceptionHandler(NotLoginException.class)
    public R handleException(NotLoginException e) {
        String type = e.getType();
        Integer statusCode = 50000;
        String message = "";
        if ("-1".equals(type)) {
            message = "未提供Token";
        } else if ("-2".equals(type)) {
            message = "Token无效";
            statusCode = 40001;
        } else if ("-3".equals(type)) {
            message = "登录已过期";
            statusCode = 40001;
        } else if ("-4".equals(type) || e.getMessage().contains("Token已被顶下线")) {
            message = "账号已被顶下线";
            statusCode = 40002;
        } else if ("-5".equals(type)) {
            message = "账号已被踢下线";
            statusCode = 40003;
        } else {
            message = "当前会话未登录";
        }
        return R.error(null, message, statusCode);
    }

    /**
     * 角色异常
     */
    @ExceptionHandler(NotRoleException.class)
    public R handleException(NotRoleException e) {
        return R.error(null, "无此角色：" + e.getRole());
    }

    /**
     * 权限异常
     */
    @ExceptionHandler(NotPermissionException.class)
    public R handleException(NotPermissionException e) {
        return R.error(null, "无此权限：" + e.getCode());
    }

    /**
     * 被封禁异常
     */
    @ExceptionHandler(DisableServiceException.class)
    public R handleException(DisableServiceException e) {
        return R.error(null, "账号被封禁：" + e.getDisableTime() + "秒后解封");
    }

}
