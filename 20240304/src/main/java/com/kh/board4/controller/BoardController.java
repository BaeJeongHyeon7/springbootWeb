package com.kh.board4.controller;

import java.sql.Date;
import java.time.LocalDateTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.board4.entity.Board;
import com.kh.board4.entity.Reply;
import com.kh.board4.repository.BoardRepository;
import com.kh.board4.repository.ReplyRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	// 0304컨트롤러
	@GetMapping("/")
	public String index(Model model,
					@RequestParam(defaultValue="0") int page) {
								// ▲ 요청파라메터 없을 경우 기본 디폴트값으로 0을 준다는 뜻.
		log.info("indes() 실행");
		
		// 페이지 네이션을 위한 페이지 및 사이즈 설정
		// @RequestParam(defaultValue="0") int page
		// 우리가 버튼을 눌러스 1페이지, 2페이지 넘어가서 글을 확인하려고
		// 근데 시작하자마자 page값ㄷ이 들어올 일이 없다. 버튼을 안 누르기 때문에
		// 그럼 에러가 발생한다. 그거 없애기 위해서 defaultValue에 기본값 설정을 한다.
		int pageSize = 5;
		
		// 스프링 jpa에서 페이지를 쿼리하느넫 사용되는 Pageable 안에 페이지 번호,
		// 페이지 크기, 정렬순서 등과 같은 페이지에 관한 정보를 제공하는 인터페이스
		// PageRequest.of()메서드를 이용해서 PageRequest객체를 생성할 수 있다.
		// 현재 보고 있는 페이지와 몇개씩 보여줄건지 넘겨주어야한다.
		Pageable pageable = PageRequest.of(page,pageSize);
		
		// 페이지로 부터 게시글 목록을 가져오기
		Page<Board> list = boardRepository.findAll(pageable);
		
		// 게시글 가져오는 명령문
		// List<Board> list = boardRepository.findAll();
		
		// 리스트 자체로 넘겨도 상관없지만 페이지 인터페이스로 객체를 반환 받으면 그 내용들이
		// 대부분 getContent에 리스트형태로 저장이 되어있다.
		
		log.info("{}",list.getContent());
		log.info("{}",list);
		model.addAttribute("lists", list);
		
		// page 변수를 이용해서 페이징 버튼을 만들 것!
		model.addAttribute("page", list);
		return "board/index";
	}
	
	@GetMapping("/board/detail/{no}")
	public String detail(@PathVariable("no") Long no, Model model) {
		
		log.info("no:{}", no);
		log.info("detail()");
		// 데이터베이스에서 데이터 꺼내기
		Board board = boardRepository.findById(no).orElse(null);
		
		// 조회수 증가시키는 코드
		// null 값이라서 +더하기 못함
		// 데이터베이스의 insert값을 확인해서 처리하면된다.
		board.setReadCount(board.getReadCount() +1);
		boardRepository.save(board);
		
		// 로그값 출력
		log.info("{}", board);
		log.info("{}", board.getReadCount());
		
		// 댓글을 조회해서 뷰페이지로 같이 넘겨줘야된다.
		List <Reply> replyList = replyRepository.findByBoardNoAndWriterNo(no, board.getWriterNo());

		// 모델에 값 저장
		model.addAttribute("board",board);
		model.addAttribute("replyList",replyList);
		// 뷰페이지 이동
		return "board/detail";
	}
	
	// 검색을 위한 메서드
	// 제목 또는 내용 또는 제목+내용으로 검색을 할 때 사용하는 메서드
	@PostMapping("/board/search")
	public String search(
			@RequestParam(value="searchOption", required=false) String searchOption,
			@RequestParam(value="search", required=false) String keyword,
			Model model) {
		
		// 1. 페이지네이션 설정
		Page<Board> searchResultPage = null;
										//현재페이지, 몇개씩볼건지(pageSize)
		Pageable pageable= PageRequest.of(0,5);
		// 2. 내용, 제목, 내용+제목 구별을 해야한다.
		if(searchOption != null && keyword != null) {
			switch(searchOption) {
			
			case "title":
				searchResultPage = boardRepository.findByTitleContaining(keyword, pageable);
				break;
			case "content":
				searchResultPage = boardRepository.findByContentContaining(keyword, pageable);
				break;
			case "titleContent":
				searchResultPage = boardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
				break;
			
			}
		}else {
			// 위에 내용들이 모두 null일 경우는 전체 선택
			// findAll(페이지설정정보만 pageable을 보내준다!)
			searchResultPage = boardRepository.findAll(pageable);
		}
		// boards 현재 페이지 설정 정보 + 디비의 모든 내용이 list형태로 담겨있다.
		// 그중에서 getContent()는 디비의 내용만 뷰페이지로 전송된다.
		
		model.addAttribute("lists", searchResultPage.getContent());
		model.addAttribute("page", searchResultPage);
		model.addAttribute("loginMember", "admin");
		
		return "board/index";
	}
	
	// 댓글 추가
	@PostMapping("/board/reply")
	public String reply(
			@RequestParam Long boardNo,
			@RequestParam String writerId,
			@RequestParam String content,
			RedirectAttributes redirect) {
		// 1. 게시글 가져오기
		Board board= boardRepository.findById(boardNo).orElse(null);
		// 2. 로그 출력
		log.info("reply{}");
		log.info("board{} : ", board);
		log.info("boardNo{} :", boardNo);
		
		// 3. 댓글 생성
		Reply reply = new Reply();
		reply.setBoardNo(boardNo);
		reply.setWriterNo(board.getWriterNo());
		reply.setContent(content);
		reply.setCreateDate(Date.valueOf(LocalDateTime.now().toLocalDate()));
		
		// 4. 디비에 추가
		replyRepository.save(reply);
		
		// 5.리다이렉트시 게시글 상세 페이지로 이동할 수 있도록 값을 저장할 수 있다.
		redirect.addAttribute("no", boardNo);
		return "redirect:/board/detail/{no}";
	}
	
	
}
