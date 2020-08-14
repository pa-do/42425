
package com.web.blog.dao.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.blog.model.user.User;
import com.web.blog.model.user.UserNicknameMapping;

@Transactional // javax.persistence.TransactionRequiredException: Executing an update/delete
               // query 에러
public interface UserDao extends JpaRepository<User, Integer> {
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

    @Modifying
    @Query(value = "UPDATE user SET name=?2 WHERE uid=?1", nativeQuery = true)
    Integer updateName(@Param("uid") int uid, @Param("name") String name);

    @Modifying
    @Query(value = "UPDATE user SET birth_date=?2 WHERE uid=?1", nativeQuery = true)
    Integer updateBirthDate(@Param("uid") int uid, @Param("birthDate") Date birthDate);

    @Modifying
    @Query(value = "UPDATE user SET address=?2 WHERE uid=?1", nativeQuery = true)
    Integer updateAddress(@Param("uid") int uid, @Param("address") String address);

    @Modifying
    @Query(value = "UPDATE user SET position=?2 WHERE uid=?1", nativeQuery = true)
    Integer updatePosition(@Param("uid") int uid, @Param("position") String position);

    List<UserNicknameMapping> findAllByNicknameContaining(String nickname);
}
