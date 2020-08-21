package com.web.blog.service.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.dao.project.ProjectDao;
import com.web.blog.model.board.Board;
import com.web.blog.model.project.Project;

@Service
public class ProjectService {

	@Autowired
	private ProjectDao projectDao;

	public List<Project> findAll() {
		List<Project> projects = new ArrayList<>();
		projectDao.selectAllProject().forEach(e -> projects.add(e));
		return projects;
	}

	public List<Project> findAllProjectByUid(int uid) {
		List<Project> projects = new ArrayList<>();
		projectDao.selectAllProjectByUid(uid).forEach(e -> projects.add(e));
		return projects;
	}

	public Project findByPid(int pid) {
		return projectDao.selectProjectByPid(pid);
	}

	public void deleteByPid(int pid) {
		projectDao.delete(findByPid(pid));
	}

	public void modify(Project project) {
		projectDao.updateProject(project.getPid(), project.getTitle(), project.getStartdate(), project.getEnddate(),
				project.getContent(), project.getSummary(), project.getProjectImg());
	}

	public Project save(Project project) {
		projectDao.save(project);
		return project;
	}
}
