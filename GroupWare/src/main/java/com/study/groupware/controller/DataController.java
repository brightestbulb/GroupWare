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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.groupware.service.DataReplyService;
import com.study.groupware.service.DataService;
import com.study.groupware.util.FileUpload;
import com.study.groupware.util.Paging;
import com.study.groupware.vo.DataVO;

@Controller
@RequestMapping("/data/*")
public class DataController {

	private static final Logger logger = LoggerFactory.getLogger(DataController.class);

	@Inject
	private DataService service;

	@Inject
	private DataReplyService service1;

	@Resource(name = "uploadPath2")
	private String uploadPath2;

	@RequestMapping(value = "/dataList", method = RequestMethod.GET)
	public String listAll(HttpServletRequest request,HttpSession session,Model model) throws Exception {

		logger.info("-------------start listAll [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
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

		// 총 게시물 수 
		int totalCnt = service.count();

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


		DataVO dvo = new DataVO();
		dvo.setStartCount(startCount);
		dvo.setEndCount(endCount);

		System.out.println("==========================================="+startCount);
		System.out.println("==========================================="+endCount);

		model.addAttribute("list", service.listAll(dvo));

		model.addAttribute("pageIndexList", pageIndexList);

		// ======================================================================================================== 페이징 처리


		logger.info("---------------end listAll [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/dataListSearch", method = { RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> dataListSearch(@RequestBody DataVO params, HttpServletRequest request) throws Exception {

		logger.info("-------------start dataListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		Map<String, Object> result = new HashMap<String, Object>();
		List dataList = new ArrayList<HashMap<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		try {

			// 페이징 처리 ========================================================================================================
			Paging paging = new Paging();
			// 총 게시물 수 
			int cate=params.getCate();
			System.out.println(params.toString());
			int totalCnt = service.searchCount(params);
			// 현재 페이지 초기화
			int current_page = 1;

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



			dataList = service.listAll(params);
			map.put("pageIndexListAjax", pageIndexListAjax);
			map.put("dataList", dataList);

			result.putAll(map);

			// ======================================================================================================== 페이징 처리

		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end dataListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}


	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(@RequestParam("data_sq") int data_sq , Model model)
			throws Exception {
		logger.info("-------------start read [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		model.addAttribute(service.read(data_sq));
		model.addAttribute("list", service1.listReply(data_sq));
		logger.info("---------------end read [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
	}

	@RequestMapping(value = "/removePage", method = RequestMethod.POST)
	public String remove(@RequestParam("data_sq") int data_sq, RedirectAttributes rttr) throws Exception {

		logger.info("-------------start remove [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		service.remove(data_sq);


		rttr.addFlashAttribute("msg", "SUCCESS");
		logger.info("---------------end remove [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return "redirect:/data/dataList";
	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.GET)
	public void modifyPagingGET(int data_sq, Model model) throws Exception {
		logger.info("-------------start modifyPagingGET [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		System.out.println(data_sq);
		model.addAttribute(service.read(data_sq));
		logger.info("---------------end modifyPagingGET [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.POST)
	public String modifyPagingPOST(DataVO data, RedirectAttributes rttr) throws Exception {
		logger.info("-------------start modifyPagingPOST [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		logger.info(toString());
		service.modify(data);

		rttr.addFlashAttribute("msg", "SUCCESS");

		logger.info(rttr.toString());
		logger.info("---------------end modifyPagingPOST [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return "redirect:/data/dataList";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registGET() throws Exception {
		logger.info("-------------start registGET [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		logger.info("regist get ...........");
		logger.info("---------------end registGET [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(DataVO data,HttpServletRequest request,MultipartFile file,HttpSession session,RedirectAttributes rttr) throws Exception {
		logger.info("-------------start registPOST [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		logger.info("regist post ...........");
		logger.info(data.toString());

		logger.info("originalName : " + file.getOriginalFilename());	// 파일명.확장자
		logger.info("size : " + file.getSize());						// 파일 용량(byte)
		logger.info("contentType : " + file.getContentType());

		try{
			FileUpload fileupload = new FileUpload();

			System.out.println(data.toString());

			String savedName = fileupload.uploadfile(file.getOriginalFilename(), file.getBytes(), uploadPath2);

			System.out.println(savedName+"==================================");

			data.setData_crs("/resources/file/" + savedName);
			data.setData_pl_nm(file.getOriginalFilename());

			// 세션이 있는지 확인한다, 만약 없다면 새로 생성하지 않는다.
			session = request.getSession(false);
			String stf_sq = null;
			// 세션을 불러와 admn_id에 넣는다. 없다면 null이나 ""이가 들어오겟죠
			stf_sq = (String)session.getAttribute("stf_sq");

			data.setStf_sq(stf_sq);
			service.regist(data);

			rttr.addFlashAttribute("msg", "SUCCESS");
		}catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("---------------end registPOST [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return "redirect:/data/dataList";
	}







}
