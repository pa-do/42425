package com.web.blog.dao.portfolio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.blog.model.portfolio.MySkillKey;
import com.web.blog.model.portfolio.Myskill;
import com.web.blog.model.user.User;

@Transactional
public interface MySkillDao extends JpaRepository<Myskill, Integer> {
	
	@Modifying
	@Query(value = "SELECT * "
				 + "  FROM myskill"
				 + " WHERE uid = ?1"
				 , nativeQuery = true)
	List<Myskill> select(@Param(value="uid") int uid);
	
	@Modifying
	@Query(value = "UPDATE myskill    "
				 + "   SET skill = ?3, "
				 + "	   value = ?4 "
				 + " WHERE uid = ?1   "
				 + "   AND sid = ?2 "
				 , nativeQuery = true)
	void updateSkills(@Param(value = "uid") int uid, @Param(value = "sid") int sid, @Param(value = "skill") String skill, @Param(value = "value") int value);
	
	@Modifying
	@Query(value = "DELETE 			"
				 + "  FROM myskill	"
				 + " WHERE uid=?1 	"
				 + "   AND skill=?2	"
				 , nativeQuery = true)
	int deleteSkill(@Param(value = "uid") int uid, @Param(value = "skill") String skill);
	
	@Modifying
	@Query(value = "INSERT 								"
				 + "  INTO myskill (uid, skill, value)	"
				 + "VALUES (?1, '기술명', 0)				" 
				 , nativeQuery = true)
	int insertDefaultSkill(@Param(value = "uid") int uid);
	
	List<Myskill> findMyskillByUserUid(int uid);
}
