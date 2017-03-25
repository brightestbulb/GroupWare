package com.study.groupware.daoimpl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.study.groupware.dao.OfficerDAO;

@Repository
public class OfficerDAOImpl implements OfficerDAO {

	@Inject
	private SqlSession session;

	private static String namespace = "com.study.groupware.officerMapper";

	@Override
	public List officerList(Map param) throws Exception {
		return session.selectList(namespace + ".officerList", param);
	}
	
	@Override
	public int officerListCount(Map param) throws Exception {
		return session.selectOne(namespace + ".officerListCount", param);
	}

	@Override
	public List selectOrganization() throws Exception {
		return session.selectList(namespace + ".selectOrganization");
	}
	
	
}
