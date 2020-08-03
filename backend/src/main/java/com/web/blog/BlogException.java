package com.web.blog;

public class BlogException extends RuntimeException {
	public BlogException() {
			super("데이타를 처리 중 오류 발생");
		}

	public BlogException(String msg) {
			super(msg);
		}

}
