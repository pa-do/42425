package com.web.blog.controller.board;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.board.Board;
import com.web.blog.model.board.Likes;
import com.web.blog.service.board.LikesService;

@RestController
@RequestMapping("/likes")
public class LikesController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	LikesService likesService;

	@PostMapping
	public ResponseEntity<Likes> save(Likes likes) {
		return new ResponseEntity<Likes>(likesService.save(likes), HttpStatus.OK);
	}

	@DeleteMapping(value = "delete/{lid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> deleteLikes(@PathVariable("lid") int lid) {
		likesService.deleteByLid(lid);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Likes>> getAllLikes() {
		List<Likes> likes = likesService.findAll();
		return new ResponseEntity<List<Likes>>(likes, HttpStatus.OK);
	}

	@GetMapping(value = "/detailBid/{bid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Likes>> getLikesByBid(@PathVariable("bid") int bid) {
		List<Likes> likes = likesService.findByBid(bid);
		return new ResponseEntity<List<Likes>>(likes, HttpStatus.OK);
	}

	@GetMapping(value = "/detailUid/{uid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Likes>> getLikesByUid(@PathVariable("uid") int uid) {
		List<Likes> likes = likesService.findByUid(uid);
		return new ResponseEntity<List<Likes>>(likes, HttpStatus.OK);
	}

	@PostMapping(value = "/checkLikes")
	public ResponseEntity<Boolean> check(Likes likes) {
		return new ResponseEntity<>(likesService.checkLikes(likes.getUid(), likes.getBid()), HttpStatus.OK);
	}

	@PostMapping(value = "/likesCheck")
	public boolean checkLikes(int uid, int bid) {
		return likesService.likesCheck(uid, bid);
	}

	/*
	 * (bid uid로 좋아요 여부 확인)=>lid if(좋아요no) save(new Likes(bid uid))
	 * else(deleteLikes)
	 */
}
