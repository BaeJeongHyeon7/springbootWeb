package com.kh.board4.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.kh.board4.entity.Board;
import com.kh.board4.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BoardRestController {
	
	@Autowired
	private BoardRepository repository;
	
	// ResponseEntity<?> 메서드 실행마다 타입을 지정해서 보낼 수 있고
	// ResponseEntity<Board> 타입이 고정되서 Board 타입이 아니면 에러가 난다.
	@GetMapping("board/detailModal/{id}")
	public ResponseEntity<?> getBoardDetail(
			@PathVariable("id") Long id){
		// 1. 데이터를 잘 받았는지 log 값 출력
		log.info("id:{}", id);
		// 2. 아이디를 기준으로 Board엔티티 데이터를 찾는다.
		// .orElse(null); -> id 값이 없는경우 null
		Board board = repository.findById(id).orElse(null);
		// 조회수 올려주는 코드
		board.setReadCount(board.getReadCount() +1);
		repository.save(board);
		log.info("{}",board);
		// 찾는 board가 없는 경우
		if(board == null) {
			// 404 상태 코드와 함께 메시지를 반환
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Not found id: " + id);
		}
		return ResponseEntity.ok(board);
	}
	
	
	@GetMapping ("/board/all")
	public ResponseEntity<?> boardAll(){
		// 모든 board엔티티를 조회한다.
		List<Board> list = repository.findAll();
		// 조회된 list가 없는경우
		if(list == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("데이터가 없습니다.");
		}
		
		log.info("board/all : {}", list);
		return ResponseEntity.ok(list);
	}
	
	// 모달창 내 검색창(제목+내용)
	@PostMapping("/board/searchModal")
	public ResponseEntity<?> searchModal(
			@RequestParam("keyword") String keyword){
		log.info("searchModal(메서드실행-- keyword : {}",keyword); 
		//제목 또는 내용에 keyword값이 포함하는 Board엔티티를 디비에서 가져온다.
		List<Board> list =
				repository.findByTitleContainingOrContentContaining(keyword, keyword);
		// 찾는 board가 없는 경우
		if(list == null) {
			// 404 상태 코드와 함께 메시지를 반환
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body("Not found id: " + keyword);
				}
				return ResponseEntity.ok(list);
		
	}
}
