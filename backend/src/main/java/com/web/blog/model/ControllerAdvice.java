package com.web.blog.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RestException.class)
	public ResponseEntity<Map<String, Object>> handler(RestException e) {
		Map<String, Object> resBody = new HashMap<>();
		resBody.put("status", e.getStatus());
		resBody.put("message", e.getMessage());
		
		return new ResponseEntity<>(resBody, e.getStatus());
	}
	
}