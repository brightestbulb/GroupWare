package com.study.groupware.controller;

import java.net.InetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/error/*")
public class ErrorController {

	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

	@RequestMapping(value = "/404", method = { RequestMethod.GET, RequestMethod.POST})
	public void error404(HttpServletRequest request) throws Exception {

		logger.info("-------------start 404 [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		try {
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end 404 [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
	}
	
	
}
