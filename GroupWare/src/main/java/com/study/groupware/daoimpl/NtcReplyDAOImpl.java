package com.study.groupware.daoimpl;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.study.groupware.dao.NtcReplyDAO;
import com.study.groupware.vo.NtcReplyVO;

@Repository
public class NtcReplyDAOImpl implements NtcReplyDAO {

  @Inject
  private SqlSession session;

  private static String namespace = "com.study.groupware.mapper.NtcReplyMapper";

  @Override
  public NtcReplyVO create(Map param) throws Exception {
   return session.selectOne(namespace + ".create", param);
  }
 

  @Override
  public void delete(Map param) throws Exception {
    session.delete(namespace + ".delete", param);
  }

  @Override
  public List<NtcReplyVO> listAll(String ntc_sq) throws Exception {
    return session.selectList(namespace + ".listAll",ntc_sq);
  }

  @Override
  public NtcReplyVO read(Map param) throws Exception {
    return session.selectOne(namespace + ".read", param);
  }
  

  @Override
  public NtcReplyVO update(Map param) throws Exception {
    return session.selectOne(namespace + ".update", param);
  }

}
