package com.web.blog.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.dao.user.FollowDao;
import com.web.blog.model.user.Follow;

@Service
public class FollowService {
	@Autowired
	private FollowDao followDao;

	// public Follow save(Follow follow) {
	// followDao.save(follow);
	// return follow;
	// }//안씀

	// public void deleteByFid(int fid) {
	// followDao.removeFollowByFid(fid);
	// }//안씀

	public List<Follow> findAllByFollowerUid(int uid) {
		List<Follow> follows = new ArrayList<>();
		followDao.findAllByFollowerUid(uid).forEach(e -> follows.add(e));
		return follows;
	}

	public List<Follow> findAllByFolloweeUid(int uid) {
		List<Follow> follows = new ArrayList<>();
		followDao.findAllByFolloweeUid(uid).forEach(e -> follows.add(e));
		return follows;
	}

	public boolean toggleFollow(int followerUid, int followeeUid) {
		if (followDao.findByFollowerUidAndFolloweeUid(followerUid, followeeUid) != null) {
			followDao.removeFollowByFollowerUidAndFolloweeUid(followerUid, followeeUid);
			return false;
		} else {
			followDao.saveByFollowerUidAndFolloweeUid(followerUid, followeeUid);
			return true;
		}
	}

	public boolean checkFollow(int followerUid, int followeeUid) {
		if (followDao.findByFollowerUidAndFolloweeUid(followerUid, followeeUid) != null) {
			return true;
		} else {
			return false;
		}
	}

}
