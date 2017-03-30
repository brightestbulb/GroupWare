package com.study.groupware.controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.groupware.service.BoardService;
import com.study.groupware.service.NtcReplyService;
import com.study.groupware.util.Paging;
import com.study.groupware.vo.BoardVO;

@Controller
@RequestMapping("/sboard/*")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject
	private BoardService service;
	
	@Inject
	private NtcReplyService service1;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listAll(HttpServletRequest request,HttpSession session, @RequestParam int ntc_div_sq, Model model) throws Exception {

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
	
		
		  //model 을 이용해서 모든 게시물을 jsp로 전송

		// 페이징 처리 ========================================================================================================
		Paging paging = new Paging();

		// 총 게시물 수 
		int totalCnt = service.count(ntc_div_sq);

		// 현재 페이지 초기화
		int current_page = 1;

		// 만약 사용자로부터 페이지를 받아왔다면
		if (request.getParameter("page") != null) {
			current_page = Integer.parseInt((String)request.getParameter("page"));
		}
		
		String ntc_div = "ntc_div_sq="+ntc_div_sq;

		// jsp에 뿌릴 페이지 태그를 만들어서 보낸다.
		String pageIndexList = paging.pageIndexLista(totalCnt, current_page, ntc_div);

		// SQL 쿼리문에 넣을 조건문
/*		int startCount = (current_page - 1) * 10 + 1;
		int endCount = current_page * 10;
*/
		int endCount = totalCnt - ((current_page - 1) * 10);
        int startCount = totalCnt - (current_page * 10) + 1;
		BoardVO bvo = new BoardVO();
		bvo.setNtc_div_sq(ntc_div_sq); 
		bvo.setStartCount(startCount);
		bvo.setEndCount(endCount);

		
		
		model.addAttribute("list", service.listAll(bvo));

		model.addAttribute("pageIndexList", pageIndexList);
		// ======================================================================================================== 페이징 처리

		
		logger.info("---------------end listAll [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
	
		
		String msg = "";
		if (request.getParameter("msg") != null) {
			msg = (String)request.getParameter("msg");
		}
		model.addAttribute("msg", msg);
		
		
		
		
		return "sboard/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "/boardListSearch", method = { RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> boardListSearch(@RequestBody BoardVO params) throws Exception {

		logger.info("-------------start boardListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		Map<String, Object> result = new HashMap<String, Object>();
		List boardList = new ArrayList<HashMap<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {

			// 페이징 처리 ========================================================================================================
			Paging paging = new Paging();

			int ntc_div_sq = params.getNtc_div_sq();
			// 총 게시물 수 
			int totalCnt = service.searchCount(params);

			// 현재 페이지 초기화
			int current_page = 1;

			// 만약 사용자로부터 페이지를 받아왔다면
			if (params.getPage() != null) {
				current_page = Integer.parseInt(params.getPage());
			}
			/*String ntc_div = "ntc_div_sq="+ntc_div_sq;*/
			// jsp에 뿌릴 페이지 태그를 만들어서 보낸다.
			String pageIndexListAjax = paging.pageIndexListAjax(totalCnt, current_page);

			// SQL 쿼리문에 넣을 조건문
			int startCount = (current_page - 1) * 10 + 1;
			int endCount = current_page * 10;

			params.setNtc_div_sq(ntc_div_sq); 
			params.setStartCount(startCount);
			params.setEndCount(endCount);

			boardList = service.listAll(params);
			map.put("pageIndexListAjax", pageIndexListAjax);
			map.put("boardList", boardList);
			
			result.putAll(map);
	
			// ======================================================================================================== 페이징 처리
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end boardListSearch [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return result;
	}


	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(@RequestParam("ntc_sq") String ntc_sq ,HttpServletRequest request, HttpSession session,Model model)
			throws Exception {

		logger.info("-------------start read [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		session = request.getSession(false);
		String stf_sq = null;
		stf_sq = (String)session.getAttribute("stf_sq");
		model.addAttribute(service.read(ntc_sq));
		model.addAttribute("list", service1.listReply(ntc_sq));            //게시판 댓글 리스트 가져오기
        model.addAttribute("stf_sq", stf_sq);                              //로그인 한 사람 사번 보내서 리플 작성자 사번과 비교
			
		logger.info("---------------end read [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
	}

	@RequestMapping(value = "/removePage", method = RequestMethod.POST)
	public String remove(@RequestParam("ntc_sq") String ntc_sq, RedirectAttributes rttr) throws Exception {

		logger.info("-------------start remove [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		service.remove(ntc_sq);

		rttr.addFlashAttribute("msg", "SUCCESS");
		
		logger.info("---------------end remove [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return "redirect:/sboard/list?ntc_div_sq=1";
		
	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.GET)
	public void modifyPagingGET(String ntc_sq, Model model) throws Exception {

		logger.info("-------------start modifyPagingGET [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		model.addAttribute(service.read(ntc_sq));
		logger.info("---------------end modifyPagingGET [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.POST)
	public String modifyPagingPOST(BoardVO board, RedirectAttributes rttr) throws Exception {

		logger.info("-------------start modifyPagingPOST [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		logger.info(toString());
		
		service.modify(board);

		rttr.addFlashAttribute("msg", "SUCCESS");
		

		logger.info(rttr.toString());
		logger.info("---------------end modifyPagingPOST [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		return "redirect:/sboard/list?ntc_div_sq=1";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registGET() throws Exception {
		logger.info("-------------start registGET [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");

		logger.info("regist get ...........");
		logger.info("---------------end registGET [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(HttpServletRequest request,HttpSession session,BoardVO board, RedirectAttributes rttr) throws Exception {

		logger.info("-------------start registPOST [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		logger.info("regist post ...........");
		logger.info(board.toString());
		session = request.getSession(false);
		String stf_sq = null;
		stf_sq = (String)session.getAttribute("stf_sq");
		board.setStf_sq(stf_sq);
		service.regist(board);

		rttr.addFlashAttribute("msg", "SUCCESS");

		logger.info("---------------end registPOST [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		return "redirect:/sboard/list?ntc_div_sq=1";
		
	}







}
