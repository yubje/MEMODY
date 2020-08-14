package com.web.blog.config.jwt;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

	private final JwtTokenProvider jwtTokenProvider;
	private final RedisTemplate redisTemplate;

//    private final 	UserService 		userService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 헤더에서 JWT 를 받아옵니다.
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
		
		System.out.println("Token Dofilter");
		// 유효한 토큰인지 확인합니다.
		try {
			HttpServletResponse res = (HttpServletResponse) response;
			HttpServletRequest req = (HttpServletRequest) request;
			if (token != null && jwtTokenProvider.validateToken(token)) {

				// 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
				Authentication authentication = jwtTokenProvider.getAuthentication(token);
				
				// SecurityContext 에 Authentication 객체를 저장합니다.
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				Date now = new Date();
				// 사용자 요청마다 토큰 유효기간 확인
				long min = (jwtTokenProvider.getExpirationDate(token).getTime() - now.getTime()) / 60000;
				if (min < 5) {
					String email = jwtTokenProvider.getUserPk(token);
					List<String> roles = jwtTokenProvider.getRole(token);
					String newToken = jwtTokenProvider.createToken(email, roles);
					
					// 변경된 토큰 저장
					res.setHeader("auth", newToken);
					
					// 변경되었다는 메세지를 Front에게 알려주기 위한 헤더
					res.setIntHeader("expires", 1);
					
				}
			} else if (token != null && !jwtTokenProvider.validateToken(token)) { // 유효하지 않는 토큰일 경우 자동 로그아웃 되도록한다.
				// 자동 로그아웃 할 수 있도록 Front에게 알려주기 위한 헤더
				res.setIntHeader("expires", 2);
			}

		} catch (ExpiredJwtException e) {
			System.out.println("토큰시간 만료");
			System.out.println(e.toString());
		}
		chain.doFilter(request, response);
	}
}