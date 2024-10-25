package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// 首頁
	@GetMapping("/index")
	public String index() {
		log.info("return index page");
		return "view/index";
	}

	@GetMapping("/signup")
	public String signup() {
		log.info("return signup page");
		return "view/signup";
	}

	@GetMapping("/login")
	public String login() {
		log.info("return login page");
		return "view/login";
	}

	@GetMapping("/allArticle")
	public String article() {
		log.info("return allArticle page");
		return "view/allArticle";
	}

	@GetMapping("/navbar")
	public String navbar() {
		log.info("return navbar ");
		return "view/navbar";
	}

	/********************** 購車討論區 ***********************************/
	// 購車-新車
	@GetMapping("/buyNewCar")
	public String buyNewCar() {
		log.info("return buyNewCar ");
		return "view/buyCarArea/buyNewCar";
	}

	// 購車-新車
	@GetMapping("/buyUsedCar")
	public String buyUsedCar() {
		log.info("return buyUsedCar ");
		return "view/buyCarArea/buyUsedCar";
	}
	
	
	// 新增文章頁面
	@GetMapping("/createArticle")
	public String createArticle() {
		log.info("return postNewCarArticle");
		return "view/buyCarArea/postNewCarArticle";
	}
	
	// 單筆詳細文章
	@GetMapping("/getOneArticle")
	public String getOneArticle() {
		log.info("return getOneArticle");
		return "view/buyCarArea/getOneArticle";
	}

}
