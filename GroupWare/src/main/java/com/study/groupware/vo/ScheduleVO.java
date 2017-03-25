package com.study.groupware.vo;

public class ScheduleVO {

	private String bs_scd_sq, scd_sq, scd_nm, dpt_nm, stf_sq, stf_nm, bs_scd_nm, bs_scd_cnt, bs_scd_str_dt, bs_scd_end_dt;

	public String getBs_scd_sq() {
		return bs_scd_sq;
	}

	public void setBs_scd_sq(String bs_scd_sq) {
		this.bs_scd_sq = bs_scd_sq;
	}

	public String getScd_sq() {
		return scd_sq;
	}

	public void setScd_sq(String scd_sq) {
		this.scd_sq = scd_sq;
	}

	public String getScd_nm() {
		return scd_nm;
	}

	public void setScd_nm(String scd_nm) {
		this.scd_nm = scd_nm;
	}

	public String getDpt_nm() {
		return dpt_nm;
	}

	public void setDpt_nm(String dpt_nm) {
		this.dpt_nm = dpt_nm;
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

	public String getBs_scd_nm() {
		return bs_scd_nm;
	}

	public void setBs_scd_nm(String bs_scd_nm) {
		this.bs_scd_nm = bs_scd_nm;
	}

	public String getBs_scd_cnt() {
		return bs_scd_cnt;
	}

	public void setBs_scd_cnt(String bs_scd_cnt) {
		this.bs_scd_cnt = bs_scd_cnt;
	}

	public String getBs_scd_str_dt() {
		return bs_scd_str_dt;
	}

	public void setBs_scd_str_dt(String bs_scd_str_dt) {
		this.bs_scd_str_dt = bs_scd_str_dt;
	}

	public String getBs_scd_end_dt() {
		return bs_scd_end_dt;
	}

	public void setBs_scd_end_dt(String bs_scd_end_dt) {
		this.bs_scd_end_dt = bs_scd_end_dt;
	}

	@Override
	public String toString() {
		return "ScheduleVO [bs_scd_sq=" + bs_scd_sq + ", scd_sq=" + scd_sq + ", scd_nm=" + scd_nm + ", dpt_nm=" + dpt_nm
				+ ", stf_sq=" + stf_sq + ", stf_nm=" + stf_nm + ", bs_scd_nm=" + bs_scd_nm + ", bs_scd_cnt="
				+ bs_scd_cnt + ", bs_scd_str_dt=" + bs_scd_str_dt + ", bs_scd_end_dt=" + bs_scd_end_dt + "]";
	}
	
}
