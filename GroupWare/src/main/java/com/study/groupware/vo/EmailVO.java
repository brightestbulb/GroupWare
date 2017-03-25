package com.study.groupware.vo;

public class EmailVO {
	
	private int eml_sq;            //편지번호(키)
	private String stf_sq;
	private String stf_snd_sq;     //보낸 사원번호
	private String stf_rcv_sq;     //받은사원번호
	private String eml_nm;         //편지제목
	private String snd_dt;         //송신시간
	private String rcv_dt;         //수신시간
	private String eml_cnt;        //이메일 내용
	private String ipt_nm;         //편지중요도
	private int ipt_sq;         //중요번호(키)
    private String eml_pl_nm;      //파일명
    private String eml_pl_crs;     //파일경로
    private String stf_nm;         //보낸사원이름,받은사원이름 (뷰에 따라 다른 데이터를 갖고옴 변수명은 같음)
    private int snd_eml_sq;       //보내편지번호
    private int rcv_eml_sq;     //전송된편지번호(키)
    private int startCount;
    private int endCount;
    private int cate;
    private String page;
    private String keyword;
    
	public int getEml_sq() {
		return eml_sq;
	}
	public void setEml_sq(int eml_sq) {
		this.eml_sq = eml_sq;
	}
	public String getStf_sq() {
		return stf_sq;
	}
	public void setStf_sq(String stf_sq) {
		this.stf_sq = stf_sq;
	}
	public String getStf_snd_sq() {
		return stf_snd_sq;
	}
	public void setStf_snd_sq(String stf_snd_sq) {
		this.stf_snd_sq = stf_snd_sq;
	}
	public String getStf_rcv_sq() {
		return stf_rcv_sq;
	}
	public void setStf_rcv_sq(String stf_rcv_sq) {
		this.stf_rcv_sq = stf_rcv_sq;
	}
	public String getEml_nm() {
		return eml_nm;
	}
	public void setEml_nm(String eml_nm) {
		this.eml_nm = eml_nm;
	}
	public String getSnd_dt() {
		return snd_dt;
	}
	public void setSnd_dt(String snd_dt) {
		this.snd_dt = snd_dt;
	}
	public String getRcv_dt() {
		return rcv_dt;
	}
	public void setRcv_dt(String rcv_dt) {
		this.rcv_dt = rcv_dt;
	}
	public String getEml_cnt() {
		return eml_cnt;
	}
	public void setEml_cnt(String eml_cnt) {
		this.eml_cnt = eml_cnt;
	}
	public String getIpt_nm() {
		return ipt_nm;
	}
	public void setIpt_nm(String ipt_nm) {
		this.ipt_nm = ipt_nm;
	}
	public int getIpt_sq() {
		return ipt_sq;
	}
	public void setIpt_sq(int ipt_sq) {
		this.ipt_sq = ipt_sq;
	}
	public String getEml_pl_nm() {
		return eml_pl_nm;
	}
	public void setEml_pl_nm(String eml_pl_nm) {
		this.eml_pl_nm = eml_pl_nm;
	}
	public String getEml_pl_crs() {
		return eml_pl_crs;
	}
	public void setEml_pl_crs(String eml_pl_crs) {
		this.eml_pl_crs = eml_pl_crs;
	}
	public String getStf_nm() {
		return stf_nm;
	}
	public void setStf_nm(String stf_nm) {
		this.stf_nm = stf_nm;
	}
	public int getSnd_eml_sq() {
		return snd_eml_sq;
	}
	public void setSnd_eml_sq(int snd_eml_sq) {
		this.snd_eml_sq = snd_eml_sq;
	}
	public int getRcv_eml_sq() {
		return rcv_eml_sq;
	}
	public void setRcv_eml_sq(int rcv_eml_sq) {
		this.rcv_eml_sq = rcv_eml_sq;
	}
	public int getStartCount() {
		return startCount;
	}
	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}
	public int getEndCount() {
		return endCount;
	}
	public void setEndCount(int endCount) {
		this.endCount = endCount;
	}
	public int getCate() {
		return cate;
	}
	public void setCate(int cate) {
		this.cate = cate;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		return "EmailVO [eml_sq=" + eml_sq + ", stf_sq=" + stf_sq + ", stf_snd_sq=" + stf_snd_sq + ", stf_rcv_sq="
				+ stf_rcv_sq + ", eml_nm=" + eml_nm + ", snd_dt=" + snd_dt + ", rcv_dt=" + rcv_dt + ", eml_cnt="
				+ eml_cnt + ", ipt_nm=" + ipt_nm + ", ipt_sq=" + ipt_sq + ", eml_pl_nm=" + eml_pl_nm + ", eml_pl_crs="
				+ eml_pl_crs + ", stf_nm=" + stf_nm + ", snd_eml_sq=" + snd_eml_sq + ", rcv_eml_sq=" + rcv_eml_sq
				+ ", startCount=" + startCount + ", endCount=" + endCount + ", cate=" + cate + ", page=" + page
				+ ", keyword=" + keyword + "]";
	}
	
}
