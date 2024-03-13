package com.example8.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 헤더는 우편봉투 바디는 우편에 담긴 내용물! 


@RestController // RestAPI 컨트롤러 JSON반환!
public class FirstApiController {

	@GetMapping("/api/hello")
	public String hello() {
		return "hello world!";
	}	
}
