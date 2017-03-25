package com.study.groupware.service;

import java.util.List;

import com.study.groupware.vo.DataVO;

public interface DataService {
	  public void regist(DataVO data) throws Exception;

	  public DataVO read(int data_sq) throws Exception;

	  public void modify(DataVO data) throws Exception;

	  public void remove(int data_sq) throws Exception;

	  public List<DataVO> listAll(DataVO data) throws Exception;
	  
	  public int count() throws Exception;

	  public int searchCount(DataVO data) throws Exception;
}
