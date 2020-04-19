package com.study.groupware.controller;

import java.net.InetAddress;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.study.groupware.service.LoginService;
import com.study.groupware.vo.LoginVO;


@RequestMapping("/login/*")
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Inject
	private LoginService service;

	@RequestMapping(value = "/loginForm", method = { RequestMethod.GET, RequestMethod.POST})
	public String loginForm(HttpSession session, HttpServletRequest request, Model model) throws Exception {

		logger.info("-------------start loginForm [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		try {
			session = request.getSession();
			if (session.getAttribute("errMsg") != null) {
				model.addAttribute("errMsg", session.getAttribute("errMsg"));
			}
			session.invalidate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("---------------end loginForm [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return "login/loginForm";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, HttpServletRequest request, LoginVO vo) throws Exception
	{
		logger.info("---------------start login [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		try{
			String cnt = service.cnt(vo);
			session = request.getSession();
			if (Integer.parseInt(cnt) == 0) {
				session.setAttribute("errMsg", "계정명과 비밀번호를 확인해주세요.");
			} else if (Integer.parseInt(cnt) == 1) {
				LoginVO list = service.read(vo);
				session.setAttribute("stf_sq", list.getStf_sq());

				return "redirect:/sboard/list?ntc_div_sq=1";
			} else if (Integer.parseInt(cnt) > 1) {
				session.setAttribute("errMsg", "계정이 너무 많습니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("---------------end login [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return "redirect:/login/loginForm";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletRequest request) {
		session = request.getSession(false);
		session.invalidate();
		return "redirect:/login/loginForm";	
	}

}
