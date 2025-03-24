package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeControoler {
	
	@GetMapping("/")	// 현재경로로 접근
	public String index() {
		return "index";
	}
	public String a() {
		return "";
	}
}
