package com.study.groupware.daoimpl;

import java.util.List;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.study.groupware.dao.DataDAO;
import com.study.groupware.vo.DataVO;

@Repository
public class DataDAOImpl implements DataDAO {

  @Inject
  private SqlSession session;

  private static String namespace = "com.study.groupware.mapper.DataMapper";

  @Override
  public void create(DataVO vo) throws Exception {
    session.insert(namespace + ".create", vo);
  }

  @Override
  public DataVO read(int data_sq) throws Exception {
    return session.selectOne(namespace + ".read", data_sq);
  }
 
  @Override
  public void update(DataVO vo) throws Exception {
    session.update(namespace + ".update", vo);
  }

  @Override
  public void delete(int data_sq) throws Exception {
    session.delete(namespace + ".delete", data_sq);
  }

  @Override
  public List<DataVO> listAll(DataVO data) throws Exception {
    return session.selectList(namespace + ".listAll",data);
  }

  @Override
  public void updateViewCnt(int data_sq) throws Exception {
    
    session.update(namespace+".updateViewCnt", data_sq);
    
  }
  

  @Override
  public int count() throws Exception {
    return session.selectOne(namespace + ".count");
  }
  

  @Override
  public int searchCount(DataVO vo) throws Exception {
    return session.selectOne(namespace + ".searchCount", vo);
  }
 
 

}
