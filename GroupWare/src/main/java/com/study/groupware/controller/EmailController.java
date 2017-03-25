package com.study.groupware.controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.groupware.service.EmailService;
import com.study.groupware.util.FileUpload;
import com.study.groupware.util.Paging;
import com.study.groupware.vo.BoardVO;
import com.study.groupware.vo.EmailVO;

@Controller
@RequestMapping("/email/*")
public class EmailController {

	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

	@Inject
	private EmailService service;

	@Resource(name = "uploadPath2")
	private String uploadPath2;

	@RequestMapping(value = "/sndList", method = RequestMethod.GET)
	public String sndListAll(HttpServletRequest request,HttpSession session,Model model) throws Exception {
		logger.info("-------------start sndListAll [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
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
		logger.info(toString());

		// 페이징 처리 ========================================================================================================
		Paging paging = new Paging();
		String stf_snd_sq = stf_sq;

		// 총 게시물 수 
		int totalCnt = service.sndCount(stf_snd_sq);

		// 현재 페이지 초기화
		int current_page = 1;

		// 만약 사용자로부터 페이지를 받아왔다면
		if (request.getParameter("page") != null) {
			current_page = Integer.parseInt((String)request.getParameter("page"));
		}



		// jsp에 뿌릴 페이지 태그를 만들어서 보낸다.
		String pageIndexList = paging.pageIndexList(totalCnt, current_page);

		// SQL 쿼리문에 넣을 조건문
		/*int startCount = (current_page - 1) * 10 + 1;
		int endCount = current_page * 10;*/
		int endCount = totalCnt - ((current_page - 1) * 10);
        int startCount = totalCnt - (current_page * 10) + 1;

		EmailVO evo = new EmailVO();

		evo.setStf_snd_sq(stf_snd_sq);

		evo.setStartCount(startCount);
		evo.setEndCount(endCount);

		model.addAttribute("sndList",service.sndListAll(evo));
		model.addAttribute("pageIndexList", pageIndexList);
		// ======================================================================================================== 페이징 처리
		logger.info("-------------end sndListAll [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return null;
	}


	@RequestMapping(value = "/rcvList", method = RequestMethod.GET)
	public String rcvListAll(HttpServletRequest request,HttpSession session,Model model) throws Exception {

		logger.info("-------------start rcvListAll [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
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
		logger.info(toString());

		// 페이징 처리 ========================================================================================================
		Paging paging = new Paging();
		String stf_rcv_sq = stf_sq;

		// 총 게시물 수 
		int totalCnt = service.rcvCount(stf_rcv_sq);

		// 현재 페이지 초기화
		int current_page = 1;

		// 만약 사용자로부터 페이지를 받아왔다면
		if (request.getParameter("page") != null) {
			current_page = Integer.parseInt((String)request.getParameter("page"));
		}



		// jsp에 뿌릴 페이지 태그를 만들어서 보낸다.
		String pageIndexList = paging.pageIndexList(totalCnt, current_page);

		// SQL 쿼리문에 넣을 조건문
		int startCount = (current_page - 1) * 10 + 1;
		int endCount = current_page * 10;

		EmailVO evo = new EmailVO();

		evo.setStf_rcv_sq(stf_rcv_sq);
		evo.setStartCount(startCount);
		evo.setEndCount(endCount);

		model.addAttribute("rcvList",service.rcvListAll(evo));
		model.addAttribute("pageIndexList", pageIndexList);
		// ======================================================================================================== 페이징 처리
		logger.info("-------------end rcvListAll [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return null;
	}

	@RequestMapping(value = "/keepList", method = RequestMethod.GET)
	public String keepListAll(HttpServletRequest request, HttpSession session,Model model) throws Exception {

		logger.info("-------------start keepListAll [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
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
		logger.info(toString());

		// 페이징 처리 ========================================================================================================
		Paging paging = new Paging();
		String stf_rcv_sq = stf_sq;

		// 총 게시물 수 
		int totalCnt = service.keepCount(stf_rcv_sq);

		// 현재 페이지 초기화
		int current_page = 1;

		// 만약 사용자로부터 페이지를 받아왔다면
		if (request.getParameter("page") != null) {
			current_page = Integer.parseInt((String)request.getParameter("page"));
		}



		// jsp에 뿌릴 페이지 태그를 만들어서 보낸다.
		String pageIndexList = paging.pageIndexList(totalCnt, current_page);

		// SQL 쿼리문에 넣을 조건문
		int startCount = (current_page - 1) * 10 + 1;
		int endCount = current_page * 10;

		EmailVO evo = new EmailVO();

		evo.setStf_rcv_sq(stf_rcv_sq);

		evo.setStartCount(startCount);
		evo.setEndCount(endCount);

		model.addAttribute("keepList",service.keepListAll(evo));
		model.addAttribute("pageIndexList", pageIndexList);
		// ======================================================================================================== 페이징 처리
		logger.info("-------------end keepListAll [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/rcvEmailListSearch", method = { RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> rcvEmailListSearch(@RequestBody EmailVO params, HttpServletRequest request,HttpSession session) throws Exception {

		logger.info("-------------start rcvEmailListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		Map<String, Object> result = new HashMap<String, Object>();
		List rcvList = new ArrayList<HashMap<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 세션이 있는지 확인한다, 만약 없다면 새로 생성하지 않는다.
			session = request.getSession(false);

			

			String stf_sq = null;
			// 세션을 불러와 admn_id에 넣는다. 없다면 null이나 ""이가 들어오겟죠
			stf_sq = (String)session.getAttribute("stf_sq");

			// 페이징 처리 ========================================================================================================
			Paging paging = new Paging();

			String stf_rcv_sq = stf_sq;
			// 총 게시물 수 
			params.setStf_rcv_sq(stf_rcv_sq);
			int totalCnt = service.rcvSearchCount(params);

			// 현재 페이지 초기화
			int current_page = 1;

			// 만약 사용자로부터 페이지를 받아왔다면
			if (params.getPage() != null) {
				current_page = Integer.parseInt(params.getPage());
			}

			// jsp에 뿌릴 페이지 태그를 만들어서 보낸다.
			String pageIndexListAjax = paging.pageIndexListAjax(totalCnt, current_page);

			// SQL 쿼리문에 넣을 조건문
			int startCount = (current_page - 1) * 10 + 1;
			int endCount = current_page * 10;

			params.setStartCount(startCount);
			params.setEndCount(endCount);
			params.setStf_rcv_sq(stf_rcv_sq);

			rcvList = service.rcvListAll(params);
			map.put("pageIndexListAjax", pageIndexListAjax);
			map.put("rcvList", rcvList);

			result.putAll(map);

			// ======================================================================================================== 페이징 처리

		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end rcvEmailListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/sndEmailListSearch", method = { RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> sndEmailListSearch(@RequestBody EmailVO params, HttpServletRequest request,HttpSession session) throws Exception {

		logger.info("-------------start sndEmailListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		Map<String, Object> result = new HashMap<String, Object>();
		List sndList = new ArrayList<HashMap<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		try {

			// 세션이 있는지 확인한다, 만약 없다면 새로 생성하지 않는다.
			session = request.getSession(false);


			String stf_sq = null;
			// 세션을 불러와 admn_id에 넣는다. 없다면 null이나 ""이가 들어오겟죠
			stf_sq = (String)session.getAttribute("stf_sq");
			// 페이징 처리 ========================================================================================================
			Paging paging = new Paging();

			String stf_snd_sq = stf_sq;
			params.setStf_snd_sq(stf_snd_sq);
			// 총 게시물 수 
			int totalCnt = service.sndSearchCount(params);

			// 현재 페이지 초기화
			int current_page = 1;

			// 만약 사용자로부터 페이지를 받아왔다면
			if (params.getPage() != null) {
				current_page = Integer.parseInt(params.getPage());
			}

			// jsp에 뿌릴 페이지 태그를 만들어서 보낸다.
			String pageIndexListAjax = paging.pageIndexListAjax(totalCnt, current_page);

			// SQL 쿼리문에 넣을 조건문
			int startCount = (current_page - 1) * 10 + 1;
			int endCount = current_page * 10;

			params.setStartCount(startCount);
			params.setEndCount(endCount);
			params.setStf_snd_sq(stf_snd_sq);


			sndList = service.sndListAll(params);
			map.put("pageIndexListAjax", pageIndexListAjax);
			map.put("sndList", sndList);

			result.putAll(map);

			// ======================================================================================================== 페이징 처리

		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end sndEmailListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/keepEmailListSearch", method = { RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> keepEmailListSearch(@RequestBody EmailVO params, HttpServletRequest request,HttpSession session) throws Exception {

		logger.info("-------------start keepEmailListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		Map<String, Object> result = new HashMap<String, Object>();
		List keepList = new ArrayList<HashMap<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 세션이 있는지 확인한다, 만약 없다면 새로 생성하지 않는다.
			session = request.getSession(false);


			String stf_sq = null;
			// 세션을 불러와 admn_id에 넣는다. 없다면 null이나 ""이가 들어오겟죠
			stf_sq = (String)session.getAttribute("stf_sq");

			// 페이징 처리 ========================================================================================================
			Paging paging = new Paging();

			String stf_rcv_sq = stf_sq;
			params.setStf_rcv_sq(stf_rcv_sq);
			// 총 게시물 수 
			int totalCnt = service.keepSearchCount(params);

			// 현재 페이지 초기화
			int current_page = 1;

			// 만약 사용자로부터 페이지를 받아왔다면
			if (params.getPage() != null) {
				current_page = Integer.parseInt(params.getPage());
			}

			// jsp에 뿌릴 페이지 태그를 만들어서 보낸다.
			String pageIndexListAjax = paging.pageIndexListAjax(totalCnt, current_page);

			// SQL 쿼리문에 넣을 조건문
			int startCount = (current_page - 1) * 10 + 1;
			int endCount = current_page * 10;

			params.setStartCount(startCount);
			params.setEndCount(endCount);
			params.setStf_rcv_sq(stf_rcv_sq);


			keepList = service.keepListAll(params);
			map.put("pageIndexListAjax", pageIndexListAjax);
			map.put("keepList", keepList);

			result.putAll(map);

			// ======================================================================================================== 페이징 처리

		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end keepEmailListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/emailRead", method = RequestMethod.POST)
	public EmailVO emailRead(@RequestBody Map<String, Object> param)throws Exception {

		logger.info("-------------start emailRead [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		EmailVO vo = service.read(param);

		System.out.println("=================================================================" + vo.getRcv_dt());


		logger.info("---------------end emailRead [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return vo;

	}

	@ResponseBody
	@RequestMapping(value = "/emailRemove", method = RequestMethod.POST)
	public ResponseEntity<String> remove(@RequestBody Map<String, Object> param) throws Exception {

		logger.info("-------------start emailRemove [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		ResponseEntity<String> entity = null;

		service.remove(param);
		entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		logger.info("---------------end emailRemove [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return entity;

	}

	@ResponseBody
	@RequestMapping(value = "/emailKeep", method = RequestMethod.POST)
	public ResponseEntity<String> emailKeep(@RequestBody Map<String, Object> param) throws Exception {

		logger.info("-------------start emailKeep [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		ResponseEntity<String> entity = null;
		service.modify(param);

		entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		logger.info("---------------end emailKeep [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]"); 
		return entity;


	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registGET() throws Exception {

		logger.info("-------------start registGET [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		logger.info("regist get ...........");
		logger.info("---------------end registGET [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
	}
	

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(EmailVO email,HttpServletRequest request,MultipartFile file,HttpSession session, RedirectAttributes rttr) throws Exception {

		logger.info("-------------start registPOST [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		logger.info("regist post ...........");
		logger.info(email.toString());
		logger.info("originalName : " + file.getOriginalFilename());	// 파일명.확장자
		logger.info("size : " + file.getSize());						// 파일 용량(byte)
		logger.info("contentType : " + file.getContentType());

		try{
			FileUpload fileupload = new FileUpload();

			System.out.println(email.toString());

			String savedName = fileupload.uploadfile(file.getOriginalFilename(), file.getBytes(), uploadPath2);

			System.out.println(savedName+"==================================");

			email.setEml_pl_crs("/resources/file/" + savedName);
			email.setEml_pl_nm(file.getOriginalFilename());


			session = request.getSession(false);
			String stf_sq = null;
			stf_sq = (String)session.getAttribute("stf_sq");
			email.setStf_snd_sq(stf_sq);
			service.regist(email);  //eml_tb
			service.regist2(email);  //eml_rcv_tb
			service.regist3(email);  //eml_snd_tb
			rttr.addFlashAttribute("msg", "SUCCESS");
		}catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("---------------end registPOST [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return "redirect:/email/rcvList?stf_rcv_sq=1";
	}


}
