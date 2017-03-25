package com.study.groupware.dao;

import java.util.List;
import java.util.Map;

import com.study.groupware.vo.ApprovalVO;

public interface ApprovalDAO {
	
	  public void create(ApprovalVO vo) throws Exception;

	  public ApprovalVO read(Map param) throws Exception;

	  public ApprovalVO update(Map param) throws Exception;

	  public void delete(String apv_sq) throws Exception;

	  public List<ApprovalVO> listAll(ApprovalVO approval) throws Exception;
	  
	  public int count(ApprovalVO vo)throws Exception;
	  
	  public int searchCount(ApprovalVO vo)throws Exception;
	  
	  public int stfAdmn(String stf_sq) throws Exception;
	  

}
