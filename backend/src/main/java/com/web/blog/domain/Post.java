package com.web.blog.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author multicampus
 *
 */
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
	
	@Column(length = 45, nullable = false)
	private String manager;
	
	@Column
	private LocalDateTime postTime;
	
	@Column
	private LocalDateTime update_time;
	
	@Column(nullable = true)
	private int fork;
	
	@Column(length = 10, nullable = true)
	private String ptype;
	
	@Column(nullable = true)
	private int postlikecnt;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="email")
	private Collection<PostLike> liker;
	
	
	
	@Builder
	public Post(int bid, int lcid, int mcid, String ptitle, String pcontent, String author,String manager, LocalDateTime postTime, LocalDateTime update_time, String ptype, int postlikecnt) {
		this.bid = bid;
		this.lcid = lcid;
		this.mcid = mcid;
		this.ptitle = ptitle;
		this.pcontent = pcontent;
		this.author = author;
		this.manager = manager;
		this.postTime = postTime;
		this.update_time = update_time;
		this.ptype = ptype;
		this.postlikecnt = postlikecnt;
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
	public void setPostlikecnt(int postlikecnt) {
		this.postlikecnt = postlikecnt;
	}
	
	public void setFork(int fork) {
		this.fork = fork;
	}

	public void addLiker(PostLike lik) {
		if(liker == null) {
			liker = new ArrayList<PostLike>();
		}
		liker.add(lik);
	}

	@Override
	public String toString() {
		return "Post [pid=" + pid + ", bid=" + bid + ", lcid=" + lcid + ", mcid=" + mcid + ", ptitle=" + ptitle
				+ ", pcontent=" + pcontent + ", author=" + author + ", manager=" + manager + ", postTime=" + postTime
				+ ", update_time=" + update_time + ", fork=" + fork + ", ptype=" + ptype + ", postlikecnt="
				+ postlikecnt + ", liker=" + liker + "]";
	}
	
}
