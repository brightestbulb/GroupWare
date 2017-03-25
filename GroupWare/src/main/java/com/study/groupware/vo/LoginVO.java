package com.study.groupware.vo;

public class LoginVO {
	
	private String stf_sq;
	private String stf_pw;
	private int cnt;
	
	public String getStf_sq() {
		return stf_sq;
	}
	public void setStf_sq(String stf_sq) {
		this.stf_sq = stf_sq;
	}
	public String getStf_pw() {
		return stf_pw;
	}
	public void setStf_pw(String stf_pw) {
		this.stf_pw = stf_pw;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	@Override
	public String toString() {
		return "LoginVO [stf_sq=" + stf_sq + ", stf_pw=" + stf_pw + ", cnt=" + cnt + "]";
	}

	
}
