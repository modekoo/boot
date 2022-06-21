package com.boot.service;

import com.boot.dto.ResultDto;
import com.boot.dto.UserDto;

public interface UserService {

    //유저 등록
	ResultDto insertUser(UserDto user);
    
    //유저 로그인
	ResultDto loginUser(UserDto user);
    
    //유저 정보 확인
	ResultDto infoUser(String token);
}
