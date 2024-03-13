package com.example8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

	@GetMapping("/member/index.do")
	public String index(Model model) {
		
		
		return "member/index";
	}
}
