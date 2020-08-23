package com.web.blog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.config.jwt.JwtTokenProvider;
import com.web.blog.domain.LCategory;
import com.web.blog.domain.MCategory;
import com.web.blog.domain.Post;
import com.web.blog.model.Response;
import com.web.blog.model.ResponseMessage;
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
 * @version 1.0, 2020-08-19, Category 관리 Controller
 * @see None
 * 
 */
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CategoryController {

	private final 	JwtTokenProvider 	jwtTokenProvider;
	private final 	PostService 		postService;
	private final 	BlogService 		blogService;
	private final 	CategoryService		categoryService;

	/**
	 * 카테고리 추가(대분류) - 블로그 내의 카테고리를 추가하는 기능. 
	 * 
	 * @param 	lcategory - 추가할 대분류 카테고리 객체
	 * @return 	ResponseEntity<Response> - StatusCode, ResponseMessage(CREATE_CATEGORY_SUCCESS,CREATE_CATEGORY_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 추가(대분류)", response = ResponseEntity.class, notes = "블로그 내의 대분류 카테고리를 추가합니다.")
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
	 * @param mcategory - 추가할 중분류 카테고리 객체 (int lcid, String medium_dir)
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(CREATE_CATEGORY_SUCCESS,CREATE_CATEGORY_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 추가(중분류)", response = ResponseEntity.class, notes = "블로그 내의 중분류 카테고리를 추가합니다.")
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

	/**
	 * 카테고리 수정(대분류) - 블로그 내의 카테고리를 수정하는 기능. 
	 * 
	 * @param lcategory - 수정할 카테고리 객체 (int lcid, int bid, String large_dir)
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(UPDATE_CATEGORY_SUCCESS,UPDATE_CATEGORY_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 수정(대분류)", response = ResponseEntity.class, notes = "블로그 내의 대분류 카테고리를 수정합니다.")
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
	 * @param mcategory - 수정할 카테고리 중분류 (int mcid, int lcid, String medium_dir)
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(UPDATE_CATEGORY_SUCCESS,UPDATE_CATEGORY_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 수정(중분류)", response = ResponseEntity.class, notes = "블로그 내의 중분류 카테고리를 수정합니다.")
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
	
	/**
	 * 카테고리 삭제(대분류) - 블로그 내의 카테고리를 삭제하는 기능. 
	 * 
	 * @param lcategory - 삭제할 카테고리 대분류 (int lcid, int bid)
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(DELETE_CATEGORY_SUCCESS,DELETE_CATEGORY_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 삭제(대분류)", response = ResponseEntity.class, notes = "블로그 내의 대분류 카테고리를 삭제합니다.")
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
	 * @param mcategory - 삭제할 카테고리 중분류 (int lcid, int mcid)
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(DELETE_CATEGORY_SUCCESS,DELETE_CATEGORY_FAIL), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 삭제(중분류)", response = ResponseEntity.class, notes = "블로그 내의 중분류 카테고리를 삭제합니다.")
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
	
	/**
	 * 카테고리 조회 - 블로그 내의 카테고리를 구조를 조회하는 기능. 
	 * 
	 * @param bid - 조회할 블로그의 고유 id
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(SEARCH_CATEGORY_FAIL,SEARCH_CATEGORY_SUCCESS), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리 조회", response = ResponseEntity.class, notes = "블로그 내의 카테고리 구조를 조회합니다.")
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
	
	
	/**
	 * 카테고리 게시글 조회 - 중분류 카테고리내의 게시글을 조회합니다.
	 * 
	 * @param bid - 조회할 블로그의 고유 id
	 * @param mcid - 조회할 블로그 중분류 카테고리 고유 id
	 * @param pageable - 페이지네이션 관련 객체
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(SEARCH_CATEGORY_FAIL,SEARCH_CATEGORY_SUCCESS), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "카테고리의 게시글 조회", response = ResponseEntity.class, notes = "중분류 카테고리내의 게시글을 조회합니다.")
	@GetMapping("/blogs/{bid}/categories/{mcid}")
	public ResponseEntity searchPostByCategory(@PathVariable int bid,@PathVariable int mcid, @PageableDefault(size=10) Pageable pageable, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);
			if(!blogService.checkBlog(bid)){
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.SEARCH_CATEGORY_FAIL),
						HttpStatus.FORBIDDEN);
			}else {
				Page<Post> list = postService.listAllPostByMCategory(bid, mcid, pageable);
				if(list.getSize()!=0) {
					return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.SEARCH_POSTBYCATEGORY_SUCCESS,list),
						HttpStatus.CREATED);
				}else {
					return new ResponseEntity<Response>(new Response(StatusCode.NO_CONTENT, ResponseMessage.SEARCH_POSTBYCATEGORY_FAIL,list),
							HttpStatus.NO_CONTENT);
				}
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
}
