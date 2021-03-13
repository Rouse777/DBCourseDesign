package com.project.handler;

import com.project.result.Result;
import com.project.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String msg = "";
        BindingResult bindingResult = ex.getBindingResult();
        //组装校验错误信息
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : bindingResult.getFieldErrors()) {
                FieldError errorMessage = (FieldError) error;
                sb.append(errorMessage.getDefaultMessage()).append("; ");
            }
            //返回信息格式处理
            msg = sb.toString();
        }
        Result result = Result.any(ResultCode.ILLEGAL_ARGS, msg);
        return new ResponseEntity<>(result, headers, status);
    }
}
