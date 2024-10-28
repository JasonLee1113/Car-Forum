package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;


@Entity
@Table(name = "Article")
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonProperty("category")
	@Column(name = "articleCategory")
	private String articleCategory;
	
	@JsonProperty("title")
	private String articleTitle;
	
	@JsonProperty("content")
	private String articleContent;
	
	@Lob
	@ElementCollection
	@JsonProperty("imageList")
	private List<byte[]> imageList = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public List<byte[]> getImageList() {
		return imageList;
	}

	public void setImageList(List<byte[]> imageList) {
		this.imageList = imageList;
	}
	
	
	
	
	
	
}
