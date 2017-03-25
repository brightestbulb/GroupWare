package com.study.groupware.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.groupware.dao.NtcReplyDAO;
import com.study.groupware.service.NtcReplyService;
import com.study.groupware.vo.NtcReplyVO;

@Service
public class NtcReplyServiceImpl implements NtcReplyService {

  @Inject
  private NtcReplyDAO dao;

  
  @Transactional
  @Override
  public NtcReplyVO addReply(Map param) throws Exception {
	  
  
    return dao.create(param);

    }   

  
  @Transactional
  @Override
  public void removeReply(Map param) throws Exception {
    dao.delete(param);
  } 

  @Override
  public List<NtcReplyVO> listReply(String ntc_sq) throws Exception {
    return dao.listAll(ntc_sq);
  }

  @Transactional
  @Override
  public NtcReplyVO read(Map param) throws Exception {
	  return dao.read(param);
   
    }
  
  @Transactional
  @Override
  public NtcReplyVO replyUpdate(Map param) throws Exception {
	  return dao.update(param);
   
    }


}
