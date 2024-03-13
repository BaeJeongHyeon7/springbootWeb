package com.example8.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example8.dto.ArticleForm;
import com.example8.entity.Article;
import com.example8.repository.ArticleRepository;
import com.example8.repository.CommentRepository;
import com.example8.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j // 롬복에서 지원하는 로그 어노테이션
public class HomeController {

	// 댓글 목록을 가져오기 위해서 CommentService클래스 bean
	// 얻어오기 
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ArticleRepository articleRe;

	@GetMapping("/articles/new")
	public String newArticleForm() {
		// form을 보여주는 페이지로 이동만 시키는 메서드
		return "articles/new";

	}

	@PostMapping("/articles/create")
	public String createArticle(ArticleForm form) {

		System.out.println(form);
		// log라는 변수를 이용해서 로그값을 찍기 위해서
		// @Slf4j 어노테이션을 달아야된다.
		// 그리고 반드시 log() 문자열로 지정한다.

		// 가장 디테일 하게 데이터를 확인할 수있다.
		// log.trace(form.toString());

		// 1. DTO데이터를 Entity변경하는 메서드 호출
		Article article = form.toEntity();

		// 2. 저장 save();
		Article saved = articleRe.save(article);
		System.out.println(saved);

		return "redirect:/articles";
	}

	@GetMapping("/articles")
	public String index(Model model) {
		log.info("컨트롤러의 index()메서드 실!행");

		// 테이블의 모든 저장된 글을 가져온다.
		List<Article> articleEntityList = articleRe.findAll();
		log.info(articleEntityList + "");
		model.addAttribute("articleList", articleEntityList);

		return "articles/index";
	}

	// 브라우저에서 / articles/글번호 형태의 요청을 받아 처리한다.
	// {}는 변경되는 / articles/1,/ articles/2,/ articles/3
	// 와 같이 변화되는 데이터를 받는다는 의미로 url매핑 안에 {}를 사용한다.
	// @PathVariable 어노테이션을 변수 앞에 붙인다.
	@GetMapping("/articles/{id}")
	public String show(@PathVariable Long id, Model model) {
		log.info("컨트롤러의 show()메서드 실행");
		log.info("id :" + id);

		
		
		// articleRe변수가 메소드 인자로 id에 해당하는 데이터
		// 1건을 테이블에서 얻어온다.
		// 데이터가 있을 때는 우리가 지정한 엔티티 타입으로 객체를
		// 저장해서 반환하는데 만약 데이터가 없다면 어떻게 할 것인지
		// 알려줘야된다. 데이터가 없으면 orEsle()메서드를 이용해서
		// 어떤 값을 리턴할 지 결정해라! null을 돌려준다.
		Article articleEntity = articleRe.findById(id).orElse(null);

		// 테이블로 보내기
		model.addAttribute("article", articleEntity);

		// 댓글 목록을 얻어온다.
		// 테이블에서 가져온 댓글 show.mustache 파일로 넘기기위해
		// Model인터페이스 객체에 댓글을 넣어줘야된다. 
		
		commentService.comments(id);
				
		
		// 뷰페이지 설정 머스태치로 이동한다.
		return "articles/show";
	}
	// NPE(NullPointerException)
	// null여부
	// 프로그램 실행시 null이 존재할 수있다. null대신
	// 초기값을 사용하길 권장

	// 반환하는 타입이 Optional클래스
	// java 8버전에서 NPE 방지를 도와준다
	// null 이 올수 있는 값을 감싸는 Warpper클래스

	@GetMapping("/articles/{id}/edit")
	public String edit(@PathVariable Long id, Model model) {
		log.info("컨트롤러의 edit() 실행 ");
		log.info("id : " + id);

		// 수정할 데이터를 얻어온다.

		Article articleEntity = articleRe.findById(id).orElse(null);

		// 테이블로 보내기
		model.addAttribute("article", articleEntity);

		return "articles/edit";
	}

	@PostMapping("/articles/update")
	public String update(ArticleForm form) {

		// form객체가 왔으니 데이터베이스랑 매핑해야된다
		// 변환하기 entity
		Article article = form.toEntity();
		log.info(article.toString());

		Article target = articleRe.findById(article.getId()).orElse(null);

		// 수정하는 메서드는 데이터가 있으면 수정을 하면 되고
		// 데이터가 없으면 수정을 하면 안된다.
		if (target != null) {
			articleRe.save(article);
		}
		// 글 1건 수정이 완료되었으므로 redirect를 이용해서
		// 상세 페이지로 이동한다.

		return "redirect:/articles/" + article.getId();
	}

	@GetMapping("/articles/{id}/delete")
	public String delete(@PathVariable Long id) {

		log.info("컨트롤러의 delete()메서드 실행");
		log.info("id = " + id);

		Article target = articleRe
						.findById(id)
						.orElse(null);

		
		// 삭제할 데이터를 가져와서 null이 아니면 삭제하기
		if (target != null) {
			articleRe.delete(target);
		}

		return "redirect:/articles";
	}

	@GetMapping("/hi")
	public String home(Model model) {

		// hi url 들어오면 namelist에서 랜덤하게
		// 데이터를 hello 보내기
		String[] namelist = { "이익준", "채송화", "강동원", "김준완", "양석형" };

		Random rand = new Random();
		int i = rand.nextInt(namelist.length);

		model.addAttribute("username", namelist[i]);
		return "hello"; // 페이지명만 입력하면 된다.
	}
}

/*
 * JPA(Java Persistence API) 자바에서 사용하는 ORM(Object- Relational Mapping) 자바
 * 애플리케이션과 JDBC 사이에서 동작하는 자바 인터페이스!
 * 
 * ORM(객체 관계 매핑) 객체와 관계형 데이터베이스의 데이터를 매핑하는 기술 객체와 테이블을 매핑하여 편하게 DB를 관리할 수있다.
 * 
 * ex) 만약 JPA를 이용해서 추가를 한다. DAO 클래스를 통해서 persist() 를 실행하면 JPA가 Entity 객체를 분석하여
 * SQL문을 생성한다.
 * 
 * JDBC API를 사용하여 DB에 생성된 insert sql을 보낸다.
 * 
 * ex2) 만약 JPA를 이용해서 조회를 한다. DAO 클래스에 메서드명을 호출하면서 매개변수로 찾아야되는 값을 넣어서 JPA에 보내면
 * 분석해서 sql명령문 생성(select sql)
 * 
 * JDBC API를 사용하여 생성된 select sql문을 보낸다.
 * 
 * DB에서 반환된 정보를 ResultMap 매핑을 통해 객체로 변환한다.
 * 
 * 
 * JPA 를 사용하는 이유 - 기존 개발 방식은 SQL중심적인 개발 - JPA를 이용하면 객체 중심의 애플케이션 개발이 가능하다.
 * 
 * 저장 : persist() 조회 : find() 수정 : set변수명() 삭제 : remove()
 * 
 * 설정 # 서버 포트 설정 server.port=9090 # h2 데이터베이스 웹 콘솔 접근을 허용한다.
 * spring.h2.console.enabled=true
 * 
 * # DB url이 계속 변경이 된다. 서버를 실행할 때마다 # 고정하는 방식을 사용한다.
 * 
 * # 서버를 실행할 때마다 새로운 url을 생성하지 않는다! spring.datasource.generate-unique-name=false
 * 
 * # 고정 url을 설정한다. spring.datasource.url=jdbc:h2:mem:testdb
 * 
 * # JPA 로깅 설정 디버그 레벨로 쿼리를 출력한다. logging.level.org.hibernate.SQL=DEBUG
 * 
 * # 쿼리를 보기 편하게 출력한다. spring.jpa.properties.hibernate.format_sql=true
 * 
 * # entity를 먼저 생성하기 기다렸다가 sql파일의 sql명령을 실행한다.
 * spring.jpa.defer-datasource-initialization=true
 * 
 * #쿼리에 적용되는 파라미터를 보여준다.
 * logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
 * 
 * # 실제 h2데이터베이스 콘솔에 연결하는 세팅! # 이게 없으면 페이지를 찾을 수 없다 에러가 나온다.
 * 
 * spring.h2.console.enabled=true
 */
