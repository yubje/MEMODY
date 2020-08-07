package com.web.blog.config.jwt;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    
//    private final 	UserService 		userService;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        
        // 유효한 토큰인지 확인합니다.
        try {
        	if (token != null && jwtTokenProvider.validateToken(token)) {
        		
        		// 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
        		Authentication authentication = jwtTokenProvider.getAuthentication(token);
        		// SecurityContext 에 Authentication 객체를 저장합니다.
        		SecurityContextHolder.getContext().setAuthentication(authentication);
        		
        		//////////////////////////////////////////////////////////////////////////
        		HttpServletResponse res = (HttpServletResponse) response;
        		List<String> roles = jwtTokenProvider.getRole(token);
        		Date now = new Date();
        		// 사용자 요청마다 토큰 유효기간 확인
        		long min = (jwtTokenProvider.getExpirationDate(token).getTime() - now.getTime())/60000;
//        		System.out.println(jwtTokenProvider.getExpirationDate(token)+" "+now+" >> "+min);
        		if(min<5) {
        			String email = jwtTokenProvider.getUserPk(token);
        			String newToken = jwtTokenProvider.createToken(email, roles);
        			res.setHeader("auth", newToken);
//        			System.out.println("갱신된만료기한>>"+jwtTokenProvider.getExpirationDate(newToken));
//        			System.out.println(newToken);
        		}
        	}
			
		} catch (ExpiredJwtException e) {
			
		}
        chain.doFilter(request, response);
    }
}