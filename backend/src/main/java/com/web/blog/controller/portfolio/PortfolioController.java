package com.web.blog.controller.portfolio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.portfolio.MySkillDao;
import com.web.blog.dao.portfolio.ResumeDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.portfolio.Myskill;
import com.web.blog.model.portfolio.Resume;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
				        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
				        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
				        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })
@RestController
@RequestMapping("/portfolio")
@CrossOrigin(origins = { "*" })
public class PortfolioController {
	@Autowired
	MySkillDao mySkillDao;
	@Autowired
	ResumeDao resumeDao;
	
	@PostMapping("/skill/create")
	@ApiOperation(value = "스킬 등록하기")
	public Object createSkill (@RequestBody Myskill ms) {
		 final BasicResponse result = new BasicResponse();
		
		 ResponseEntity response = null;
		 
		 Myskill newskill = mySkillDao.save(ms);
		if(newskill != null) {
			result.status = true;
	        result.data = "success";
	        result.object = newskill;
	        response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@GetMapping("/skill/{uid}")
	@ApiOperation(value = "스킬 가져오기")
	public Object skill(@PathVariable int uid) {
		final BasicResponse result = new BasicResponse();
			
		ResponseEntity response = null;
		
		List<Myskill> skills = mySkillDao.findByUid(uid);
		if(skills != null) {
			result.status = true;
	        result.data = "success";
	        result.object = skills;
	        response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@PutMapping("/skill/modify")
	@ApiOperation(value = "스킬 퍼센테이지 수정하기")
	public Object modifySkill(@RequestBody List<Myskill> myskills) {
		final BasicResponse result = new BasicResponse();
		
		try {
			for (Myskill myskill : myskills) {
				System.out.println(myskill.getSkill());
				mySkillDao.updateSkills(myskill.getUid(), myskill.getSkill(), myskill.getValue());
			}
			result.status = true;
			result.data = "success";
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} 
	}
	
	@DeleteMapping("/skill/delete/{uid}/{skill}")
	@ApiOperation(value = "스킬 삭제하기")
	public Object deleteSkill(@PathVariable int uid, @PathVariable String skill) {
		final BasicResponse result = new BasicResponse();
		
		ResponseEntity response = null;
		if(mySkillDao.deleteSkill(uid, skill) > 0) {
			result.status = true;
	        result.data = "success";
	        response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@PostMapping("/resume/create")
	@ApiOperation(value = "이력 등록하기")
	public Object createResume (@RequestBody Resume resume) {
		 final BasicResponse result = new BasicResponse();
		
		 ResponseEntity response = null;
		 
		 Resume newresume = resumeDao.save(resume);
		if(newresume != null) {
			result.status = true;
	        result.data = "success";
	        result.object = newresume;
	        response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@PutMapping("/resume/modify")
	@ApiOperation(value = "이력 수정 하기")
	public Object modifyResume(@RequestBody Resume resume) {
		final BasicResponse result = new BasicResponse();
		
		int temp = resumeDao.update(resume.getUid(), 
									resume.getRid(), 
									resume.getStartYear(),
									resume.getEndYear(),
									resume.getPlace(),
									resume.getTitle(),
									resume.getContent());
		if(temp != 0) {
			result.status = true;
			result.data = "success";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	} 
	
	@GetMapping("/resume/{uid}")
	@ApiOperation(value = "이력 가져오기")
	public Object resume(@PathVariable int uid) {
		final BasicResponse result = new BasicResponse();
			
		ResponseEntity response = null;
		
		List<Resume> resumes = resumeDao.findByUid(uid);
		if(resumes != null) {
			result.status = true;
	        result.data = "success";
	        result.object = resumes;
	        response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return response;
	}  
	
	@DeleteMapping("/resume/delete/{uid}/{rid}")
	@ApiOperation(value = "resume 삭제하기")
	public Object deleteResume(@PathVariable int uid, @PathVariable int rid) {
		final BasicResponse result = new BasicResponse();
		
		ResponseEntity response = null;
		if(resumeDao.deleteResume(uid, rid) > 0) {
			result.status = true;
	        result.data = "success";
	        response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
}
