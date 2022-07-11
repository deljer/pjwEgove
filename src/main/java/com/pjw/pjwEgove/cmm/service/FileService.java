package com.pjw.pjwEgove.cmm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	
	public List<Map<String, Object>> fileUploadService(List<MultipartFile> fileList,Map<String, Object> paramMap  ) throws Exception;

	public List<Map<String, Object>> uploadFileList(Map<String, Object> param);

	public Map<String, Object> fileDownload(HttpServletRequest request, HttpServletResponse response, List<Map<String, Object>> resultList) throws Exception  ;
}
