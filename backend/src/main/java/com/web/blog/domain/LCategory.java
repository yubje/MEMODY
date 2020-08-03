package com.web.blog.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="large_categories")
public class LCategory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int lcid;
	
	@Column(nullable = false)
	private int bid;
	
	@Column(length = 200, nullable = false)
	private String large_dir;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="medium_dir")
	private Collection<MCategory> mcategory;
	
	public void addMCategory(List<MCategory> list) {
		if(mcategory == null) {
			mcategory = new ArrayList<MCategory>();
		}
//		mcategory.add(list);
		mcategory = list;
	}
}
