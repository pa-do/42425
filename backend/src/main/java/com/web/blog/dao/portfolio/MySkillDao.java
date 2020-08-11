package com.web.blog.dao.portfolio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.blog.model.portfolio.MySkillKey;
import com.web.blog.model.portfolio.Myskill;

@Transactional
public interface MySkillDao extends JpaRepository<Myskill, MySkillKey> {
	
	List<Myskill> findByUid(int uid);
	
	@Modifying
	@Query(value = "UPDATE myskill"
				 + "   SET value=?3 "
				 + " WHERE uid=?1 "
				 + "   AND skill=?2",
				 nativeQuery = true)
	void updateSkills(@Param(value = "uid") int uid, @Param(value = "skill") String skill, @Param(value = "value") int value);
	
	@Modifying
	@Query(value = "DELETE FROM myskill "
				 + "WHERE uid=?1 AND skill=?2", nativeQuery = true)
	int deleteSkill(@Param(value = "uid") int uid, @Param(value = "skill") String skill);
}
