package com.web.blog.service;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.blog.domain.Users;
import com.web.blog.repository.UsersRepository;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

	private final UsersRepository userRepository;
//	private final PasswordEncoder passwordEncoder;

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

//	public void join(String email, String uid, String password) {
//		userRepository.save(Users.builder().email(email).uid(uid).password(passwordEncoder.encode(password))
//				.roles(Collections.singletonList("ROLE_USER")).build());
//	}
//	public void join(String email, String uid, String password) {
//		userRepository.save(Users.builder().email(email).uid(uid).password(passwordEncoder.encode(password))
//				.roles(Collections.singletonList("ROLE_USER")).build());
//	}
	public void join(Users user,String password) {
		userRepository.save(Users.builder().email(user.getEmail()).uid(user.getUid()).password(password)
				.roles(Collections.singletonList("ROLE_USER")).build());
	}

	public void tempPwdUpdate(String email, String password) {
		Optional<Users> updateUser = userRepository.findByEmail(email);
		updateUser.ifPresent(selectUser->{
			selectUser.setPassword(password);
			userRepository.save(selectUser);
		});
	}
	
//	public void userUpdate(Users user, String password) {
//		Optional<Users> updateUser = userRepository.findByEmail(user.getEmail());
//		updateUser.ifPresent(selectUser->{
//			selectUser.setUid(user.getUid());
//			selectUser.setPassword(password);
//			userRepository.save(selectUser);
//		});
//	}
	public void userUpdate(String email, String uid, String password) {
		Optional<Users> updateUser = userRepository.findByEmail(email);
		updateUser.ifPresent(selectUser->{
			selectUser.setUid(uid);
			selectUser.setPassword(password);
			userRepository.save(selectUser);
		});
	}
}