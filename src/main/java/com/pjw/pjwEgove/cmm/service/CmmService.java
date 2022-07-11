package com.pjw.pjwEgove.cmm.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface CmmService {

	
	public String kakaoLoginService(String code );
	public Map<String, Object> kakaoUserInfo(String accessCode);

	public String naverLoginService(String code );
	public Map<String, Object> naverUserInfo(String accessCode);
	
	public String googleLoginService(String code );
	public Map<String, Object> googleUserInfo(String accessCode);
	
	public List<Map<String, Object>> selectMenuInfo(Map<String, Object> param);
	
	
	
	
}
