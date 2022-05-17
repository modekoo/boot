package com.boot.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.boot.common.ResultData;
import com.boot.service.ConnectService;
import com.google.gson.Gson;

@Service("ConnectService")
public class ConnectServiceImpl implements ConnectService {
	public ResultData sendPost(String  uri, String bodyStr) throws IOException, InterruptedException {
		
		ResultData resultData = new ResultData();
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create(uri))
				.header("Content-Type", "application/json")
				.POST(BodyPublishers.ofString(bodyStr))
				.build();

		HttpResponse<?> response = client.send(request, BodyHandlers.ofString());
		
		resultData.setResultCode(response.statusCode());
		
		if(response.body() != null) {
			Gson gson = new Gson();
			HashMap<String, Object> resultMap = new HashMap<>();
			resultMap.put("resultData", gson.fromJson((String) response.body(), Map.class) );
			resultData.setResultData(resultMap);
		}
		
		return resultData;
	}
}
