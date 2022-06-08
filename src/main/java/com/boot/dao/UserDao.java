package com.boot.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class UserDao {

    private String userId;
	
    private String password;
    
	private String name;
    
	private String regNo;
	
	@Builder
    public UserDao(String userId, String password, String name, String regNo) {
    	this.userId = userId;
    	this.password = password;
    	this.name = name;
    	this.regNo = regNo;
    }
    
}
