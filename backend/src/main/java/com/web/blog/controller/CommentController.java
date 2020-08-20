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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.config.jwt.JwtTokenProvider;
import com.web.blog.domain.Comments;
import com.web.blog.domain.Users;
import com.web.blog.model.Response;
import com.web.blog.model.ResponseMessage;
import com.web.blog.model.RestException;
import com.web.blog.model.StatusCode;
import com.web.blog.service.CommentService;
import com.web.blog.service.UserService;

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
@RequestMapping("/api")
public class CommentController {

	private final 	JwtTokenProvider 	jwtTokenProvider;
	private final 	CommentService 		commentService;
	private final	UserService			userService;
	
	/**
	 * 댓글 작성 - 사용자가 댓글 작성하는 기능. 
	 * 
	 * @param Post String lcid, String mcid, String pTitle, String pContent, String pType
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(CREATE_BLOG_SUCCESS), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "댓글 작성", response = ResponseEntity.class, notes = "사용자가 댓글을 작성합니다.")
	@PostMapping("/comments")
	public ResponseEntity createComment(@RequestBody Comments comment, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String email = jwtTokenProvider.getUserPk(token);
			Comments temp = new Comments(comment.getPid(), comment.getComment(), email, LocalDateTime.now(), LocalDateTime.now());
			commentService.createComments(temp);
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
	@ApiOperation(value = "게시글의 댓글 목록 조회", response = ResponseEntity.class, notes = "해당 게시글에 있는 전체 댓글 목록 조회합니다.")
	@GetMapping(value = "/comments/{pid}")
	public ResponseEntity readCommentListAll(@PathVariable int pid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			List<Comments> list = commentService.listAllComments(pid);
			System.out.println("게시글의 댓글 목록 조회");
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
	 * 댓글 수정 - 댓글을 수정한다.
	 * 
	 * @param String Email
	 * @return ResponseEntity<Response> - 
	 * @exception RestException - NOT_FOUND
	 */
	@ApiOperation(value = "댓글 수정", response = ResponseEntity.class, notes = "댓글을 수정합니다.")
	@PutMapping(value = "/comments")
	public ResponseEntity updatePost(@RequestBody Comments comment, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			commentService.updateComments(comment);
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
	@ApiOperation(value = "댓글 삭제", response = ResponseEntity.class, notes = "댓글을 삭제합니다.")
	@DeleteMapping(value = "/comments")
	public ResponseEntity deletePost(@RequestBody Comments comment, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String userEmail = jwtTokenProvider.getUserPk(token);
			Users user = userService.findByEmail(userEmail)
					.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
			if(commentService.deleteComments(userEmail, comment.getCmid(), user.getRoles().get(0))) {
				return new ResponseEntity<Response>(new Response(StatusCode.NO_CONTENT, ResponseMessage.DELETE_COMMENT_SUCCESS),HttpStatus.OK);
			}else {
				return new ResponseEntity<Response>(new Response(StatusCode.NO_CONTENT, ResponseMessage.DELETE_COMMENT_FAIL),HttpStatus.OK);
			}
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
}
