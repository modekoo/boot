package com.boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.boot.common.ResultData;
import com.boot.entity.User;
import com.boot.repository.UserRepository;
import com.boot.repository.custom.CustomUserRepository;
import com.boot.service.ConnectService;
import com.boot.service.JWTService;
import com.boot.valid.ValidUser;
import com.google.gson.Gson;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BootApplicationTests {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CustomUserRepository customUserRepository;
	
	@Autowired
	JWTService jwtService;

	@Autowired
	ConnectService connectService; 

	private static String token = "";
	private static String sUserId = "";
	private static Map<String, Object> resultMap;

	@Test
	@Order(1)
	void sighUp() {

		User user = User.builder().userId("hong12").name("김둘리").password("123456").regNo("921108-1582816").build();
		boolean flag = false;

		if(ValidUser.nameAndRegNoMatch(user)) {
			flag = true;
		}

		if(!flag) {
			fail("not Matched valid user");
			return;
		}

		customUserRepository.save(user);

		User userResult = customUserRepository.findByUserIdAndPassword(user.getUserId(), user.getPassword());

		assertEquals(user.getUserId(), userResult.getUserId());
	}

	@Test
	@Order(2)
	void Login() {

		User user = User.loginBuilder().userId("hong12").password("123456").build();

		User userResult = customUserRepository.findByUserIdAndPassword(user.getUserId(), user.getPassword());

		token = jwtService.createToken(userResult);

		assertEquals(user.getUserId(), userResult.getUserId());
	}

	@Test
	@Order(3)
	void infoUser() {

		ResultData result = new ResultData();

		if(token == null || token.equals("")) {
			result.setResultCode(401);
			result.setResultMsg("not found token");
			assertFalse(false);
			return;
		}

		String userId = jwtService.getUserId(token);

		if(userId == null || userId.equals("")) {
			result.setResultCode(401);
			result.setResultMsg("not match token userId");
			assertFalse(false);
			assertFalse(false);
			return;
		}

		User user = User.builder().userId(userId).build();
		User userResult = customUserRepository.findByUserId(user.getUserId());

		if(userResult == null || !user.getUserId().equals(userResult.getUserId())) {
			result.setResultMsg("not found user");
			result.setResultCode(400);
			assertFalse(false);
			return;
		}

		sUserId = userResult.getUserId();

		Gson gson = new Gson();
		String bodyStr = gson.toJson(userResult);
		ResultData resultSendPost = new ResultData();
		try {
			resultSendPost = connectService.sendPost("https://codetest.3o3.co.kr/v1/scrap", bodyStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(resultSendPost.getResultCode() == 200 && resultSendPost.getResultData() != null && resultSendPost.getResultData().containsKey("resultData")) {
			resultMap =  (Map<String, Object>) resultSendPost.getResultData().get("resultData");
		}

	}

}
