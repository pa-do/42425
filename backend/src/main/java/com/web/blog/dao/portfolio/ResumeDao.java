package com.web.blog.dao.portfolio;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.blog.model.portfolio.Resume;

@Transactional
public interface ResumeDao extends JpaRepository<Resume, Integer> {
	Resume findByUid(int uid);
	
	@Modifying 
    @Query(value = "UPDATE resume SET start_year=?2 WHERE uid=?1", nativeQuery = true)
    Integer updateStartYear(@Param("uid") int uid, @Param("startYear") int startYear);

	@Modifying 
	@Query(value = "UPDATE resume SET end_year=?2 WHERE uid=?1", nativeQuery = true)
	Integer updateEndYear(@Param("uid") int uid, @Param("endYear") int endYear);

    @Modifying
    @Query(value = "UPDATE resume SET place=?2 WHERE uid=?1", nativeQuery = true)
    Integer updatePlace(@Param("uid") int uid, @Param("place") String place);

    @Modifying
    @Query(value = "UPDATE resume SET title=?2 WHERE uid=?1", nativeQuery = true)
    Integer updateTitle(@Param("uid") int uid, @Param("title") String title);

    @Modifying
    @Query(value = "UPDATE resume SET content=?2 WHERE uid=?1", nativeQuery = true)
    Integer updateContent(@Param("uid") int uid, @Param("content") String content);

}
