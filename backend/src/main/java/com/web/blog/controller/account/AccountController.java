package com.web.blog.controller.account;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.user.UserDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.ModifyPwdRequest;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.model.user.User;
import com.web.blog.model.user.UserNicknameMapping;
import com.web.blog.util.Encryption;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/account")
public class AccountController {
    Encryption enc = new Encryption();

    @Autowired
    UserDao userDao;

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestParam(required = true) final String email,
            @RequestParam(required = true) final String password) throws NoSuchAlgorithmException {

        String encryptedPassword = enc.sha256(password);

        Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, encryptedPassword);

        ResponseEntity response = null;

        if (userOpt.isPresent()) {
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            result.object = userOpt;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @PostMapping("/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody SignupRequest request) throws NoSuchAlgorithmException {
        // 이메일, 닉네임 중복처리 필수
        // 회원가입단을 생성해 보세요.

        User user = User.builder().email(request.getEmail()).nickname(request.getNickname())
                .password(enc.sha256(request.getPassword())).build();

        final BasicResponse result = new BasicResponse();
        userDao.save(user);
        result.status = true;
        result.data = "success";
        result.object = userDao.findUserByEmailAndPassword(request.getEmail(), request.getPassword());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/nicknameChk/{nickname}")
    @ApiOperation(value = "닉네임 중복 체크")
    public Object nicknameChk(@PathVariable String nickname) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        if (userDao.countByNickname(nickname) > 0) {
            result.status = true;
            result.data = "fail";
            result.object = "nickname";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/emailChk/{email}")
    @ApiOperation(value = "이메일 중복 체크")
    public Object emailChk(@PathVariable String email) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        if (userDao.countByEmail(email) > 0) {
            result.status = true;
            result.data = "fail";
            result.object = "email";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{uid}")
    @ApiOperation(value = "회원 정보")
    public Object getUser(@PathVariable int uid) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        User user = userDao.findUserByUid(uid);
        if (user != null) {
            result.status = true;
            result.data = "success";
            result.object = user;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping("/getuid")
    @ApiOperation(value = "회원 uid 정보")
    public Object getUserIdByEmail(@RequestParam String email) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        User user = userDao.findUserByEmail(email);
        if (user != null) {
            result.status = true;
            result.data = String.valueOf(user.getUid());
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/modify/nickname")
    @ApiOperation(value = "회원정보수정 - 닉네임 변경")
    public Object modifyNickname(@RequestBody User user) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        if (userDao.updateNickname(user.getUid(), user.getNickname()) > 0) {
            User modifiedUser = userDao.findUserByUid(user.getUid());
            result.status = true;
            result.data = "success";
            result.object = modifiedUser;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/modify/bio")
    @ApiOperation(value = "회원정보수정 - 자기소개 변경")
    public Object modifyBio(@RequestBody User user) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        if (userDao.updateBio(user.getUid(), user.getBio()) > 0) {
            User modifiedUser = userDao.findUserByUid(user.getUid());
            result.status = true;
            result.data = "success";
            result.object = modifiedUser;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/modify/password")
    @ApiOperation(value = "회원정보수정 - 비밀번호 변경")
    public Object modifyPassword(@Valid @RequestBody ModifyPwdRequest request) throws NoSuchAlgorithmException {
        final BasicResponse result = new BasicResponse();

        User user = User.builder().uid(request.getUid()).password(request.getPassword()).build();

        ResponseEntity response = null;
        if (userDao.updatePassword(user.getUid(), enc.sha256(user.getPassword())) > 0) {
            User modifiedUser = userDao.findUserByUid(user.getUid());
            result.status = true;
            result.data = "success";
            result.object = modifiedUser;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/modify/profileimg")
    @ApiOperation(value = "회원정보수정 - 프로필사진 변경")
    public Object modifyProfileImg(@RequestBody User user) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        if (userDao.updateProfileImg(user.getUid(), user.getProfileImg()) > 0) {
            User modifiedUser = userDao.findUserByUid(user.getUid());
            result.status = true;
            result.data = "success";
            result.object = modifiedUser;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/modify/phone")
    @ApiOperation(value = "회원정보수정 - 연락처 변경")
    public Object modifyPhone(@RequestBody User user) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        if (userDao.updatePhone(user.getUid(), user.getPhone()) > 0) {
            User modifiedUser = userDao.findUserByUid(user.getUid());
            result.status = true;
            result.data = "success";
            result.object = modifiedUser;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/modify/website")
    @ApiOperation(value = "회원정보수정 - 웹사이트 변경")
    public Object modifyWebsite(@RequestBody User user) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        if (userDao.updateWebsite(user.getUid(), user.getWebsite()) > 0) {
            User modifiedUser = userDao.findUserByUid(user.getUid());
            result.status = true;
            result.data = "success";
            result.object = modifiedUser;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/modify/name")
    @ApiOperation(value = "회원정보수정 - 이름 변경")
    public Object modifyName(@RequestBody User user) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        if (userDao.updateName(user.getUid(), user.getName()) > 0) {
            User modifiedUser = userDao.findUserByUid(user.getUid());
            result.status = true;
            result.data = "success";
            result.object = modifiedUser;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/modify/birthdate")
    @ApiOperation(value = "회원정보수정 - 생일 변경")
    public Object modifyBirthDate(@RequestBody User user) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        if (userDao.updateBirthDate(user.getUid(), user.getBirthDate()) > 0) {
            User modifiedUser = userDao.findUserByUid(user.getUid());
            result.status = true;
            result.data = "success";
            result.object = modifiedUser;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/modify/address")
    @ApiOperation(value = "회원정보수정 - 주소 변경")
    public Object modifyAddress(@RequestBody User user) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        if (userDao.updateAddress(user.getUid(), user.getAddress()) > 0) {
            User modifiedUser = userDao.findUserByUid(user.getUid());
            result.status = true;
            result.data = "success";
            result.object = modifiedUser;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/modify/position")
    @ApiOperation(value = "회원정보수정 - 직책 변경")
    public Object modifyPosition(@RequestBody User user) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        if (userDao.updatePosition(user.getUid(), user.getPosition()) > 0) {
            User modifiedUser = userDao.findUserByUid(user.getUid());
            result.status = true;
            result.data = "success";
            result.object = modifiedUser;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @DeleteMapping("/dropout/{uid}")
    @ApiOperation(value = "회원탈퇴")
    public Object dropOut(@PathVariable int uid) {
        final BasicResponse result = new BasicResponse();

        ResponseEntity response = null;

        if (userDao.removeUserByUid(uid) > 0) {
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/searchNickname/{keyword}")
    @ApiOperation(value = "닉네임 검색")
    public Object searchNickname(@PathVariable String keyword) {
        List<UserNicknameMapping> nicknames = userDao.findAllByNicknameContaining(keyword);
        return new ResponseEntity<List<UserNicknameMapping>>(nicknames, HttpStatus.OK);
    }
}