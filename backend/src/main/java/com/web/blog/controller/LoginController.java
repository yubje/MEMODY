package com.web.blog.controller;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.web.blog.advice.RestException;
import com.web.blog.common.ResponseMessage;
import com.web.blog.common.StatusCode;
import com.web.blog.config.jwt.JwtTokenProvider;
import com.web.blog.dao.UsersRepository;
import com.web.blog.model.Response;
import com.web.blog.model.Users;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/*
 * LoginController
 * <pre>
 * <b> History:</b>
 *			김형택, ver.0.1 , 2020-07-26, (First Commit)
 * </pre>
 * 
 * @author 김형택
 * @version 0.1, 2020-07-26, 유저 관리 Controller
 * @see None
 * 
 */

@RequiredArgsConstructor
@RestController
public class LoginController {

	private final 	PasswordEncoder 	passwordEncoder;
	private final 	JwtTokenProvider 	jwtTokenProvider;
	private final 	UsersRepository		usersRepository;
	private final 	RedisTemplate 		redisTemplate;

	/**
	 * 로그인 - 가입한 Email과 Password를 입력하여 로그인한다. Header에 토큰 값을 전달한다.
	 * 
	 * @param Map<String, String> - String email, String password
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(LOGIN_SUCCESS,LOGIN_FAIL), HttpStatus
	 * @exception RestException - NOT_FOUND_USER
	 */
	@ApiOperation(value = "로그인", response = ResponseEntity.class)
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody Map<String, String> user, HttpServletResponse response) {
		Users member = usersRepository.findByEmail(user.get("email"))
				.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
		
		if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.LOGIN_FAIL),
					HttpStatus.FORBIDDEN);
		}
		
		String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
		response.setHeader("auth", token);
		
		return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, member),
				HttpStatus.OK);
	}

	/**
	 * 회원가입 - Email, Password, NickName을 입력하여 회원가입을 한다.
	 * 
	 * @param Map<String, String> - String email, String uid(NickName), password 
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(LOGIN_SUCCESS,LOGIN_FAIL), HttpStatus
	 * @exception RestException - ALREADY_USER
	 */
	@ApiOperation(value = "회원 가입", response = ResponseEntity.class)
	@PostMapping("/users")
	public ResponseEntity<Response> join(@RequestBody Map<String, String> user) {
		if (usersRepository.findByEmail(user.get("email")).isPresent()) {
			throw new RestException(ResponseMessage.ALREADY_USER, HttpStatus.FORBIDDEN);
		}
		
		usersRepository.save(Users.builder().email(user.get("email")).uid(user.get("uid"))
				.password(passwordEncoder.encode(user.get("password"))).roles(Collections.singletonList("ROLE_USER"))
				.build()).getUid();
		
		return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.CREATED_USER),HttpStatus.CREATED);
	}

	/**
	 * 로그아웃 - 토큰을 만료시키고 redis에 저장하여 블랙리스트 생성(토큰만료시간까지 저장시켜두고 추후 자동 삭제)
	 * 
	 * @param
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(LOGOUT_SUCCESS,LOGOUT_FAIL), HttpStatus
	 * @exception UNAUTHORIZED
	 */
	@ApiOperation(value = "로그아웃", response = ResponseEntity.class)
	@GetMapping(path = "/logout")
	public ResponseEntity logout(HttpServletRequest req) {

		String token = req.getHeader("auth");

		if (jwtTokenProvider.validateToken(token)) {
			Date expirationDate = jwtTokenProvider.getExpirationDate(token);
			redisTemplate.opsForValue().set(token, "logout",
					expirationDate.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.UNAUTHORIZED, ResponseMessage.LOGOUT_FAIL),HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<Response>(new Response(StatusCode.NO_CONTENT, ResponseMessage.LOGOUT_SUCCESS),HttpStatus.NO_CONTENT);
	}

	/**
	 * 회원 정보 조회
	 * 
	 * 
	 */
	@ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
	@GetMapping(value = "/users")
	public ResponseEntity findAllUser(HttpServletRequest req) {

		String token = req.getHeader("auth");
		if(jwtTokenProvider.validateToken(token)) {
			// 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
			return new ResponseEntity<>(
					new Response(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, usersRepository.findAll()), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(
					new Response(StatusCode.OK, ResponseMessage.LOGIN_FAIL), HttpStatus.OK);
			
		}
	}

}