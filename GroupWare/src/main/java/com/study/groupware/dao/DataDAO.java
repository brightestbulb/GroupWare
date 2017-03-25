package com.study.groupware.dao;

import java.util.List;

import com.study.groupware.vo.DataVO;

public interface DataDAO {
	
	  public void create(DataVO vo) throws Exception;

	  public DataVO read(int data_sq) throws Exception;

	  public void update(DataVO vo) throws Exception;

	  public void delete(int data_sq) throws Exception;

	  public List<DataVO> listAll(DataVO data) throws Exception;

	  public void updateViewCnt(int data_sq)throws Exception;
	  
	  public int count()throws Exception;
	  
	  public int searchCount(DataVO vo)throws Exception;
}
