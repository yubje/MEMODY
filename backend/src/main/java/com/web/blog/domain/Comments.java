package com.web.blog.domain;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Comments")
public class Comments {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int cmid;
	
	@Column(nullable = false)
	private int pid;

	@Column(length = 200, nullable = false)
	private String comment;
	
	@Column(length = 45, nullable = false)
	private String email;
	
	@Column
	private LocalDateTime commentTime;
	
	@Column
	private LocalDateTime updateTime;
	
	@Builder
	public Comments(int pid, String comment, String email, LocalDateTime commentTime, LocalDateTime updateTime) {
		this.pid = pid;
		this.comment = comment;
		this.email = email;
		this.commentTime = commentTime;
		this.updateTime = updateTime;
	}

	public void setComment(String comment) {
    	this.comment = comment;
    }
	
	public void setUpdate_time(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Comments [cmid=" + cmid + ", pid=" + pid + ", comment=" + comment + ", email=" + email
				+ ", comment_time=" + commentTime + ", update_time=" + updateTime + "]";
	}
	
}
