package com.study.groupware.serviceimpl;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

import com.study.groupware.dao.OfficerDAO;
import com.study.groupware.service.OfficerService;

@Service
public class OfficerServiceImpl implements OfficerService {

	@Inject
	private OfficerDAO dao;

	@Override
	public List officerList(Map params) throws Exception {
		return dao.officerList(params);
	}
	
	@Override
	public int officerListCount(Map params) throws Exception {
		return dao.officerListCount(params);
	}

	@Override
	public List selectOrganization() throws Exception {
		return dao.selectOrganization();
	}
	
	
}
