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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.groupware.service.AdminService;
import com.study.groupware.service.NavService;
import com.study.groupware.util.FileUpload;
import com.study.groupware.util.Paging;
import com.study.groupware.vo.OfficerVO;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Inject
	private AdminService service;

	@Inject
	private NavService navService;

	@Resource(name = "uploadPath")
	private String uploadPath;

	// 관리자 페이지와 일반페이지를 나누려고 했으나 나눌 필요는 없는 것 같다.
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST})
	public void index(HttpServletRequest request) throws Exception {

		logger.info("-------------start index [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		Map<String, Object> result = new HashMap<String, Object>();

		try {

		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end index [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

	}	

	// 관리 - 구성원 관리 페이지
	@RequestMapping(value = "/officerList", method = { RequestMethod.GET, RequestMethod.POST})
	public String officerList(HttpServletRequest request,HttpSession session, Model model, RedirectAttributes attr) throws Exception {

		logger.info("-------------start officerList [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
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
		else if(!stf_sq.equals("5")){
			attr.addAttribute("msg", "test");
			return "redirect:/sboard/list?ntc_div_sq=1";
		}
		/*** 로그인 ***/

		// 유저 사번 받아오고

		// 서비스들어가서 데이터받아오고
		Map<String, Object> myInfoList = new HashMap<String, Object>();

		List officerList = new ArrayList<HashMap<String, Object>>();
		int officerListCount = 0;
		Map params = new HashMap<String, Object>();
		List selectStf_tb = new ArrayList<HashMap<String, Object>>();
		List selectAdmn_Tb = new ArrayList<HashMap<String, Object>>();
		List selectRnk_Tb = new ArrayList<HashMap<String, Object>>();
		List selectDpt_Div_Tb = new ArrayList<HashMap<String, Object>>();

		try {

			officerListCount = service.officerListCount(params);
			myInfoList = navService.myInfo(stf_sq);
			selectStf_tb = service.selectStf_tb();
			selectAdmn_Tb = service.selectAdmn_Tb();
			selectRnk_Tb = service.selectRnk_Tb();
			selectDpt_Div_Tb = service.selectDpt_Div_Tb();

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
			String pageIndexList = paging.pageIndexList(totalCnt, current_page);

			// SQL 쿼리문에 넣을 조건문
			int startCount = (current_page - 1) * 10 + 1;
			int endCount = current_page * 10;

			params.put("startCount", startCount);
			params.put("endCount", endCount);

			officerList = service.officerList(params);

			model.addAttribute("pageIndexList", pageIndexList);
			// ======================================================================================================== 페이징 처리

			model.addAttribute("myInfoList", myInfoList);
			model.addAttribute("officerList", officerList);
			model.addAttribute("officerListCount", officerListCount);
			model.addAttribute("selectStf_tb", selectStf_tb);
			model.addAttribute("selectAdmn_Tb", selectAdmn_Tb);
			model.addAttribute("selectRnk_Tb", selectRnk_Tb);
			model.addAttribute("selectDpt_Div_Tb", selectDpt_Div_Tb);


		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end officerList [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return null;
	}

	// 관리 - 구성원 관리 - 검색 - 사원 목록 Ajax
	@ResponseBody
	@RequestMapping(value = "/officerListSearch", method = { RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> officerListSearch(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {

		logger.info("-------------start officerListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

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

		logger.info("---------------end officerListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/selectStf_Sq", method = { RequestMethod.GET, RequestMethod.POST})
	public int selectStf_Sq(HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {

		logger.info("-------------start selectStf_Sq [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		int result = 0;

		try {
			result = service.selectStf_Sq(params);
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end selectStf_Sq [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}

	// 관리 - 구성원 관리 - 구성원 추가 Ajax
	@ResponseBody
	@RequestMapping(value = "/officerInsert", method = { RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<String> officerInsert(MultipartHttpServletRequest request, MultipartFile file, OfficerVO vo, Model model) throws Exception {

		logger.info("-------------start officerInsert [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");	

		ResponseEntity<String> entity = null;

		try {
			logger.info("originalName : " + file.getOriginalFilename());	// 파일명.확장자
			logger.info("size : " + file.getSize());						// 파일 용량(byte)
			logger.info("contentType : " + file.getContentType());			// 파일 종류

			FileUpload fileupload = new FileUpload();

			String savedName = fileupload.uploadfile(file.getOriginalFilename(), file.getBytes(), uploadPath);
			vo.setStf_pt_rt("/resources/img/" + savedName);
			vo.setStf_pt_nm(file.getOriginalFilename());

			service.officerInsert(vo);

			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("FAIL", HttpStatus.OK);
		}

		logger.info("---------------end officerInsert [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return entity;
	}


	// 관리자 - 임직원 수정
	@ResponseBody
	@RequestMapping(value = "/selectUpdateOfficer", method = { RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> selectUpdateOfficer(HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {

		logger.info("-------------start officerUpdateList [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			result = service.selectUpdateOfficer(params);
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end officerUpdateList [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}

	// 관리 - 구성원 관리 - 구성원 추가 Ajax
	@ResponseBody
	@RequestMapping(value = "/officerUpdate", method = { RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<String> officerUpdate(MultipartHttpServletRequest request, MultipartFile file, OfficerVO vo, Model model) throws Exception {

		logger.info("-------------start officerUpdate [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");	

		ResponseEntity<String> entity = null;

		try {
			logger.info("originalName : " + file.getOriginalFilename());	// 파일명.확장자
			logger.info("size : " + file.getSize());						// 파일 용량(byte)
			logger.info("contentType : " + file.getContentType());			// 파일 종류

			FileUpload fileupload = new FileUpload();

			System.out.println(vo.toString());

			String savedName = fileupload.uploadfile(file.getOriginalFilename(), file.getBytes(), uploadPath);
			vo.setStf_pt_rt("/resources/img/" + savedName);
			vo.setStf_pt_nm(file.getOriginalFilename());

			service.officerUpdate(vo);

			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

		} catch (NullPointerException npe) {
			logger.info(npe.toString());

			int result = service.officerUpdate(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);				
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("FAIL", HttpStatus.OK);
		}

		logger.info("---------------end officerUpdate [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return entity;
	}

	@ResponseBody
	@RequestMapping(value = "/deptInsert", method = { RequestMethod.GET, RequestMethod.POST})
	public int deptInsert(HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {

		logger.info("-------------start deptInsert [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		int result = 0;

		try {
			result = service.deptInsert(params);
		} catch(UncategorizedSQLException use) {
			use.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end deptInsert [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}

	// 관리 - 구성원 관리 페이지
	@ResponseBody
	@RequestMapping(value = "/selectDpt_Div_Tb", method = { RequestMethod.GET, RequestMethod.POST})
	public List selectDpt_Div_Tb(HttpServletRequest request) throws Exception {

		logger.info("-------------start selectDpt_Div_Tb [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		List selectDpt_Div_Tb = new ArrayList<HashMap<String, Object>>();

		try {

			selectDpt_Div_Tb = service.selectDpt_Div_Tb();

		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end selectDpt_Div_Tb [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return selectDpt_Div_Tb;
	}

	@ResponseBody
	@RequestMapping(value = "/selectDeptNm", method = { RequestMethod.GET, RequestMethod.POST})
	public int selectDeptNm(HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {

		logger.info("-------------start selectDeptNm [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		int result = 0;

		try {
			result = service.selectDeptNm(params);
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end selectDeptNm [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/deptUpdate", method = { RequestMethod.GET, RequestMethod.POST})
	public int deptUpdate(HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {

		logger.info("-------------start deptUpdate [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		int result = 0;

		try {
			result = service.deptUpdate(params);
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end deptUpdate [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/deptDelete", method = { RequestMethod.GET, RequestMethod.POST})
	public int deptDelete(HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {

		logger.info("-------------start deptDelete [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		int result = 0;

		try {
			result = service.deptDelete(params);
		} catch(DataIntegrityViolationException dive) {
			logger.info(dive.toString());
			result = -1;
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end deptDelete [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}

	/*
	@RequestMapping(value = "/boardList", method = { RequestMethod.GET, RequestMethod.POST})
	public void boardList(HttpServletRequest request, Model model) throws Exception {

		logger.info("-------------start boardList [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		List officerList = new ArrayList<HashMap<String, Object>>();
		Map officerListCount = new HashMap<String, Object>();
		Map params = new HashMap<String, Object>();
		List selectAdmn_Tb = new ArrayList<HashMap<String, Object>>();
		List selectRnk_Tb = new ArrayList<HashMap<String, Object>>();
		List selectDpt_Div_Tb = new ArrayList<HashMap<String, Object>>();

		try {

			officerList = service.officerList(params);
			officerListCount = service.officerListCount(params);
			selectAdmn_Tb = service.selectAdmn_Tb();
			selectRnk_Tb = service.selectRnk_Tb();
			selectDpt_Div_Tb = service.selectDpt_Div_Tb();

			model.addAttribute("officerList", officerList);
			model.addAttribute("officerListCount", officerListCount);
			model.addAttribute("selectAdmn_Tb", selectAdmn_Tb);
			model.addAttribute("selectRnk_Tb", selectRnk_Tb);
			model.addAttribute("selectDpt_Div_Tb", selectDpt_Div_Tb);


		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end boardList [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

	}
	 */

	/*
	@ResponseBody
	@RequestMapping(value = "/console", method = { RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> test(@RequestBody Map<String, Object> param) throws Exception {

		logger.info("-------------start console");

		Map<String, Object> result = new HashMap<String, Object>();

		try {

			List contents = service.test(param);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("contents", contents);

			result.putAll(map);


		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("----------------end console");

		return result;
	}
	 */
}
