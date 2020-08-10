package com.web.blog.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.web.blog.domain.Blog;
import com.web.blog.domain.BlogFollow;
import com.web.blog.domain.Member;
import com.web.blog.domain.Users;
import com.web.blog.domain.Post;
import com.web.blog.domain.PostLike;
import com.web.blog.repository.BlogFollowRepository;
import com.web.blog.repository.BlogRepository;
import com.web.blog.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlogFollowService {

	private final BlogFollowRepository blogFollowRepository;
	private final BlogRepository blogRepository;
	private final UsersRepository userRepository;
	private final BlogService blogService;

	public void increaseBlogFollow(int bid, String email) {
		blogFollowRepository.save(BlogFollow.builder().bid(bid).email(email).build());
		Blog updateBlog = blogRepository.findByBid(bid);
		updateBlog.setFollowers(blogFollowRepository.countByBid(bid));
		blogRepository.save(updateBlog);
		// 팔로우 하면 블로그 멤버들의 경험치 상승
		List<Member> list = blogService.searchMember(bid);
		for(Member mem : list) {
			Optional<Users> user = userRepository.findByEmail(mem.getEmail());
			user.ifPresent(selectUser->{
				selectUser.setExp(selectUser.getExp()+5);
				userRepository.save(selectUser);
			});
		}
	}
	
	public void decreaseBlogFollow(int bid, String email) {
		blogFollowRepository.deleteByEmailAndBid(email, bid);
		Blog updateBlog = blogRepository.findByBid(bid);
		updateBlog.setFollowers(blogFollowRepository.countByBid(bid));
		blogRepository.save(updateBlog);
	}

	public boolean searchBlogFollow(int bid, String email) {
		List<BlogFollow> list = blogFollowRepository.findByBidAndEmail(bid, email);
		if(list.size()==1) {
			return true;
		}else {
			return false;
		}
	}
	
}