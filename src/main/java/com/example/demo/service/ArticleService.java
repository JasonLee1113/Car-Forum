package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ArticleRepository;
import com.example.demo.model.Article;

@Service
public class ArticleService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ArticleRepository articleRepository;
	
	/**
	 * 新增文章
	 */
	public void createArticle(Article article) {
		try {
			articleRepository.save(article);
		}catch(Exception e) {
			log.error("新增文章錯誤 Exception:" + e.toString());
		}
	}
	
	
	/**
	 * 查詢全部文章
	 */
	public List<Article> readArticle() {
		
		List<Article> articleList = new ArrayList<>(); 
		try {
			articleList = articleRepository.findAll();			
			
		}catch(Exception e) {
			log.error("讀取全部文章錯誤 Exception:" + e.toString());
		}
		return articleList;
	}
	
	/**
	 * @param articleID
	 * 查詢單筆文章
	 */
	public Optional<Article> getOneArticle(Integer articleId) {
		Optional<Article> article = Optional.empty();
		
		try {
			article = articleRepository.findById(articleId);
			
		}catch(Exception e) {
			log.error("讀取全部文章錯誤 Exception:" + e.toString());
		}
		return article;
	}
	
	/**
	 *  @param articleID
	 *  刪除文章
	 */
	public void deleteArticle(Integer articleId) {
		
		try {
			articleRepository.deleteById(articleId);
		}catch(Exception e) {
			log.error("刪除文章錯誤 Exception:" + e.toString());
		}
	}
	

	/**
	 * 
	 * 更新文章
	 */
	public void updateArticle(Integer articleId) {
		try {
			articleRepository.save(null);
			
		}catch(Exception e) {
			
		}
	}
	

	
}
