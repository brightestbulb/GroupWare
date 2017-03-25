package com.study.groupware.service;

import java.util.List;

import com.study.groupware.vo.BoardVO;

public interface BoardService {
	
  public void regist(BoardVO board) throws Exception;

  public BoardVO read(String ntc_sq) throws Exception;

  public void modify(BoardVO board) throws Exception;

  public void remove(String ntc_sq) throws Exception;

  public List<BoardVO> listAll(BoardVO board) throws Exception;

  public int count(int ntc_div_sq) throws Exception;
  
  public int searchCount(BoardVO board) throws Exception;


 

}
