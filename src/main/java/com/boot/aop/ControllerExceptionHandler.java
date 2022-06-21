package com.boot.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.boot.dto.ResultDto;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(Exception.class)
    protected ResponseEntity<ResultDto> handleException(Exception e) {

        ResultDto response = new ResultDto();
        response.setResultCode(500);
        response.setResultMsg(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
