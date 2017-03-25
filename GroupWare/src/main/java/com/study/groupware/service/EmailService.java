package com.study.groupware.service;

import java.util.List;
import java.util.Map;

import com.study.groupware.vo.EmailVO;

public interface EmailService {
	
  public void regist(EmailVO email) throws Exception;
  
  public void regist2(EmailVO email) throws Exception;
  
  public void regist3(EmailVO email) throws Exception;

  public EmailVO read(Map param) throws Exception;

  public void modify(Map param) throws Exception;

  public void remove(Map param) throws Exception;

  public List<EmailVO> sndListAll(EmailVO email) throws Exception;
  
  public List<EmailVO> rcvListAll(EmailVO email) throws Exception;

  public List<EmailVO> keepListAll(EmailVO email) throws Exception;
  
  public int rcvCount(String stf_rcv_sq) throws Exception;
  
  public int keepCount(String stf_rcv_sq) throws Exception;
 
  public int sndCount(String stf_snd_sq) throws Exception;
  
  public int rcvSearchCount(EmailVO email) throws Exception;
  
  public int sndSearchCount(EmailVO email) throws Exception;
  
  public int keepSearchCount(EmailVO email) throws Exception;
}
