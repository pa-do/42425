package com.web.blog.dao.portfolio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.blog.model.board.Board;
import com.web.blog.model.portfolio.MySkillKey;
import com.web.blog.model.portfolio.Myskill;
import com.web.blog.model.user.User;

@Transactional
public interface MySkillDao extends JpaRepository<Myskill, Integer> {

	@Modifying
	@Query(value = "SELECT *       " + "  FROM myskill   " + " WHERE uid = ?1   ", nativeQuery = true)
	List<Myskill> select(@Param(value = "uid") int uid);

	@Modifying
	@Query(value = "UPDATE myskill       " + "   SET skill = ?2,   " + "      value = ?3   "
			+ " WHERE sid = ?1    ", nativeQuery = true)
	int updateSkill(@Param(value = "sid") int sid, @Param(value = "skill") String skill,
			@Param(value = "value") int value);

	@Modifying
	@Query(value = "DELETE          " + "  FROM myskill   " + " WHERE sid=?1    ", nativeQuery = true)
	int deleteSkill(@Param(value = "sid") int sid);

	@Modifying
	@Query(value = "INSERT                         " + "  INTO myskill (uid, skill, value)   "
			+ "VALUES (?1, ?2, ?3)               ", nativeQuery = true)
	int insertSkill(@Param(value = "uid") int uid, @Param(value = "skill") String skill,
			@Param(value = "value") int value);

	List<Myskill> findMyskillByUserUid(int uid);

	Myskill findMyskillBySid(int sid);
	
	//skill로 검색
	@Query(value = "SELECT user.nickname, myskill.* FROM myskill, user WHERE myskill.uid=user.uid and myskill.skill like %?1%", nativeQuery = true)
	public List<Myskill> searchSkill(@Param("keyword") String keyword);
	
	

}