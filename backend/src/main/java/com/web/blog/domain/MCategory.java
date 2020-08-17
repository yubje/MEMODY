package com.web.blog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.web.blog.domain.LCategory.LCategoryBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="medium_categories")
public class MCategory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int mcid;
	
	@Column(nullable = false)
	private int lcid;
	
	@Column(name="medium_dir",length = 200, nullable = false)
	private String medium_dir;

	@Override
	public String toString() {
		return "MCategory [mcid=" + mcid + ", lcid=" + lcid + ", medium_dir=" + medium_dir + "]";
	}
	
}
