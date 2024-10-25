package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

public class ArticleDto {
	
	@JsonProperty("category")
	private String articleCategory;
	
	@JsonProperty("title")
	private String articleTitle;
	
	@JsonProperty("content")
	private String articleContent;
	
	@Lob
	@ElementCollection
	@JsonProperty("imageList")
	private List<MultipartFile> imageList = new ArrayList<>();
	
	@JsonProperty("base64ImageList")
	private List<String> base64ImageList = new ArrayList<>();

	public String getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(String articleCategory) {
		this.articleCategory = articleCategory;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public List<MultipartFile> getImageList() {
		return imageList;
	}

	public void setImageList(List<MultipartFile> imageList) {
		this.imageList = imageList;
	}

	public List<String> getBase64ImageList() {
		return base64ImageList;
	}

	public void setBase64ImageList(List<String> base64ImageList) {
		this.base64ImageList = base64ImageList;
	}
	
}
