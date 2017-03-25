package com.study.groupware.daoimpl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.study.groupware.dao.NavDAO;


@Repository
public class NavDAOImpl implements NavDAO {

	@Inject
	private SqlSession session;

	private static String namespace = "com.study.groupware.navMapper";
	
	@Override
	public Map<String, Object> myInfo(String param) throws Exception {
		return session.selectOne(namespace + ".myInfo", param);
	}

	@Override
	public int stfPwUpdate(Map param) throws Exception {
		return session.update(namespace + ".stfPwUpdate", param);
	}
	
	
}
