package com.web.blog.controller.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.web.blog.dao.user.UserDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = { "http://localhost:3000" })
public class FileController {

    @Autowired
    UserDao userDao;

    @ApiOperation(value = "유저 프로필사진 업로드")
    @PostMapping("/uploadProfileImg/{uid}")
    public Object uploadProfileImg(@PathVariable int uid, @RequestParam("profileImg") MultipartFile file,
            HttpServletRequest request) {
        ResponseEntity response = null;
        String retPath;
        // for (MultipartFile file : files) {
        // String fileName = file.getOriginalFilename();
        System.out.println(request.getServletContext().getRealPath("/img/userProfileImg/"));
        try {

            retPath = save(file, request.getServletContext().getRealPath("/img/userProfileImg/"));

            // final BasicResponse result = new BasicResponse();
            // result.status = true;
            // result.data = "success";
            // result.object = "/img/userProfileImg/" + retPath;
            // response = new ResponseEntity<>(result, HttpStatus.OK);

        } catch (IllegalStateException e) {
            e.printStackTrace();

            response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
        // }

        if (userDao.updateProfileImg(uid, retPath) > 0) {
            User modifiedUser = userDao.findUserByUid(uid);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            result.object = modifiedUser;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;

    }

    private String save(MultipartFile file, String contextPath) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String newFileName = simpleDateFormat.format(new Date()) + "_" + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            System.out.println(contextPath);
            Path path = Paths.get(contextPath + newFileName);
            // .get(contextPath + newFileName);
            Files.write(path, bytes);
            return newFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation(value = "포스트 이미지 업로드")
    @PostMapping("/uploadBoardImg")
    public Object uploadProfileImg(@RequestParam("boardImg") MultipartFile file, HttpServletRequest request) {
        ResponseEntity response = null;
        String baseURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + "/img/boardImg/";
        String retPath;
        try {
            retPath = save(file, request.getServletContext().getRealPath("/img/boardImg/"));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = baseURL + retPath;
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;

    }

    @ApiOperation(value = "프로젝트 이미지 업로드")
    @PostMapping("/uploadProjectImg")
    public Object uploadProjectImg(@RequestParam("projectImg") MultipartFile file, HttpServletRequest request) {
        ResponseEntity response = null;
        String baseURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + "/img/projectImg/";
        String retPath;
        try {
            retPath = save(file, request.getServletContext().getRealPath("/img/projectImg/"));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = baseURL + retPath;
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;

    }

    @ApiOperation(value = "test")
    @PostMapping("/test")
    public Object test(HttpServletRequest request) {
        String baseURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + "/img/boardImg/";
        System.out.println(baseURL);
        return null;

    }
}