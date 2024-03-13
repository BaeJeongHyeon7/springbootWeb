package com.example8.dto;

import com.example8.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDTO {

	private Long id;
	private String nickname;
	private String body;
	
	// 커맨드  객체를 사용해서 json 데이터를 받아 저장하려면 
	// 커맨드 객체는 카멜표기법을 사용하면 데이터를 받지 못한다. 
	// json 데이터의 key 표기법이 달라서 데이터를 못받는다.
	
	//@JsonProperty("json 키") 어노테이션을 사용하면
	// json의 key와 커맨드객체의 필드 이름이 다르더라도 
	// 데이터를 받을 수 있다. 
	@JsonProperty("article_id")
	private Long article_id;
		
	
	// entity를 dto로 변환하는 메서드 
	public static CommentDTO createCommentDTO
						(Comment comment) {
		return new CommentDTO(
				comment.getId(),
				comment.getNickname(),
				comment.getBody(),
				comment.getArticle().getId()
				);
	}
	
	
}
