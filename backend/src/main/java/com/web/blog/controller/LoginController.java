package com.web.blog.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.advice.RestException;
import com.web.blog.common.ResponseMessage;
import com.web.blog.common.StatusCode;
import com.web.blog.config.security.JwtTokenProvider;
import com.web.blog.config.security.User;
import com.web.blog.config.security.UserRepository;
import com.web.blog.model.DefaultRes;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import static com.web.blog.model.DefaultRes.FAIL_DEFAULT_RES;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    /**
     * 로그인
     * @param Map<String, String>
     * @return ResponseEntity
     */
    @ApiOperation(value = "로그인", response = ResponseEntity.class)
    @PostMapping("/login")
    public ResponseEntity  login(@RequestBody Map<String, String> user,HttpServletResponse response) {
        	User member = userRepository.findByEmail(user.get("email"))
        	.orElseThrow(() -> new RestException(ResponseMessage.NOT_FOUND_USER,HttpStatus.NOT_FOUND));
//        	.orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        	if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
        		return new ResponseEntity<>(new DefaultRes(StatusCode.FORBIDDEN, ResponseMessage.LOGIN_FAIL), HttpStatus.FORBIDDEN);
        	}
        	response.setHeader("auth", jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
        	Map<String,String> result = new HashMap<String, String>();
        	result.put("uid", member.getUid());
        	result.put("email", member.getUid());
        	return new ResponseEntity<>(new DefaultRes(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS,result), HttpStatus.OK);
    }
    
    /**
     * 로그아웃
     */
    
    
}