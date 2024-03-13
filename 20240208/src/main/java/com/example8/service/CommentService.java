package com.example8.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example8.dto.CommentDTO;
import com.example8.entity.Comment;
import com.example8.repository.ArticleRepository;
import com.example8.repository.CommentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentService {

	// 댓글 목록을 DB에서 꺼내오는 JPA의 객체를 생성
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ArticleRepository articleRespository;
	
	
	// 댓글 목록을 조회 실행 
	public List<CommentDTO> comments(Long articleId){
		log.info("CommentService comments()");
		log.info("articleId: {}",articleId);
		
		// 1. 댓글 모두 조회해서 가져온다.
		List<Comment> comments = commentRepository
								.findByArticleId(articleId);
		
		// 2.entity 를 dto변환 
		List<CommentDTO> dtos = 
						new ArrayList<CommentDTO>();
		// 3. 반복문을 이용해서 dto 변환한다.
		
		for(int i = 0; i < comments.size(); i++) {
			
			Comment comment = comments.get(i);
			CommentDTO dto = CommentDTO
							.createCommentDTO(comment);
			dtos.add(dto);
			
		}
		// 업그레이드 된 for 
		for (Comment comment : comments) {
			CommentDTO dto = CommentDTO.createCommentDTO(comment);
			dtos.add(dto);
		}
		
		
		// 4. 데이터가 정상적으로 왔는 지 info 작성한다.
		log.info("dtos : {}" , dtos);
		
		// 5. return 한다.
		return dtos;
	}
	
	
	
	
	

}
