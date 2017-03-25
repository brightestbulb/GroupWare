package com.study.groupware.daoimpl;

import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.study.groupware.dao.LoginDAO;
import com.study.groupware.vo.LoginVO;

@Repository
public class LoginDAOImpl implements LoginDAO {
	
	@Inject
	private SqlSession session;

	private static String namespace = "com.study.groupware.mapper.LoginMapper";


	// 해당 계정이 있는지 조회하라
	@Override
	public String cnt(LoginVO vo) throws SQLException
	{
		return session.selectOne(namespace + ".cnt", vo);
	}

	// 계정이 존재한다면 그 정보를 불러와라
	@Override
	public LoginVO read(LoginVO vo) throws SQLException
	{
		return session.selectOne(namespace + ".read", vo);
	}

}
