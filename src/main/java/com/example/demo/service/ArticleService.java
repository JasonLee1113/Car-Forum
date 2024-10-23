package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ArticleRepository;
import com.example.demo.dto.ArticleDto;
import com.example.demo.model.Article;

@Service
public class ArticleService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ArticleRepository articleRepository;
	
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
	

	
}
