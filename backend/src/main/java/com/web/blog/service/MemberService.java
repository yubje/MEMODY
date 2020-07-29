package com.web.blog.service;

import org.springframework.stereotype.Service;

import com.web.blog.domain.Member;
import com.web.blog.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	public void inviteMember(int bid, String email) {
		memberRepository.save(Member.builder().bid(bid).email(email).build());
	}

	
}