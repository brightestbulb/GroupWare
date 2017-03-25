package com.study.groupware.daoimpl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.study.groupware.dao.AdminDAO;
import com.study.groupware.vo.OfficerVO;

@Repository
public class AdminDAOImpl implements AdminDAO {

	@Inject
	private SqlSession session;

	private static String namespace = "com.study.groupware.adminMapper";

	@Override
	public List officerList(Map param) throws Exception {
		return session.selectList(namespace + ".officerList", param);
	}
	
	@Override
	public int officerListCount(Map param) throws Exception {
		return session.selectOne(namespace + ".officerListCount", param);
	}

	@Override
	public List selectStf_tb() throws Exception {
		return session.selectList(namespace + ".selectStf_tb");
	}

	@Override
	public List selectAdmn_Tb() throws Exception {
		return session.selectList(namespace + ".selectAdmn_Tb");
	}

	@Override
	public List selectRnk_Tb() throws Exception {
		return session.selectList(namespace + ".selectRnk_Tb");
	}

	@Override
	public List selectDpt_Div_Tb() throws Exception {
		return session.selectList(namespace + ".selectDpt_Div_Tb");
	}
	
	@Override
	public int selectStf_Sq(Map params) throws Exception {
		return session.selectOne(namespace + ".selectStf_Sq", params);
	}

	@Override
	public void officerInsert(OfficerVO vo) throws Exception {
		session.insert(namespace + ".officerInsert", vo);
	}

	@Override
	public Map<String, Object> selectUpdateOfficer(Map params) throws Exception {
		return session.selectOne(namespace + ".selectUpdateOfficer", params);
	}
	
	@Override
	public int officerUpdate(OfficerVO vo) throws Exception {
		return session.update(namespace + ".officerUpdate", vo);
	}

	@Override
	public int deptInsert(Map params) throws Exception {
		return session.insert(namespace + ".deptInsert", params);
	}
	
	@Override
	public int selectDeptNm(Map params) throws Exception {
		return session.selectOne(namespace + ".selectDeptNm", params);
	}

	@Override
	public int deptUpdate(Map params) throws Exception {
		return session.update(namespace + ".deptUpdate", params);
	}

	@Override
	public int deptDelete(Map params) throws Exception {
		return session.delete(namespace + ".deptDelete", params);
	}
}
