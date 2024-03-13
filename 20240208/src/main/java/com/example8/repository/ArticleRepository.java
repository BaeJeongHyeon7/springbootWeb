package com.example8.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example8.entity.Article;

public interface ArticleRepository 
				extends JpaRepository<Article,
									  Long>{
	
	public List<Article> findByContent(String content);
	public List<Article> findByTitle(String title);
}
