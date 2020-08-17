package com.web.blog.controller.account;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.Follow;
import com.web.blog.service.user.FollowService;

@RestController
@RequestMapping("/follow")
public class FollowController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	FollowService followService;

	@ApiOperation(value = "uid에 해당하는 유저의 팔로워 목록 조회")
	@GetMapping(value = "/getFollowerList/{uid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Follow>> getFollowerList(@PathVariable("uid") int uid) {
		List<Follow> follows = followService.findAllByFolloweeUid(uid);
		return new ResponseEntity<List<Follow>>(follows, HttpStatus.OK);
	}

	@ApiOperation(value = "uid에 해당하는 유저가 팔로우하는 목록 조회")
	@GetMapping(value = "/getFolloweeList/{uid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Follow>> getFolloweeList(@PathVariable("uid") int uid) {
		List<Follow> follows = followService.findAllByFollowerUid(uid);
		return new ResponseEntity<List<Follow>>(follows, HttpStatus.OK);
	}

	@ApiOperation(value = "팔로우 버튼을 누를 때 동작하는 API. 호출할 때마다 팔로우/언팔로우 전환됨")
	@PostMapping(value = "/toggleFollow")
	public ResponseEntity<Object> toggleFollow(int followerUid, int followeeUid) {
		if (followerUid == followeeUid) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "error";
			result.object = "not allow self follow";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(followService.toggleFollow(followerUid, followeeUid), HttpStatus.OK);
	}

	@ApiOperation(value = "팔로우 상태 확인 true/false")
	@PostMapping(value = "/checkFollow")
	public ResponseEntity<Boolean> checkFollow(int followerUid, int followeeUid) {
		return new ResponseEntity<>(followService.checkFollow(followerUid, followeeUid), HttpStatus.OK);
	}
}
