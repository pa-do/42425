package com.web.blog.model.portfolio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.web.blog.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Myskill{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sid;

	@ManyToOne
	@JoinColumn(name = "uid")
	private User user;
	
	private String skill;
	private int value;
}
