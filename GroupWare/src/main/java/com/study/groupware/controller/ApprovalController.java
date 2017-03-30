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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.groupware.service.ApprovalService;
import com.study.groupware.util.FileUpload;
import com.study.groupware.util.Paging;
import com.study.groupware.vo.ApprovalVO;
import com.study.groupware.vo.BoardVO;
import com.study.groupware.vo.DataVO;

@Controller
@RequestMapping("/approval/*")
public class ApprovalController {

	private static final Logger logger = LoggerFactory.getLogger(ApprovalController.class);

	@Inject
	private ApprovalService service;
	
	@Resource(name = "uploadPath2")
	private String uploadPath2;

	@RequestMapping(value = "/approvalList", method = RequestMethod.GET)
	public String listAll(@RequestParam int div_apv_sq,HttpSession session,HttpServletRequest request, Model model) throws Exception {

		logger.info("-------------start approvalList [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
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

		ApprovalVO count = new ApprovalVO();
		count.setDiv_apv_sq(div_apv_sq);
		count.setStf_sq(stf_sq);
		// 총 게시물 수 
		int totalCnt = service.count(count);

		// 현재 페이지 초기화
		int current_page = 1;

		// 만약 사용자로부터 페이지를 받아왔다면
		if (request.getParameter("page") != null) {
			current_page = Integer.parseInt((String)request.getParameter("page"));
		}

		String div_apv = "div_apv_sq="+div_apv_sq;

		// jsp에 뿌릴 페이지 태그를 만들어서 보낸다.
		String pageIndexList = paging.pageIndexLista(totalCnt, current_page, div_apv);

		// SQL 쿼리문에 넣을 조건문
		int startCount = (current_page - 1) * 10 + 1;
		int endCount = current_page * 10;
		/*	int endCount = totalCnt - ((current_page - 1) * 10);
		        int startCount = totalCnt - (current_page * 10) + 1;*/

		ApprovalVO avo = new ApprovalVO();

		avo.setDiv_apv_sq(div_apv_sq);
		avo.setStartCount(startCount);
		avo.setEndCount(endCount);
		avo.setStf_sq(stf_sq);


		model.addAttribute("approvalList", service.listAll(avo));

		model.addAttribute("pageIndexList", pageIndexList);
		// ======================================================================================================== 페이징 처리



		logger.info("---------------end approvalList [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/apvListSearch", method = { RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> apvListSearch(@RequestBody ApprovalVO params,HttpSession session, HttpServletRequest request) throws Exception {

		logger.info("-------------start approvalListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		Map<String, Object> result = new HashMap<String, Object>();
		List apvList = new ArrayList<HashMap<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		try {

			session = request.getSession(false);

			String stf_sq = null;
			// 세션을 불러와 admn_id에 넣는다. 없다면 null이나 ""이가 들어오겟죠
			stf_sq = (String)session.getAttribute("stf_sq");

			params.setStf_sq(stf_sq);
			// 페이징 처리 ========================================================================================================
			Paging paging = new Paging();

			int div_apv_sq =params.getDiv_apv_sq();
			// 총 게시물 수 
			int totalCnt = service.searchCount(params);

			// 현재 페이지 초기화
			int current_page = 1;

			// 만약 사용자로부터 페이지를 받아왔다면
			if (params.getPage() != null) {
				current_page = Integer.parseInt(params.getPage());
			}
			/*		String apv_div = "div_apv_sq="+div_apv_sq;*/
			// jsp에 뿌릴 페이지 태그를 만들어서 보낸다.
			String pageIndexListAjax = paging.pageIndexListAjax(totalCnt, current_page);

			// SQL 쿼리문에 넣을 조건문
			int startCount = (current_page - 1) * 10 + 1;
			int endCount = current_page * 10;

			params.setDiv_apv_sq(div_apv_sq); 
			params.setStartCount(startCount);
			params.setEndCount(endCount);

			apvList = service.listAll(params);
			map.put("pageIndexListAjax", pageIndexListAjax);
			map.put("apvList", apvList);

			result.putAll(map);

			// ======================================================================================================== 페이징 처리

		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end approvalListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}


	@ResponseBody
	@RequestMapping(value = "/approvalRead", method = {RequestMethod.POST})
	public ApprovalVO approvalRead(@RequestBody Map<String, Object> param,HttpSession session,HttpServletRequest request) throws Exception {
		logger.info("-------------start approvalRead [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		session = request.getSession(false);

		String stf_sq = null;
		// 세션을 불러와 admn_id에 넣는다. 없다면 null이나 ""이가 들어오겟죠
		stf_sq = (String)session.getAttribute("stf_sq");
		ApprovalVO vo = new ApprovalVO();
		int admn_sq = service.stfAdmn(stf_sq);
		vo = service.read(param);
		vo.setStf_admn_sq(admn_sq);

		logger.info("---------------end approvalRead [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return vo;
	}

	@RequestMapping(value = "/removePage", method = RequestMethod.POST)
	public String remove(@RequestParam("apv_sq") String apv_sq, RedirectAttributes rttr) throws Exception {

		logger.info("-------------start removeApproval [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		service.remove(apv_sq);


		rttr.addFlashAttribute("msg", "SUCCESS");
		logger.info("---------------end removeApproval [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return "redirect:/approval/approvalList?div_apv_sq=1";
	}

	@ResponseBody
	@RequestMapping(value = "/approvalOk", method = RequestMethod.POST)
	public ResponseEntity<String> modifyPagingPOST(@RequestBody Map<String, Object> param) throws Exception {
		logger.info("-------------start approvalOk [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		ResponseEntity<String> entity = null;
		ApprovalVO vo = service.modify(param);

		entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		logger.info("---------------end approvalOk [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return entity;
	}


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registGET(Model model) throws Exception {
		logger.info("-------------start registerGET [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		logger.info("regist get ...........");
		logger.info("---------------end registerGET [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(ApprovalVO approval, RedirectAttributes rttr,MultipartFile file,HttpSession session,HttpServletRequest request) throws Exception {
		logger.info("-------------start registPOST [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		logger.info("regist post ...........");
		logger.info("originalName : " + file.getOriginalFilename());	// 파일명.확장자
		logger.info("size : " + file.getSize());						// 파일 용량(byte)
		logger.info("contentType : " + file.getContentType());
		logger.info(approval.toString());
		
		try{
			FileUpload fileupload = new FileUpload();

			System.out.println(approval.toString());

			String savedName = fileupload.uploadfile(file.getOriginalFilename(), file.getBytes(), uploadPath2);

			System.out.println(savedName+"==================================");

			approval.setApv_pl_rt(savedName);
			approval.setApv_pl_nm(file.getOriginalFilename());
		
		session = request.getSession(false);
		String stf_sq = null;
		stf_sq = (String)session.getAttribute("stf_sq");
		approval.setStf_sq(stf_sq);
		service.regist(approval);

		rttr.addFlashAttribute("msg", "SUCCESS");
		}catch(Exception e){
			e.printStackTrace();
		}

		logger.info("---------------end registPOST [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return "redirect:/approval/approvalList?div_apv_sq=1";
	}
	

		
}
