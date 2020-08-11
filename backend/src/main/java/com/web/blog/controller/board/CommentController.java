package com.web.blog.controller.board;

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

import com.web.blog.model.board.Board;
import com.web.blog.model.board.Comment;
import com.web.blog.service.board.CommentService;

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = { "http://localhost:3000" })
public class CommentController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CommentService commentService;

	// 모든 댓글 조회
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Comment>> getAllcomment() {
		List<Comment> comment = commentService.findAll();
		return new ResponseEntity<List<Comment>>(comment, HttpStatus.OK);
	}

	// 게시글 번호로 댓글 조회
	@GetMapping(value = "/board/{bid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Comment>> getCommentByBid(@PathVariable("bid") int bid) {
		List<Comment> comment = commentService.findByBid(bid);
		return new ResponseEntity<List<Comment>>(comment, HttpStatus.OK);
	}

	// 유저 번호로 댓글 조회
	@GetMapping(value = "/user/{uid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Comment>> getCommentByUid(@PathVariable("uid") int uid) {
		List<Comment> comment = commentService.findByUid(uid);
		return new ResponseEntity<List<Comment>>(comment, HttpStatus.OK);
	}

	// //댓글 번호로 댓글 조회
	// @GetMapping(value="/comment/{cid}", produces =
	// {MediaType.APPLICATION_JSON_VALUE})
	// public ResponseEntity<Comment> getCommentByCid(@PathVariable("cid") int cid){
	// Comment comment = commentService.findByCid(cid);
	// return new ResponseEntity<Comment>(comment,HttpStatus.OK);
	// }

	// 댓글 번호로 삭제
	@DeleteMapping(value = "/delete/{cid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> deleteComment(@PathVariable("cid") int cid) {
		commentService.deleteByCid(cid);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	// 댓글 등록
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public ResponseEntity<Comment> save(HttpServletRequest req, Comment comment) {
		return new ResponseEntity<Comment>(commentService.save(comment), HttpStatus.OK);
	}

	// 댓글 번호로 게시글 수정
	@PutMapping(value = "/modify", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Comment> updateComment(Comment comment) {
		commentService.modify(comment);
		return new ResponseEntity<Comment>(comment, HttpStatus.OK);

	}

}
