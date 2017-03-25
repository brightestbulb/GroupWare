package com.study.groupware.vo;

import java.util.Date;

public class NtcReplyVO {
	
	private int rpy_sq;      //리플번호(키)
	private String ntc_sq;      //게시글 번호
	private String stf_sq;      //사원번호
    private String stf_nm;   //사원이름
	private String rpy_cnt;  //내용
	private Date rpy_dt;     //리플작성일
	private Date rpy_mod;    //리플 최종수정일
	
	
	public int getRpy_sq() {
		return rpy_sq;
	}
	public void setRpy_sq(int rpy_sq) {
		this.rpy_sq = rpy_sq;
	}
	public String getNtc_sq() {
		return ntc_sq;
	}
	public void setNtc_sq(String ntc_sq) {
		this.ntc_sq = ntc_sq;
	}
	public String getStf_sq() {
		return stf_sq;
	}
	public void setStf_sq(String stf_sq) {
		this.stf_sq = stf_sq;
	}
	public String getStf_nm() {
		return stf_nm;
	}
	public void setStf_nm(String stf_nm) {
		this.stf_nm = stf_nm;
	}
	public String getRpy_cnt() {
		return rpy_cnt;
	}
	public void setRpy_cnt(String rpy_cnt) {
		this.rpy_cnt = rpy_cnt;
	}
	public Date getRpy_dt() {
		return rpy_dt;
	}
	public void setRpy_dt(Date rpy_dt) {
		this.rpy_dt = rpy_dt;
	}
	public Date getRpy_mod() {
		return rpy_mod;
	}
	public void setRpy_mod(Date rpy_mod) {
		this.rpy_mod = rpy_mod;
	}
	@Override
	public String toString() {
		return "NtcReplyVO [rpy_sq=" + rpy_sq + ", ntc_sq=" + ntc_sq + ", stf_sq=" + stf_sq + ", stf_nm=" + stf_nm
				+ ", rpy_cnt=" + rpy_cnt + ", rpy_dt=" + rpy_dt + ", rpy_mod=" + rpy_mod + "]";
	}
	
	

	
	
	
	

}
