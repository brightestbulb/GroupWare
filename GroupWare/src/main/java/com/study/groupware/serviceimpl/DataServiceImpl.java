package com.study.groupware.serviceimpl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.study.groupware.dao.DataDAO;
import com.study.groupware.service.DataService;
import com.study.groupware.vo.DataVO;

@Service
public class DataServiceImpl implements DataService {

  @Inject
  private DataDAO dao;

  
  @Transactional
  @Override
  public void regist(DataVO data) throws Exception {
    dao.create(data);
    }   
  

  @Transactional(isolation=Isolation.READ_COMMITTED)
  @Override
  public DataVO read(int data_sq) throws Exception {
    dao.updateViewCnt(data_sq);
    return dao.read(data_sq);
  }

  @Transactional
  @Override
  public void modify(DataVO data) throws Exception {
	  dao.update(data);
   
    }
  
  @Transactional
  @Override
  public void remove(int data_sq) throws Exception {
    dao.delete(data_sq);
  } 

  @Override
  public List<DataVO> listAll(DataVO data) throws Exception {
    return dao.listAll(data);
  }

  @Override
  public int count() throws Exception {
    return dao.count();
  }
  
  @Override
  public int searchCount(DataVO data) throws Exception {
    return dao.searchCount(data);
  }
  
  


}
