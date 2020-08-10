
package com.web.blog.dao.user;

import java.util.Optional;

import javax.transaction.Transactional;

import com.web.blog.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Transactional	// javax.persistence.TransactionRequiredException: Executing an update/delete
				// query 에러
public interface UserDao extends JpaRepository<User, String> {
    int countByEmail(String email);

    int countByNickname(String nickname);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    User findUserByEmail(String email);

    User findUserByUid(int uid);

    @Modifying // 수정,삭제 시 사용하는 어노테이션
    @Query(value = "UPDATE user SET nickname=?2 WHERE uid=?1", nativeQuery = true)
    Integer updateNickname(@Param("uid") int uid, @Param("nickname") String nickname);

    @Modifying
    @Query(value = "UPDATE user SET bio=?2 WHERE uid=?1", nativeQuery = true)
    Integer updateBio(@Param("uid") int uid, @Param("bio") String bio);

    @Modifying
    @Query(value = "UPDATE user SET password=?2 WHERE uid=?1", nativeQuery = true)
    Integer updatePassword(@Param("uid") int uid, @Param("password") String password);

    @Modifying
    @Query(value = "UPDATE user SET profile_img=?2 WHERE uid=?1", nativeQuery = true)
    Integer updateProfileImg(@Param("uid") int uid, @Param("profileImg") String profileImg);

    int removeUserByUid(int uid);
    
    @Modifying
    @Query(value = "UPDATE user SET phone=?2 WHERE uid=?1", nativeQuery = true)
    Integer updatePhone(@Param("uid") int uid, @Param("phone") String phone);

    @Modifying
    @Query(value = "UPDATE user SET website=?2 WHERE uid=?1", nativeQuery = true)
    Integer updateWebsite(@Param("uid") int uid, @Param("website") String website);
    
}
