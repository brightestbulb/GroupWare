package com.study.groupware.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.study.groupware.dao.ApprovalDAO;
import com.study.groupware.service.ApprovalService;
import com.study.groupware.vo.ApprovalVO;

@Service
public class ApprovalServiceImpl implements ApprovalService {

  @Inject
  private ApprovalDAO dao;

  
  @Transactional
  @Override
  public void regist(ApprovalVO approval) throws Exception {
  
    dao.create(approval);

    }   
  

  @Transactional(isolation=Isolation.READ_COMMITTED)
  @Override
  public ApprovalVO read(Map param) throws Exception {
    
    return dao.read(param);
  }


  @Transactional
  @Override
  public ApprovalVO modify(Map param) throws Exception {
	  return dao.update(param);
   
    }
  
  @Transactional
  @Override
  public void remove(String apv_sq) throws Exception {
    dao.delete(apv_sq);
  } 

  @Override
  public List<ApprovalVO> listAll(ApprovalVO approval) throws Exception {
    return dao.listAll(approval);
  }
  
  @Override
  public int count(ApprovalVO approval) throws Exception {
    
    return dao.count(approval);
  }
  
  @Override
  public int searchCount(ApprovalVO approval) throws Exception {
    
    return dao.searchCount(approval);
  }

  @Override
  public int stfAdmn(String stf_sq) throws Exception {
    
    return dao.stfAdmn(stf_sq);
  }

  
}
