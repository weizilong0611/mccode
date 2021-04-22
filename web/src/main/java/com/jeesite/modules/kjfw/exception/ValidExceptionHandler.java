package com.jeesite.modules.kjfw.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
* @Author Tony
*  2021年4月14日 上午10:39:16
*  Never give up
*/
@Slf4j
@RestControllerAdvice(basePackageClasses=com.jeesite.modules.kjfw.rest.KjfwCommonsApi.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidExceptionHandler {
	
	/**
	 * 对运行时异常进行统一管理
	 */
	/**
     * 对方法参数校验异常处理方法(仅对于表单提交有效，对于以json格式提交将会失效)
     * 如果是表单类型的提交，则spring会采用表单数据的处理类进行处理（进行参数校验错误时会抛出BindException异常）
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, Object>> handlerBindException(BindException exception) {
        return handlerNotValidException(exception);
    }
 
    /**
     * 对方法参数校验异常处理方法(前端提交的方式为json格式出现异常时会被该异常类处理)
     * json格式提交时，spring会采用json数据的数据转换器进行处理（进行参数校验时错误是抛出MethodArgumentNotValidException异常）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handlerArgumentNotValidException(MethodArgumentNotValidException exception) {
        return handlerNotValidException(exception);
    }
 
    //响应返回异常字段及提示信息  用来捕获无法到达controller层的异常
    public ResponseEntity<Map<String, Object>> handlerNotValidException(Exception e) {
        BindingResult result;
        if (e instanceof BindException) {
            BindException exception = (BindException) e;
            result = exception.getBindingResult();
        } else {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            result = exception.getBindingResult();
        }
        Map<String, Object> maps = new HashMap();
        Map<String, Object> params = new HashMap();
        Map<String, Object> paramlist;
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            paramlist = new HashMap<>(fieldErrors.size());
            fieldErrors.forEach(error -> {
            	paramlist.put(error.getField(), error.getDefaultMessage());
            });
        } else {
        	paramlist = Collections.EMPTY_MAP;
        }
        params.put("list", paramlist);
        params.put("code",HttpStatus.SC_BAD_REQUEST);
        maps.put("data",params);
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(maps);
    }
    
}