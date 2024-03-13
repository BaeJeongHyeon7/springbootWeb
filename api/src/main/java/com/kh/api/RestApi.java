package com.kh.api;

public class RestApi {

}
// 클라이언트
// - 웹, 모바일(휴대폰, 패드, 워치, cctv, iot)
// API
// - 소프트웨어가 다른 소프트웨어로부터 지정된 형식으로 요청
// - 명령을 받을 수 있는 수단
// - 예) 나 = 리모콘 = 에어컨

// REST API
// - 각 요청이 어떤 동작이나 정보를 위한 것인지를 요청을 보내는 주소만으로도 추론이 가능
// - HTTP요청을 보낼 때 어떤 URI에 어떤 메서드를 사용할 지 개발자들 사이에서 널리 지켜지는 약속

// REST API 사용방법
// - URL에 동사를 쓰지말고 자원을 표시해야된다.
// - 올바른 예) localhost/students/1
//           localhost/class
// - 올바르지 않은 방법)
// - get-students?student=1

// REST 구체적인 개념
// - CRUD
// - Create : 생성(POST)
// - Read : 조회(Get)
// - Update : 수정(PUT)
// - DELETE : 삭제(DELETE)

// 서버랑 클라이언트 구조
// - REST server : API 제공하고 비지니스 로직 처리 및 저장한다.
// - Client : 사용자 인증, context(세션, 로그인 정보)등을 직접관리하고 책임진다.

// 서버코드
// 200 : 응답 성공
// 201 : 데이터 생성완료
// 404 : 경로 못 찾음
// 500 : 서버 내부 에러(문법에러, 오타)

// Rest Controller
// - 데이터를 반환한다. json 타입이나 문자열 타입도 반환한다.
// Controller
// - 뷰 템플릿 페이지를 반환한다.