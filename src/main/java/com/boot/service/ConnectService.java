package com.boot.service;

import java.io.IOException;

import com.boot.dto.ResultDto;

public interface ConnectService {
	ResultDto sendPost(String uri, String bodyStr) throws IOException, InterruptedException;
}
