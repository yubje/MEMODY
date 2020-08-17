package com.web.blog.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.config.jwt.JwtTokenProvider;
import com.web.blog.domain.Blog;
import com.web.blog.domain.Users;
import com.web.blog.model.Response;
import com.web.blog.model.ResponseMessage;
import com.web.blog.model.RestException;
import com.web.blog.model.StatusCode;
import com.web.blog.service.BlogService;
import com.web.blog.service.BlogTagService;
import com.web.blog.service.MemberService;
import com.web.blog.service.TagService;
import com.web.blog.service.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/*
 * BlogController
 * <pre>
 * <b> History:</b>
 *			김형택, ver.0.1 , 2020-07-28, (First Commit)
 * </pre>
 * 
 * @author 김형택
 * @version 0.1, 2020-07-30, 메인 관리 Controller(메인 페이지 데이터 response)
 * @see None
 * 
 */
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MainController {

	private final 	JwtTokenProvider 	jwtTokenProvider;
	private final 	BlogService 		blogService;
	private final	UserService			userService;
	private final 	RedisTemplate 		redisTemplate;

	/**
	 * 로그인 후 메인 - 내가 참여하고 있는 블로그와 추천 블로그 출력
	 * 
	 * @param
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(MAIN_SUCCESS), HttpStatus, data(블로그 목록 정보)
	 * @exception FORBIDDEN
	 */
	@ApiOperation(value = "로그인 후 메인", response = ResponseEntity.class, notes = "로그인 후에는 내가 참여하고 있는 블로그와 추천 블로그를 메인화면에 보여줍니다.")
	@GetMapping(value = "/main/after")
	public ResponseEntity loginAfter(HttpServletRequest req,HttpServletResponse res) {
		String token = req.getHeader("auth");
		
		String temp = res.getHeader("auth"); 
		System.out.println("[업데이트 토큰1]"+token);
		System.out.println("[업데이트 토큰2]"+temp);
		
		String email = jwtTokenProvider.getUserPk(token);
		if (jwtTokenProvider.validateToken(token)) {
			List<Blog> recommendList = blogService.recommendBlogs();
			List<Blog> myList = blogService.myBlogList(email);
			List<Blog> followList = blogService.followBlogList(email);
			Map<String,List<Blog>> result = new HashMap<String, List<Blog>>();
			
			result.put("myBlog",myList);
			result.put("recommendBlog",recommendList);
			result.put("followBlog",followList);
			
			return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.MAIN_SUCCESS, result),HttpStatus.OK);
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
	
	/**
	 * 로그인 전 메인 - 조회수 기반의 블로그를 추천.
	 * 
	 * @param 
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(RECOMMEND_BLOG_SUCCESS), HttpStatus, data(블로그 목록 정보)
	 * 
	 */
	@ApiOperation(value = "로그인 전 메인", response = ResponseEntity.class, notes = "로그인 전에는 조회수 기반의 블로그를 추천하여 메인화면에 보여줍니다.")
	@GetMapping(value = "/main/before")
	public ResponseEntity loginBefore() {
			List<Blog> list = blogService.recommendBlogs();
			return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.RECOMMEND_BLOG_SUCCESS,list),HttpStatus.OK);
	}
	
	/**
	 * 토큰 업데이트 - 토큰 만료 전 refresh하여 사용자에게 헤더로 새로운 토큰 값을 전송한다.
	 * 
	 * @param 
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(TOKEN_REFRESH), HttpStatus, token
	 * 
	 */
	@ApiOperation(value = "JWT 토큰 Refresh", response = ResponseEntity.class, notes = "토큰 만료 전 토큰 값을 갱신한다.")
	@PostMapping(value = "/refresh")
	public ResponseEntity tokenRefresh(HttpServletRequest req,HttpServletResponse res) {
		String token = req.getHeader("auth");
		String email = jwtTokenProvider.getUserPk(token);
		Users user = userService.findByEmail(email)
				.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
		
		// 이전 토큰은 redis에 저장하여 로그아웃된 것처럼 처리
		Date expirationDate = jwtTokenProvider.getExpirationDate(token);
		redisTemplate.opsForValue().set(token, "logout", expirationDate.getTime() - System.currentTimeMillis(),
				TimeUnit.MILLISECONDS);
		
		// 새로운 토큰 생성
		String newToken = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
		res.setHeader("auth", newToken);
		return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.TOKEN_REFRESH),HttpStatus.OK);
}
}
