package com.web.blog.dao.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.board.Likes;

@Transactional
@Repository
public interface LikesDao extends JpaRepository<Likes,Integer> {
	public void deleteByLid(int lid);
	
	@Query(value = "SELECT * from likes", nativeQuery = true)
	public List<Likes> selectAllLikes();

}
