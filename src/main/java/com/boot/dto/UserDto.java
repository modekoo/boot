package com.boot.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
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
    
}
