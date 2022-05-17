package com.boot.controller;
  
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.boot.common.ResultData;
import com.boot.entity.User;
import com.boot.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController {
 
	@Autowired
	UserService userService;
	
	@ApiOperation(value = "회원가입")
	@RequestMapping(value = "/signup", method = {RequestMethod.POST})
	@ResponseStatus(code = HttpStatus.OK)
    public ResultData insertUser(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) throws NoSuchAlgorithmException {
    	
    	ResultData resultData = userService.insertUser(user);
    	
    	return resultData;
    }
    
	@ApiOperation(value = "로그인")
	@RequestMapping(value = "/login", method = {RequestMethod.POST})
	@ResponseStatus(code = HttpStatus.OK)
    public ResultData loginUser(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) throws NoSuchAlgorithmException {
    	
		ResultData resultData = userService.loginUser(user);
    	
    	return resultData;
    }
	
	@ApiOperation(value = "내 정보 보기")
	@RequestMapping(value = "/info", method = {RequestMethod.GET})
	@ResponseStatus(code = HttpStatus.OK)
    public ResultData selectMyInfo(@RequestHeader("Authorization") String auth, HttpServletResponse response, HttpServletRequest request) throws NoSuchAlgorithmException {
    	
		ResultData resultData = userService.infoUser(auth);
    	
    	return resultData;
    }
	
}