package com.study.groupware.serviceimpl;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

import com.study.groupware.dao.NavDAO;
import com.study.groupware.service.NavService;

@Service
public class NavServiceImpl implements NavService {

	@Inject
	private NavDAO dao;
	
	@Override
	public Map<String, Object> myInfo(String param) throws Exception {
		return dao.myInfo(param);
	}
	
	@Override
	public int stfPwUpdate(Map param) throws Exception {
		return dao.stfPwUpdate(param);
	}
}
