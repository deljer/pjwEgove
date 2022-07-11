package com.pjw.pjwEgove.cmm.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
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
	public List<Map<String, Object>> uploadFileList(Map<String, Object> param) {
		return fileServiceMapper.uploadFileList(param);
	}
	
	
	


	@Override
	public Map<String, Object> fileDownload(HttpServletRequest request, HttpServletResponse response,
			List<Map<String, Object>> resultList) throws Exception   {
	
		Map<String, Object> fileItem = resultList.get(0);
		String filePath = (String)fileItem.get("filePath");
		String fileNm = (String)fileItem.get("fileNm");
		String fileOriginNm = (String)fileItem.get("fileOrizinNm");
		 File f = new File(filePath+fileNm);
		 String downloadName = null;
		 
		 
		 String browser = request.getHeader("User-Agent");
		 if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
		      //브라우저 확인 파일명 encode  		             
		      downloadName = URLEncoder.encode(fileOriginNm, "UTF-8").replaceAll("\\+", "%20");		             
	     }else{		             
		      downloadName = new String(fileOriginNm.getBytes("UTF-8"), "ISO-8859-1");
	    }
		 
		 
		 response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadName +"\"");             
		 response.setContentType("application/octer-stream");
		 response.setHeader("Content-Transfer-Encoding", "binary;");

		 try(
				 FileInputStream fis = new FileInputStream(f);
				 ServletOutputStream sos = response.getOutputStream();	
			){

			      byte[] b = new byte[1024];
			      int data = 0;

			      while((data=(fis.read(b, 0, b.length))) != -1){		             
			        sos.write(b, 0, data);		             
			      }

			      sos.flush();
			    } catch(Exception e) {
			      throw e;
			    }

		
		
		
		
		
		
		
		return null;
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





	










}
