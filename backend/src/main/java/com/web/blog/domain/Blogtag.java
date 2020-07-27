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
@Table(name="blog_and_tag")
public class Blogtag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int btid;
	
	@Column(nullable = false)
	private int tid;
	
	@Column(nullable = true)
	private int bid;

}
