package com.pjw.pjwEgove.cmm.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjw.pjwEgove.cmm.mapper.CmmMapper;
import com.pjw.pjwEgove.cmm.service.CmmService;

@Service
public class CmmServiceImpl implements CmmService {
	
	@Autowired
	CmmMapper cmmMapper;
	
	/*카카오톡 로그인 엑세스 코드 가져오기*/
	@Override
	public String kakaoLoginService(String code) {
		String access_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=4479fee139ca7deff4f33bfdb027dfad"); //본인이 발급받은 key
			sb.append("&redirect_uri=http://localhost:8080/kakaoLogin.do"); // 본인이 설정한 주소
			sb.append("&code=" + code);
			
			bw.write(sb.toString());
			bw.flush();
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			while ((line = br.readLine()) != null) {
				result += line;
			}
			 
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> paseMap  = mapper.readValue(result, Map.class);
			
			access_Token = paseMap.get("access_token").toString();
			
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return access_Token;
		
	}
	/*카카오톡 엑세스 코드를 통한 정보가져오기*/
	@Override
	public Map<String, Object> kakaoUserInfo(String token) {
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = null;
        try {
        	 URL url = new URL(reqURL);
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setRequestMethod("GET");
             //요청에 필요한 Header에 포함될 내용
             conn.setRequestProperty("Authorization", "Bearer " + token);
             int responseCode = conn.getResponseCode();
             System.out.println("responseCode : " + responseCode);
             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
             String br_line = "";
             String result = "";
             while ((br_line = br.readLine()) != null) {
                 result += br_line;
             }
             
            System.out.println("response:" + result);
            resultMap  = mapper.readValue(result, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
	return resultMap;
	}
	
	
	@Override
	public String naverLoginService(String code) {
		String access_Token = "";
		String reqURL = "https://nid.naver.com/oauth2.0/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=ZUl3fYwPnuN6gOYTX0ny"); //본인이 발급받은 key
			sb.append("&client_secret=pdFTCYlhTD"); //본인이 발급받은 key
			sb.append("&redirect_uri=http://localhost:8080/naverLogin.do"); // 본인이 설정한 주소
			sb.append("&code=" + code);
			
			bw.write(sb.toString());
			bw.flush();
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			while ((line = br.readLine()) != null) {
				result += line;
			}
			 
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> paseMap  = mapper.readValue(result, Map.class);
			
			access_Token = paseMap.get("access_token").toString();
			
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return access_Token;
	}
	@Override
	public Map<String, Object> naverUserInfo(String accessCode) {
		String reqURL = "https://openapi.naver.com/v1/nid/me";
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = null;
        try {
        	 URL url = new URL(reqURL);
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setRequestMethod("GET");
             conn.setRequestProperty("Authorization", "Bearer " + accessCode);
             int responseCode = conn.getResponseCode();
             System.out.println("responseCode : " + responseCode);
             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
             String br_line = "";
             String result = "";
             while ((br_line = br.readLine()) != null) {
                 result += br_line;
             }
             
            System.out.println("response:" + result);
            resultMap  = mapper.readValue(result, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
	return resultMap;
	}
	
	@Override
	public String googleLoginService(String code) {
		String access_Token = "";
		String reqURL = "https://oauth2.googleapis.com/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=585961978422-c9i56qcvak750omhhidl0e06otv8fl62.apps.googleusercontent.com"); 
			sb.append("&client_secret=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"); //본인이 발급받은 key
			sb.append("&redirect_uri=http://localhost:8080/googleLogin.do"); // 본인이 설정한 주소
			sb.append("&code=" + code);
			
			bw.write(sb.toString());
			bw.flush();
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			while ((line = br.readLine()) != null) {
				result += line;
			}
			 
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> paseMap  = mapper.readValue(result, Map.class);
			System.out.println(paseMap.toString());
			access_Token = paseMap.get("access_token").toString();
			
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return access_Token;
	}
	@Override
	public Map<String, Object> googleUserInfo(String accessCode) {
		String reqURL = "https://www.googleapis.com/oauth2/v1/userinfo";
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = null;
        try {
        	 URL url = new URL(reqURL);
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setRequestMethod("GET");
             conn.setRequestProperty("Authorization", "Bearer " + accessCode);
             int responseCode = conn.getResponseCode();
             System.out.println("responseCode : " + responseCode);
             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
             String br_line = "";
             String result = "";
             while ((br_line = br.readLine()) != null) {
                 result += br_line;
             }
             
            System.out.println("response:" + result);
            resultMap  = mapper.readValue(result, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
	return resultMap;
	}
	
	
	@Override
	public List<Map<String, Object>> selectMenuInfo(Map<String, Object> param) {
		return cmmMapper.selectMenuInfo(param);
	}
	
	

}
