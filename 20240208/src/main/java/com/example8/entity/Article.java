package com.example8.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor

@Data
@Entity  // 현재 클래스는 entity로 데이터베이스에 테이블생성을
		 // 위한 클래스가 된다. 
public class Article {
	
	/*
	 * @GeneratedValue(startegy=GenerationType.SEQUENCE)
	 * 
	 * SEQUENCE: h2,oracle,postgresql =>SEQUENCE
	 * IDENTITY: mysql, mariadb=> auto_increment사용
	 */
	
	
	//기본키 설정 
	@Id    // 반드시 entity에 존재해야된다. 
	@GeneratedValue(strategy = GenerationType.IDENTITY) // id값이 자동으로 증가하는 것!
	private Long id; 
	
	@Column     // 필드를 테이블컬럼과 매핑한다
	private String title;
	
	@Column
	private String content;

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", content=" + content + "]";
	}
	
	// 업데이트시 타이틀 있니? 내용이 있니?
	public void patch(Article article) {
		if(article.title != null) {
			// 수정할 title이 입력 되었니?
			this.title = article.title;
		}
		if(article.content != null) {
			// 수정할 content이 입력 되었니?
			this.content = article.content;
		}
	}
	
	
	

//	public Article() {
//		super();
//	}
//
//	public Article(Long id, String title, String content) {
//		super();
//		this.id = id;
//		this.title = title;
//		this.content = content;
//	}
}
