package com.web.blog.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
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
import com.web.blog.domain.Fork;
import com.web.blog.domain.Post;
import com.web.blog.domain.Users;
import com.web.blog.model.Response;
import com.web.blog.model.ResponseMessage;
import com.web.blog.model.RestException;
import com.web.blog.model.StatusCode;
import com.web.blog.service.PostLikeService;
import com.web.blog.service.PostService;
import com.web.blog.service.UserService;
import com.web.blog.util.FileUpload;
import com.web.blog.util.S3Util;

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
@RequestMapping("/api")
public class PostController {

	private final 	JwtTokenProvider 	jwtTokenProvider;
	private final 	PostService 		postService;
	private final 	PostLikeService 	postLikeService;
	private final	UserService			userService;

	@Value("${cloud.aws.s3.bucket}")
	String bucketName;
	@Value("${cloud.aws.credentials.accessKey}")
	String accessKey; // 엑세스 키
	@Value("${cloud.aws.credentials.secretKey}")
	String secretKey;
	
	/**
	 * 게시글 작성 - 사용자가 게시글을 작성하는 기능. 
	 * 
	 * @param Post String lcid, String mcid, String pTitle, String pContent, String pType
	 * @return ResponseEntity<Response> - StatusCode, ResponseMessage(CREATE_BLOG_SUCCESS), HttpStatus
	 * @exception FORBIDDEN
	 * 			  
	 */
	@ApiOperation(value = "게시글 작성", response = ResponseEntity.class, notes = "사용자가 게시글을 작성합니다.")
	@PostMapping("/blogs/{bid}/posts")
	public ResponseEntity createPost(@PathVariable int bid, @RequestBody Map<String,String> post, HttpServletRequest req) {
		String token = req.getHeader("auth");
		String uemail = jwtTokenProvider.getUserPk(token);
		if (jwtTokenProvider.validateToken(token)) {
			String input = post.get("pcontent");
			if(input.contains("img")) {
				String base64String = null;
				String[] inputArr = post.get("pcontent").split("\"");
				for(int i=0;i<inputArr.length;i++) {
					if(inputArr[i].contains("data:image/")) {
						base64String = inputArr[i];
						String[] strings = base64String.split(",");
				        String extension;
				        switch (strings[0]) {//check image's extension
				            case "data:image/jpeg;base64":
				                extension = "jpeg";
				                break;
				            case "data:image/png;base64":
				                extension = "png";
				                break;
				            default://should write cases for more images types
				                extension = "jpg";
				                break;
				        }
				        //convert base64 string to binary data
				        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
				        
				        String uploadpath = "post";
						S3Util s3 = new S3Util(accessKey, secretKey);
						String img_path;
						String url = null;
						try {
							img_path = FileUpload.uploadFile(uploadpath, post.get("mcid")+"_"+uemail, data,bucketName, accessKey, secretKey);
							String img_url = img_path;
							url = s3.getFileURL(bucketName, uploadpath+img_url);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						inputArr[i] = url;
					}
				}
				String result = "";
				for(int i=0;i<inputArr.length;i++) {
					result+=inputArr[i];
					if(i!=inputArr.length-1) {
						result+="'";
					}
				}
				input = result;
			}
			int pid;
			String email = jwtTokenProvider.getUserPk(token);
			Post temp = null;
			if(post.get("ptype")=="") {
				temp = new Post(bid, Integer.parseInt(post.get("lcid")), Integer.parseInt(post.get("mcid")), post.get("ptitle")
						, input, email,email, LocalDateTime.now(), LocalDateTime.now(), null, 0);
				if(!post.get("pid").equals("")) {
					Users user = userService.findByEmail(email)
							.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
					postService.deletePost(email, Integer.parseInt(post.get("pid")), user.getRoles().get(0));
				}
			}else {
				temp = new Post(bid, Integer.parseInt(post.get("lcid")), Integer.parseInt(post.get("mcid")), post.get("ptitle")
						, input, email,email, LocalDateTime.now(), LocalDateTime.now(), post.get("ptype"), 0);
			}
//			pid = postService.createPost(temp);
			postService.createPost(temp);
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
	@ApiOperation(value = "블로그의 게시글 목록 조회", response = ResponseEntity.class, notes = "해당 블로그에 있는 전체 게시글 목록 조회합니다.")
	@GetMapping(value = "/blogs/{bid}/posts")
	public ResponseEntity readPostListAll(@PathVariable int bid, @PageableDefault(size=10) Pageable pageable, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			Page<Post> list = postService.listAllPost(bid, pageable);
			if(list.getSize()==0) {
				return new ResponseEntity<Response>(new Response(StatusCode.NOT_FOUND, ResponseMessage.SEARCH_ALLPOST_NONE, list),HttpStatus.OK);
			}else {
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_ALLPOST_SUCCESS, list),HttpStatus.OK);
			}
		}else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 블로그의 임시저장 게시글 목록 조회 - 해당 블로그에 있는 임시저장 게시글 목록 조회
	 * 
	 * @param String Email
	 * @return ResponseEntity<Response> - 
	 * @exception RestException - NOT_FOUND
	 */
	@ApiOperation(value = "블로그의 임시저장 게시글 목록 조회", response = ResponseEntity.class, notes = "해당 블로그에 있는 임시저장 게시글 목록 조회합니다.")
	@GetMapping(value = "/blogs/{bid}/tmpposts/")
	public ResponseEntity readTmpPostListAll(@PathVariable int bid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String author = jwtTokenProvider.getUserPk(token);
			List<Post> list = postService.listAllSavePost(bid, author);
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
	@ApiOperation(value = "게시글 상세 조회", response = ResponseEntity.class, notes = "게시글의 상세내용을 조회합니다.")
	@GetMapping(value = "/blogs/{bid}/posts/{pid}")
	public ResponseEntity readPost(@PathVariable int bid, @PathVariable int pid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			Post post = postService.postInfo(pid);
			Users manager = userService.findByEmail(post.getManager())
					.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
			String managerNickname = manager.getUid();
			Users author = userService.findByEmail(post.getAuthor())
					.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
			String authorNickname = author.getUid();
			Map<String, Object> map = new HashMap<>();
			map.put("post", post);
			map.put("managerNickname", managerNickname);
			map.put("authorNickname", authorNickname);
			map.put("postManager", manager);
			if(post != null) {
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_POST_SUCCESS, map),HttpStatus.OK);
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
	@ApiOperation(value = "게시글 수정", response = ResponseEntity.class, notes = "게시글을 수정합니다.")
	@PutMapping(value = "/blogs/posts")
	public ResponseEntity updatePost(@RequestBody Map<String,String> post, HttpServletRequest req) {
		String token = req.getHeader("auth");
		String email = jwtTokenProvider.getUserPk(token);
		if (jwtTokenProvider.validateToken(token) && email.equals(postService.findByPid(Integer.parseInt(post.get("pid"))).getManager())) {
			String input = post.get("pcontent");
			
			if(input.contains("img")) {
				String base64String = null;
				String[] inputArr = input.split("\"");
				for(int i=0;i<inputArr.length;i++) {
					if(inputArr[i].contains("data:image/")) {
						base64String = inputArr[i];
						String[] strings = base64String.split(",");
				        String extension;
				        switch (strings[0]) {//check image's extension
				            case "data:image/jpeg;base64":
				                extension = "jpeg";
				                break;
				            case "data:image/png;base64":
				                extension = "png";
				                break;
				            default://should write cases for more images types
				                extension = "jpg";
				                break;
				        }
				        //convert base64 string to binary data
				        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
				        
				        String uploadpath = "post";
						S3Util s3 = new S3Util(accessKey, secretKey);
						String img_path;
						String url = null;
						try {
							img_path = FileUpload.uploadFile(uploadpath, post.get("pid")+"_"+email, data,bucketName, accessKey, secretKey);
							String img_url = img_path;
							url = s3.getFileURL(bucketName, uploadpath+img_url);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						inputArr[i] = url;
					}
				}
				String result = "";
				for(int i=0;i<inputArr.length;i++) {
					result+=inputArr[i];
					if(i!=inputArr.length-1) {
						result+="'";
					}
				}
				input = result;
			}
			Post result = postService.findByPid(Integer.parseInt(post.get("pid")));
			result.setPtitle(post.get("ptitle"));
			String content = result.getPcontent();
			result.setPcontent(input);
			
			postService.updatePost(content,result,bucketName,accessKey, secretKey);
			Post updatePost = postService.findByPid(Integer.parseInt(post.get("pid")));
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
	@ApiOperation(value = "게시글 삭제", response = ResponseEntity.class, notes = "게시글을 삭제합니다.")
	@DeleteMapping(value = "/blogs/posts/{pid}")
	public ResponseEntity deletePost(@PathVariable int pid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String userEmail = jwtTokenProvider.getUserPk(token);
			Users user = userService.findByEmail(userEmail)
					.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
			if(postService.deletePost(userEmail, pid, user.getRoles().get(0))) {
				
				return new ResponseEntity<Response>(new Response(StatusCode.NO_CONTENT, ResponseMessage.DELETE_POST_SUCCESS),HttpStatus.OK);
			}else {
				return new ResponseEntity<Response>(new Response(StatusCode.NO_CONTENT, ResponseMessage.DELETE_POST_FAIL),HttpStatus.OK);
			}
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
	@ApiOperation(value = "게시글 Fork", response = ResponseEntity.class, notes = "나의 블로그로 원하는 게시글을 fork합니다.")
	@PostMapping(value = "/blogs/fork")
	public ResponseEntity blogFork(@RequestBody Post post, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);

			Users member = userService.findByEmail(user)
					.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
			
			postService.forkPost(post,user,member.getUid());
			
			return new ResponseEntity<Response>(
					new Response(StatusCode.OK, ResponseMessage.CREATE_POST_SUCCESS), HttpStatus.OK);

		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	
	@ApiOperation(value = "게시글 Fork한 사용자 리스트", response = ResponseEntity.class)
	@GetMapping(value = "/blogs/fork/{pid}")
	public ResponseEntity blogForkUserList(@PathVariable int pid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			List<Fork> list = postService.forkList(pid);
			return new ResponseEntity<Response>(
					new Response(StatusCode.OK, ResponseMessage.FORK_USER_LIST_SUCCESS,list), HttpStatus.OK);

		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	
	
	/**
	 * 게시글 좋아요 - 게시글에 좋아요를 누르면 좋아요가 증가한다.
	 * 
	 */
	@ApiOperation(value = "게시글 좋아요 증가", response = ResponseEntity.class, notes = "게시글에 좋아요를 누르면 좋아요가 증가합니다.")
	@PostMapping(value = "/posts/likes")
	public ResponseEntity increasePostLike(@RequestBody Post post, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String loginuser = jwtTokenProvider.getUserPk(token);
			if(!postService.checkPost(post.getPid())){
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.SEARCH_POST_FAIL),
						HttpStatus.FORBIDDEN);
			}else {
				postLikeService.increasePostLike(post.getPid(), loginuser);
				return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.LIKE_POST_SUCCESS, loginuser),
						HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	/**
	 * 게시글 좋아요 취소 - 게시글에 좋아요를 다시 누르면 좋아요가 취소된다.
	 * 
	 */
	@ApiOperation(value = "게시글 좋아요 취소", response = ResponseEntity.class, notes = "게시글에 좋아요를 다시 누르면 좋아요가 취소됩니다.")
	@DeleteMapping(value = "/posts/likes")
	public ResponseEntity decreasePostLike(@RequestBody Post post, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String loginuser = jwtTokenProvider.getUserPk(token);
			if(!postService.checkPost(post.getPid())){
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.SEARCH_POST_FAIL),
						HttpStatus.FORBIDDEN);
			}else {
				postLikeService.decreasePostLike(post.getPid(), loginuser);
				return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.UNLIKE_POST_SUCCESS, loginuser),
						HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	
	// 상세 게시글 조회시 내가 좋아요 했는지 안했는지 알기 위한 기능 필요함!
	/**
	 * 게시글 좋아요 조회 - 게시글에 좋아요 했는지 여부를 알려준다. 좋아요 했을경우 빨간하트 / 안했을경우 회색하트
	 * 
	 */
	@ApiOperation(value = "게시글 좋아요 조회", response = ResponseEntity.class, notes = "게시글에 좋아요 했는지 여부를 알려줍니다. 좋아요 했을경우 빨간하트 / 안했을경우 회색하트")
	@GetMapping(value = "/posts/{pid}/likes")
	public ResponseEntity searchPostLike(@PathVariable int pid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String loginuser = jwtTokenProvider.getUserPk(token);
			if(!postService.checkPost(pid)){
				return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.SEARCH_POST_FAIL),
						HttpStatus.FORBIDDEN);
			}else {
				boolean liked = postLikeService.searchPostLike(pid, loginuser);
				if(liked) {
					return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.SEARCH_POSTLIKE_SUCCESS, liked),
							HttpStatus.OK);
				}else {
					return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.SEARCH_POSTLIKE_SUCCESS, liked),
							HttpStatus.OK);
				}
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
	
	
}
