package com.study.groupware.serviceimpl;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.study.groupware.dao.DataReplyDAO;
import com.study.groupware.service.DataReplyService;
import com.study.groupware.vo.DataReplyVO;
import com.study.groupware.vo.NtcReplyVO;

@Service
public class DataReplyServiceImpl implements DataReplyService {

  @Inject
  private DataReplyDAO dao;

  
  @Transactional
  @Override
  public DataReplyVO addReply(Map param) throws Exception {
	  
    return dao.create(param);
    }   
  
  
  @Transactional
  @Override
  public void removeReply(Map param) throws Exception {
    dao.delete(param);
  } 

  @Override
  public List<DataReplyVO> listReply(int data_sq) throws Exception {
    return dao.listAll(data_sq);
  }

  
  @Transactional
  @Override
  public DataReplyVO read(Map param) throws Exception {
	  return dao.read(param);
   
    }
  
  @Transactional
  @Override
  public DataReplyVO replyUpdate(Map param) throws Exception {
	  return dao.update(param);
   
    }



}
