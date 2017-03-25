package com.study.groupware.controller;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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

import com.study.groupware.service.ScheduleService;
import com.study.groupware.vo.ScheduleVO;

@Controller
@RequestMapping("/schedule/*")
public class ScheduleController {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

	@Inject
	private ScheduleService service;

	@RequestMapping(value = "/scheduleList", method = { RequestMethod.GET, RequestMethod.POST})
	public void Schedule(@RequestParam String scd_sq, Model model) throws Exception {
		logger.info("-------------start scheduleList [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		// 로그인 한 사람의 권한번호
		String admn_sq = "4";
		
		try {
			model.addAttribute("scd_sq", scd_sq);
			model.addAttribute("login_admn_sq", admn_sq);
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end scheduleList [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
	}

	@ResponseBody
	@RequestMapping(value = "/selectSchedule", method = { RequestMethod.GET, RequestMethod.POST})
	public List<ScheduleVO> selectSchedule(@RequestBody Map<String, Object> params, Model model) throws Exception {
		logger.info("-------------start selectSchedule [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		List<ScheduleVO> result = null;
		
		// 로그인했을 때 부서번호, 사원번호
		params.put("dpt_sq", "2");
		params.put("stf_sq", "2");
		
		try {
			
			// 회사일정에 등록된 모든 스케쥴을 DB에서 조회한다.
			result = service.selectSchedule(params);
			
			// 날짜 뒤에 .0 없애기
			for (int i=0; i<result.size(); i++) {
				String tempStr = result.get(i).getBs_scd_str_dt();
				String tempEnd = result.get(i).getBs_scd_end_dt();
				tempStr = tempStr.substring(0, tempStr.length()-2);
				tempEnd = tempEnd.substring(0, tempEnd.length()-2);
				result.get(i).setBs_scd_str_dt(tempStr);
				result.get(i).setBs_scd_end_dt(tempEnd);
			}

		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end selectSchedule [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		return result;
	}

	@RequestMapping(value = "/insertSchedule", method = { RequestMethod.GET, RequestMethod.POST})
	public String insertSchedule(ScheduleVO vo) throws Exception {
		logger.info("-------------start insertSchedule [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		// 로그인했을 때 사번
		String stf_sq = "2";
		
		vo.setStf_sq(stf_sq);
		vo.setBs_scd_cnt(vo.getBs_scd_cnt().replaceAll("\n", "<br>"));
		
		System.out.println(vo.toString());
		
		try {
			service.insertSchedule(vo);
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end insertSchedule [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		return "redirect:/schedule/scheduleList?scd_sq=" + vo.getScd_sq();
	}
	
	@ResponseBody
	@RequestMapping(value = "/readSchedule", method = { RequestMethod.GET, RequestMethod.POST})
	public ScheduleVO readSchedule(@RequestBody Map<String, Object> params) throws Exception {
		logger.info("-------------start readSchedule [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		ScheduleVO result = null;
	
		try {
			result = service.readSchedule(params);
			
			System.out.println(result.getBs_scd_cnt());
			result.setBs_scd_cnt(result.getBs_scd_cnt().replaceAll("<br>", "\n"));
			System.out.println(result.getBs_scd_cnt());
			
			// 날짜 뒤에 .0 없애기		
			String tempStr = result.getBs_scd_str_dt();
			String tempEnd = result.getBs_scd_end_dt();
			tempStr = tempStr.substring(0, tempStr.length()-2);
			tempEnd = tempEnd.substring(0, tempEnd.length()-2);
			result.setBs_scd_str_dt(tempStr);
			result.setBs_scd_end_dt(tempEnd);
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end readSchedule [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateSchedule", method = { RequestMethod.GET, RequestMethod.POST})
	public int updateSchedule(@RequestBody Map<String, String> params) throws Exception {
		logger.info("-------------start updateSchedule [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		int result = 0;
	
		System.out.println(params);
		
		System.out.println(params.get("bs_scd_cnt"));
		params.put("bs_scd_cnt", params.get("bs_scd_cnt").replaceAll("\n", "<br>"));
		System.out.println(params.get("bs_scd_cnt"));
		
		try {
			result = service.updateSchedule(params);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end updateSchedule [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteSchedule", method = { RequestMethod.GET, RequestMethod.POST})
	public int deleteSchedule(@RequestBody Map<String, Object> params) throws Exception {
		logger.info("-------------start deleteSchedule [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		int result = 0;
	
		try {
			result = service.deleteSchedule(params);
			System.out.println("==============================================================================result : " + result);
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("---------------end deleteSchedule [Connect IP : " + InetAddress.getLocalHost().getHostAddress() + "]");
		
		return result;
	}
}
