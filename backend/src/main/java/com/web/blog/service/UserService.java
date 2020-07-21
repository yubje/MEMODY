package com.web.blog.service;

import com.web.blog.model.UserDto;

public interface UserService {
	
	/**
	 * 회원가입 - userId 겹치는지 확인하고 회원가입시키기
	 * @param User	회원가입할 정보
	 * @return		회원가입 성공 여부, 회원가입 성공시 true return
	 */
	public boolean register(UserDto user);
	
	/**
	 * 이메일 체크 - userEmail로 이메일 중복 체크 
	 * @param userEmail 사용자 이메일
	 * @return 해당 Email의 사용자 return, 없을 경우 null return
	 */
	public UserDto emailcheck(String email);
	
	/**
	 * 회원정보 조회 	
	 * @param userId	id로 사용자 검색
	 * @return			해당 user 반환
	 */
	public UserDto search(UserDto user);
	
	
	/**
	 * 회원정보 수정 - userId로 회원찾아서 정보수정
	 * @param User	수정할 정보
	 * @return		수정 성공 여부, 수정 성공시 true return
	 */
	public boolean modify(UserDto user);
	
	/**
	 * 회원 탈퇴
	 * @param userId 	id로 사용자 검색
	 * @return 			삭제 성공 여부, 삭제 성공시 false return
	 */
	public boolean delete(UserDto user);
	
	/**
	 * 로그인 - userId와 userPw로 회원 확인 
	 * @param userId 사용자 아이디
	 * @param uwerPw 사용자 패스워드
	 * @return 로그인 성공 여부, 로그인 성공시 true return
	 */
	public UserDto login(UserDto user);

	boolean send(String subject, String text, String from, String to, String filePath);
}
