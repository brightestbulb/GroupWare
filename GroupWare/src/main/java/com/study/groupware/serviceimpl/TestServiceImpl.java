package com.study.groupware.serviceimpl;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import com.study.groupware.dao.TestDAO;
import com.study.groupware.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Inject
	private TestDAO dao;

	@Override
	public List test(Map param) throws Exception {
		return dao.test(param);
	}
}
