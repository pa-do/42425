package com.web.blog.dao.portfolio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.blog.model.portfolio.Resume;

@Transactional
public interface ResumeDao extends JpaRepository<Resume, Integer> {
	List<Resume> findByUid(int uid);
	
	@Modifying    
    @Query(value = "UPDATE resume 			"
    			 + "   SET start_year = ?3, "
    			 + "       end_year   = ?4, "
    			 + "       place 	  = ?5, "
    			 + "       title 	  = ?6, "
    			 + "       content 	  = ?7 	"
    			 + " WHERE uid		  = ?1	"
    			 + "   And rid		  = ?2	"
    			 , nativeQuery = true)
    int update(@Param("uid") int uid, 
			   @Param("rid") int rid, 
			   @Param("startYear") int startYear,
			   @Param("endYear") int endYear,
			   @Param("place") String place,
			   @Param("title") String title,
			   @Param("content") String content);

	@Modifying
	@Query(value = "DELETE FROM resume "
				 + "WHERE uid=?1 AND rid=?2", nativeQuery = true)
	int deleteResume(@Param(value = "uid") int uid, @Param(value = "rid") int rid);
	
	Resume findResumeByRid(int rid);

}
