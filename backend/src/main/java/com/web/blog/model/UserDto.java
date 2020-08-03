package com.web.blog.model;

import java.time.LocalDateTime;

public class UserDto {
	private String uid;
	private String email;
	private String password;
	private LocalDateTime createDate;
	
	// 회원수정 임시 변수 지정 *****************************
	private String changeUid;
	private String changePassword;
	
	public String getChangeUid() {
		return changeUid;
	}

	public void setChangeUid(String changeUid) {
		this.changeUid = changeUid;
	}

	public String getChangePassword() {
		return changePassword;
	}

	public void setChangePassword(String changePassword) {
		this.changePassword = changePassword;
	}
	
	//********************************************

	public UserDto() {}
	
	public UserDto(String uid, String email, String password, LocalDateTime createDate) {
		super();
		this.uid = uid;
		this.email = email;
		this.password = password;
		this.createDate = createDate;
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "UserDto [uid=" + uid + ", email=" + email + ", password=" + password + ", createDate=" + createDate
				+ "]";
	}
	
}
