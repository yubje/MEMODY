package com.web.blog.config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
		basePackages = "com.web.blog.dao.user"
)
public class DatabaseConfig {}