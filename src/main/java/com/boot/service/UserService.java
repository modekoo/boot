package com.boot.service;

import com.boot.common.ResultData;
import com.boot.entity.User;

public interface UserService {

    //유저 등록
    ResultData insertUser(User user);
    
    //유저 로그인
    ResultData loginUser(User user);
    
    //유저 정보 확인
    ResultData infoUser(String token);
}
