package com.study.groupware.serviceimpl;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

import com.study.groupware.dao.AdminDAO;
import com.study.groupware.service.AdminService;
import com.study.groupware.vo.OfficerVO;

@Service
public class AdminServiceImpl implements AdminService {

	@Inject
	private AdminDAO dao;

	@Override
	public List officerList(Map params) throws Exception {
		return dao.officerList(params);
	}
	
	@Override
	public int officerListCount(Map params) throws Exception {
		return dao.officerListCount(params);
	}
	
	@Override
	public List selectStf_tb() throws Exception {
		return dao.selectStf_tb();
	}

	@Override
	public List selectAdmn_Tb() throws Exception {
		return dao.selectAdmn_Tb();
	}

	@Override
	public List selectRnk_Tb() throws Exception {
		return dao.selectRnk_Tb();
	}

	@Override
	public List selectDpt_Div_Tb() throws Exception {
		return dao.selectDpt_Div_Tb();
	}
		
	@Override
	public int selectStf_Sq(Map params) throws Exception {
		return dao.selectStf_Sq(params);
	}

	@Override
	public void officerInsert(OfficerVO vo) throws Exception {
		dao.officerInsert(vo);
	}

	@Override
	public Map<String, Object> selectUpdateOfficer(Map params) throws Exception {
		return dao.selectUpdateOfficer(params);
	}

	@Override
	public int officerUpdate(OfficerVO vo) throws Exception {
		return dao.officerUpdate(vo);
	}

	@Override
	public int deptInsert(Map params) throws Exception {
		return dao.deptInsert(params);
	}
	
	@Override
	public int selectDeptNm(Map params) throws Exception {
		return dao.selectDeptNm(params);
	}

	@Override
	public int deptUpdate(Map params) throws Exception {
		return dao.deptUpdate(params);
	}

	@Override
	public int deptDelete(Map params) throws Exception {
		return dao.deptDelete(params);
	}
}
