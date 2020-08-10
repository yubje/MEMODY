package com.web.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web.blog.domain.Blog;
import com.web.blog.domain.BlogFollow;
import com.web.blog.domain.Post;
import com.web.blog.domain.PostLike;
import com.web.blog.repository.BlogFollowRepository;
import com.web.blog.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlogFollowService {

	private final BlogFollowRepository blogFollowRepository;
	private final BlogRepository blogRepository;

	public void increaseBlogFollow(int bid, String email) {
		blogFollowRepository.save(BlogFollow.builder().bid(bid).email(email).build());
		Blog updateBlog = blogRepository.findByBid(bid);
		updateBlog.setFollowers(blogFollowRepository.countByBid(bid));
		blogRepository.save(updateBlog);
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