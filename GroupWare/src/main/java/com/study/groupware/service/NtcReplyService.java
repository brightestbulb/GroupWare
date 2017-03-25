package com.study.groupware.service;

import java.util.List;
import java.util.Map;

import com.study.groupware.vo.BoardVO;
import com.study.groupware.vo.NtcReplyVO;

public interface NtcReplyService {
	
	public NtcReplyVO addReply(Map param) throws Exception;
	
	public List<NtcReplyVO> listReply(String ntc_sq) throws Exception;
	
	public void removeReply(Map param) throws Exception;
	
	 public NtcReplyVO read(Map param) throws Exception;
	 
	 public NtcReplyVO replyUpdate(Map param) throws Exception;


	

}
