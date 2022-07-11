package com.pjw.pjwEgove.cmm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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


@Controller
public class CmmController {

	
	@Autowired
	private CmmService cmmService;
	
	@RequestMapping( value = "/kakaoLogin.do", method = { RequestMethod.GET, RequestMethod.POST } )
	public String kakaoLogin( @RequestParam(value = "code", required = false) String code ,  HttpServletRequest request, HttpServletResponse response )   {
		String accesKey = "";
		Map<String, Object> userInfoMap =null;
		ModelAndView model = new ModelAndView();
		
		
		accesKey = cmmService.kakaoLoginService(code);
		userInfoMap = cmmService.kakaoUserInfo(accesKey);
		
		model.addObject("code",code);
		
		return "index";
    }
	
	
	

	@RequestMapping( value = "/naverLogin.do", method = { RequestMethod.GET, RequestMethod.POST } )
	public String naverLogin( @RequestParam(value = "code", required = false) String code ,  HttpServletRequest request, HttpServletResponse response )   {
		String accesKey = "";
		Map<String, Object> userInfoMap =null;
		ModelAndView model = new ModelAndView();
		accesKey = cmmService.naverLoginService(code);
		userInfoMap = cmmService.naverUserInfo(accesKey);
		
		model.addObject("code",code);
		
		return "index";
    }
	
	@RequestMapping( value = "/googleLogin.do", method = { RequestMethod.GET, RequestMethod.POST } )
	public String googleLogin( @RequestParam(value = "code", required = false) String code ,  HttpServletRequest request, HttpServletResponse response )   {
		String accesKey = "";
		Map<String, Object> userInfoMap =null;
		ModelAndView model = new ModelAndView();
		accesKey = cmmService.googleLoginService(code);
		userInfoMap = cmmService.googleUserInfo(accesKey);
		
		model.addObject("code",code);
		
		return "index";
    }

	@RequestMapping( value = "/selectMenuInfo.do" ,method=RequestMethod.POST)
	public String  selectMenuInfo( @RequestBody(required= false)  Map<String, Object> param,Model model)   {
		List<Map<String, Object>>returnList =  new ArrayList<Map<String, Object>>();
		returnList = cmmService.selectMenuInfo(param);
		
		model.addAttribute("resultItem", returnList); 
		return "jsonView";
    }
	
	@RequestMapping( value = "/pageMove.do" )
	public String  pageMove( @RequestParam(value = "url", required = false) String url ,Model model)   {
		return url;
    }
	
	
}
