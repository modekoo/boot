package com.boot.common;

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ApiResponse
public class ResultData {
	int resultCode = 200;
	String resultMsg = "";
	Map<String, Object> resultData = new HashMap<>();
	
	public ResultData() {}
	
}
