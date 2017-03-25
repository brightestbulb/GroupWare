package com.study.groupware.vo;

import java.util.Date;

public class DataReplyVO {
	
	private int dt_rpy_sq;            //리플번호(키)
	private int stf_sq;               //사원번호
	private String stf_nm;            //사원이름
	private int data_sq;              //자료번호
	private String dt_rpy_cnt;        //내용
	private Date dt_rpy_dt;           //작성일
	private Date dt_rpy_mod;          //최종수정일
	
	
	public int getDt_rpy_sq() {
		return dt_rpy_sq;
	}
	public void setDt_rpy_sq(int dt_rpy_sq) {
		this.dt_rpy_sq = dt_rpy_sq;
	}
	public int getStf_sq() {
		return stf_sq;
	}
	public void setStf_sq(int stf_sq) {
		this.stf_sq = stf_sq;
	}
	public String getStf_nm() {
		return stf_nm;
	}
	public void setStf_nm(String stf_nm) {
		this.stf_nm = stf_nm;
	}
	public int getData_sq() {
		return data_sq;
	}
	public void setData_sq(int data_sq) {
		this.data_sq = data_sq;
	}
	public String getDt_rpy_cnt() {
		return dt_rpy_cnt;
	}
	public void setDt_rpy_cnt(String dt_rpy_cnt) {
		this.dt_rpy_cnt = dt_rpy_cnt;
	}
	public Date getDt_rpy_dt() {
		return dt_rpy_dt;
	}
	public void setDt_rpy_dt(Date dt_rpy_dt) {
		this.dt_rpy_dt = dt_rpy_dt;
	}
	public Date getDt_rpy_mod() {
		return dt_rpy_mod;
	}
	public void setDt_rpy_mod(Date dt_rpy_mod) {
		this.dt_rpy_mod = dt_rpy_mod;
	}
	@Override
	public String toString() {
		return "DataReplyVO [dt_rpy_sq=" + dt_rpy_sq + ", stf_sq=" + stf_sq + ", stf_nm=" + stf_nm + ", data_sq="
				+ data_sq + ", dt_rpy_cnt=" + dt_rpy_cnt + ", dt_rpy_dt=" + dt_rpy_dt + ", dt_rpy_mod=" + dt_rpy_mod
				+ "]";
	}
	
	

	
	
	
}
