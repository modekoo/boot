package com.boot.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserDto {

    private String userId;
	
    private String password;
    
	private String name;
    
	private String regNo;
	
	@Builder
    public UserDto(String userId, String password, String name, String regNo) {
    	this.userId = userId;
    	this.password = password;
    	this.name = name;
    	this.regNo = regNo;
    }
	
	@Builder(builderMethodName = "userInfoBuilder", builderClassName = "userInfoBuilder")
    public UserDto(String userId, String name, String regNo) {
    	this.userId = userId;
    	this.name = name;
    	this.regNo = regNo;
    }
    
}
