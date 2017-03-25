package com.study.groupware.service;

import java.util.List;
import java.util.Map;

import com.study.groupware.vo.DataReplyVO;
import com.study.groupware.vo.NtcReplyVO;

public interface DataReplyService {
	

	public DataReplyVO addReply(Map param) throws Exception;
	
	public List<DataReplyVO> listReply(int data_sq) throws Exception;
	
	public void removeReply(Map param) throws Exception;

	 public DataReplyVO read(Map param) throws Exception;
	 
	 public DataReplyVO replyUpdate(Map param) throws Exception;


}
