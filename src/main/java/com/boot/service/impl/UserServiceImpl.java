package com.boot.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.dto.ResultDto;
import com.boot.dto.UserDto;
import com.boot.entity.User;
import com.boot.repository.custom.CustomUserRepository;
import com.boot.service.JWTService;
import com.boot.service.UserService;

@Service("UserService")
public class UserServiceImpl implements UserService {
	
    
	@Autowired
	CustomUserRepository customUserRepository;
    
    @Autowired
    private JWTService jwtService;
    
    //유저 등록
    public ResultDto insertUser(UserDto userDto) {
    	
    	ResultDto result = new ResultDto();
    	
    	User user = User.builder().userId(userDto.getUserId()).password(userDto.getPassword()).regNo(userDto.getRegNo()).name(userDto.getName()).build();
    	
    	User userResult = customUserRepository.findByUserIdAndPassword(user.getUserId(), user.getPassword());
    	
    	if(userResult != null) {
    		result.setResultMsg("aleady join us");
    		result.setResultCode(400);
    		return result;
    	}
    	
    	userResult = customUserRepository.save(user);
    			
    	if(userResult == null || !user.getUserId().equals(userResult.getUserId())) {
    		result.setResultMsg("insert user fail");
    		result.setResultCode(500);
    	}
    	
        return result;
    }
    
    private ResultDto validationInserUser(UserDto user) {
    	ResultDto ResultDto = new ResultDto();
		boolean flag = false;
    	
    	if(user == null || user.getUserId() == null || user.getPassword() == null || user.getRegNo() == null) {
    		ResultDto.setResultCode(400);
    		ResultDto.setResultMsg("input values");
    		return ResultDto;
    	}
    	
		if(!flag) {
			ResultDto.setResultCode(400);
    		ResultDto.setResultMsg("not matched valid user");
    		return ResultDto;
		}
    	
    	return ResultDto;
    }
    
    //유저 로그인
    public ResultDto loginUser(UserDto userDto) {
    	
    	ResultDto result = new ResultDto();
    	
    	if(userDto == null || userDto.getUserId() == null || userDto.getPassword() == null) {
    		result.setResultMsg("input user, password");
    		result.setResultCode(400);
    		return result;
    	}
    	User user = User.builder().userId(userDto.getUserId()).password(userDto.getPassword()).regNo(userDto.getRegNo()).name(userDto.getName()).build();
    	
    	User userResult = customUserRepository.findByUserIdAndPassword(user.getUserId(), user.getPassword());
    	
    	if(userResult == null || !user.getUserId().equals(userResult.getUserId())) {
    		result.setResultMsg("not found user");
    		result.setResultCode(400);
    		return result;
    	}
    	
    	String token = jwtService.createToken(userResult);
    	
    	if(token != null) {
    		HashMap<String, Object> returnMap = new HashMap<>();
    		returnMap.put("Authorization", token);
    		result.setResultData(returnMap);
    	}
    		
        return result;
    }
    
    //유저 정보 확인
    public ResultDto infoUser(String token){
    	
    	ResultDto result = new ResultDto();
    	
    	if(token == null || token.equals("")) {
    		result.setResultCode(400);
    		result.setResultMsg("not found token");
    	}
    	
    	String userId = jwtService.getUserId(token);
    	
    	if(userId == null || userId.equals("")) {
    		result.setResultCode(400);
    		result.setResultMsg("not match token userId");
    	}
    	
    	User user = User.builder().userId(userId).build();
    	User userResult = customUserRepository.findByUserId(user.getUserId());
    	
    	if(userResult == null || !user.getUserId().equals(userResult.getUserId())) {
    		result.setResultMsg("not found user");
    		result.setResultCode(400);
    		return result;
    	}
    	
    	UserDto userDto = UserDto.builder().userId(userResult.getUserId()).name(userResult.getName()).regNo(userResult.getRegNo()).build();
    	
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	resultMap.put("userInfo", userDto);
    	result.setResultData(resultMap);
    	
        return result;
    }
    
}
