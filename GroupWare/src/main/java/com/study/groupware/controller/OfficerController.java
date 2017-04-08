package com.study.groupware.controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.groupware.service.AdminService;
import com.study.groupware.service.OfficerService;
import com.study.groupware.util.Paging;

@Controller
@RequestMapping("/officer/*")
public class OfficerController {

	private static final Logger logger = LoggerFactory.getLogger(OfficerController.class);

	@Inject
	private OfficerService service;

	@Inject
	private AdminService adminService;

	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST})
	public void index() throws Exception {

		logger.info("-------------start index [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		Map<String, Object> result = new HashMap<String, Object>();

		try {


		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end index [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
	}


	@RequestMapping(value = "/organization", method = { RequestMethod.GET, RequestMethod.POST})
	public String organization(HttpServletRequest request,HttpSession session, @RequestParam Map<String, Object> params, Model model) throws Exception {

		logger.info("-------------start organization [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		/*** 로그인 ***/
		// 세션이 있는지 확인한다, 만약 없다면 새로 생성하지 않는다.
		session = request.getSession(false);


		String stf_sq = null;
		// 세션을 불러와 admn_id에 넣는다. 없다면 null이나 ""이가 들어오겟죠
		stf_sq = (String)session.getAttribute("stf_sq");

		// 만약 admn_id가 null 또는 "" 이라면 (로그인을 하지 않은 상태라면) 
		// 로그인 페이지로 강제로 보내라
		if (stf_sq == null || stf_sq.equals(""))
			return "redirect:/login/loginForm";
		/*** 로그인 ***/

		List officerList = new ArrayList<HashMap<String, Object>>();
		int officerListCount = 0;
		List selectDpt_Div_Tb = new ArrayList<HashMap<String, Object>>();

		List selectOrganization = new ArrayList<HashMap<String, Object>>();

		try {

			officerListCount = service.officerListCount(params);
			selectDpt_Div_Tb = adminService.selectDpt_Div_Tb();            //조직도 부서명 리스트 뽑기
			selectOrganization = service.selectOrganization();             // 부서,직금,이름 뽑아오기

			// 페이징 처리 ========================================================================================================
			Paging paging = new Paging();

			// 총 게시물 수 
			int totalCnt = officerListCount;

			// 현재 페이지 초기화
			int current_page = 1;

			// 만약 사용자로부터 페이지를 받아왔다면
			if (request.getParameter("page") != null) {
				current_page = Integer.parseInt((String)request.getParameter("page"));
			}

			// jsp에 뿌릴 페이지 태그를 만들어서 보낸다.
			String pageIndexListAjax = paging.pageIndexListAjax(totalCnt, current_page);

			// SQL 쿼리문에 넣을 조건문
			int startCount = (current_page - 1) * 10 + 1;
			int endCount = current_page * 10;

			params.put("startCount", startCount);
			params.put("endCount", endCount);

			officerList = service.officerList(params);

			model.addAttribute("pageIndexList", pageIndexListAjax);
			// ======================================================================================================== 페이징 처리

			model.addAttribute("navStfNm", params.get("navStfNm"));
			model.addAttribute("officerList", officerList);
			model.addAttribute("officerListCount", officerListCount);
			model.addAttribute("selectDpt_Div_Tb", selectDpt_Div_Tb);                //조직도 부서명 리스트 뽑기
			model.addAttribute("selectOrganization", selectOrganization);            // 부서,직금,이름 뽑아오기

		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end organization [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return null;
	}


	// 관리 - 구성원 관리 - 검색 - 사원 목록 Ajax
	@ResponseBody
	@RequestMapping(value = "/organizationAjax", method = { RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> organizationAjax(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {

		logger.info("-------------start organizationAjax [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		Map<String, Object> result = new HashMap<String, Object>();
		List officerList = new ArrayList<HashMap<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		int officerListCount = 0;


		try {

			officerListCount = service.officerListCount(params);

			// 페이징 처리 ========================================================================================================
			Paging paging = new Paging();

			// 총 게시물 수 
			int totalCnt = officerListCount;

			// 현재 페이지 초기화
			int current_page = 1;
			int before_page = 1;

			// 만약 사용자로부터 페이지를 받아왔다면
			if (params.get("page") != null) {
				current_page = Integer.parseInt((String)params.get("page"));
			}

			// jsp에 뿌릴 페이지 태그를 만들어서 보낸다.
			String pageIndexListAjax = paging.pageIndexListAjax(totalCnt, current_page);

			// SQL 쿼리문에 넣을 조건문


			int startCount = (current_page - 1) * 10 + 1;
			int endCount = current_page * 10;

			params.put("startCount", startCount);
			params.put("endCount", endCount);

			officerList = service.officerList(params);

			map.put("pageIndexListAjax", pageIndexListAjax);
			// ======================================================================================================== 페이징 처리



			map.put("officerList", officerList);
			map.put("officerListCount", officerListCount);

			result.putAll(map);


		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end organizationAjax [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}
}
