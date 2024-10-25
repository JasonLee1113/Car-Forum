package com.example.demo.controller;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ArticleRepository;
import com.example.demo.dto.ArticleDto;
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

			service.createArticle(article);
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
	
	/**
	 * @param articleID
	 * 查找單筆文章
	 */
	@GetMapping("/getOneArticle")
	public ResponseEntity<?> getOneArticle(@RequestParam("articleId") Integer articleId){
		try {
			log.info("查找單篇文章 ID:" + articleId);
			Optional<Article> articleOptional = service.getOneArticle(articleId);
			if(articleOptional.isPresent()) {
				Article article = articleOptional.get();
				List<String> encodedImages = article.getImageList().stream()
						.map(img -> Base64.getEncoder().encodeToString(img))
						.collect(Collectors.toList());
				
				ArticleDto articleDto = new ArticleDto();
				articleDto.setArticleCategory(article.getArticleCategory());
				articleDto.setArticleTitle(article.getArticleTitle());
				articleDto.setArticleContent(article.getArticleContent());
				articleDto.setBase64ImageList(encodedImages);
				
				return ResponseEntity.ok(articleDto);
				
				
			}else {
				ApiResponse response = new ApiResponse(false, "文章不存在", null);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		}catch(Exception e) {
			log.error("取得單筆文章 例外訊息" + e.toString());
			ApiResponse response = new ApiResponse(false, "取得單筆文章失敗", null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	/**
	 *  @param articleID
	 *  刪除文章
	 */
	@DeleteMapping("/deleteArticle")
	public ResponseEntity<?> deleteArticle(@RequestParam("articleId") Integer articleId) {
		String logTitle = "刪除單筆文章";
		try {
			log.info(logTitle + "ID:" + articleId);
			service.deleteArticle(articleId);
			ApiResponse response = new ApiResponse(true, "文章刪除成功", null);
			return ResponseEntity.ok(response);
		}catch(Exception e) {
			log.error(logTitle + "Exception:" + e.toString());
			ApiResponse response = new ApiResponse(false, "文章刪除失敗", null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	/**
	 * 
	 * 更新文章
	 */
	@PutMapping("/updateArticle")
	public ResponseEntity<?> updateArticle(@RequestParam("category") String articleCategory,
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

			service.createArticle(article);
			ApiResponse response = new ApiResponse(true, "新增文章成功!", null);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("例外訊息:" + e.toString());
			ApiResponse response = new ApiResponse(false, "testErrorMsg", null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(response);
		}
	}

	

}
