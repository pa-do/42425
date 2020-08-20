package com.web.blog.controller.project;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.blog.model.project.Project;
import com.web.blog.service.project.ProjectService;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = { "http://localhost:3000" })
public class ProjectController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProjectService projectService;

//	// 전체 조회
//	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<List<Project>> getAllprojects() {
//		List<Project> project = projectService.findAll();
//		return new ResponseEntity<List<Project>>(project, HttpStatus.OK);
//	}

	// user id로 조회
	@GetMapping("/byuser/{uid}")
	public ResponseEntity<List<Project>> getAllprojectsByUid(@PathVariable int uid) {
		List<Project> project = projectService.findAllProjectByUid(uid);
		return new ResponseEntity<List<Project>>(project, HttpStatus.OK);
	}  

	// 프로젝트 번호로 삭제
	@DeleteMapping(value = "/delete/{pid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> deleteProject(@PathVariable("pid") int pid) {
		projectService.deleteByPid(pid);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	// 프로젝트 번호로 수정
	@PutMapping(value = "/modify", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Project> updateProject(Project project, MultipartFile file, Boolean isRemoveImg,
			HttpServletRequest request) {
		String retPath;
		if (isRemoveImg && file == null) {// 등록된 이미지를 제거하고 default로 변경하는 경우
			retPath = "default.png";
			project.setProjectImg(retPath);
		} else if (file != null) {
			try {
				retPath = save(file, request.getServletContext().getRealPath("/img/projectImg/"));
			} catch (IllegalStateException e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			project.setProjectImg(retPath);
		}
		projectService.modify(project);
		return new ResponseEntity<Project>(project, HttpStatus.OK);

	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public ResponseEntity<Project> save(HttpServletRequest req, Project project, MultipartFile file,
			HttpServletRequest request) {
		String retPath = null;
		try {
			if (file != null)
				retPath = save(file, request.getServletContext().getRealPath("/img/projectImg/"));
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		project.setProjectImg(retPath);
		return new ResponseEntity<Project>(projectService.save(project), HttpStatus.OK);
	}

	private String save(MultipartFile file, String contextPath) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = simpleDateFormat.format(new Date()) + "_" + file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			System.out.println(contextPath);
			Path path = Paths.get(contextPath + newFileName);
			// .get(contextPath + newFileName);
			Files.write(path, bytes);
			return newFileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
