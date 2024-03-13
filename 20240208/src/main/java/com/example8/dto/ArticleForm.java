package com.example8.dto;

import com.example8.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleForm {
	private Long id; //id필드 추가!
	private String title;
	private String content;
	
	
	// entity 객체화를 할 것! 
	// DTO 클래스에 데이터를 엔티티로 변경해서 테이블과 
	// 매핑되는 메서드를 추가한다.
	public Article toEntity() {
		return new Article(id,title,content);
	}
	
	
}
