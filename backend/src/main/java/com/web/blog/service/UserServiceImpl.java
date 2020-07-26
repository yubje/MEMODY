package com.web.blog.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.web.blog.BlogException;
import com.web.blog.dao.UserDao2;
import com.web.blog.model.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao2 dao;
	
	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public UserDto login(UserDto user) {
		try {
			System.out.println(user);
			UserDto dto = dao.login(user);
			System.out.println(dto);
			return dto;
		} catch (SQLException e) {
			throw new BlogException("로그인 정보 조회중 오류 발생");
		}
	}

	@Override
	public boolean register(UserDto user) {
		try {
			return dao.register(user);
		} catch (SQLException e) {
			throw new BlogException("회원 정보 등록중 오류 발생");
		}
	}

	@Override
	public UserDto search(UserDto user) {
		try {
			return dao.search(user);
		} catch (SQLException e) {
			throw new BlogException("회원 정보 조회중 오류 발생");
		}
	}

	@Override
	public boolean modify(UserDto user) {
		try {
			return dao.modify(user);
		} catch (SQLException e) {
			throw new BlogException("회원 정보 수정중 오류 발생");
		}
	}

	@Override
	public boolean delete(UserDto user) {
		try {
			return dao.delete(user);
		} catch (SQLException e) {
			throw new BlogException("회원 정보 삭제중 오류 발생");
		}
	}

	@Override
	public UserDto emailcheck(String email) {
		try {
			return dao.emailcheck(email);
		} catch (SQLException e) {
			throw new BlogException("이메일 체크 중 오류 발생");
		}
	}

	@Override
	public boolean send(String subject, String text, String from, String to, String filePath) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);

		return true;
	}
}
