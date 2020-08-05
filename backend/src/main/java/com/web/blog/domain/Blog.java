package com.web.blog.domain;

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

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Blogs")
public class Blog {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int bid;
	
	@Column(length = 100, nullable = false)
	private String btitle;
	
	@Column(length = 200, nullable = true)
	private String bsubtitle;
	
	@Column(length = 2000, nullable = true)
	private String bcontent;
	
	@Column(length = 45, nullable = false)
	private String manager;

	@Column(nullable = true)
	private int views;
	
	@Column(nullable = true)
	private int followers;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="email")
	private Collection<BlogFollow> follower;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="tname")
	private Collection<Blogtag> hashtags;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="email")
	private Collection<Member> member;
	
	@Builder
	public Blog( String btitle,String bsubtitle, String bcontent, String manager, int views, int followers) {
		this.btitle = btitle;
		this.bsubtitle = bsubtitle;
		this.bcontent = bcontent;
		this.manager = manager;
		this.views = views;
		this.followers = followers;
	}
	
	public void setManager(String manager) {
		this.manager = manager;
	}
	
	public void setViews(int views) {
		this.views = views;
	}
	
	public void addHashTag(Blogtag tag) {
		if(hashtags == null) {
			hashtags = new ArrayList<Blogtag>();
		}
		hashtags.add(tag);
	}

	public void addMember(Member mem) {
		if(member == null) {
			member = new ArrayList<Member>();
		}
		member.add(mem);
	}

	public void addFollower(BlogFollow fol) {
		if(follower == null) {
			follower = new ArrayList<BlogFollow>();
		}
		follower.add(fol);
	}

	@Override
	public String toString() {
		return "Blog [bid=" + bid + ", btitle=" + btitle + ", bsubtitle=" + bsubtitle + ", bcontent=" + bcontent
				+ ", manager=" + manager + ", views=" + views + ", followers=" + followers + ", follower=" + follower
				+ ", hashtags=" + hashtags + ", member=" + member + "]";
	}
	
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}

	public void setBsubtitle(String bsubtitle) {
		this.bsubtitle = bsubtitle;
	}

	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}


	

}
