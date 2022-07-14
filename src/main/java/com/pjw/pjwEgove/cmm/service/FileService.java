package com.pjw.pjwEgove.cmm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.pjw.pjwEgove.cmm.vo.FileServiceVo;

public interface FileService {

	
	public List<Map<String, Object>> fileUploadService(List<MultipartFile> fileList,Map<String, Object> paramMap  ) throws Exception;

	public List<FileServiceVo> uploadFileList(Map<String, Object> param) throws Exception;


	public void fileDownload(HttpServletResponse response, Map<String, Object> param) throws Exception;
}
