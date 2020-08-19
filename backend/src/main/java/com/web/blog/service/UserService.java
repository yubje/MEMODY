package com.web.blog.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.blog.domain.Users;
import com.web.blog.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

	private final UsersRepository userRepository;
	
	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	}

	@Transactional
	public Optional<Users> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Transactional
	public void deleteUser(String email) {
		userRepository.deleteByEmail(email);
	}

	public void join(Users user,String password) {
		userRepository.save(Users.builder().email(user.getEmail()).uid(user.getUid()).password(password)
				.roles(Collections.singletonList("ROLE_USER")).build());
	}

	public void pwdUpdate(String email, String password) {
		Optional<Users> updateUser = userRepository.findByEmail(email);
		updateUser.ifPresent(selectUser->{
			selectUser.setPassword(password);
			userRepository.save(selectUser);
		});
	}
	
	public void userUpdate(String email, String uid, String password) {
		Optional<Users> updateUser = userRepository.findByEmail(email);
		updateUser.ifPresent(selectUser->{
			selectUser.setUid(uid);
			selectUser.setPassword(password);
			userRepository.save(selectUser);
		});
	}
	
	@Transactional
	public Optional<Users> findByUid(String uid) {
		return userRepository.findByUid(uid);
	}

	public void profileUpdate(String email,String url) {
		Optional<Users> user = userRepository.findByEmail(email);
		userRepository.save(Users.builder().email(email).uid(user.get().getUid()).password(user.get().getPassword()).profile(url).roles(Collections.singletonList("ROLE_USER")).build());
		
	}
	
	public List<Users> findAll(){
//		return userRepository.findAllByOrderByExpDesc();
		return userRepository.findTop10ByOrderByExpDesc();
	}
	
	public boolean send(String subject, String text, String from, String to, String filePath) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);

		return true;
	}

	public List<Users> searchListByNickname(String uid){
		return userRepository.findByUidContaining(uid);
	}

	public List<Users> searchAllUsers(String roles){
		List<Users> list = userRepository.findAllByOrderByEmail();
		List<Users> result = new ArrayList<Users>();
		for(Users user : list) {
			if(user.getRoles().get(0).equals(roles)) {
				System.out.println(user.getRoles().get(0));
				result.add(user);
			}
		}
		return result;
	}
}
