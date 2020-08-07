package com.web.blog.service;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.blog.repository.UsersRepository;
import com.web.blog.util.FileUpload;
import com.web.blog.util.S3Util;

import lombok.RequiredArgsConstructor;

@Service
public class S3Service {

	@Value("${cloud.aws.s3.bucket}")
	String bucketName;
	@Value("${cloud.aws.credentials.accessKey}")
	String accessKey; // 엑세스 키
	@Value("${cloud.aws.credentials.secretKey}")
	String secretKey;

	public String profileUpload(MultipartFile file) {
		try {
			
			String uploadpath = "profile";
			S3Util s3 = new S3Util(accessKey, secretKey);
//			ResponseEntity<String> img_path = new ResponseEntity<>(
//					FileUpload.uploadFile(uploadpath, file.getOriginalFilename(), file.getBytes()),
//					HttpStatus.CREATED);
			System.out.println("1");
			String img_path = FileUpload.uploadFile(uploadpath, file.getOriginalFilename(), file.getBytes(),bucketName, accessKey, secretKey);
			System.out.println("*****   "+img_path);
//			String img_url = (String) img_path.getBody();
			String img_url = img_path;
			System.out.println(img_url);
			System.out.println(bucketName);
			String url = s3.getFileURL(bucketName, uploadpath+img_url);
			System.out.println("Service: "+url);
			return url;
			
		}catch(RuntimeException e) {
			System.out.println(e);
			return null;
		}catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
