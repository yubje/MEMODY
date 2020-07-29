package com.web.blog.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import com.web.blog.domain.Comments;
import com.web.blog.domain.Post;
import com.web.blog.model.Response;
import com.web.blog.model.ResponseMessage;
import com.web.blog.model.RestException;
import com.web.blog.model.StatusCode;
import com.web.blog.service.CommentService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/*
 * CommentController
 * <pre>
 * <b> History:</b>
 *			조민경, ver.0.1 , 2020-07-29, (First Commit)
 * </pre>
 * 
 * @author 조민경
 * @version 0.1, 2020-07-29, Comment 관리 Controller
 * @see None
 * 
 */
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@RestController
public class CommentController {

	private final 	JwtTokenProvider 	jwtTokenProvider;
	private final 	CommentService 		commentService;

	/**
	 * 게시글 작성 - 사용자가 게시글을 작성하는 기능. 
	 * 
	 * @param Post String lcid, String mcid, String pTitle, String pContent, String pType
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(CREATE_BLOG_SUCCESS), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "댓글 작성", response = ResponseEntity.class)
	@PostMapping("/comments")
	public ResponseEntity createComment(@RequestBody Comments comment, HttpServletRequest req) {
		System.out.println("댓글 생성 ");
		System.out.println(comment);
		String token = req.getHeader("auth");
		System.out.println("COMMENT>>>>>>>>>>>>>>>"+token);
		if (jwtTokenProvider.validateToken(token)) {
			String email = jwtTokenProvider.getUserPk(token);
			Comments temp = new Comments(comment.getPid(), comment.getComment(), email, LocalDateTime.now(), LocalDateTime.now());
			commentService.createComments(temp);
			System.out.println("댓글 작성 성공 ");
			return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.CREATE_COMMENT_SUCCESS),HttpStatus.CREATED);
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}

	
	/**
	 * 게시글의 댓글 목록 조회 - 해당 게시글에 있는 전체 댓글 목록 조회
	 * 
	 * @param String Email
	 * @return ResponseEntity<Response> - 
	 * @exception RestException - NOT_FOUND
	 */
	@ApiOperation(value = "게시글의 댓글 목록 조회", response = ResponseEntity.class)
	@GetMapping(value = "/comments/{pid}")
	public ResponseEntity readCommentListAll(@PathVariable int pid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		System.out.println("게시글의 댓글 목록 조회");
		if (jwtTokenProvider.validateToken(token)) {
			List<Comments> list = commentService.listAllComments(pid);
			System.out.println(list);
			if(list.size()==0) {
				return new ResponseEntity<Response>(new Response(StatusCode.NOT_FOUND, ResponseMessage.SEARCH_ALLCOMMENT_NONE),HttpStatus.OK);
			}else {
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_ALLCOMMENT_SUCCESS, list),HttpStatus.OK);
			}
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 댓글 상세 조회 - 댓글 상세내용 조회
	 * 
	 * @param String Email
	 * @return ResponseEntity<Response> - 
	 * @exception RestException - NOT_FOUND
	 */
//	@ApiOperation(value = "댓글 상세 조회", response = ResponseEntity.class)
//	@GetMapping(value = "/comments/{cmid}")
//	public ResponseEntity readPost(@PathVariable int cmid, HttpServletRequest req) {
//		String token = req.getHeader("auth");
//		System.out.println("댓글 조회 ");
//		if (jwtTokenProvider.validateToken(token)) {
//			Comments comment = commentService.findByCmid(cmid);
//			System.out.println(comment);
//			if(comment != null) {
//				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_COMMENT_SUCCESS, comment),HttpStatus.OK);
//			}else {
//				return new ResponseEntity<Response>(new Response(StatusCode.NOT_FOUND, ResponseMessage.SEARCH_COMMENT_NONE, comment),HttpStatus.OK);
//			}
//		}else {
//			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
//		}
//	}
	
	/**
	 * 댓글 수정 - 댓글을 수정한다.
	 * 
	 * @param String Email
	 * @return ResponseEntity<Response> - 
	 * @exception RestException - NOT_FOUND
	 */
	@ApiOperation(value = "댓글 수정", response = ResponseEntity.class)
	@PutMapping(value = "/comments")
	public ResponseEntity updatePost(@RequestBody Comments comment, HttpServletRequest req) {
		String token = req.getHeader("auth");
		System.out.println("댓글 수정 ");
		System.out.println(comment);
		
		// 토큰 유효성 검사 & 로그인한 사용자와 게시글 작성자 같은지 체크 
//		if (jwtTokenProvider.validateToken(token) && jwtTokenProvider.getUserPk(token).equals(comment.getEmail())) {
		if (jwtTokenProvider.validateToken(token)) {
			commentService.updateComments(comment);
			System.out.println("수정 성공");
			return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.UPDATE_COMMENT_SUCCESS, comment),HttpStatus.OK);
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 댓글 삭제 - 댓글 삭제한다.
	 * 
	 * @param String Email
	 * @return ResponseEntity<Response> - 
	 * @exception RestException - NOT_FOUND
	 */
	@ApiOperation(value = "댓글 삭제", response = ResponseEntity.class)
	@DeleteMapping(value = "/comments")
	public ResponseEntity deletePost(int cmid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		System.out.println("댓글 삭제 ");
		System.out.println(cmid);
		
//		 토큰 유효성 검사 & 로그인한 사용자와 게시글 작성자 같은지 체크 
		if (jwtTokenProvider.validateToken(token)) {
			
			commentService.deleteComments(cmid);
			System.out.println("삭제 성공");
			return new ResponseEntity<Response>(new Response(StatusCode.NO_CONTENT, ResponseMessage.DELETE_COMMENT_SUCCESS),HttpStatus.OK);
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
}
