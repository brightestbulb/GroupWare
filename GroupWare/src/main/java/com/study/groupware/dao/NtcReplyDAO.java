package com.study.groupware.dao;

import java.util.List;
import java.util.Map;

import com.study.groupware.vo.NtcReplyVO;

public interface NtcReplyDAO {
	
	  public NtcReplyVO create(Map param) throws Exception;

	  public void delete(Map param) throws Exception;

	  public List<NtcReplyVO> listAll(String ntc_sq) throws Exception;

	  public NtcReplyVO read(Map param) throws Exception;
	  
	  public NtcReplyVO update(Map param) throws Exception;
	}

