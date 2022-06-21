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

import com.boot.dto.ResultDto;
import com.boot.dto.UserDto;
import com.boot.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController {
 
	@Autowired
	UserService userService;
	
	@ApiOperation(value = "회원가입")
	@RequestMapping(value = "/signup", method = {RequestMethod.POST})
    public ResponseEntity<ResultDto> insertUser(@RequestBody UserDto userDto, HttpServletResponse response, HttpServletRequest request) throws NoSuchAlgorithmException {
    	
    	ResultDto ResultDto = userService.insertUser(userDto);
    	
    	return ResponseEntity.ok().body(ResultDto);
    }
    
	@ApiOperation(value = "로그인")
	@RequestMapping(value = "/login", method = {RequestMethod.POST})
    public ResponseEntity<ResultDto> loginUser(@RequestBody UserDto userDto, HttpServletResponse response, HttpServletRequest request) throws NoSuchAlgorithmException {
    	
		ResultDto ResultDto = userService.loginUser(userDto);
    	
		return ResponseEntity.ok().body(ResultDto);
    }
	
	@ApiOperation(value = "내 정보 보기")
	@RequestMapping(value = "/info", method = {RequestMethod.GET})
    public ResponseEntity<ResultDto> selectMyInfo(@RequestHeader("Authorization") String auth, HttpServletResponse response, HttpServletRequest request) throws NoSuchAlgorithmException {
    	
		ResultDto ResultDto = userService.infoUser(auth);
    	
		return ResponseEntity.ok().body(ResultDto);
    }
	
}