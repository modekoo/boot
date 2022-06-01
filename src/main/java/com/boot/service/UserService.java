package com.boot.service;

import com.boot.common.ResultData;
import com.boot.dao.UserDao;

public interface UserService {

    //유저 등록
    ResultData insertUser(UserDao user);
    
    //유저 로그인
    ResultData loginUser(UserDao user);
    
    //유저 정보 확인
    ResultData infoUser(String token);
}
