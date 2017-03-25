package com.study.groupware.dao;

import java.util.List;
import java.util.Map;
import com.study.groupware.vo.DataReplyVO;
import com.study.groupware.vo.NtcReplyVO;

public interface DataReplyDAO {

	  public DataReplyVO create(Map param) throws Exception;

	  public void delete(Map param) throws Exception;

	  public List<DataReplyVO> listAll(int data_sq) throws Exception;
	  
	  public DataReplyVO read(Map param) throws Exception;
	  
	  public DataReplyVO update(Map param) throws Exception;
	  

}
