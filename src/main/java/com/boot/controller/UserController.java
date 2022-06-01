package com.boot.controller;
  
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boot.common.ResultData;
import com.boot.dao.UserDao;
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
    public ResponseEntity<ResultData> insertUser(@RequestBody UserDao userDao, HttpServletResponse response, HttpServletRequest request) throws NoSuchAlgorithmException {
    	
    	ResultData resultData = userService.insertUser(userDao);
    	
    	return ResponseEntity.ok().body(resultData);
    }
    
	@ApiOperation(value = "로그인")
	@RequestMapping(value = "/login", method = {RequestMethod.POST})
    public ResponseEntity<ResultData> loginUser(@RequestBody UserDao userDao, HttpServletResponse response, HttpServletRequest request) throws NoSuchAlgorithmException {
    	
		ResultData resultData = userService.loginUser(userDao);
    	
		return ResponseEntity.ok().body(resultData);
    }
	
	@ApiOperation(value = "내 정보 보기")
	@RequestMapping(value = "/info", method = {RequestMethod.GET})
    public ResponseEntity<ResultData> selectMyInfo(@RequestHeader("Authorization") String auth, HttpServletResponse response, HttpServletRequest request) throws NoSuchAlgorithmException {
    	
		ResultData resultData = userService.infoUser(auth);
    	
		return ResponseEntity.ok().body(resultData);
    }
	
}