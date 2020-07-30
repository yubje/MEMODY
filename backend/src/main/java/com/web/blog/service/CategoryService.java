package com.web.blog.service;

import org.springframework.stereotype.Service;

import com.web.blog.domain.Blog;
import com.web.blog.domain.LCategory;
import com.web.blog.domain.MCategory;
import com.web.blog.repository.BlogRepository;
import com.web.blog.repository.LCategoryRepository;
import com.web.blog.repository.MCategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {

	private final BlogRepository		blogRepository;
	private final LCategoryRepository 	lcategoryRepository;
	private final MCategoryRepository 	mcategoryRepository;
	
	public boolean checkCategory(int lcid) {
		LCategory category = lcategoryRepository.findByLcid(lcid);
		if (category == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean createLCategory(String user,LCategory category) {
		Blog blog = blogRepository.findByBid(category.getBid());
		if (user.equals(blog.getManager())) {
			lcategoryRepository.save(LCategory.builder()
					.bid(category.getBid())
					.large_dir(category.getLarge_dir())
					.build());
			return true;
		} else {
			return false;
		}
	}
	public boolean createMCategory(String user,MCategory category) {
		LCategory lcategory = lcategoryRepository.findByLcid(category.getLcid());
		Blog blog = blogRepository.findByBid(lcategory.getBid());
		if (user.equals(blog.getManager())) {
			mcategoryRepository.save(MCategory.builder()
					.lcid(category.getLcid())
					.medium_dir(category.getMedium_dir())
					.build());
			return true;
		} else {
			return false;
		}
	}

	public boolean updateLCategory(String user,LCategory category) {
		Blog blog = blogRepository.findByBid(category.getBid());
		if (user.equals(blog.getManager())) {
			lcategoryRepository.save(LCategory.builder()
					.lcid(category.getLcid())
					.bid(category.getBid())
					.large_dir(category.getLarge_dir())
					.build());
			return true;
		} else {
			return false;
		}
	}
	public boolean updateMCategory(String user,MCategory category) {
		LCategory lcategory = lcategoryRepository.findByLcid(category.getLcid());
		Blog blog = blogRepository.findByBid(lcategory.getBid());
		if (user.equals(blog.getManager())) {
			mcategoryRepository.save(MCategory.builder()
					.mcid(category.getMcid())
					.lcid(category.getLcid())
					.medium_dir(category.getMedium_dir())
					.build());
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteLCategory(String user,LCategory category) {
		
		Blog blog = blogRepository.findByBid(category.getBid());
		if (user.equals(blog.getManager())) {
			lcategoryRepository.deleteByLcid(category.getLcid());
			return true;
		} else {
			return false;
		}
	}
	public boolean deleteMCategory(String user,MCategory category) {
		LCategory lcategory = lcategoryRepository.findByLcid(category.getLcid());
		Blog blog = blogRepository.findByBid(lcategory.getBid());
		if (user.equals(blog.getManager())) {
			mcategoryRepository.deleteByMcid(category.getMcid());
			return true;
		} else {
			return false;
		}
	}
}