
package com.web.blog.dao.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.board.Board;
import com.web.blog.model.board.Comment;

@Transactional
@Repository
public interface CommentDao extends JpaRepository<Comment, Integer> {

	@Query(value = "SELECT user.nickname, (SELECT COUNT(*) from likes where likes.bid = board.bid) as likes_count, board.* FROM board, user WHERE board.uid=user.uid and board.bid=?1", nativeQuery = true)
	public Optional<Board> findByBid(@Param("bid") int bid);

	public void deleteByCid(int cid);

	
	@Query(value = "SELECT user.nickname, comment.* FROM comment, user WHERE comment.uid=user.uid and comment.bid=?1", nativeQuery = true)
	public List<Comment> selectCommentByBid(@Param("bid") int bid);
	
	@Query(value = "SELECT user.nickname, comment.* FROM comment, user WHERE comment.uid=user.uid and comment.uid=?1", nativeQuery = true)
	public List<Comment> selectCommentByUid(@Param("uid") int uid);

	@Query(value = "SELECT user.nickname, comment.* FROM comment, user WHERE comment.uid=user.uid and comment.cid=?1", nativeQuery = true)
	public Comment selectCommentByCid(@Param("cid") int cid);
	
	@Query(value = "SELECT user.nickname, comment.* FROM comment, user WHERE comment.uid=user.uid", nativeQuery = true)
	public List<Comment> selectAllComment();
	
	@Query(value = "SELECT user.nickname, (SELECT COUNT(*) from likes where likes.bid = board.bid) as likes_count, board.* FROM board, user WHERE board.uid=user.uid order by bid desc limit 6", nativeQuery = true)
	public List<Board> selectAllBoard();
	
	@Modifying
    @Query(value = "UPDATE comment SET content=?2 WHERE cid=?1", nativeQuery = true)
    Integer updateComment(@Param("cid") int cid, @Param("content") String content);
}