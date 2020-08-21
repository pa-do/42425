package com.web.blog.controller.board;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.blog.model.board.Board;
import com.web.blog.service.board.BoardService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/board")
@CrossOrigin(origins = { "http://localhost:3000" })
public class BoardController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	BoardService boardService;

	// 모든 게시글 조회
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Board>> getAllboards() {
		List<Board> board = boardService.findAll();
		return new ResponseEntity<List<Board>>(board, HttpStatus.OK);
	}

	// 게시글 번호로 조회
	@GetMapping(value = "/{bid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Board> getboard(@PathVariable("bid") int bid) {
		Board board = boardService.findByBid(bid);
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}

	// 게시글 번호로 삭제
	@DeleteMapping(value = "/delete/{bid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> deleteBoard(@PathVariable("bid") int bid) {
		boardService.deleteByBid(bid);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	// 게시글 번호로 게시글 수정
	@PutMapping(value = "/modify", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Board> updateBoard(Board board, MultipartFile file, Boolean isRemoveImg,
			HttpServletRequest request) {
		String retPath;
		if (isRemoveImg) {// 등록된 이미지를 제거하고 default로 변경하는 경우
			retPath = "default.png";
			board.setBoardImg(retPath);
		} else if (file != null) {
			try {
				retPath = save(file, request.getServletContext().getRealPath("/img/boardImg/"));
			} catch (IllegalStateException e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			board.setBoardImg(retPath);
		}
		boardService.modify(board);
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}

	// 게시글 등록
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public ResponseEntity<Board> save(HttpServletRequest req, Board board, MultipartFile file,
			HttpServletRequest request) {
		String retPath = null;
		try {
			if (file != null)
				retPath = save(file, request.getServletContext().getRealPath("/img/boardImg/"));
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		board.setBoardImg(retPath);
		return new ResponseEntity<Board>(boardService.save(board), HttpStatus.OK);
	}

	// 검색
	@GetMapping("/searchTitle/{keyword}")
	public Object searchTitle(@PathVariable String keyword) {
		List<Board> board = boardService.searchBoardTitle(keyword);
		// if(board == null) {
		// return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		// }
		return new ResponseEntity<List<Board>>(board, HttpStatus.OK);

	}

	@GetMapping("/searchContent/{keyword}")
	public Object searchContent(@PathVariable String keyword) {
		List<Board> board = boardService.searchBoardContent(keyword);
		return new ResponseEntity<List<Board>>(board, HttpStatus.OK);
	}

	// @GetMapping("/searchNickname/{keyword}")
	// public Object searchNickname(@PathVariable String keyword) {
	// List<Board> board = boardService.searchBoardNickname(keyword);
	// return new ResponseEntity<List<Board>>(board, HttpStatus.OK);
	// }

	// 전체 게시글 중 특정 유저(uid)가 작성한 글 목록 조회
	@ApiOperation(value = "전체 게시글 중 특정 유저(uid)가 작성한 글만 조회")
	@GetMapping(value = "byUser/{uid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Board>> getBoardByUid(@PathVariable("uid") int uid) {
		List<Board> board = boardService.findByUid(uid);
		return new ResponseEntity<List<Board>>(board, HttpStatus.OK);
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
}
