package com.web.blog.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.user.Follow;

@Transactional
@Repository
public interface FollowDao extends JpaRepository<Follow, Integer> {
	// create = save(Follow follow)
	@Modifying
	@Query(value = "insert into follow (follower_uid,followee_uid) values (?1,?2)", nativeQuery = true)
	Integer saveByFollowerUidAndFolloweeUid(@Param("followerUid") int followerUid,
			@Param("followeeUid") int followeeUid);

	// read = findAll()
	public List<Follow> findAllByFollowerUid(int uid); // uid에 해당하는 유저가 팔로우 하는 유저 목록 반환

	public List<Follow> findAllByFolloweeUid(int uid); // uid에 해당하는 유저를 팔로우 하는 유저 목록 반환(개수 = 팔로워수)
	// check 얘를 팔로우 했나 안했나 확인

	public Follow findByFollowerUidAndFolloweeUid(int followerUid, int followeeUid);
	// update 필요없을것같은디
	// delete = deleteByFid()

	public void removeFollowByFid(int fid);

	public void removeFollowByFollowerUidAndFolloweeUid(int followerUid, int followeeUid);

}
