package com.web.blog.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.advice.RestException;
import com.web.blog.common.ResponseMessage;
import com.web.blog.common.StatusCode;
import com.web.blog.config.security.JwtTokenProvider;
import com.web.blog.config.security.Users;
import com.web.blog.config.security.UsersRepository;
import com.web.blog.model.DefaultRes;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LoginController {

	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final UsersRepository usersRepository;

	/**
	 * 로그인
	 * 
	 * @param Map<String, String> - email, password
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "로그인", response = ResponseEntity.class)
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody Map<String, String> user, HttpServletResponse response) {
		Users member = usersRepository.findByEmail(user.get("email"))
				.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
		if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
			return new ResponseEntity<>(new DefaultRes(StatusCode.FORBIDDEN, ResponseMessage.LOGIN_FAIL),
					HttpStatus.FORBIDDEN);
		}
		response.setHeader("auth", jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
		Map<String, String> result = new HashMap<String, String>();
		result.put("uid", member.getUid());
		result.put("email", member.getUid());
		return new ResponseEntity<>(new DefaultRes(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, result),
				HttpStatus.OK);
	}
	
	/**
	 * 회원가입
	 * 
	 * @param Map<String, String> - email, uid, password
	 * @return ResponseEntity
	 */
	@PostMapping("/users")
	public String join(@RequestBody Map<String, String> users) {
		System.out.println(users.get("email"));
		System.out.println(usersRepository.findByEmail(users.get("email")));
		if (usersRepository.findByEmail(users.get("email")).isPresent()) {
			throw new IllegalArgumentException("중복된 Email 입니다.");
		}
		return usersRepository.save(Users.builder()
				.email(users.get("email"))
				.uid(users.get("uid"))
				.password(passwordEncoder.encode(users.get("password")))
				.roles(Collections.singletonList("ROLE_USER"))
				.build()).getUid();
	}
	
	/**
	 * 로그아웃
	 */

}