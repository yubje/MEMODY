package com.web.blog.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Tags")
public class Tag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int tid;
	
	@Column(length = 45, nullable = false)
	private String tname;
	
	@Builder
	public Tag(String tname) {
		this.tname = tname;
	}

	@Override
	public String toString() {
		return "Tag [tid=" + tid + ", tname=" + tname + "]";
	}
}
