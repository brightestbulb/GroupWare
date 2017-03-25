package com.study.groupware.daoimpl;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.study.groupware.dao.DataReplyDAO;
import com.study.groupware.vo.DataReplyVO;
import com.study.groupware.vo.NtcReplyVO;

@Repository
public class DataReplyDAOImpl implements DataReplyDAO {

  @Inject
  private SqlSession session;

  private static String namespace = "com.study.groupware.mapper.DataReplyMapper";

  @Override
  public DataReplyVO create(Map param) throws Exception {
   return session.selectOne(namespace + ".create", param);
  }

  @Override
  public void delete(Map param) throws Exception {
    session.delete(namespace + ".delete", param);
  }

  @Override
  public List<DataReplyVO> listAll(int data_sq) throws Exception {
    return session.selectList(namespace + ".listAll",data_sq);
  }
  
  @Override
  public DataReplyVO read(Map param) throws Exception {
    return session.selectOne(namespace + ".read", param);
  }
  

  @Override
  public DataReplyVO update(Map param) throws Exception {
    return session.selectOne(namespace + ".update", param);
  }



}
