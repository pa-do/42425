package com.web.blog.dao.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.project.Project;

@Transactional
@Repository
public interface ProjectDao extends JpaRepository<Project, Integer> {

	// 전체 조회
	@Query(value = "SELECT user.nickname, project.*, TIMESTAMPDIFF(week, start_date,end_date) as devdate FROM project, user WHERE project.uid=user.uid", nativeQuery = true)
	public List<Project> selectAllProject();

	// uid로 조회
	@Query(value = "SELECT user.nickname, project.*,TIMESTAMPDIFF(week, start_date,end_date) as devdate FROM project, user WHERE project.uid=user.uid AND project.uid=?1", nativeQuery = true)
	public List<Project> selectAllProjectByUid(@Param("uid") int uid);

	// 수정
	@Modifying
	@Query(value = "UPDATE project SET title=?2,start_date=?3,end_date=?4,content=?5,summary=?6,project_img=?7 WHERE pid=?1", nativeQuery = true)
	Integer updateProject(@Param("pid") int pid, @Param("title") String title, @Param("start_date") String startdate,
			@Param("end_date") String enddate, @Param("content") String content, @Param("summary") String summary,
			@Param("project_img") String project_img);

	// 삭제
	@Query(value = "SELECT user.nickname, project.*,TIMESTAMPDIFF(week, start_date,end_date) as devdate FROM project, user WHERE project.uid=user.uid and project.pid=?1", nativeQuery = true)
	public Project selectProjectByPid(@Param("pid") int pid);
}
