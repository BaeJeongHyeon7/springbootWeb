package com.kh.jpa9.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kh.jpa9.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{
	// 쿼리 메서드를 이요해서 Page 반환될 수 있도록 만든다.
	// 전체 선택
	Page<Member> findByNameContainsOrIdContains(String name, String id, Pageable pageable);

	// 회원 검색용 - name, 키워드 포함된 이름 검색
	// Containing : 특정 문자열을 포함하는 엔티티를 검색할 때 사용
	Page<Member> findByNameContaining(String name, Pageable pageable);
	
	// 회원 검색용 - id, 단 id는 모든 이름이 맞아야 검색이 가능하다.
	Page<Member> findById(String id, Pageable pageable);
	
	// 회원 검색용 - phone
	// 010이 포함된 번호를 모두 검색한다.
	Page<Member> findByPhoneContaining(String phone, Pageable pageable);

	// 특정 문자열이 포함되어있는지 확인
	// Like %111%
	Page<Member> findByPhoneLike(String phone, Pageable pageable);
	
	// 111번호로 시작하는 번호를 찾으려면? 111%
	// 쿼리 조건 키워드를 조합
	Page<Member> findByPhoneStartsWith(String phone, Pageable pageable);
	
	// 111번호로 시작하는 번호를 찾으려면? %111
	// 쿼리 조건 키워드를 조합
	Page<Member> findByPhoneEndsWith(String phone, Pageable pageable);

	// 대소문자 구분하기
	Page<Member> findByIdContains(String id, Pageable pageable);
	
	// 게시글 내용 + 작성자
	// and 쿼리 조건 만들기
	Page<Member> findByNameAndId(String name, String id, Pageable pageable);
	
	// JPA 정렬 쿼리 메서드 만들기 1
	// 회원 등록순(num)으로 정렬 (1)조건 (2)정렬
	// 주어진 값보다 크거나 같은 값을 갖는 열을 검색할 때 사용된다.
	Page<Member> findByNumGreaterThanEqualOrderByNameAsc(
			Integer num, Pageable pageable);
	
	// JPA 정렬 쿼리 메서드 만들기 2
	Page<Member> findByAgeLessThanEqualOrderByNameAsc(
				Integer num, Pageable pageable);
	// @Query 어노테이션을 이용하여 쿼리를 작성하는방법
	// 어떤 이름이 포함되어있고 나이가 20세 이상만 검색할 수 있다.
	// 정렬
//	@Query("select * from member m where m.name=? "
//			+" and m.age > 20 "
//			+" m.name ASC")
//	Page<Member> findByName(String name, Pageable pageable);

	// 이름이 "익준" 들어가고 나이가 20이상이고, 정렬은 이름 기준으로 오름차순으로 정렬하시오.
//	@Query("select * from member m"
//			+" where m.name Like %?% "
//			+" and m.age > 20 "
//			+" order by m.name asc")
//	Page<Member> findByName(String name, Pageable pageable);
	
	

}
