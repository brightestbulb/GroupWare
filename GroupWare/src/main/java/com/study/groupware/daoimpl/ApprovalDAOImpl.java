package com.study.groupware.daoimpl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.study.groupware.dao.ApprovalDAO;
import com.study.groupware.vo.ApprovalVO;

@Repository
public class ApprovalDAOImpl implements ApprovalDAO {

  @Inject
  private SqlSession session;

  private static String namespace = "com.study.groupware.mapper.ApprovalMapper";

  @Override
  public void create(ApprovalVO vo) throws Exception {
    session.insert(namespace + ".create", vo);
  }

  @Override
  public ApprovalVO read(Map param) throws Exception {
    return session.selectOne(namespace + ".read", param);
  }

  @Override
  public ApprovalVO update(Map param) throws Exception {
    return session.selectOne(namespace + ".update", param);
  }

  @Override
  public void delete(String apv_sq) throws Exception {
    session.delete(namespace + ".delete", apv_sq);
  }

  @Override
  public List<ApprovalVO> listAll(ApprovalVO approval) throws Exception {
    return session.selectList(namespace + ".listAll",approval);
  }
  
  @Override
  public int count(ApprovalVO vo) throws Exception{
	  return session.selectOne(namespace + ".count",vo);
  }
  
  @Override
  public int searchCount(ApprovalVO vo) throws Exception{
	  return session.selectOne(namespace + ".searchCount",vo);
  }
  
  @Override
  public int stfAdmn(String stf_sq) throws Exception {
    return session.selectOne(namespace + ".stfAdmn", stf_sq);
  }
  

}
