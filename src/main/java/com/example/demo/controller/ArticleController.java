package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ArticleRepository;
import com.example.demo.model.ApiResponse;
import com.example.demo.model.Article;
import com.example.demo.security.JwtAuthTokenFilter;
import com.example.demo.service.ArticleService;

@RestController
@RequestMapping("/article")
@CrossOrigin(origins = "*")
public class ArticleController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private JwtAuthTokenFilter tokenFilter;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ArticleService service;

	/**
	 * 新增文章
	 * 
	 * @param articleCategory
	 * @return
	 */
	@PostMapping("/create")
	public ResponseEntity<?> insertArticle(@RequestParam("category") String articleCategory,
			@RequestParam("title") String articleTitle, @RequestParam("content") String articleContent,
			@RequestParam("imageList") MultipartFile[] imageList) {

		try {
			log.info("createArticle");
			log.info("article category:" + articleCategory);
			log.info("article title:" + articleTitle);
			log.info("article content:" + articleContent);

			Article article = new Article();
			article.setArticleCategory(articleCategory);
			article.setArticleTitle(articleTitle);
			article.setArticleContent(articleContent);

			for (MultipartFile image : imageList) {
				log.info("article image:" + image.getBytes());
				article.getImageList().add(image.getBytes());
			}

			articleRepository.save(article);

			ApiResponse response = new ApiResponse(true, "新增文章成功!", null);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("例外訊息:" + e.toString());
			ApiResponse response = new ApiResponse(false, "testErrorMsg", null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(response);
		}
	}

	/**
	 * 查找文章
	 */
	@GetMapping("/getArticle")
	public ResponseEntity<?> readArticle() {
		try {
			List<Article> articleList = service.readArticle();
			log.info("查找文章 count:" + articleList.size());
			log.info("查找文章 content:" + articleList.get(0).getArticleContent());


			return ResponseEntity.ok(articleList);
		} catch (Exception e) {
			log.error("例外訊息:" + e.toString());
			ApiResponse response = new ApiResponse(false, "testErrorMsg", null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(response);
		}
	}

}
