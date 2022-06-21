package com.boot.service;

import java.security.NoSuchAlgorithmException;

import com.boot.dto.UserDto;

public interface JWTService {

    String createToken(UserDto user);
 
    byte[] generateKey();
    
    boolean verify(String jwt) throws Exception;
    
    String sha256(String pass) throws NoSuchAlgorithmException;
    
    String getUserId(String jwt);
}
