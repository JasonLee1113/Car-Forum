package com.example.demo.model;

public class ApiResponse {
	
	private boolean success;
	private String message;
	private String jwt;
	
	public ApiResponse(boolean success, String message, String jwt) {
		super();
		this.success = success;
		this.message = message;
		this.jwt = jwt;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
}
