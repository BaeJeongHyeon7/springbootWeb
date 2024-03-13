package com.example8.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example8.dto.ArticleForm;
import com.example8.entity.Article;
import com.example8.repository.ArticleRepository;
import com.example8.service.ArticleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController // RestAPI용 컨트럴러! 데이터(json)반환용!
public class ArticleApiController {

	// 스프링부트한테 articleRepository 객체를 주입!
//	@Autowired
//	private ArticleRepository articleRepository;
//	
	@Autowired
	private ArticleService articleService;

	// GET 전체적인 내용을 조회하는 것!
	@GetMapping("/api/articles")
	public List<Article> index() {
		// 1. 데이터베이스 조회
		// 2.데이터 리턴
		log.info("RestController 의 index()메서드실행!");
		return articleService.index();
	}

	@GetMapping("/api/articles/{id}")
	public Article index(@PathVariable Long id) {
		// 1. 데이터베이스 조회
		// 2.데이터 리턴
		log.info("RestController 의 index(id)메서드실행!");
		return articleService.show(id);
	}

	// POST
	// 데이터를 테이블에 저장하고 저장한 데이터를 리턴시킨다.
	// 상태 코드를 담아서 리턴하려면 ResponeEntity 객체를
	// 사용해야 한다.
	// ResponseEntity<Article>를 리턴타입으로
	// 사용하면 ResponseEntity 객체에 Article
	// 담아서 리턴한다.
	@PostMapping("/api/articles")
	public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
		// form 태그를 이용해서 데이터를 주고 받을 때는 매개변수
		// name을 이용해서 바로 매칭이 된다.
		// 하지만 json 타입으로 REST API는 json타입이기 떄문에
		// 매핑을 시켜주지 않는다.
		// 1. 엔티티로 변경
		// Article article = dto.toEntity();

		log.info("RestAPIController create()");
		log.info(dto.toEntity().toString());

		// 서비스가 처리하고 난 객체를 반환 받는다.
		// 그래서 만약 객체가 있다면 정상적으로 저장되었고 그 객체를
		// 반환해준다. 만약 객체가 null이면 body없이 넘긴다.
		// body() 메서드는 body에 데이터를 담아서 넘긴다.
		// build() 메서드를 이용해서 body없이 넘긴다.

		Article saved = articleService.create(dto);

		return saved != null ? ResponseEntity.status(HttpStatus.CREATED).body(saved)
							 : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	// PATCH
	@PatchMapping("/api/articles/{id}")
	public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
		log.info("id: {} , article: {}", id, dto.toString());
		
		// 4. 업데이트 및 정상 응답
		Article updated = articleService.update(id,dto);

		return updated != null ? ResponseEntity.status(HttpStatus.OK).body(updated)
							   : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

				
	}

	// DELETE

	@DeleteMapping("/api/articles/{id}")
	public ResponseEntity<Article> delete(@PathVariable Long id) {

		System.out.println("delete()메서드 실행");
		System.out.println("id :" + id);

		/*
		 * // 삭제할 entity 조회 Article target = articleRepository .findById(id)
		 * .orElse(null); try { System.out.println("target: "+ target.toString());
		 * }catch(Exception e) { System.out.println(e.getMessage()); } // 잘못된 요청이 들어왔다.
		 * if(target == null) { return ResponseEntity .status(HttpStatus.BAD_REQUEST)
		 * .body(null); }
		 * 
		 * // 삭제가 정상적으로 이루어졌다면 그 결과값도 보내줘야된다. // ResponseEntity 객체에 204
		 * 상태코드(HttpStatus.NO_CONTENT)를 저장하고 // Body에는 아무것도 저장하지 않는다.
		 * 
		 * articleRepository.delete(target);
		 * 
		 * return ResponseEntity .status(HttpStatus.NO_CONTENT) .body(null);
		 * 
		 * 
		 * 
		 * Article target = articleRepository .findById(id) .orElse(null);
		 * 
		 * if(target == null) { return ResponseEntity .status(HttpStatus.BAD_REQUEST)
		 * .body(null); } articleRepository.delete(target);
		 */

		Article deleted = articleService.delete(id);

		return deleted != null ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

}
