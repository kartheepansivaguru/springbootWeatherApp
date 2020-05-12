package com.hackerrank.weather.service;

public class CustomException extends Exception {
	private static final long serialVersionUID = 7718828512143293558L;
	private final String code;

	public CustomException(String code) {
		super();
		this.code = code;
	}

	public CustomException(String message, Throwable cause, String code) {
		super(message, cause);
		this.code = code;
	}

	public CustomException(String message, String code) {
		super(message);
		this.code = code;
	}

	public CustomException(Throwable cause, String code) {
		super(cause);
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}
}
