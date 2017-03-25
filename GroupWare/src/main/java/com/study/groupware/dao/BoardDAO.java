package com.study.groupware.dao;

import java.util.List;

import com.study.groupware.vo.BoardVO;

public interface BoardDAO {

  public void create(BoardVO vo) throws Exception;

  public BoardVO read(String ntc_sq) throws Exception;

  public void update(BoardVO vo) throws Exception;

  public void delete(String ntc_sq) throws Exception;

  public List<BoardVO> listAll(BoardVO board) throws Exception;

  public void updateViewCnt(String ntc_sq)throws Exception;
  
  public int count(int ntc_div_sq)throws Exception;

  public int searchCount(BoardVO board)throws Exception;
 
	
}
