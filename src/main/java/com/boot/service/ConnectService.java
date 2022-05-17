package com.boot.service;

import java.io.IOException;

import com.boot.common.ResultData;

public interface ConnectService {
	ResultData sendPost(String uri, String bodyStr) throws IOException, InterruptedException;
}
