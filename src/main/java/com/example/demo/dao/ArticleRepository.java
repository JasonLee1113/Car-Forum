package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer>{
	
	// 依分類主題查詢文章
	List<Article> findByArticleCategory(String articleCategory);
	
}
