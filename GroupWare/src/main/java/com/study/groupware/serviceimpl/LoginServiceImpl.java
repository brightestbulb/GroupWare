package com.study.groupware.serviceimpl;

import java.sql.SQLException;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import com.study.groupware.dao.LoginDAO;
import com.study.groupware.service.LoginService;
import com.study.groupware.vo.LoginVO;

@Service
public class LoginServiceImpl implements LoginService{
	@Inject
	private LoginDAO dao;

	// 해당 계정이 있는지 조회하라
	@Override
	public String cnt(LoginVO vo) throws SQLException{
		return dao.cnt(vo);
	}

	// 계정이 존재한다면 그 정보를 불러와라
	@Override
	public LoginVO read(LoginVO vo) throws SQLException{
		return dao.read(vo);
	}



}
