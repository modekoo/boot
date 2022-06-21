package com.boot.dto;

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@ApiResponse
public class ResultDto {
	int resultCode = 200;
	String resultMsg = "";
	Map<String, Object> resultData = new HashMap<>();
}
