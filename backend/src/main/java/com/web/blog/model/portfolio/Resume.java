package com.web.blog.model.portfolio;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Resume {
	@JoinColumn(name = "user_uid")
	private int uid;    
	
	@Id
	private int rid;
	
	private int startYear;
	private int endYear;
	private String place;
	private String title;
	private String content;
}
