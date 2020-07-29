package com.web.blog.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.config.jwt.JwtTokenProvider;
import com.web.blog.domain.Blog;
import com.web.blog.domain.Tag;
import com.web.blog.domain.Users;
import com.web.blog.model.Response;
import com.web.blog.model.ResponseMessage;
import com.web.blog.model.RestException;
import com.web.blog.model.StatusCode;
import com.web.blog.repository.BlogRepository;
import com.web.blog.service.BlogService;
import com.web.blog.service.BlogTagService;
import com.web.blog.service.MemberService;
import com.web.blog.service.TagService;
import com.web.blog.service.UserService;
import com.web.blog.service.UserService2;

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
 * @version 0.1, 2020-07-28, 메인 관리 Controller(메인 페이지 데이터 response)
 * @see None
 * 
 */
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@RestController
public class MainController {

	private final 	JwtTokenProvider 	jwtTokenProvider;
	private final 	BlogService 		blogService;
	private final 	TagService 			tagService;
	private final 	BlogTagService 		blogtagService;
	private final 	MemberService 		memberService;

	/**
	 * 로그인 후 메인 - 내가 참여하고 있는 블로그와 추천 블로그 출력
	 * 
	 * @param
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(MAIN_SUCCESS), HttpStatus, data(블로그 목록 정보)
	 * @exception FORBIDDEN
	 */
	@ApiOperation(value = "로그인 후 메인", response = ResponseEntity.class)
	@GetMapping(value = "/main/after")
	public ResponseEntity loginAfter(HttpServletRequest req) {
		String token = req.getHeader("auth");
		String email = jwtTokenProvider.getUserPk(token);
		if (jwtTokenProvider.validateToken(token)) {
			List<Blog> recommendList = blogService.recommendBlogs();
			
			List<Blog> myList = blogService.myBlogList(email);
			
			List<Blog> mainList = new ArrayList<Blog>();
			
			mainList.addAll(myList);
			mainList.addAll(recommendList);
			Map<String,List<Blog>> result = new HashMap<String, List<Blog>>();
			result.put("myBlog",myList);
			result.put("recommendBlog",recommendList);
			
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
	@ApiOperation(value = "로그인 전 메인", response = ResponseEntity.class)
	@GetMapping(value = "/main/before")
	public ResponseEntity loginBefore() {
			List<Blog> list = blogService.recommendBlogs();
			return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.RECOMMEND_BLOG_SUCCESS,list),HttpStatus.OK);
	}
}
