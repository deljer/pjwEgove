package com.pjw.pjwEgove.cmm.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.pjw.pjwEgove.cmm.mapper.CmmMapper;
import com.pjw.pjwEgove.cmm.mapper.FileServiceMapper;
import com.pjw.pjwEgove.cmm.service.FileService;
import com.pjw.pjwEgove.cmm.vo.FileServiceVo;
@Service
public class FileServiceImpl implements FileService{

	@Autowired
	FileServiceMapper fileServiceMapper;
	
    @Value("${fileUpload.filePath}")
    private String uploadPath;
	
	@Override
	public List<Map<String, Object>> fileUploadService(List<MultipartFile> fileList, Map<String, Object> paramMap) throws Exception {
		List<FileServiceVo> fileServiceList = new ArrayList<FileServiceVo>();
		
		for(MultipartFile file : fileList) {
			long t = System.currentTimeMillis();
			int  r = (int)(Math.random()*1000000);
			String fileNm = file.getOriginalFilename()+"_"+String.valueOf(t)+String.valueOf(r);
					
			FileServiceVo fileServiceVo = new FileServiceVo();
			fileServiceVo.setFileOrizinNm(file.getOriginalFilename());
			fileServiceVo.setFileNm(fileNm);
			fileServiceVo.setFileSize(file.getSize());
			
			fileServiceList.add(fileServiceVo);
			FileOutputStream fos=null;
			try {
				
				String dateUploadPath = dateMkDir();
				
				fileServiceVo.setFilePath(dateUploadPath);
				fos = new FileOutputStream(dateUploadPath + fileNm);
				fos.write(file.getBytes());
			}catch (Exception e) {
				throw new Exception("FILE_UPLOAD_ERROR");
			}finally {
				fos.close();
			}
		}
		int resultInt = fileServiceMapper.insertFileUpload(fileServiceList);
		System.out.println("insert_ITEM ========="+resultInt);
		return null;
	}
	
	



	@Override
	public List<FileServiceVo> uploadFileList(Map<String, Object> param) {
		return fileServiceMapper.uploadFileList(param);
	}
	
	
	


	@Override
	public void fileDownload(HttpServletResponse response, Map<String, Object> param) throws Exception {
		
		List<FileServiceVo> fileServiceList=  fileServiceMapper.uploadFileList(param);
		
		if(fileServiceList.size()>0) {
			FileServiceVo fileServiceVo  = fileServiceList.get(0);
			
			String filePath =fileServiceVo.getFilePath(); 
			String orizinNm= fileNameIncode(fileServiceVo.getFileOrizinNm());
			String fileNm= fileServiceVo.getFileNm();
			String fileSize = String.valueOf(fileServiceVo.getFileSize());
			
			
			File file  = null;
			byte[] fileByte = null;
			file =new File(filePath,fileNm);
			response.setHeader("Content-Type", "application/octect-stream");
			response.setHeader("Content-Length", fileSize);
			response.setHeader("Content-Disposition", "attachment; filename=" + orizinNm  );
			if(file.isFile()) {
				fileByte = Files.readAllBytes(file.toPath());
				response.getOutputStream().write(fileByte);
		        response.getOutputStream().flush();
		        response.getOutputStream().close();
			}
		}
	}
	
	
	
	
	
	
	
	private String dateMkDir() throws IOException { //날짜별로 디렉터리 만드는 소스
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(cal.YEAR);
		int month = cal.get(cal.MONTH) + 1;
		int date = cal.get(cal.DATE);
		String folderPath = "";
		folderPath  = uploadPath;
		folderPath += Integer.toString(year);
		folderPath += "_";
		folderPath += Integer.toString(month);
		folderPath += "_";
		folderPath += Integer.toString(date);
		folderPath += "\\";
		System.out.println(folderPath);
		File folder = new File(folderPath);
		
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		return folderPath;
	}




	private String fileNameIncode(String fileNm) throws Exception {
		String incodeFileName = "";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String userAgent = request.getHeader("User-Agent");
		incodeFileName="\""+ URLEncoder.encode(fileNm , "UTF-8")+ "\"";
		return incodeFileName;
	}
	










}
