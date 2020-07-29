package com.web.blog.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.web.blog.domain.Tag;
import com.web.blog.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TagService {

	private final TagRepository tagRepository;

	public int createTag(String tname) {
		return tagRepository.save(Tag.builder()
				.tname(tname).build()).getTid();
	}
	public boolean checkTagName(String tname){
		return tagRepository.findByTname(tname).isPresent();
	}
	
	public int findByTname(String tname) {
		return tagRepository.findTidByTname(tname).getTid();
	}

}