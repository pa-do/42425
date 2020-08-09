package com.web.blog.model.portfolio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@IdClass(MySkillKey.class)
public class Myskill implements Serializable{
	@Id
	@JoinColumn(name = "user_uid")
	private int uid;
	@Id
	private String skill;
	private int value;
}
