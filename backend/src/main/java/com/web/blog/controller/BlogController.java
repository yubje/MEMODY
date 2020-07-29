package com.web.blog.controller;

import java.util.ArrayList;
import java.util.Date;
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
 *			김형택, ver.0.1 , 2020-07-27, (First Commit)
 * </pre>
 * 
 * @author 김형택
 * @version 0.1, 2020-07-28, Blog 관리 Controller
 * @see None
 * 
 */
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@RestController
public class BlogController {

	private final 	JwtTokenProvider 	jwtTokenProvider;
	private final 	BlogService 		blogService;
	private final 	TagService 			tagService;
	private final 	BlogTagService 		blogtagService;
	private final 	MemberService 		memberService;

	/**
	 * 블로그 생성 - 사용자가 블로그를 생성하는 기능. 
	 * 
	 * @param Blog String bTitle, String bSubTitle, String bContent
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(CREATE_BLOG_SUCCESS), HttpStatus
	 * @exception CREATE_BLOG_FAIL - 블로그 최대 갯수 6개 초과 시
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "블로그 생성", response = ResponseEntity.class)
	@PostMapping("/blogs")
	public ResponseEntity createBlog(@RequestBody Map<String,String> blog, HttpServletRequest req) {
		
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			int bid;
			String email = jwtTokenProvider.getUserPk(token);
			if(blogService.countBlogByUser(email)) {
				Blog temp = new Blog(blog.get("btitle"), blog.get("bsubtitle"), blog.get("bcontent"), email,0);
				bid = blogService.createBlog(temp);
				
				String hashtags[] = blog.get("hashtag").split("#");
				String tname;
				int tid;
				for(int i=1;i<hashtags.length;i++) {
					tname = hashtags[i];
					if(!tagService.checkTagName(tname)) { // 존재하지 않는다면
						tid = tagService.createTag(tname);
						// blog_and_tag 테이블에 추가
					}else {
						// tag 테이블에서 tid 가져오고
						tid = tagService.findByTname(tname);
					}
					// blog_and_tag 테이블에 추가
					blogtagService.blogAndTag(bid, tid, tname);
				}
				memberService.inviteMember(bid, email);
				return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.CREATE_BLOG_SUCCESS),HttpStatus.CREATED);
			}else {
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.CREATE_BLOG_FAIL),HttpStatus.FORBIDDEN);
			}
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}

	
	/**
	 * 내 블로그 목록 조회 - 내가 참여하고 있는 블로그 조회
	 * 
	 * @param
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(SEARCH_MYBLOG_NONE,SEARCH_MYBLOG_SUCCESS), HttpStatus, data(블로그 목록 정보)
	 * @exception FORBIDDEN
	 */
	@ApiOperation(value = "내 블로그 목록 조회", response = ResponseEntity.class)
	@GetMapping(value = "/blogs/")
	public ResponseEntity userBlogList(HttpServletRequest req) {
		String token = req.getHeader("auth");
		System.out.println("to Front: "+token);
		String email = jwtTokenProvider.getUserPk(token);
		if (jwtTokenProvider.validateToken(token)) {
			List<Blog> list = blogService.myBlogList(email);
			
			if(list.size()==0) {
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_MYBLOG_NONE),HttpStatus.OK);
			}else {
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_MYBLOG_SUCCESS, list),HttpStatus.OK);
			}
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
	
	/**
	 * 블로그 정보 조회 - 블로그 클릭 시, 메인화면(Home)
	 * 
	 * @param int bid - 블로그 번호
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(SEARCH_BLOG_SUCCESS,SEARCH_BLOG_FAIL), HttpStatus, data(사용자 정보)
	 * @exception FORBIDDEN
	 */
	@ApiOperation(value = "블로그 정보 조회", response = ResponseEntity.class)
	@GetMapping(value = "/blogs/{bid}")
	public ResponseEntity blogInfo(@PathVariable int bid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			Blog blog = blogService.blogInfo(bid);
			
			if(blog != null) {
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_BLOG_SUCCESS,blog),HttpStatus.OK);
			}else {
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_BLOG_FAIL),HttpStatus.OK);
			}
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 추천 블로그 목록 - 조회수 기반의 블로그를 추천.
	 * 
	 * @param 
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(RECOMMEND_BLOG_SUCCESS), HttpStatus, data(사용자 정보)
	 * 
	 */
	@ApiOperation(value = "추천 블로그 목록", response = ResponseEntity.class)
	@GetMapping(value = "/blogs/views")
	public ResponseEntity recommendBlog() {
			List<Blog> list = blogService.recommendBlogs();
			return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.RECOMMEND_BLOG_SUCCESS,list),HttpStatus.OK);
	}
}