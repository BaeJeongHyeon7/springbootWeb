package com.example8.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example8.entity.Article;
@SpringBootTest
class ArticleRepositoryTest {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Test
	@Transactional
	void test() {
		
		List<Article> aList = articleRepository.findAll();
		aList.forEach(System.out::println);
		
		
		System.out.println("타이틀으로 검색 findByTitle >>>"+
				articleRepository.findByTitle("손오공"));
		
		System.out.println("컨텐츠내용으로 검색 findByContent >>>"+
		articleRepository.findByContent("천재"));
		

		

	}
	

}
