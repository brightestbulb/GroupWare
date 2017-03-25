package com.study.groupware.dao;

import java.util.List;
import java.util.Map;
import com.study.groupware.vo.EmailVO;

public interface EmailDAO {
	
	  public void create(EmailVO vo) throws Exception;
	  
	  public void create2(EmailVO vo) throws Exception;   // eml_rcv_tb insert
	  
	  public void create3(EmailVO vo) throws Exception;   // eml_snd_tb insert

	  public EmailVO read(Map param) throws Exception;

	  public void update(Map param) throws Exception;

	  public void delete(Map param) throws Exception;

	  public List<EmailVO> sndListAll(EmailVO vo) throws Exception;
	  
	  public List<EmailVO> rcvListAll(EmailVO vo) throws Exception;
	  
	  public List<EmailVO> keepListAll(EmailVO vo) throws Exception;
	  
	  public int rcvCount(String stf_rcv_sq)throws Exception;
	  
	  public int sndCount(String stf_snd_sq)throws Exception;
	  
	  public int keepCount(String stf_rcv_sq)throws Exception;
	  
	  public int rcvSearchCount(EmailVO vo)throws Exception;
	  
	  public int sndSearchCount(EmailVO vo)throws Exception;
	  
	  public int keepSearchCount(EmailVO vo)throws Exception;
}

