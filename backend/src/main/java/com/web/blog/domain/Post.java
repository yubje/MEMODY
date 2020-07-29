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
@Table(name="Posts")
public class Post {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int pid;
	
	@Column(nullable = false)
	private int bid;
	
	@Column(nullable = true)
	private int lcid;
	
	@Column(nullable = true)
	private int mcid;
	
	@Column(length = 100, nullable = false)
	private String ptitle;
	
	@Column(length = 2000, nullable = false)
	private String pcontent;
	
	@Column(length = 45, nullable = false)
	private String author;
	
	@Column
	private LocalDateTime post_time;
	
	@Column
	private LocalDateTime update_time;
	
	@Column(length = 10, nullable = true)
	private String ptype;
	
	@Builder
	public Post(int bid, int lcid, int mcid, String ptitle, String pcontent, String author, LocalDateTime post_time, LocalDateTime update_time, String ptype) {
		this.bid = bid;
		this.lcid = lcid;
		this.mcid = mcid;
		this.ptitle = ptitle;
		this.pcontent = pcontent;
		this.author = author;
		this.post_time = post_time;
		this.update_time = update_time;
//		this.post_time = LocalDateTime.now();
//		this.update_time = LocalDateTime.now();
		this.ptype = ptype;
	}

	@Override
	public String toString() {
		return "Post [pid=" + pid + ", lcid=" + lcid + ", mcid=" + mcid + ", ptitle=" + ptitle + ", pcontent="
				+ pcontent + ", author=" + author + ", post_time=" + post_time + ", update_time=" + update_time
				+ ", ptype=" + ptype + "]";
	}

	public void setPtitle(String ptitle) {
    	this.ptitle = ptitle;
    }
	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}
	public void setUpdate_time(LocalDateTime update_time) {
		this.update_time = update_time;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
//	public void setPcontent(String pcontent) {
//		this.pcontent = pcontent;
//	}
	
	
//	public void setManager(String manager) {
//		this.manager = manager;
//	}

//	@Override
//	public String toString() {
//		return "Blog [bid=" + pid + ", btitle=" + btitle + ", bsubtitle=" + bsubtitle + ", bcontent=" + bcontent
//				+ ", manager=" + manager + "]";
//	}

}