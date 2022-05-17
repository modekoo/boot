package com.boot.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.boot.common.ResultData;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(Exception.class)
    protected ResponseEntity<ResultData> handleException(Exception e) {

        ResultData response = new ResultData();
        response.setResultCode(500);
        response.setResultMsg(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
