
package com.web.blog.dao.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.board.Board;

@Transactional
@Repository
public interface BoardDao extends JpaRepository<Board, Integer> {

	public Optional<Board> findByBid(int bid);

	public List<Board> findByTitle(String title);

	public void deleteByBid(int bid);

	
//	@Query(value = "update board set where title=?1, content=?2 where bid=?3", nativeQuery = true)
//	public Board modify(@Param("title") String title,@Param("content") String content, @Param("bid") int bid);

	@Query(value = "SELECT user.nickname, (SELECT COUNT(*) from likes where likes.bid = board.bid) as likes_count, board.* FROM board, user WHERE board.uid=user.uid and board.bid=?1", nativeQuery = true)
	public Board selectBoardByBid(@Param("bid") int bid);

	@Query(value = "SELECT user.nickname, (SELECT COUNT(*) from likes where likes.bid = board.bid) as likes_count, board.* FROM board, user WHERE board.uid=user.uid order by bid desc limit 6", nativeQuery = true)
	public List<Board> selectAllBoard();
	
//	public Collection<Board> findByTitle(String title);
	
	public List<Board> findByTitleContaining(String keyword);
}