package com.web.blog.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.UserDto;
import com.web.blog.service.UserService2;

import io.swagger.annotations.ApiOperation;

@RestController
public class AuthController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String DUPLICATE = "duplicate";
	
	@Autowired
	private UserService2 userService;
	
	// 회원가입 시 이메일 인증 보내기
	@ApiOperation(value = "Req.5-1 이메일 인증", response = UserDto.class)
	@GetMapping(value = "/auth")
	public ResponseEntity auth(@RequestParam("email") String email) {
		final String SEND_EMAIL_ID = "kimhyungtaik@gmail.com"; // 관리자 email
		// 이메일 인증
		int random = new Random().nextInt(900000) + 100000;
		String authCode = String.valueOf(random);

		String subject = "회원가입 인증 코드 발급 안내 입니다.";
		StringBuilder sb = new StringBuilder();
		sb.append("귀하의 인증 코드는 " + authCode + "입니다.");

		if (userService.send(subject, sb.toString(), SEND_EMAIL_ID, email, null)) {
			return new ResponseEntity<String>(authCode, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
}
