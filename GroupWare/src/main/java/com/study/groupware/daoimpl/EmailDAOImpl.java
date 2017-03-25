package com.study.groupware.daoimpl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.study.groupware.dao.EmailDAO;
import com.study.groupware.vo.EmailVO;

@Repository
public class EmailDAOImpl implements EmailDAO {

  @Inject
  private SqlSession session;

  private static String namespace = "com.study.groupware.mapper.EmailMapper";

  @Override
  public void create(EmailVO vo) throws Exception {
    session.insert(namespace + ".create", vo);
  }
  
  @Override
  public void create2(EmailVO vo) throws Exception {
    session.insert(namespace + ".create2", vo);
  }
  
  @Override
  public void create3(EmailVO vo) throws Exception {
    session.insert(namespace + ".create3", vo);
  }

  @Override
  public EmailVO read(Map param) throws Exception {
    return session.selectOne(namespace + ".read", param);
  }
 
  @Override
  public void update(Map param) throws Exception {
     session.selectOne(namespace + ".update", param);

  }

  @Override
  public void delete(Map param) throws Exception {
    session.delete(namespace + ".delete", param);
  }

  @Override
  public List<EmailVO> sndListAll(EmailVO vo) throws Exception {
    return session.selectList(namespace + ".sndListAll",vo);
  }
  
  @Override
  public List<EmailVO> rcvListAll(EmailVO vo) throws Exception {
    return session.selectList(namespace + ".rcvListAll",vo);
  }

  @Override
  public List<EmailVO> keepListAll(EmailVO vo) throws Exception {
    return session.selectList(namespace + ".keepListAll",vo);
  }
  @Override
  public int sndCount(String stf_snd_sq) throws Exception {
    return session.selectOne(namespace + ".sndCount", stf_snd_sq);
  }
  @Override
  public int rcvCount(String stf_rcv_sq) throws Exception {
    return session.selectOne(namespace + ".rcvCount", stf_rcv_sq);
  }
  @Override
  public int keepCount(String stf_rcv_sq) throws Exception {
    return session.selectOne(namespace + ".keepCount", stf_rcv_sq);
  }
  @Override
  public int rcvSearchCount(EmailVO vo) throws Exception {
    return session.selectOne(namespace + ".rcvSearchCount", vo);
  }

  @Override
  public int sndSearchCount(EmailVO vo) throws Exception {
    return session.selectOne(namespace + ".sndSearchCount", vo);
  }
  
  @Override
  public int keepSearchCount(EmailVO vo) throws Exception {
    return session.selectOne(namespace + ".keepSearchCount", vo);
  }
}
