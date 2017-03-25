package com.study.groupware.dao;

import java.util.List;
import java.util.Map;

import com.study.groupware.vo.OfficerVO;

public interface OfficerDAO {
	public List officerList(Map params) throws Exception;
	public int officerListCount(Map params) throws Exception;
	
	public List selectOrganization() throws Exception;
}
