package com.example8.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example8.entity.Comment;


public interface CommentRepository 
	extends JpaRepository<Comment, Long>{
	
	// 특정 게시글의 모든 댓글을 조회하는 메서드
	// @Query() 쿼리를 만들어 사용할 수 있다. 
	@Query(value="select * from comment where article_id =:articleId"
			,nativeQuery = true)
	List<Comment> findByArticleId(Long articleId);
	
	

	
	
	

}
