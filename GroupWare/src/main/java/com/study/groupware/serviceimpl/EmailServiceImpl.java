package com.study.groupware.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.study.groupware.dao.EmailDAO;
import com.study.groupware.service.EmailService;
import com.study.groupware.vo.EmailVO;

@Service
public class EmailServiceImpl implements EmailService {

  @Inject
  private EmailDAO dao;

  
  @Transactional
  @Override
  public void regist(EmailVO email) throws Exception {
    dao.create(email);
    }   
  
  @Transactional
  @Override
  public void regist2(EmailVO email) throws Exception {
    dao.create2(email);
    }   
  
  @Transactional
  @Override
  public void regist3(EmailVO email) throws Exception {
    dao.create3(email);
    }  
  

  @Transactional(isolation=Isolation.READ_COMMITTED)
  @Override
  public EmailVO read(Map param) throws Exception {
    return dao.read(param);
  }

  @Transactional
  @Override
  public void modify(Map param) throws Exception {
	 dao.update(param);
   
    }
  
  @Transactional
  @Override
  public void remove(Map param) throws Exception {
     dao.delete(param);
	
  } 

  @Override
  public List<EmailVO> sndListAll(EmailVO email) throws Exception {
    return dao.sndListAll(email);
  }
  
  @Override
  public List<EmailVO> rcvListAll(EmailVO email) throws Exception {
    return dao.rcvListAll(email);
  }


  @Override
  public List<EmailVO> keepListAll(EmailVO email) throws Exception {
    return dao.keepListAll(email);
  }

  @Override
  public int rcvCount(String stf_rcv_sq) throws Exception {
    return dao.rcvCount(stf_rcv_sq);
  }

  @Override
  public int sndCount(String stf_snd_sq) throws Exception {
    return dao.sndCount(stf_snd_sq);
  }
  
  @Override
  public int keepCount(String stf_rcv_sq) throws Exception {
    return dao.keepCount(stf_rcv_sq);
  }
  
  @Override
  public int rcvSearchCount(EmailVO email) throws Exception {
    return dao.rcvSearchCount(email);
  }
  
  @Override
  public int sndSearchCount(EmailVO email) throws Exception {
    return dao.sndSearchCount(email);
  }

  @Override
  public int keepSearchCount(EmailVO email) throws Exception {
    return dao.keepSearchCount(email);
  }

}
