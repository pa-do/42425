package com.web.blog.service.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.dao.board.CommentDao;
import com.web.blog.model.board.Comment;

@Service
public class CommentService {

	@Autowired
	private CommentDao commentDao;

	public List<Comment> findAll() {
		List<Comment> comments = new ArrayList<>();
		commentDao.selectAllComment().forEach(e -> comments.add(e));
		return comments;
	}

	public List<Comment> findByBid(int bid) {
		List<Comment> comments = new ArrayList<>();
		commentDao.selectCommentByBid(bid).forEach(e -> comments.add(e));
		return comments;
	}

	public List<Comment> findByUid(int uid) {
		List<Comment> comments = new ArrayList<>();
		commentDao.selectCommentByUid(uid).forEach(e -> comments.add(e));
		return comments;
	}

	public Comment findByCid(int cid) {
		return commentDao.selectCommentByCid(cid);
	}

	public void deleteByCid(int cid) {
		commentDao.delete(findByCid(cid));
	}

	public Comment save(Comment comment) {
		commentDao.save(comment);
		return comment;
	}

	public void modify(Comment comment) {
		commentDao.updateComment(comment.getCid(), comment.getContent());
	}
}
