package com.web.blog.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	
	@Builder
	public Blog(String btitle,String bsubtitle, String bcontent, String manager) {
		this.btitle = btitle;
		this.bsubtitle = bsubtitle;
		this.bcontent = bcontent;
		this.manager = manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "Blog [bid=" + bid + ", btitle=" + btitle + ", bsubtitle=" + bsubtitle + ", bcontent=" + bcontent
				+ ", manager=" + manager + "]";
	}

}
