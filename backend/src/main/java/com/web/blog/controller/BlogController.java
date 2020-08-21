package com.web.blog.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.config.jwt.JwtTokenProvider;
import com.web.blog.domain.Blog;
import com.web.blog.domain.Users;
import com.web.blog.model.Response;
import com.web.blog.model.ResponseMessage;
import com.web.blog.model.RestException;
import com.web.blog.model.StatusCode;
import com.web.blog.repository.BlogRepository;
import com.web.blog.service.BlogFollowService;
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
 *			김형택, ver.0.1 , 2020-07-27, (First Commit)
 * </pre>
 * 
 * @author 김형택
 * @version 1.0, 2020-08-19, Update Blog Controller
 * @see None
 * 
 */
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BlogController {

	private final JwtTokenProvider 	jwtTokenProvider;
	private final BlogService 		blogService;
	private final TagService 		tagService;
	private final BlogTagService 	blogtagService;
	private final MemberService 	memberService;
	private final BlogFollowService blogFollowService;
	private final UserService		userService;
	private final BlogRepository 	blogRepository;
	
	/**
	 * 블로그 생성 - 사용자가 블로그를 생성하는 기능.
	 * 
	 * @param Blog String bTitle, String bSubTitle, String bContent
	 * @return ResponseEntity<Response> - StatusCode<br>
	 *         ResponseMessage(CREATE_BLOG_SUCCESS), HttpStatus<br>
	 * @exception CREATE_BLOG_FAIL - 블로그 최대 갯수 6개 초과 시
	 * @exception FORBIDDEN
	 * 
	 */
	@ApiOperation(value = "블로그 생성", response = ResponseEntity.class, notes = "사용자가 블로그를 생성할 수 있습니다.")
	@PostMapping("/blogs")
	public ResponseEntity createBlog(@RequestBody Map<String, String> blog, HttpServletRequest req) {

		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			int bid;
			String email = jwtTokenProvider.getUserPk(token);
			if (blogService.countBlogByUser(email)) {
				bid = blogService.createBlog(blog.get("btitle"), blog.get("bsubtitle"), blog.get("bcontent"), email);

				if (blog.get("hashtags") != null) {
					String hashtags[] = blog.get("hashtags").replaceAll(" ", "").trim().split("#");
					String tname;
					int tid;
					for (int i = 1; i < hashtags.length; i++) {
						tname = hashtags[i];
						if (!tagService.checkTagName(tname)) { // 존재하지 않는다면
							tid = tagService.createTag(tname);
							// blog_and_tag 테이블에 추가
						} else {
							// tag 테이블에서 tid 가져오고
							tid = tagService.findByTname(tname);
						}
						// blog_and_tag 테이블에 추가
						blogtagService.blogAndTag(bid, tid, tname);
					}
				}
				memberService.inviteMember(bid, email);
				return new ResponseEntity<Response>(
						new Response(StatusCode.CREATED, ResponseMessage.CREATE_BLOG_SUCCESS), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Response>(
						new Response(StatusCode.FORBIDDEN, ResponseMessage.CREATE_BLOG_FAIL), HttpStatus.FORBIDDEN);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 내 블로그 목록 조회 - 내가 참여하고 있는 블로그 조회
	 * 
	 * @param
	 * @return ResponseEntity<Response> - StatusCode<br>
	 *         ResponseMessage(SEARCH_MYBLOG_NONE,SEARCH_MYBLOG_SUCCESS)<br>
	 *         HttpStatus, data(블로그 목록 정보)
	 * @exception FORBIDDEN
	 */
	@ApiOperation(value = "내 블로그 목록 조회", response = ResponseEntity.class, notes = "내가 참여하고 있는 블로그를 조회합니다.")
	@GetMapping(value = "/blogs/")
	public ResponseEntity userBlogList(HttpServletRequest req) {
		String token = req.getHeader("auth");
		String email = jwtTokenProvider.getUserPk(token);
		if (jwtTokenProvider.validateToken(token)) {
			List<Blog> list = blogService.myBlogList(email);

			if (list.size() == 0) {
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_MYBLOG_NONE),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<Response>(
						new Response(StatusCode.OK, ResponseMessage.SEARCH_MYBLOG_SUCCESS, list), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 다른 사용자 블로그 목록 조회 - 다른 사용자가 참여하고 있는 블로그 조회
	 * 
	 * @param	email - 조회할 사용자의 email
	 * @return 	ResponseEntity<Response> - StatusCode<br>
	 *         	ResponseMessage(SEARCH_MYBLOG_NONE,SEARCH_MYBLOG_SUCCESS),HttpStatus<br>
	 *         	data(블로그 목록 정보)
	 */
	@ApiOperation(value = "다른 사용자 블로그 목록 조회", response = ResponseEntity.class, notes = "다른 사용자가 참여하고 있는 블로그를 조회합니다.")
	@GetMapping(value = "/blogs/list/{email}")
	public ResponseEntity anotherBlogList(@PathVariable String email) {
		List<Blog> list = blogService.myBlogList(email);

		if (list.size() == 0) {
			return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_MYBLOG_NONE),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(
					new Response(StatusCode.OK, ResponseMessage.SEARCH_MYBLOG_SUCCESS, list), HttpStatus.OK);
		}
	}

	/**
	 * 블로그 정보 조회 - 블로그 클릭 시, 메인화면(Home)
	 * 
	 * @param 	bid - 블로그 번호
	 * @return	ResponseEntity<Response> - StatusCode<br>
	 *        	ResponseMessage(SEARCH_BLOG_SUCCESS,SEARCH_BLOG_FAIL), HttpStatus<br>
	 *        	data(블로그 정보)
	 * @exception FORBIDDEN
	 */
	@ApiOperation(value = "블로그 정보 조회", response = ResponseEntity.class, notes = "블로그 클릭 시 블로그의 메인화면(Home)이 보여집니다.")
	@GetMapping(value = "/blogs/{bid}")
	public ResponseEntity blogInfo(@PathVariable int bid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			Blog blog = blogService.blogInfo(bid);

			if (blog != null) {
				return new ResponseEntity<Response>(
						new Response(StatusCode.OK, ResponseMessage.SEARCH_BLOG_SUCCESS, blog), HttpStatus.OK);
			} else {
				return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_BLOG_FAIL),
						HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 추천 블로그 목록 - 조회수 기반의 블로그를 추천.
	 * 
	 * @param
	 * @return ResponseEntity<Response> - StatusCode<br>
	 *         ResponseMessage(RECOMMEND_BLOG_SUCCESS), HttpStatus, data(블로그 정보 목록)
	 * 
	 */
	@ApiOperation(value = "추천 블로그 목록", response = ResponseEntity.class, notes = "조회수 기반으로 블로그를 추천합니다.")
	@GetMapping(value = "/blogs/views")
	public ResponseEntity recommendBlog() {
		List<Blog> list = blogService.recommendBlogs();
		return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.RECOMMEND_BLOG_SUCCESS, list),
				HttpStatus.OK);
	}


	/**
	 * 해쉬태그로 블로그 목록 조회 - 해쉬태그를 입력하여 해당 해쉬태그를 가진 블로그 목록을 조회
	 * 
	 * @param	hashtag - 해쉬태그 이름
	 * @return 	ResponseEntity<Response> - StatusCode<br>
	 *         	ResponseMessage(SEARCH_BLOG_SUCCESS), HttpStatus, data(블로그 목록 정보)
	 * 
	 */
	@ApiOperation(value = "해쉬태그로 블로그 목록 조회", response = ResponseEntity.class, notes = "해쉬태그를 입력하여 해당 해쉬태그를 가진 블로그 목록을 조회합니다.")
	@GetMapping(value = "/tags/{hashtag}/list")
	public ResponseEntity listByHashtag(@PathVariable String hashtag) {
		List<Blog> list = blogService.searchListByTag(hashtag);
		return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_BLOG_SUCCESS, list),
				HttpStatus.OK);
	}

	/**
	 * 블로그 이름으로 블로그 목록 조회 - 블로그 이름을 입력하여 해당 블로그 이름을 가진 블로그 목록을 조회
	 * 
	 * @param	btitle - 블로그 이름
	 * @return 	ResponseEntity<Response> - StatusCode<br>
	 *         	ResponseMessage(SEARCH_BLOG_SUCCESS), HttpStatus, data(블로그 목록 정보)
	 * 
	 */
	@ApiOperation(value = "블로그 이름으로 블로그 목록 조회", response = ResponseEntity.class, notes = "블로그 이름을 입력하여 해당 블로그 이름을 가진 블로그 목록을 조회합니다.")
	@GetMapping(value = "/blogs/{btitle}/list")
	public ResponseEntity listByBtitle(@PathVariable String btitle) {
		List<Blog> list = blogService.searchListByBlog(btitle);

		return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_BLOG_SUCCESS, list),
				HttpStatus.OK);
	}

	/**
	 * 블로그 정보 수정 - 해당 블로그 정보를 수정
	 * 
	 * @param	blog - 수정할 블로그 정보
	 * @return 	ResponseEntity<Response> - StatusCode<br>
	 *        	ResponseMessage(UPDATE_BLOG_SUCCESS,UPDATE_BLOG_FAIL), HttpStatus<br>
	 * @exception FORBIDDEN
	 */
	@ApiOperation(value = "블로그 정보 수정", response = ResponseEntity.class, notes = "블로그의 블로그정보를 수정합니다.")
	@PutMapping(value = "/blogs")
	public ResponseEntity blogUpdate(@RequestBody Map<String, String> blog, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);
			int bid = Integer.parseInt(blog.get("bid"));
			String btitle = blog.get("btitle");
			String bsubtitle = blog.get("bsubtitle");
			String bcontent = blog.get("bcontent");
			String changeTag = blog.get("hashtags").trim();

			if (blogService.updateBlog(user, btitle, bsubtitle, bcontent, changeTag, bid)) {
				String hashtags[] = changeTag.replaceAll(" ", "").split("#");
				String tname;
				int tid;

				blogtagService.deleteByBid(bid);
				for (int i = 1; i < hashtags.length; i++) {
					tname = hashtags[i];
					if (!tagService.checkTagName(tname)) { // 존재하지 않는다면
						tid = tagService.createTag(tname);
					} else {
						tid = tagService.findByTname(tname);
					}
					blogtagService.updateTag(bid, tid, tname);
				}

				return new ResponseEntity<Response>(
						new Response(StatusCode.CREATED, ResponseMessage.UPDATE_BLOG_SUCCESS), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Response>(
						new Response(StatusCode.FORBIDDEN, ResponseMessage.UPDATE_BLOG_FAIL), HttpStatus.FORBIDDEN);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 블로그 정보 삭제 - 해당 블로그 정보를 삭제
	 * 
	 * @param	bid - 삭제할 블로그 고유 ID값
	 * @return 	ResponseEntity<Response> - StatusCode<br>
	 *        	ResponseMessage(DELETE_BLOG_SUCCESS,DELETE_BLOG_FAIL), HttpStatus<br>
	 * @exception FORBIDDEN
	 */
	@ApiOperation(value = "블로그 정보 삭제", response = ResponseEntity.class, notes = "블로그를 삭제합니다.")
	@DeleteMapping(value = "/blogs/{bid}")
	public ResponseEntity blogDelete(@PathVariable int bid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String userEmail = jwtTokenProvider.getUserPk(token);
			Users user = userService.findByEmail(userEmail)
					.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
			if (!blogService.checkBlog(bid)) {
				return new ResponseEntity<Response>(
						new Response(StatusCode.FORBIDDEN, ResponseMessage.DELETE_BLOG_FAIL), HttpStatus.FORBIDDEN);
			} else if (blogService.deleteBlog(userEmail, bid, user.getRoles().get(0))) {
				return new ResponseEntity<Response>(
						new Response(StatusCode.NO_CONTENT, ResponseMessage.DELETE_BLOG_SUCCESS), HttpStatus.OK);
			} else {
				return new ResponseEntity<Response>(
						new Response(StatusCode.FORBIDDEN, ResponseMessage.DELETE_BLOG_FAIL), HttpStatus.FORBIDDEN);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 블로그 내 참여자 목록조회 - 블로그 내에 참여하고 있는 참여자들의 정보를 출력
	 * 
	 * @param	bid - 조회할 블로그 고유 ID값
	 * @return 	ResponseEntity<Response> - StatusCode<br>
	 *         	ResponseMessage(BLOG_MEMBER_SUCCESS,BLOG_MEMBER_FAIL), HttpStatus<br>
	 *         	list - 멤버 리스트
	 * @exception FORBIDDEN
	 */
	@ApiOperation(value = "블로그 내 참여자 목록조회", response = ResponseEntity.class, notes = "블로그 내에 참여하고 있는 참여자들의 정보를 출력합니다.")
	@GetMapping(value = "/blogs/{bid}/members")
	public ResponseEntity blogMember(@PathVariable int bid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);
			List<Users> list = blogService.searchMemberToUsers(bid);
			if (!blogService.checkBlog(bid)) {
				return new ResponseEntity<Response>(
						new Response(StatusCode.FORBIDDEN, ResponseMessage.BLOG_MEMBER_FAIL), HttpStatus.FORBIDDEN);
			} else {
				return new ResponseEntity<Response>(
						new Response(StatusCode.OK, ResponseMessage.BLOG_MEMBER_SUCCESS, list), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 블로그 내 참여자 추가 - 블로그 게시를 할 수 있도록 사용자를 추가한다
	 * 
	 * @param	bid - 참여자 추가할 블로그 고유 ID값 <br>
	 * 			member - 추가할 사용자 email
	 * @return	ResponseEntity<Response> - StatusCode<br>
	 *        	ResponseMessage(INVITE_MEMBER_SUCCESS,INVITE_MEMBER_FAIL), HttpStatus<br>
	 * @exception FORBIDDEN
	 */
	@ApiOperation(value = "블로그 내 참여자 추가", response = ResponseEntity.class, notes = "블로그에 게시글을 작성 할 수 있도록 사용자를 추가합니다.")
	@PostMapping(value = "/blogs/{bid}/members")
	public ResponseEntity inviteMember(@PathVariable int bid, @RequestBody Users member, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);
			if (!blogService.checkBlog(bid)) {
				return new ResponseEntity<Response>(
						new Response(StatusCode.FORBIDDEN, ResponseMessage.INVITE_MEMBER_FAIL), HttpStatus.FORBIDDEN);
			} else {
				blogService.inviteMember(bid, member.getEmail(), user);
				return new ResponseEntity<Response>(
						new Response(StatusCode.CREATED, ResponseMessage.INVITE_MEMBER_SUCCESS, user), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 블로그 내 참여자 삭제 - 블로그 내 사용자를 탈퇴시킨다.
	 * 
	 * @param	bid - 참여자 추가할 블로그 고유 ID값<br>
	 * 			member - 탈퇴시킨 사용자의 email
	 * @return 	ResponseEntity<Response> - StatusCode<br>
	 *        	ResponseMessage(DELETE_MEMBER_SUCCESS,DELETE_MEMBER_FAIL)<br>
	 *         	HttpStatus, data(멤버 리스트)
	 * 
	 */
	@ApiOperation(value = "블로그 내 참여자 삭제", response = ResponseEntity.class, notes = "블로그 내 사용자를 탈퇴시킵니다.")
	@DeleteMapping(value = "/blogs/{bid}/members")
	public ResponseEntity deleteMember(@PathVariable int bid, @RequestBody Users member, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);
			if (!blogService.checkBlog(bid)) {
				return new ResponseEntity<Response>(
						new Response(StatusCode.FORBIDDEN, ResponseMessage.DELETE_MEMBER_FAIL), HttpStatus.FORBIDDEN);
			} else {
				blogService.deleteMember(bid, member.getEmail(), user);
				return new ResponseEntity<Response>(
						new Response(StatusCode.CREATED, ResponseMessage.DELETE_MEMBER_SUCCESS), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 매니저 블로그 조회 - 내가 매니저인 블로그 번호 조회.
	 * 
	 * @return 	ResponseEntity<Response> - StatusCode<br>
	 *         	ResponseMessage(SEARCH_BLOG_SUCCESS), HttpStatus, data(블로그 리스트)
	 * 
	 */
	@ApiOperation(value = "manager 블로그", response = ResponseEntity.class, notes = "내가 매니저인 블로그 번호를 조회합니다.")
	@GetMapping(value = "/blogs/manager")
	public ResponseEntity managerBlog(HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String user = jwtTokenProvider.getUserPk(token);

			List<Blog> list = blogService.myBlog(user);
			return new ResponseEntity<Response>(new Response(StatusCode.OK, ResponseMessage.SEARCH_BLOG_SUCCESS, list),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 블로그 팔로우 - 블로그 팔로우하기
	 * 
	 * @param	Blog - 팔로우할 블로그 정보(bid)
	 * @return 	ResponseEntity<Response> - StatusCode<br>
	 *         	ResponseMessage(FOLLOW_BLOG_SUCCESS, FOLLOW_BLOG_FAIL), HttpStatus<br>
	 *         	loginuser - 팔로우 요청을 보낸 사용자 email
	 */
	@ApiOperation(value = "블로그 팔로우", response = ResponseEntity.class, notes = "블로그를 팔로우 합니다.")
	@PostMapping(value = "/blogs/follows")
	public ResponseEntity increaseBlogFollow(@RequestBody Blog blog, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String loginuser = jwtTokenProvider.getUserPk(token);
			if (!blogService.checkBlog(blog.getBid())) {
				return new ResponseEntity<Response>(
						new Response(StatusCode.FORBIDDEN, ResponseMessage.FOLLOW_BLOG_FAIL), HttpStatus.FORBIDDEN);
			} else {
				blogFollowService.increaseBlogFollow(blog.getBid(), loginuser);
				Blog newBlog = blogRepository.findByBid(blog.getBid());
				return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.FOLLOW_BLOG_SUCCESS, newBlog),
						HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 블로그 팔로우 취소 - 블로그 팔로우를 취소합니다.
	 * 
	 * @param	Blog - 팔로우 취소할 블로그 정보(bid)
	 * @return 	ResponseEntity<Response> - StatusCode<br>
	 *         	ResponseMessage(UNFOLLOW_BLOG_SUCCESS, UNFOLLOW_BLOG_FAIL), HttpStatus<br>
	 *         	loginuser - 언팔로우 요청을 보낸 사용자 email
	 */
	@ApiOperation(value = "블로그 팔로우 취소", response = ResponseEntity.class, notes = "블로그 팔로우를 취소합니다.")
	@DeleteMapping(value = "/blogs/follows")
	public ResponseEntity decreaseBlogFollow(@RequestBody Blog blog, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String loginuser = jwtTokenProvider.getUserPk(token);
			if (!blogService.checkBlog(blog.getBid())) {
				return new ResponseEntity<Response>(
						new Response(StatusCode.FORBIDDEN, ResponseMessage.UNFOLLOW_BLOG_FAIL), HttpStatus.FORBIDDEN);
			} else {
				blogFollowService.decreaseBlogFollow(blog.getBid(), loginuser);
				Blog newBlog = blogRepository.findByBid(blog.getBid());
				return new ResponseEntity<Response>(new Response(StatusCode.CREATED, ResponseMessage.UNFOLLOW_BLOG_SUCCESS, newBlog),
						HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 블로그 팔로우 조회 - 블로그를 팔로우 했는지 여부를 알려준다. 팔로우 했을경우 파란색 / 안했을경우 회색
	 * 
	 * @param	bid - 해당 블로그의 고유 id값
	 * @return 	ResponseEntity<Response> - StatusCode<br>
	 *         	ResponseMessage(SEARCH_BLOGFOLLOW_SUCCESS, SEARCH_BLOGFOLLOW_FAIL), HttpStatus<br>
	 *         	followed - 팔로우를 했는지 여부 (true/false)
	 * 
	 */
	@ApiOperation(value = "블로그 팔로우 조회", response = ResponseEntity.class, notes = "블로그를 팔로우 했는지 여부를 알려줍니다. 팔로우 했을경우 파란색 / 안했을경우 회색 ")
	@GetMapping(value = "/blogs/{bid}/follows")
	public ResponseEntity searchPostLike(@PathVariable int bid, HttpServletRequest req) {
		String token = req.getHeader("auth");
		if (jwtTokenProvider.validateToken(token)) {
			String loginuser = jwtTokenProvider.getUserPk(token);
			if (!blogService.checkBlog(bid)) {
				return new ResponseEntity<Response>(
						new Response(StatusCode.FORBIDDEN, ResponseMessage.SEARCH_BLOGFOLLOW_FAIL), HttpStatus.FORBIDDEN);
			} else {
				boolean followed = blogFollowService.searchBlogFollow(bid, loginuser);
				if (followed) {
					return new ResponseEntity<Response>(
							new Response(StatusCode.CREATED, ResponseMessage.SEARCH_BLOGFOLLOW_SUCCESS, followed),
							HttpStatus.OK);
				} else {
					return new ResponseEntity<Response>(
							new Response(StatusCode.CREATED, ResponseMessage.SEARCH_BLOGFOLLOW_SUCCESS, followed),
							HttpStatus.OK);
				}
			}
		} else {
			return new ResponseEntity<Response>(new Response(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN),
					HttpStatus.FORBIDDEN);
		}
	}
}
