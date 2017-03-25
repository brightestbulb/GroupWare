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
			// 세션을 사용한다는 의미 즉, 세션 데이터를 부를 모든 컨트롤러는 해당 내용 입력
			session = request.getSession();

			// 만약 계정이 없어서 이 페이지로 다시 돌아왔다면
			if (session.getAttribute("errMsg") != null)
			{
				// alert을 띄우기 위해 해당 데이터를 전달한다.
				model.addAttribute("errMsg", session.getAttribute("errMsg"));
			}
			// session을 초기화 하겠다.
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
			// ID와 PWD를 넣고, 계정이 있다면 데이터가 들어가고
			// 계정이 없다면 null 이나 "" 빈공백이 들어간다.
			String cnt = service.cnt(vo);

			// 세션을 사용한다는 의미 즉, 세션 데이터를 부를 모든 컨트롤러는 해당 내용 입력
			session = request.getSession();

			// 만약 계정이 존재하지 않는다면.
			if (Integer.parseInt(cnt) == 0) 
			{
				session.setAttribute("errMsg", "계정명과 비밀번호를 확인해주세요.");
			}
			// 만약 계정이 존재한다면.
			else if (Integer.parseInt(cnt) == 1)
			{
				LoginVO list = service.read(vo);
				session.setAttribute("stf_sq", list.getStf_sq());

				return "redirect:/sboard/list?ntc_div_sq=1";
			}
			// 해당 계정이 너무 많다면 (DB에 데이터가 잘못 들어간거겠죠? 중복데이터니까)
			else if (Integer.parseInt(cnt) > 1)
			{
				session.setAttribute("errMsg", "계정이 너무 많습니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end login [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return "redirect:/login/loginForm";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletRequest request)
	{
		// 세션 객체를 불러온다. 세션이 없으면 생성하지 않는다.
		session = request.getSession(false);

		// 모든 세션을 삭제한다.
		session.invalidate();


		return "redirect:/login/loginForm";	
	}

}
