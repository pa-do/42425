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
import com.web.blog.model.BasicResponse;
import com.web.blog.model.portfolio.Myskill;

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
	MySkillDao dao;
	
	@PostMapping("/skill/create")
	@ApiOperation(value = "스킬 등록하기")
	public Object createSkill (@RequestBody Myskill ms) {
		 final BasicResponse result = new BasicResponse();
		
		 ResponseEntity response = null;
		 
		 Myskill newskill = dao.save(ms);
		if(ms != null) {
			result.status = true;
	        result.data = "success";
	        result.object = ms;
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
		
		List<Myskill> skills = dao.findByUid(uid);
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
		
		System.out.println("헬로1");
		try {
			for (Myskill myskill : myskills) {
				System.out.println(myskill.getSkill());
				dao.updateSkills(myskill.getUid(), myskill.getSkill(), myskill.getValue());
			}
			System.out.println("헬로2");
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
		if(dao.deleteSkill(uid, skill) > 0) {
			result.status = true;
	        result.data = "success";
	        response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@PutMapping("/resume/modify")
	@ApiOperation(value = "스킬 퍼센테이지 수정하기")
	public Object modifyResume(@RequestBody List<Myskill> myskills) {
		final BasicResponse result = new BasicResponse();
		
		System.out.println("헬로1");
		try {
			for (Myskill myskill : myskills) {
				System.out.println(myskill.getSkill());
				dao.updateSkills(myskill.getUid(), myskill.getSkill(), myskill.getValue());
			}
			System.out.println("헬로2");
			result.status = true;
			result.data = "success";
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} 
	}
}
