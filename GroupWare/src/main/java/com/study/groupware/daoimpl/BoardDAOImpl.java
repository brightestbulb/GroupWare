package com.study.groupware.daoimpl;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.study.groupware.dao.BoardDAO;
import com.study.groupware.vo.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

  @Inject
  private SqlSession session;

  private static String namespace = "com.study.groupware.mapper.BoardMapper";

  @Override
  public void create(BoardVO vo) throws Exception {
    session.insert(namespace + ".create", vo);
  }

  @Override
  public BoardVO read(String ntc_sq) throws Exception {
    return session.selectOne(namespace + ".read", ntc_sq);
  }
 
  @Override
  public void update(BoardVO vo) throws Exception {
    session.update(namespace + ".update", vo);
  }

  @Override
  public void delete(String ntc_sq) throws Exception {
    session.delete(namespace + ".delete", ntc_sq);
  }

  @Override
  public List<BoardVO> listAll(BoardVO board) throws Exception {
    return session.selectList(namespace + ".listAll",board);
  }

  
  @Override
  public void updateViewCnt(String ntc_sq) throws Exception {
    
    session.update(namespace+".updateViewCnt", ntc_sq);
    
  }
  
  @Override
  public int count(int ntc_div_sq) throws Exception {
    return session.selectOne(namespace + ".count", ntc_div_sq);
  }
 
  @Override
  public int searchCount(BoardVO board) throws Exception {
    return session.selectOne(namespace + ".searchCount", board);
  }
 

  

}
