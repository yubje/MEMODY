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
import com.web.blog.domain.Blog;
import com.web.blog.domain.Post;
import com.web.blog.model.Response;
import com.web.blog.model.ResponseMessage;
import com.web.blog.model.RestException;
import com.web.blog.model.StatusCode;
import com.web.blog.service.PostService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/*
 * PostController
 * <pre>
 * <b> History:</b>
 *			조민경, ver.0.1 , 2020-07-28, (First Commit)
 * </pre>
 * 
 * @author 김형택
 * @version 0.1, 2020-08-03, 게시글 Fork
 * @see None
 * 
 */
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@RestController
public class PostController {

	private final 	JwtTokenProvider 	jwtTokenProvider;
	private final 	PostService 		postService;
//	private final 	TagService 			tagService;
//	private final 	BlogTagService 		blogtagService;
//	private final 	MemberService 		memberService;

	/**
	 * 게시글 작성 - 사용자가 게시글을 작성하는 기능. 
	 * 
	 * @param Post String lcid, String mcid, String pTitle, String pContent, String pType
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(CREATE_BLOG_SUCCESS), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "게시글 작성", response = ResponseEntity.class)
	@PostMapping("/blogs/{bid}/posts")
	public ResponseEntity createPost(@PathVariable int bid, @RequestBody Map<String,String> post, HttpServletRequest req) {
		System.out.println("포스트 생성 ");
		System.out.println(post);
		String token = req.getHeader("auth");
//		System.out.println("POST>>>>>>>>>>>>>>>"+token);
		if (jwtTokenProvider.validateToken(token)) {
			int pid;
			String email = jwtTokenProvider.getUserPk(token);
			Post temp = null;
			if(post.get("ptype")=="") {
				temp = new Post(bid, Integer.parseInt(post.get("lcid")), Integer.parseInt(post.get("mcid")), post.get("ptitle")
						, post.get("pcontent"), email, LocalDateTime.now(), LocalDateTime.now(), null);
			}else {
				temp = new Post(bid, Integer.parseInt(post.get("lcid")), Integer.parseInt(post.get("mcid")), post.get("ptitle")
						, post.get("pcontent"), email, LocalDateTime.now(), LocalDateTime.now(), post.get("ptype"));
			}
			pid = postService.createPost(temp);
			if(post.get("ptype").equals("SAVE")) {
				return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.SAVE_POST_SUCCESS),HttpStatus.CREATED);
			}else {
				return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.CREATE_POST_SUCCESS),HttpStatus.CREATED);
			}
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}

	
	/**
	 * 블로그의 게시글 목록 조회 - 해당 블로그에 있는 전체 게시글 목록 조회
	 * 
	 * @param String Email
	 * @return ResponseEntity<Response> - 
	 * @exception RestException - NOT_FOUND
	 */
	@ApiOperation(value = "블로그의 게시글 목록 조회", response = ResponseEntity.class)
	@GetMapping(value = "/blogs/{bid}/posts/")
	public ResponseEntity readPostListAll(@PathVariable int bid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		System.out.println("블로그 내 게시글 목록 조회 ");
		if (jwtTokenProvider.validateToken(token)) {
			List<Post> list = postService.listAllPost(bid);
			System.out.println(list);
			if(list.size()==0) {
				return new ResponseEntity<Response>(new Response(StatusCode.NOT_FOUND, ResponseMessage.SEARCH_ALLPOST_NONE, list),HttpStatus.OK);
			}else {
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_ALLPOST_SUCCESS, list),HttpStatus.OK);
			}
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 블로그의 게시글 목록 조회 - 해당 블로그에 있는 전체 게시글 목록 조회
	 * 
	 * @param String Email
	 * @return ResponseEntity<Response> - 
	 * @exception RestException - NOT_FOUND
	 */
	@ApiOperation(value = "블로그의 임시저장 게시글 목록 조회", response = ResponseEntity.class)
	@GetMapping(value = "/blogs/{bid}/tmpposts/")
	public ResponseEntity readTmpPostListAll(@PathVariable int bid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		System.out.println("블로그 내 임시저장 게시글 목록 조회 ");
		if (jwtTokenProvider.validateToken(token)) {
			String author = jwtTokenProvider.getUserPk(token);
			List<Post> list = postService.listAllSavePost(bid, author);
			System.out.println(list);
			if(list.size()==0) {
				return new ResponseEntity<Response>(new Response(StatusCode.NOT_FOUND, ResponseMessage.SEARCH_ALLSAVEPOST_NONE),HttpStatus.OK);
			}else {
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_ALLSAVEPOST_SUCCESS, list),HttpStatus.OK);
			}
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 게시글 상세 조회 - 게시글 상세내용 조회
	 * 
	 * @param String Email
	 * @return ResponseEntity<Response> - 
	 * @exception RestException - NOT_FOUND
	 */
	@ApiOperation(value = "게시글 상세 조회", response = ResponseEntity.class)
	@GetMapping(value = "/blogs/{bid}/posts/{pid}")
	public ResponseEntity readPost(@PathVariable int bid, @PathVariable int pid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		System.out.println("게시글 조회 ");
		if (jwtTokenProvider.validateToken(token)) {
			Post post = postService.findByPid(pid);
			System.out.println(post);
			if(post != null) {
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_POST_SUCCESS, post),HttpStatus.OK);
			}else {
				return new ResponseEntity<Response>(new Response(StatusCode.NOT_FOUND, ResponseMessage.SEARCH_POST_FAIL, post),HttpStatus.OK);
			}
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 게시글 수정 - 게시글을 수정한다.
	 * 
	 * @param String Email
	 * @return ResponseEntity<Response> - 
	 * @exception RestException - NOT_FOUND
	 */
	@ApiOperation(value = "게시글 수정", response = ResponseEntity.class)
	@PutMapping(value = "/blogs/posts")
	public ResponseEntity updatePost(@RequestBody Post post, HttpServletRequest req) {
		String token = req.getHeader("auth");
		System.out.println("게시글 수정 ");
		System.out.println(post);
		
		// 토큰 유효성 검사 & 로그인한 사용자와 게시글 작성자 같은지 체크 
		if (jwtTokenProvider.validateToken(token)) {
			
			postService.updatePost(post);
			System.out.println("수정 성공");
			Post updatePost = postService.findByPid(post.getPid());
			return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.UPDATE_POST_SUCCESS, updatePost),HttpStatus.OK);
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 게시글 삭제 - 게시글을 삭제한다.
	 * 
	 * @param String Email
	 * @return ResponseEntity<Response> - 
	 * @exception RestException - NOT_FOUND
	 */
	@ApiOperation(value = "게시글 삭제", response = ResponseEntity.class)
	@DeleteMapping(value = "/blogs/posts/{pid}")
	public ResponseEntity deletePost(@PathVariable int pid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		System.out.println("게시글 삭제 ");
		System.out.println(pid);
		
//		 토큰 유효성 검사 & 로그인한 사용자와 게시글 작성자 같은지 체크 
		if (jwtTokenProvider.validateToken(token) && jwtTokenProvider.getUserPk(token).equals(postService.findByPid(pid).getAuthor())) {
			
			postService.deletePost(pid);
			System.out.println("삭제 성공");
			return new ResponseEntity<Response>(new Response(StatusCode.NO_CONTENT, ResponseMessage.DELETE_POST_SUCCESS),HttpStatus.OK);
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 게시글 Fork - 나의 블로그로 원하는 게시글을 fork한다.
	 * 
	 * @param Post post (int bid, int lcid, int mcid, int pid)
	 * @return ResponseEntity<Response> - CREATE_POST_SUCCESS
	 * @exception RestException - NOT_FOUND
	 */
	@ApiOperation(value = "게시글 Fork", response = ResponseEntity.class)
	@DeleteMapping(value = "/blogs/fork")
	public ResponseEntity blogFork(@RequestBody Post post, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);

			postService.forkPost(post);
			
			return new ResponseEntity<Response>(
					new Response(StatusCode.CREATED, ResponseMessage.CREATE_POST_SUCCESS), HttpStatus.OK);

		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	
}
