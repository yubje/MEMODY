package com.web.blog.controller;

import java.util.Collections;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.web.blog.config.jwt.JwtTokenProvider;
import com.web.blog.domain.Users;
import com.web.blog.model.UserDto;
import com.web.blog.repository.UsersRepository;
import com.web.blog.service.JwtService;
import com.web.blog.service.UserService2;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String DUPLICATE = "duplicate";

	@Autowired
	private UserService2 userService;

	@Autowired
	private JwtService jwtService;
	

	// 에러 핸들러 - ModelAndView 객체를 이용하여 에러페이지와 메세지를 담아 return
	@ExceptionHandler
	public ModelAndView handler(Exception ex) {
		ModelAndView mav = new ModelAndView("error/errorHandler");
		mav.addObject("msg", ex.getMessage());
		ex.printStackTrace();
		return mav;
	}

	@ApiOperation(value = "Req.4-1 회원가입", response = UserDto.class)
	@PostMapping(value = "/join")
	public ResponseEntity join(@RequestBody UserDto user) {
		if (userService.emailcheck(user.getEmail()) != null) {
			return new ResponseEntity<String>(DUPLICATE, HttpStatus.NO_CONTENT);
		}
		boolean check = userService.register(user);
		if (check) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Req.4-3 회원정보 조회", response = UserDto.class)
	@PostMapping(value = "/search")
	public ResponseEntity search(@RequestBody UserDto user) {
		UserDto temp = userService.login(user);
		if (temp != null) {
			return new ResponseEntity<UserDto>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "Req.4-3 회원정보 수정", response = UserDto.class)
	@PutMapping(value = "/modify")
	public ResponseEntity modify(@RequestBody UserDto user) {
		UserDto temp = userService.login(user);
		if (temp != null) {
			boolean check = userService.modify(user);
			if (check) {
				return new ResponseEntity<UserDto>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "Req.4-4 회원탈퇴", response = UserDto.class)
	@DeleteMapping(value = "/delete")
	public ResponseEntity delete(HttpSession session, @RequestBody UserDto user) {
//		String id = (String) session.getAttribute("user");
		session.invalidate();

		if (userService.delete(user)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Req.6-1 로그인", response = UserDto.class)
	@PostMapping(value = "/in")
	public ResponseEntity login(@RequestBody UserDto user, HttpSession session) {
		UserDto login = userService.login(user);
		if (login == null) {
			System.out.println("null임");
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} else {
			session.setAttribute("user", login.getEmail());

			// test
//			String id = (String)session.getAttribute("user");
//			System.out.println(id);

			return new ResponseEntity<UserDto>(login, HttpStatus.OK);
		}
	}

	// 회원가입 시 이메일 인증 보내기
	@ApiOperation(value = "Req.5-1 이메일 인증", response = UserDto.class)
	@GetMapping(value = "/joinAuth")
	public ResponseEntity joinAuth(@RequestParam("email") String email) {
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
	
	// JWT TEST
	@ApiOperation(value = "Req.6-1 로그인", response = UserDto.class)
	@PostMapping(value = "/jwt")
	public ResponseEntity jwtLogin(@RequestBody UserDto user,HttpServletResponse response) {
		System.out.println("ddd");
		UserDto login = userService.login(user);
		
		if (login == null) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} else {
			// Token 설정
			String token = jwtService.create("SSAFY",login,"user");
			response.setHeader("auth", token);
			
			return new ResponseEntity<UserDto>(login, HttpStatus.OK);
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////
	// sub 2
	
   
}
