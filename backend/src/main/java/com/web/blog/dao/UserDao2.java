package com.web.blog.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.web.blog.model.UserDto;

@Mapper
public interface UserDao2 {

	/**
	 * 회원가입 - email 겹치는지 확인하고 회원가입시키기
	 * @param User	회원가입할 정보
	 * @return		회원가입 성공 여부, 회원가입 성공시 true return
	 */
	public boolean register(UserDto user) throws SQLException;
	
	/**
	 * 이메일 체크 - userEmail로 이메일 중복 체크 
	 * @param userEmail 사용자 이메일
	 * @return 해당 Email의 사용자 return, 없을 경우 null return
	 */
	public UserDto emailcheck(String email) throws SQLException;
	
	/**
	 * 회원정보 조회 - 	userdto로 사용자 검색
	 * @param userdto	userdto로 사용자 검색
	 * @return			해당 user 반환
	 */
	public UserDto search(UserDto user) throws SQLException;
	
	/**
	 * 회원정보 수정 - userdto로 회원찾아서 정보수정
	 * @param User	수정할 정보
	 * @return		수정 성공 여부, 수정 성공시 true return
	 */
	public boolean modify(UserDto user) throws SQLException;
	
	/**
	 * 회원 탈퇴
	 * @param userdto 	id로 사용자 검색
	 * @return 			삭제 성공 여부, 삭제 성공시 false return
	 */
	public boolean delete(UserDto user) throws SQLException;
	
	/**
	 * 로그인 - userId와 userPw로 회원 확인 
	 * @param userEmail 사용자 이메일
	 * @param uwerPw 사용자 패스워드
	 * @return 로그인된 사용자, 로그인 성공시 해당 사용자 정보 return
	 */
	public UserDto login(UserDto user) throws SQLException;
}
