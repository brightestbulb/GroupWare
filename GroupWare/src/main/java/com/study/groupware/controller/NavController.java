package com.study.groupware.controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.groupware.service.NavService;

@Controller
@RequestMapping("/nav/*")
public class NavController {

	private static final Logger logger = LoggerFactory.getLogger(NavController.class);
		
	@Inject
	private NavService service;
	
	@ResponseBody
	@RequestMapping(value = "/stfPwUpdate", method = { RequestMethod.GET, RequestMethod.POST})
	public int stfPwUpdate(HttpServletRequest request, @RequestBody Map<String, Object> params, Model model) throws Exception {

		logger.info("-------------start stfPwUpdate [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		// 세션으로 대체
		String stf_sq = "S000000002";
		params.put("stf_sq", stf_sq);
						
		int result = 0;
		
		try {
			result = service.stfPwUpdate(params);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		logger.info("---------------end stfPwUpdate [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		return result;
	}	
}
