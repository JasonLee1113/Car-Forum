package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Article")
@Getter@Setter
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String articleCategory;
	
	private String articleTitle;
	
	private String articleContent;
	
	@Lob
	@ElementCollection
	private List<byte[]> imageList = new ArrayList<>();
	
	
	
	
	
	
}
