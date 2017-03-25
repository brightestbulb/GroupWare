package com.study.groupware.service;

import java.util.List;
import java.util.Map;

import com.study.groupware.vo.ApprovalVO;

public interface ApprovalService {

	  public void regist(ApprovalVO Approval) throws Exception;

	  public ApprovalVO read(Map param) throws Exception;

	  public ApprovalVO modify(Map param) throws Exception;

	  public void remove(String apv_sq) throws Exception;

	  public List<ApprovalVO> listAll(ApprovalVO approval) throws Exception;
	  
	  public int count(ApprovalVO Approval) throws Exception;

	  public int searchCount(ApprovalVO Approval) throws Exception;
	
	  public int stfAdmn(String stf_sq) throws Exception;
	
}
