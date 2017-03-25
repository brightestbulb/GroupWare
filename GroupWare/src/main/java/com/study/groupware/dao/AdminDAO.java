package com.study.groupware.dao;

import java.util.List;
import java.util.Map;

import com.study.groupware.vo.OfficerVO;

public interface AdminDAO {
	public List officerList(Map params) throws Exception;
	public int officerListCount(Map params) throws Exception;
	
	public List selectStf_tb() throws Exception;
	public List selectAdmn_Tb() throws Exception;
	public List selectRnk_Tb() throws Exception;
	public List selectDpt_Div_Tb() throws Exception;
	
	public int selectStf_Sq(Map params) throws Exception;
	public void officerInsert(OfficerVO vo) throws Exception;
	
	public Map<String, Object> selectUpdateOfficer(Map params) throws Exception;
	public int officerUpdate(OfficerVO vo) throws Exception;
	
	public int deptInsert(Map params) throws Exception;
	public int selectDeptNm(Map params) throws Exception;
	public int deptUpdate(Map params) throws Exception;
	
	public int deptDelete(Map params) throws Exception;
}
