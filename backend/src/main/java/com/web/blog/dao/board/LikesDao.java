package com.web.blog.dao.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.board.Likes;

@Transactional
@Repository
public interface LikesDao extends JpaRepository<Likes,Integer> {
	public void deleteByLid(int lid);
	
	public void deleteByBidAndUid(int bid, int uid);
	
	@Query(value = "SELECT * from likes", nativeQuery = true)
	public List<Likes> selectAllLikes();
	
	@Modifying
	@Query(value = "insert into likes (bid,uid) values (?1,?2)", nativeQuery = true)
	Integer savebyBidAndUid(@Param("bid") int bid,@Param("uid") int uid);

	@Query(value = "SELECT * from likes where bid=?1", nativeQuery = true)
	public List<Likes> selectLikesByBid(@Param("bid") int bid);
	
	@Query(value = "SELECT * from likes where uid=?1", nativeQuery = true)
	public List<Likes> selectLikesByUid(@Param("uid") int uid);
	
	@Query(value = "select likes.lid from likes,board,user where user.uid=?1 and board.bid=?2 and likes.bid=board.bid and likes.uid=user.uid;", nativeQuery = true)
	Integer checkLikes(@Param("uid") int uid,@Param("bid") int bid);
	
}
