package com.web.blog.config.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import com.web.blog.config.jwt.JwtAuthenticationFilter;
import com.web.blog.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider jwtTokenProvider;
	private final RedisTemplate redisTemplate;
	private final UserDetailsService jwtUserDetailsService;

	// 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	

	// authenticationManager를 Bean 등록합니다.
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();

        filter.setEncoding("UTF-8");

        filter.setForceEncoding(true);

//        http.addFilterBefore(filter,CsrfFilter.class);
		http.httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
				.csrf().disable() // csrf 보안 토큰 disable처리.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지
																							// 않습니다.
				.and().authorizeRequests() // 요청에 대한 사용권한 체크
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, ".users").hasRole("USER")
				.antMatchers("/logout").hasRole("USER")
				.anyRequest().permitAll() // 그외 나머지 요청은 누구나 접근 가능
				.and()
				.addFilterBefore(filter,CsrfFilter.class)
				.addFilterBefore(new CorsFilter(),SecurityContextPersistenceFilter.class)
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate),
						UsernamePasswordAuthenticationFilter.class);
		// JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	/**
	 * 
	 * CORS (Cross-Origin Resource Sharing) 해결
	 * @apiNote FrontEnd와의 통신에서 Header에 토큰 값을 노출시켜주기 위한 설정
	 * 
	 */
//	@Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.addAllowedOrigin("http://localhost:8081");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        configuration.setAllowCredentials(true);
//        
//        List<String> list = new ArrayList<String>();
//        list.add("auth");
//        configuration.setExposedHeaders(list);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}