package com.web.blog.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import com.web.blog.config.jwt.JwtTokenProvider;
import com.web.blog.domain.Users;
import com.web.blog.model.Response;
import com.web.blog.model.ResponseMessage;
import com.web.blog.model.RestException;
import com.web.blog.model.StatusCode;
import com.web.blog.service.S3Service;
import com.web.blog.service.UserService;
import com.web.blog.service.UserService2;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

/*
 * LoginController
 * <pre>
 * <b> History:</b>
 *			김형택, ver.0.1 , 2020-07-26, (First Commit)
 * </pre>
 * 
 * @author 김형택
 * @version 0.1, 2020-07-27, 유저 관리 Controller
 * @see None
 * 
 */
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {

	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisTemplate redisTemplate;
	private final UserService userService;
	private final UserService2 mailService;
	private final S3Service s3Service;

	/**
	 * 로그인 - 가입한 Email과 Password를 입력하여 로그인한다. Header에 토큰 값을 전달한다.
	 * 
	 * @param Users user - String email, String password
	 * @return ResponseEntity<Response> - StatusCode,
	 *         ResponseMessage(LOGIN_SUCCESS,LOGIN_FAIL), HttpStatus
	 * @exception RestException - NOT_FOUND_USER
	 */
	@ApiOperation(value = "로그인", response = ResponseEntity.class, notes = "가입한 Email과 Password를 입력하여 로그인합니다. 성공시 Header에 토큰 값을 전달합니다.")
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "email", value = "이메일", required = true, dataType = "String"),
//		@ApiImplicitParam(name = "password", value = "비밀번호", required = true, dataType = "String"),
//	})
	@ApiImplicitParam(name = "user", value = "users 객체", required = true, dataType = "Users")
	@ApiResponses({
		@ApiResponse(code = 200, message = "로그인 성공"),
		@ApiResponse(code = 403, message = "로그인 실패"),
		@ApiResponse(code = 404, message = "사용자 없음"),
	})
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody Users user, HttpServletResponse res) {
		Users member = userService.findByEmail(user.getEmail())
				.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER, HttpStatus.NOT_FOUND));

		if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.LOGIN_FAIL),
					HttpStatus.FORBIDDEN);
		}

		String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
		res.setHeader("auth", token);
		return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, member),
				HttpStatus.OK);
	}

	/**
	 * 회원가입 - Email, Password, NickName을 입력하여 회원가입을 한다.
	 * 
	 * @param Users user - String email, String uid(NickName), password 
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(CREATED_USER,ALREADY_USER), HttpStatus
	 * @exception RestException - ALREADY_USER
	 */
	@ApiOperation(value = "회원 가입", response = ResponseEntity.class, notes = "Email, Password, NickName을 입력하여 회원가입을 합니다.")
	@PostMapping("/users")
	public ResponseEntity signUp(@RequestBody Users user, HttpServletRequest req)  {
		String code = req.getHeader("code");
		System.out.println("**************************************");
		System.out.println(code);
		System.out.println(redisTemplate.opsForValue().get(user.getEmail()));
		System.out.println("**************************************");
		if ((redisTemplate.opsForValue().get(user.getEmail())==null) || !redisTemplate.opsForValue().get(user.getEmail()).equals(code)) {
			System.out.println(("인증되는 이메일이 아님"));
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
		redisTemplate.opsForValue().set(user.getEmail(),"",0);
		
		if (userService.findByEmail(user.getEmail()).isPresent()) {
			throw new RestException(ResponseMessage.ALREADY_USER, HttpStatus.FORBIDDEN);
		}

		userService.join(user, passwordEncoder.encode(user.getPassword()));

		return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.CREATED_USER),
				HttpStatus.CREATED);
	}

	/**
	 * 로그아웃 - 토큰을 만료시키고 redis에 저장하여 블랙리스트 생성(토큰만료시간까지 저장시켜두고 추후 자동 삭제)
	 * 
	 * @param
	 * @return ResponseEntity<Response> - StatusCode,
	 *         ResponseMessage(LOGOUT_SUCCESS,LOGOUT_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 */
	@ApiOperation(value = "로그아웃", response = ResponseEntity.class, notes = "토큰을 만료시키고 redis에 저장하여 블랙리스트를 생성합니다.(토큰만료시간까지 저장시켜두고 추후 자동 삭제)")
	@GetMapping(path = "/logout")
	public ResponseEntity logout(HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			Date expirationDate = jwtTokenProvider.getExpirationDate(token);
			redisTemplate.opsForValue().set(token, "logout", expirationDate.getTime() - System.currentTimeMillis(),
					TimeUnit.MILLISECONDS);
			return new ResponseEntity<Response>(new Response(StatusCode.NO_CONTENT, ResponseMessage.LOGOUT_SUCCESS),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.LOGOUT_FAIL),
					HttpStatus.FORBIDDEN);
		}

	}

	/**
	 * 회원 정보 조회 - 입력받은 이메일의 회원정보를 조회
	 * 
	 * @param String Email
	 * @return ResponseEntity<Response> - StatusCode,
	 *         ResponseMessage(READ_USER,NOT_FOUND_USER), HttpStatus, data(사용자 정보)
	 * @exception RestException - NOT_FOUND
	 */
	@ApiOperation(value = "회원 정보 조회", response = ResponseEntity.class, notes = "로그인하고 있는 회원의 회원정보를 조회합니다.")
	@GetMapping(value = "/users/{email}")
	public ResponseEntity userInfo(@PathVariable String email, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			Users member = userService.findByEmail(email)
					.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
			System.out.println(member.toString());
			return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.READ_USER, member),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 회원 탈퇴 - 현재 로그인되어있는 사용자의 회원 정보 삭제
	 * 
	 * @param String Email
	 * @return ResponseEntity<Response> - StatusCode,
	 *         ResponseMessage(READ_USER,NOT_FOUND_USER), HttpStatus
	 * @exception FORBIDDEN
	 */
	@ApiOperation(value = "회원 탈퇴", response = ResponseEntity.class, notes = "현재 로그인되어있는 사용자의 회원 정보를 삭제합니다.")
	@DeleteMapping(value = "/users/{email}")
	public ResponseEntity findAllUser(@PathVariable String email, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token) && jwtTokenProvider.getUserPk(token).equals(email)) {
			userService.deleteUser(email);

			return new ResponseEntity<Response>(new Response(StatusCode.NO_CONTENT, ResponseMessage.DELETE_USER),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 회원가입시 이메일 인증 - 회원가입시 이메일 중복 방지와 본인확인을 위한 인증 과정입니다.
	 * 
	 * @param String email
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(CREATE_CODE,ALREADY_USER), HttpStatus
	 */
	@ApiOperation(value = "회원가입시 이메일 인증", response = ResponseEntity.class, notes = "회원가입시 이메일 중복 방지와 본인확인을 위한 인증 과정입니다.")
	@GetMapping(value = "/auth/join/{email}")
	public ResponseEntity authEmailJoin(@PathVariable String email) {

		if (!userService.findByEmail(email).isPresent()) {
			// 인증코드 생성
			String code = UUID.randomUUID().toString().replaceAll("-", "");
			code = code.substring(0, 10);

			final String SEND_EMAIL_ID = "kimhyungtaik@gmail.com"; // 관리자 email
			String subject = "회원가입 인증코드 발급 안내 입니다.";
			StringBuilder sb = new StringBuilder();
			sb.append("귀하의 인증코드 입니다.\n");
			sb.append("인증코드 : " + code);
			
			if (mailService.send(subject, sb.toString(), SEND_EMAIL_ID, email, null)) {
				redisTemplate.opsForValue().set(email, code, 30000, TimeUnit.MILLISECONDS);
//				redisTemplate.opsForValue().set(code+"_"+SEND_EMAIL_ID, "email-code", 300,
//						TimeUnit.MILLISECONDS);
				System.out.println("인증코드 redis에 저장 "+email);
				System.out.println(redisTemplate.opsForValue().get(email));
				
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.CREATE_CODE, code),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
						HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.ALREADY_USER),
					HttpStatus.FORBIDDEN);
		}

	}
	
	/**
	 * 비밀번호 재설정 시 이메일 인증 - 비밀번호 재설정 시 본인 확인을 위한 이메일 인증 과정입니다.
	 * 
	 * @param String email
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(CREATE_CODE,NOT_FOUND_USER), HttpStatus
	 */
	@ApiOperation(value = "비밀번호 재설정 시 이메일 인증", response = ResponseEntity.class, notes = "비밀번호 재설정 시 본인 확인을 위한 이메일 인증 과정입니다.")
	@GetMapping(value = "/auth/pwd/{email}")
	public ResponseEntity authEmailPWD(@PathVariable String email) {

		if (userService.findByEmail(email).isPresent()) {
			// 인증코드 생성
			String code = UUID.randomUUID().toString().replaceAll("-", "");
			code = code.substring(0, 10);

			final String SEND_EMAIL_ID = "kimhyungtaik@gmail.com"; // 관리자 email
			String subject = "비밀번호 재설정 인증코드 발급 안내 입니다.";
			StringBuilder sb = new StringBuilder();
			sb.append("귀하의 인증코드 입니다.\n");
			sb.append("인증코드 : " + code);

			if (mailService.send(subject, sb.toString(), SEND_EMAIL_ID, email, null)) {
				redisTemplate.opsForValue().set(email, code, 30000, TimeUnit.MILLISECONDS);
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.CREATE_CODE, code),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
						HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER),
					HttpStatus.FORBIDDEN);
		}

	}

	/**
	 * 회원 정보 수정 - 로그인한 사용자의 회원 정보를 수정합니다. 
	 * @param Users user - String uid, String password
	 * @return
	 */
	@ApiOperation(value = "회원정보 수정", response = ResponseEntity.class, notes = "로그인한 사용자의 회원 정보를 수정합니다.")
	@PutMapping(value = "/users")
	public ResponseEntity updateUser(@RequestBody Users user, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token) && jwtTokenProvider.getUserPk(token).equals(user.getEmail())) {
			Users member = userService.findByEmail(user.getEmail())
					.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
			String ecdPwd = null;
			if(user.getPassword()==null) {
				ecdPwd = member.getPassword();
			}else {
				ecdPwd = passwordEncoder.encode(user.getPassword());
			}
			
			userService.userUpdate(user.getEmail(), user.getUid(), ecdPwd);

			return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.UPDATE_USER, user),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FAIL_UPDATE_USER),
					HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 비밀번호 재설정 - 사용자의 비밀번호를 재설정합니다.
	 * 
	 * @param Users - String email, String password
	 * @return
	 */
	@ApiOperation(value = "비밀번호 재설정", response = ResponseEntity.class, notes = "사용자의 비밀번호를 재설정합니다.")
	@PutMapping(value = "/users/pw")
	public ResponseEntity resetPassword(@RequestBody Users user, HttpServletRequest req) {

		String code = req.getHeader("code");
		System.out.println(redisTemplate.opsForValue().get(user.getEmail()));
		System.out.println("CODE: "+code);
		if ((redisTemplate.opsForValue().get(user.getEmail())==null) || !redisTemplate.opsForValue().get(user.getEmail()).equals(code)) {
			System.out.println(("인증되는 이메일이 아님"));
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
		System.out.println("인증됨");
		redisTemplate.opsForValue().set(user.getEmail(),"",0);
		
		String ecdPwd = passwordEncoder.encode(user.getPassword());
		userService.pwdUpdate(user.getEmail(), ecdPwd);

		return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.RESET_PWD, user.getEmail()),
				HttpStatus.OK);
	}
	
	/**
	 * 닉네임으로 회원정보 조회 - 닉네임 중복 방지를 위해 닉네임으로 회원정보를 조회합니다.
	 * @param String uid
	 * @return
	 */
	@ApiOperation(value = "닉네임으로 회원정보 조회", response = ResponseEntity.class, notes = "닉네임 중복 방지를 위해 닉네임으로 회원정보를 조회합니다.")
	@GetMapping(value = "/users/{uid}/nickname")
	public ResponseEntity searchUserByNickname(@PathVariable String uid) {
		if (!userService.findByUid(uid).isPresent()) {
			return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_NICKNAME_NONE, uid),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(
					new Response(StatusCode.FORBIDDEN, ResponseMessage.SEARCH_NICKNAME_EXIST), HttpStatus.FORBIDDEN);
		}

	}
	
	
	@ApiOperation(value = "프로필 이미지 변경", response = ResponseEntity.class)
	@PutMapping(value = "/users/{email}/profile", produces = "application/json;charset=UTF-8", consumes = {
			"multipart/form-data" })
	public ResponseEntity profileChange(@PathVariable String email, MultipartFile file, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			if (file == null) {
				System.out.println("[SYSTEM] 파일 없음");
				return new ResponseEntity<Response>(
						new Response(StatusCode.FORBIDDEN, ResponseMessage.UPDATE_PROFILE_FAIL), HttpStatus.FORBIDDEN);
			}
			System.out.println("[FILE] " + file);
			String url = s3Service.profileUpload(file);
			System.out.println("URL: "+url);
			if (url != null) {
				userService.profileUpdate(email, url);
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.UPDATE_PROFILE_SUCCESS, url),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<Response>(
						new Response(StatusCode.FORBIDDEN, ResponseMessage.UPDATE_PROFILE_FAIL), HttpStatus.FORBIDDEN);
			}
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 랭킹 조회 - 전체 랭킹순위를 조회합니다
	 * @return
	 */
	@ApiOperation(value = "랭킹 조회", response = ResponseEntity.class, notes = "전체 랭킹순위를 조회합니다.")
	@GetMapping(value = "/rank")
	public ResponseEntity searchRanking(HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			List<Users> list = userService.findAll();
			System.out.println(list);
			return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_ALLRANK_SUCCESS, list),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}

	}

}

