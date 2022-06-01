package com.boot.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.common.ResultData;
import com.boot.dao.UserDao;
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
    public ResultData insertUser(UserDao userDao) {
    	
    	ResultData result = new ResultData();
    	
    	User user = User.builder().userId(userDao.getUserId()).password(userDao.getPassword()).regNo(userDao.getRegNo()).name(userDao.getName()).build();
    	
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
    
    private ResultData validationInserUser(UserDao user) {
    	ResultData resultData = new ResultData();
		boolean flag = false;
    	
    	if(user == null || user.getUserId() == null || user.getPassword() == null || user.getRegNo() == null) {
    		resultData.setResultCode(400);
    		resultData.setResultMsg("input values");
    		return resultData;
    	}
    	
		if(!flag) {
			resultData.setResultCode(400);
    		resultData.setResultMsg("not matched valid user");
    		return resultData;
		}
    	
    	return resultData;
    }
    
    //유저 로그인
    public ResultData loginUser(UserDao userDao) {
    	
    	ResultData result = new ResultData();
    	
    	if(userDao == null || userDao.getUserId() == null || userDao.getPassword() == null) {
    		result.setResultMsg("input user, password");
    		result.setResultCode(400);
    		return result;
    	}
    	User user = User.builder().userId(userDao.getUserId()).password(userDao.getPassword()).regNo(userDao.getRegNo()).name(userDao.getName()).build();
    	
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
    public ResultData infoUser(String token){
    	
    	ResultData result = new ResultData();
    	
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
    	
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	resultMap.put("userInfo", userResult);
    	result.setResultData(resultMap);
    	
        return result;
    }
    
}
