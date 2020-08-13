package com.web.blog.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.PostLike;
import com.web.blog.domain.Users;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
	
	List<Users> findByEmail(String email);
	List<Users> findByPid(int pid);
	
	@Transactional
	void deleteByEmailAndPid(String email, int pid);
	
	int countByPid(int pid);
	
	List<PostLike> findByPidAndEmail(int pid, String email);

}