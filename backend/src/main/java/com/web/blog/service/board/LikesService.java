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
	
	public List<Likes> findByBid(int bid) {
		List<Likes> likes = new ArrayList<>();
		likesDao.selectLikesByBid(bid).forEach(e -> likes.add(e));
		return likes;
	}
	
	public List<Likes> findByUid(int uid) {
		List<Likes> likes = new ArrayList<>();
		likesDao.selectLikesByUid(uid).forEach(e -> likes.add(e));
		return likes;
	}
	
	public boolean checkLikes(int uid,int bid) {
		if(likesDao.checkLikes(uid, bid)!=null) {
			likesDao.deleteByBidAndUid(bid, uid);
			return true;
		}
		else {
			likesDao.savebyBidAndUid(bid, uid);
			return false;
		}
		}
	
	public boolean likesCheck(int uid,int bid) {
		if(likesDao.checkLikes(uid, bid)!=null) {
			return true;
		}
		else {
			return false;
		}
	}
			
		
	
	
	

}
