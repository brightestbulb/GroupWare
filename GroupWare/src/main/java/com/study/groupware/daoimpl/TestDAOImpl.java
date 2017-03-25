package com.study.groupware.daoimpl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.study.groupware.dao.TestDAO;

@Repository
public class TestDAOImpl implements TestDAO {

	@Inject
	private SqlSession session;

	private static String namespace = "com.study.groupware.TestMapper";

	@Override
	public List test(Map param) throws Exception {
		return session.selectList(namespace + ".list", param);
	}
}
