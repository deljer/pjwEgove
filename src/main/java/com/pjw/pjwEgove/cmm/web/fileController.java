package com.pjw.pjwEgove.cmm.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.pjw.pjwEgove.cmm.service.CmmService;
import com.pjw.pjwEgove.cmm.service.FileService;
import com.pjw.pjwEgove.cmm.vo.FileServiceVo;


@Controller
public class fileController {

	@Autowired
	private FileService fileService;
	
	@RequestMapping( value = "/fileUpload.do" )
	public String fileUpload( MultipartHttpServletRequest request,Model model) throws Exception {
		
		Map<String, List<MultipartFile>> fileMap = request.getMultiFileMap();
		List<MultipartFile> fileList =  fileMap.get("uploadFile");
		Map<String, Object> paramMap = new HashMap<>();
		Enumeration<String> paramName = request.getParameterNames();
		while(paramName.hasMoreElements()) {
			String paramNm = paramName.nextElement();
			paramMap.put(paramNm, request.getAttribute(paramNm));
		}
		fileService.fileUploadService(fileList, paramMap);
		return "jsonView";
    }
	
	@RequestMapping( value = "/uploadFileList.do" )
	public String uploadFileList( @RequestBody(required= false)  Map<String, Object> param,Model model) throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		resultList = fileService.uploadFileList(param);
		
		model.addAttribute("fileList",resultList);
		return "jsonView";
    }
	
	@RequestMapping( value = "/fileDownload.do" )
	@ResponseBody
	public ResponseEntity<Resource> fileDownload( @RequestBody(required= false)  Map<String, Object> param,HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		

	        Path path1 = Paths.get("D:\\gooddoctor\\2022_7_11" + "/" + "P.I 서비스-검색.html_1657519693763826031"); // 다운로드 할 파일의 최종 경로
	        String contentType = Files.probeContentType(path1); // 타입 받아오기

	        Resource resource = new InputStreamResource(Files.newInputStream(path1)); // path1의 

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + "P.I 서비스-검색.html" +"\"")
	                .header(HttpHeaders.CONTENT_TYPE, contentType)
	                .body(resource);
    }
	
	
	
	
}
