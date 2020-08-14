package com.web.blog.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.web.blog.domain.Blog;
import com.web.blog.domain.BlogFollow;
import com.web.blog.domain.Blogtag;
import com.web.blog.domain.Member;
import com.web.blog.repository.BlogFollowRepository;
import com.web.blog.repository.BlogRepository;
import com.web.blog.repository.BlogTagRepository;
import com.web.blog.repository.MemberRepository;
import com.web.blog.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlogService {

	private final BlogRepository blogRepository;
	private final MemberRepository memberRepository;
	private final BlogTagRepository blogtagRepository;
	private final TagRepository tagRepository;
	private final BlogFollowRepository blogFollowRepository;
	
	private final String ADMIN = "ROLE_ADMIN";

	public int createBlog(String title,String subtitle,String content, String email) {
		return blogRepository.save(Blog.builder().btitle(title).bsubtitle(subtitle)
				.bcontent(content).manager(email).build()).getBid();
	}

	public boolean countBlogByUser(String email) {
		if (blogRepository.countByManager(email) > 6) {
			return false;
		} else {
			return true;
		}
	}

	// 내가 manager인 블로그 번호 조회
	public List<Blog> myBlog(String email){
		return blogRepository.findByManager(email);
	}
	
	// 내 블로그 목록 조회
	public List<Blog> myBlogList(String email) {
		List<Member> list = memberRepository.findByEmail(email);
		List<Blog> result = new ArrayList<Blog>();
		for (Member member : list) {
			int bid = member.getBid();
			Blog blog = blogRepository.findByBid(bid);

			if(blog.getHashtags().size()==0) {
				List<Blogtag> hashtags = blogtagRepository.findByBid(bid);
				for (Blogtag tag : hashtags) {
					blog.addHashTag(tag);
				}
			}
			
			if(blog.getMember().size()==0) {
				List<Member> members = memberRepository.findByBid(bid);
				for(Member mem:members) {
					blog.addMember(mem);
				}
			}
			
			result.add(blog);
		}
		return result;
	}

	// 내가 팔로우한 블로그 목록 조회
	public List<Blog> followBlogList(String email) {
		List<BlogFollow> list = blogFollowRepository.findByEmail(email);
		List<Blog> result = new ArrayList<Blog>();
		for (BlogFollow blogFollow : list) {
			int bid = blogFollow.getBid();
			Blog blog = blogRepository.findByBid(bid);
			
			if(blog.getFollower().size()==0) {
				List<BlogFollow> followers = blogFollowRepository.findByBid(bid);
				for(BlogFollow fol:followers) {
					blog.addFollower(fol);
				}
			}
			
			result.add(blog);
		}
		return result;
	}

	public boolean checkBlog(int bid) {
		Blog blog = blogRepository.findByBid(bid);
		if (blog == null) {
			return false;
		} else {
			return true;
		}
	}

	public Blog blogInfo(int bid) {
		Blog blog = blogRepository.findByBid(bid);
		blog.setViews(blog.getViews() + 1);
		blogRepository.save(blog);

		List<Blogtag> hashtags = blogtagRepository.findByBid(bid);
		for (Blogtag tag : hashtags) {
			blog.addHashTag(tag);
		}
		return blog;
	}

	public List<Blog> recommendBlogs() {
		List<Blog> list = blogRepository.findAll();
		Collections.sort(list, new Comparator<Blog>() {
			@Override
			public int compare(Blog o1, Blog o2) {
				return o2.getViews() - o1.getViews();
			}
		});
		int len = list.size();

		for (int i = 0; i < len; i++) {
			List<Blogtag> hashtags = blogtagRepository.findByBid(list.get(i).getBid());
			for (Blogtag tag : hashtags) {
				list.get(i).addHashTag(tag);
			}
			
			List<Member> members = memberRepository.findByBid(list.get(i).getBid());
			for(Member mem:members) {
				list.get(i).addMember(mem);
			}
//			System.out.println(list.get(i).getHashtags());
//			System.out.println(list.get(i).getMember());
			
		}

		if (len > 6) {
			return list.subList(0, 6);
		} else {
			return list;
		}
	}

	// 수정
	// blogService.updateBlog(user, changeBlog,changeTag,bid))
	public boolean updateBlog(String user,String btitle,String bsubtitle,String bcontent, String changeTag, int bid) {
		Blog blog = blogRepository.findByBid(bid);
		if (user.equals(blog.getManager())) {
			System.out.println("수정 시작");
			blog.setBtitle(bsubtitle);
			blog.setBcontent(bcontent);
			blogRepository.save(blog);

			return true;
		} else {
			return false;
		}
	}

	// 삭제
	public boolean deleteBlog(String user, int bid, String role) {
		Blog blog = blogRepository.findByBid(bid);
		if (user.equals(blog.getManager()) | role.equals(ADMIN)) {
//				blogRepository.deleteById(bid);
			blogRepository.deleteByBid(bid);
			return true;
		} else {
			return false;
		}

	}

	// 블로그 내 멤버 조회
	public List<Member> searchMember(int bid) {
		return memberRepository.findByBid(bid);
	}

	// 블로그 내 멤버 추가
	// 매니저만 가능
	public boolean inviteMember(int bid, String email, String user) {
		String manager = blogRepository.findByBid(bid).getManager();

		if (user.equals(manager)) {
			memberRepository.save(Member.builder().bid(bid).email(email).build());
		} else {
			return false;
		}
		return true;
	}

	// 블로그 내 멤버 삭제
	// 매니저만 가능
	public boolean deleteMember(int bid, String email, String user) {
		String manager = blogRepository.findByBid(bid).getManager();

		if (user.equals(manager)) {
			memberRepository.deleteByEmail(email);
		} else {
			return false;
		}
		return true;
	}
	
	// 블로그 이름으로 블로그 목록 검색
	public List<Blog> searchListByBlog(String bname){
		List<Blog> list = blogRepository.findDistinctByBtitleContaining(bname);
		List<Blog> result = new ArrayList<Blog>();
		
		for(Blog blog:list) {
			int bid = blog.getBid();
			List<Blogtag> hashtags = blogtagRepository.findByBid(bid);
			for (Blogtag tag : hashtags) {
				System.out.println(tag);
				blog.addHashTag(tag);
			}
			result.add(blog);
		}
		return result;
	}
	
	// 해쉬태그 이름으로 블로그 목록 검색
	public List<Blog> searchListByTag(String tname){
		List<Blogtag> list = blogtagRepository.findDistinctByTnameContaining(tname);
		if(list.size()==0) {
			return null;
		}else {
			List<Blog> result = new ArrayList<Blog>();
			for(Blogtag tag:list) {
				Blog blog = blogRepository.findByBid(tag.getBid());
				List<Blogtag> hashtags = blogtagRepository.findByBid(tag.getBid());
				for (Blogtag tags : hashtags) {
					blog.addHashTag(tags);
				}
				result.add(blog);
			}
			return result;
		}
	}

}