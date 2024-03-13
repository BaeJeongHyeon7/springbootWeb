package com.example8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example8.dto.ArticleForm;
import com.example8.entity.Article;
import com.example8.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service  // springboot가 서비스로 인식하여 자동으로 객체를 생성해서
		  // 등록!
public class ArticleService {
	
	// 1. 서비스도 데이터베이스 접근 할 수있도록  설정하기
	//   스프링 부트가 관리하는 객체를 우리는 Bean을 자동으로
	//   주입받기 
	@Autowired
	private ArticleRepository articleRepository;
	
	
	// update메서드를 이용해서 데이터를 수정하는 메서드 작성하기
	// 방금전과 똑같은 실행 결과가 나와야된다. 

	// Article 삭제
	public Article delete(Long id) {
		log.info("ArticleServie delete메서드 실행");
		Article target = articleRepository
						.findById(id)
						.orElse(null);	
		if(target == null) {
			log.info("잘못된 요청! {}번 글은 테이블에 존재하지 않습니다.",id);
			return null;
		}
		articleRepository.delete(target);
		return target;
	}


	public List<Article> index() {
		log.info("ArticleServie 의 index()");
		
		return articleRepository.findAll();
	}

	// 한건 조회(
	public Article show(Long id) {
		
		return articleRepository
				.findById(id)
				.orElse(null);
	}
	// Article 생성 
	public Article create(ArticleForm dto) {
		log.info("ArticleService 의 create()메서드 실행");
		
		// 1. 엔티티로 변경 
		Article article = dto.toEntity();
		// id는 DB가 자동으로 생성하므로 id가 넘어오는 데이터는 
		// 저장하지 않는다.
		
		// 2. 중복검사 (혹시 id가 있다면 중복적인 데이터가 들어갈
		//   수있다. id값이 같다 그러면 에러를 유발한다.)
		if(article.getId() != null) {
			return null;
		}		
		
		// 3. 실제 데이터베이스 메서드 호출해서 저장하기 save();
		return articleRepository.save(article);
	}

	// Article 수정 
	public Article update(Long id, ArticleForm dto) {
		
		log.info("ArticleService메서드 update()");
		Article article = dto.toEntity();
		Article target = articleRepository
						.findById(id)
						.orElse(null);
		if( target == null || id != article.getId()) {
			return null;
		}
		// id만 입력되면 null을 리턴하고 코드 추가
		if(article.getTitle() == null && 
		   article.getContent() == null) {
			return null;
		}
		target.patch(article);
				
		return articleRepository.save(target);
	}	
	
}




