
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

@Transactional
@Repository
public interface BoardDao extends JpaRepository<Board, Integer> {

	@Query(value = "SELECT user.nickname, (SELECT COUNT(*) from likes where likes.bid = board.bid) as likes_count, board.* FROM board, user WHERE board.uid=user.uid and board.bid=?1", nativeQuery = true)
	public Optional<Board> findByBid(@Param("bid") int bid);

	public List<Board> findByTitle(String title);

	public void deleteByBid(int bid);

	// @Query(value = "update board set where title=?1, content=?2 where bid=?3",
	// nativeQuery = true)
	// public Board modify(@Param("title") String title,@Param("content") String
	// content, @Param("bid") int bid);

	@Query(value = "SELECT user.nickname, (SELECT COUNT(*) from likes where likes.bid = board.bid) as likes_count, board.* FROM board, user WHERE board.uid=user.uid and board.bid=?1", nativeQuery = true)
	public Board selectBoardByBid(@Param("bid") int bid);

	@Query(value = "SELECT user.nickname, (SELECT COUNT(*) from likes where likes.bid = board.bid) as likes_count, board.* FROM board, user WHERE board.uid=user.uid order by bid desc", nativeQuery = true)
	public List<Board> selectAllBoard();

	// public Collection<Board> findByTitle(String title);
	@Query(value = "SELECT user.nickname, (SELECT COUNT(*) from likes where likes.bid = board.bid) as likes_count, board.* FROM board, user WHERE board.uid=user.uid and board.title like %?1% ORDER BY bid DESC", nativeQuery = true)
	public List<Board> searchTitle(@Param("keyword") String keyword);

	@Query(value = "SELECT user.nickname, (SELECT COUNT(*) from likes where likes.bid = board.bid) as likes_count, board.* FROM board, user WHERE board.uid=user.uid and board.content like %?1% ORDER BY bid DESC", nativeQuery = true)
	public List<Board> searchContent(@Param("keyword") String keyword);

	@Query(value = "SELECT user.nickname, (SELECT COUNT(*) from likes where likes.bid = board.bid) as likes_count, board.* FROM board, user WHERE board.uid=user.uid and user.nickname like %?1% ORDER BY bid DESC", nativeQuery = true)
	public List<Board> searchNickname(@Param("keyword") String keyword);
	// @Query(value = "select uid,bid,title,content from board where uid=?1",
	// nativeQuery = true)
	// public Board detail(@Param("bid") int bid);

	@Modifying
	@Query(value = "UPDATE board SET title=?2,content=?3,board_img=?4 WHERE bid=?1", nativeQuery = true)
	Integer updateBoard(@Param("bid") int bid, @Param("title") String title, @Param("content") String content,
			@Param("boardImg") String boardImg);

	@Query(value = "SELECT user.nickname, (SELECT COUNT(*) from likes where likes.bid = board.bid) as likes_count, board.* FROM board, user WHERE board.uid=user.uid AND board.uid=?1 order by bid desc", nativeQuery = true)
	public List<Board> selectAllBoardByUid(@Param("uid") int uid);
}