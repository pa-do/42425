package com.web.blog.service.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.dao.board.LikesDao;
import com.web.blog.model.board.Board;
import com.web.blog.model.board.Likes;

@Service
public class LikesService {
	@Autowired
	private LikesDao likesDao;
	
	public Likes save(Likes likes) {
		likesDao.save(likes);
		return likes;
		
	}
	
	public void deleteByLid(int lid) {
		likesDao.deleteByLid(lid);
	}

	public List<Likes> findAll() {
		List<Likes> likes = new ArrayList<>();
		likesDao.selectAllLikes().forEach(e -> likes.add(e));
		return likes;
	}
	

}
