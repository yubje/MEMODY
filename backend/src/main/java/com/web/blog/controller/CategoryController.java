package com.web.blog.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.config.jwt.JwtTokenProvider;
import com.web.blog.domain.LCategory;
import com.web.blog.domain.MCategory;
import com.web.blog.domain.Post;
import com.web.blog.model.Response;
import com.web.blog.model.ResponseMessage;
import com.web.blog.model.RestException;
import com.web.blog.model.StatusCode;
import com.web.blog.service.BlogService;
import com.web.blog.service.CategoryService;
import com.web.blog.service.PostService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/*
 * CategoryController
 * <pre>
 * <b> History:</b>
 *			김형택, ver.0.1 , 2020-07-30, (First Commit)
 * </pre>
 * 
 * @author 김형택
 * @version 0.1, 2020-07-30, Category 관리 Controller
 * @see None
 * 
 */
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@RestController
public class CategoryController {

	private final 	JwtTokenProvider 	jwtTokenProvider;
	private final 	PostService 		postService;
//	private final 	TagService 			tagService;
	private final 	BlogService 		blogService;
//	private final 	MemberService 		memberService;
	private final 	CategoryService		categoryService;

	/**
	 * 카테고리 추가(대분류) - 블로그 내의 카테고리를 추가하는 기능. 
	 * 
	 * @param LCategory int bid, String large_dir
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(CREATE_CATEGORY_SUCCESS,CREATE_CATEGORY_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 추가(대분류)", response = ResponseEntity.class)
	@PostMapping("/blogs/categories/parent")
	public ResponseEntity createCategory1(@RequestBody LCategory lcategory, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);
			if(!blogService.checkBlog(lcategory.getBid())){
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.CREATE_CATEGORY_FAIL),
						HttpStatus.FORBIDDEN);
			}else {
				if(categoryService.createLCategory(user,lcategory)) {
					return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.CREATE_CATEGORY_SUCCESS),
							HttpStatus.CREATED);
				}else {
					return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.CREATE_CATEGORY_FAIL),
							HttpStatus.FORBIDDEN);
				}
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	/**
	 * 카테고리 추가(중분류) - 블로그 내의 카테고리를 추가하는 기능. 
	 * 
	 * @param MCategory int lcid, String medium_dir
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(CREATE_CATEGORY_SUCCESS,CREATE_CATEGORY_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 추가(중분류)", response = ResponseEntity.class)
	@PostMapping("/blogs/categories/child")
	public ResponseEntity createCategory2(@RequestBody MCategory mcategory, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);
			if(!categoryService.checkCategory(mcategory.getLcid())){
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.CREATE_CATEGORY_FAIL),
						HttpStatus.FORBIDDEN);
			}else {
				if(categoryService.createMCategory(user,mcategory)) {
					return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.CREATE_CATEGORY_SUCCESS),
							HttpStatus.CREATED);
				}else {
					return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.CREATE_CATEGORY_FAIL),
							HttpStatus.FORBIDDEN);
				}
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}

	///////////////////////////////////////////////////// 수정
	
	/**
	 * 카테고리 수정(대분류) - 블로그 내의 카테고리를 수정하는 기능. 
	 * 
	 * @param LCategory int lcid, int bid, String large_dir
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(UPDATE_CATEGORY_SUCCESS,UPDATE_CATEGORY_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 수정(대분류)", response = ResponseEntity.class)
	@PutMapping("/blogs/categories/parent")
	public ResponseEntity updateCategory1(@RequestBody LCategory lcategory, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);
			if(!blogService.checkBlog(lcategory.getBid())){
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.UPDATE_CATEGORY_FAIL),
						HttpStatus.FORBIDDEN);
			}else {
				if(categoryService.updateLCategory(user,lcategory)) {
					return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.UPDATE_CATEGORY_SUCCESS),
							HttpStatus.CREATED);
				}else {
					return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.UPDATE_CATEGORY_FAIL),
							HttpStatus.FORBIDDEN);
				}
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	/**
	 * 카테고리 수정(중분류) - 블로그 내의 카테고리를 수정하는 기능. 
	 * 
	 * @param MCategory int mcid, int lcid, String medium_dir
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(UPDATE_CATEGORY_SUCCESS,UPDATE_CATEGORY_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 수정(중분류)", response = ResponseEntity.class)
	@PutMapping("/blogs/categories/child")
	public ResponseEntity updateCategory2(@RequestBody MCategory mcategory, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);
			if(!categoryService.checkCategory(mcategory.getLcid())){
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.UPDATE_CATEGORY_FAIL),
						HttpStatus.FORBIDDEN);
			}else {
				if(categoryService.updateMCategory(user,mcategory)) {
					return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.UPDATE_CATEGORY_SUCCESS),
							HttpStatus.CREATED);
				}else {
					return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.UPDATE_CATEGORY_FAIL),
							HttpStatus.FORBIDDEN);
				}
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	
	
	
	///////////////////////////////////////////////////// 삭제
	
	/**
	 * 카테고리 삭제(대분류) - 블로그 내의 카테고리를 삭제하는 기능. 
	 * 
	 * @param LCategory int lcid, int bid
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(DELETE_CATEGORY_SUCCESS,DELETE_CATEGORY_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 삭제(대분류)", response = ResponseEntity.class)
	@DeleteMapping("/blogs/categories/parent")
	public ResponseEntity deleteCategory1(@RequestBody LCategory lcategory, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);
			if(!blogService.checkBlog(lcategory.getBid())){
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.DELETE_CATEGORY_FAIL),
						HttpStatus.FORBIDDEN);
			}else {
				if(categoryService.deleteLCategory(user,lcategory)) {
					return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.DELETE_CATEGORY_SUCCESS),
							HttpStatus.OK);
				}else {
					return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.DELETE_CATEGORY_FAIL),
							HttpStatus.FORBIDDEN);
				}
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	/**
	 * 카테고리 삭제(중분류) - 블로그 내의 카테고리를 삭제하는 기능. 
	 * 
	 * @param MCategory int lcid, int mcid
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(DELETE_CATEGORY_SUCCESS,DELETE_CATEGORY_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 삭제(중분류)", response = ResponseEntity.class)
	@DeleteMapping("/blogs/categories/child")
	public ResponseEntity deleteCategory2(@RequestBody MCategory mcategory, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);
			if(!categoryService.checkCategory(mcategory.getLcid())){
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.DELETE_CATEGORY_FAIL),
						HttpStatus.FORBIDDEN);
			}else {
				if(categoryService.deleteMCategory(user,mcategory)) {
					return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.DELETE_CATEGORY_SUCCESS),
							HttpStatus.CREATED);
				}else {
					return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.DELETE_CATEGORY_FAIL),
							HttpStatus.FORBIDDEN);
				}
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	
	/////////////////////////////////////////////////////////// 조회
	/**
	 * 카테고리 조회 - 블로그 내의 카테고리를 구조를 조회하는 기능. 
	 * 
	 * @param MCategory int lcid, int mcid
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(DELETE_CATEGORY_SUCCESS,DELETE_CATEGORY_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 조회", response = ResponseEntity.class)
	@GetMapping("/blogs/{bid}/categories")
	public ResponseEntity searchCategory(@PathVariable int bid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);
			if(!blogService.checkBlog(bid)){
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.SEARCH_CATEGORY_FAIL),
						HttpStatus.FORBIDDEN);
			}else {
				List<LCategory> list = categoryService.searchCategory(bid);
				return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.SEARCH_CATEGORY_SUCCESS,list),
						HttpStatus.CREATED);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	
	@ApiOperation(value = "카테고리의 게시글 조회", response = ResponseEntity.class)
	@GetMapping("/blogs/{bid}/categories/{mcid}")
	public ResponseEntity searchPostByCategory(@PathVariable int bid,@PathVariable int mcid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);
			if(!blogService.checkBlog(bid)){
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.SEARCH_CATEGORY_FAIL),
						HttpStatus.FORBIDDEN);
			}else {
				List<Post> list = postService.listAllPostByMCategory(bid, mcid);
				if(list.size()!=0) {
					return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.SEARCH_POSTBYCATEGORY_SUCCESS,list),
						HttpStatus.CREATED);
				}else {
					return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.SEARCH_POSTBYCATEGORY_FAIL,list),
							HttpStatus.FORBIDDEN);
				}
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
}
