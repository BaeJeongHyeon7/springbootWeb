package com.example8.entity;

import javax.persistence.*;

import com.example8.dto.CommentDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 실제 더미데이터는 (test할 데이터들)
// DB에 테이블 생성하고 저장하는 클래스 즉! 엔티티가 필요하다

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
@RequiredArgsConstructor
public class Comment { //댓글
	
	// 기본키 (반드시 설정)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // id값이 자동으로 증가하는 것!
	private Long id;
	
	@Column
	@NonNull
	private String nickname;
	
	@Column
	@NonNull
	private String body;
	
	// 테이블 조인 해야되는 상황! 
	// 게시글의 id는 댓글의 여러개의 값(댓글)들이 있을 수 있다.
	@ManyToOne // 댓글(Comment)엔티티에 여러개가 하나의 
				//메인 글(Article)에 연관된다.
	@JoinColumn(name = "article_id") //article_id컬럼에 Article의 대표값 
	private Article article;		// (기본키)를 저장한다. 
	
	// 위에 컬럼과 컬럼들을 연결해서 처리하는 내용
	// form태그에서 들어오는 데이터를 entity로 변환하는 작업을 
	// 해야된다. 
	// createComment를 entity변환하는 메서드(댓글,메인글)
	public static Comment createComment(
				CommentDTO dto, Article article
			) {
		log.info("Comment의 createComment()");
		log.info("dto {}" , dto);
		log.info("article {} ", article);
		
		// entity변환
		return new Comment(dto.getId()
				   		  ,dto.getNickname()
				   		  ,dto.getBody()
				   		  ,article); 
	}
	
	
	
	
	
	
}
