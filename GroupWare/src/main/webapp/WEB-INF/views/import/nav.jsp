<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>nav</title>

<style type="text/css">
	.tableMiddle > thead > tr > th, .tableMiddle > thead > tr > td,
	.tableMiddle > tbody > tr > th, .tableMiddle > tbody > tr > td {
		vertical-align: middle;
	}	
</style>

<script type="text/javascript">
	$(document).ready(function() {
		
		tel();
		
		$("#myInfoUpdate").on("click",	function() {
			if ($("#stf_pw_myInfo").val() == "") {
				alert("비밀번호를 확인해주세요.");
				return;
			} 

			myInfoUpdate();
		});
		
		// update 비밀번호 자동 입력1
		$("#stf_pw1_myInfo").on("keyup", function() {
			if ($("#stf_pw1_myInfo").val() == "" && $("#stf_pw2_myInfo").val() == "") {
				$("#stf_pw1_Div_myInfo").removeAttr("class");
				$("#stf_pw1_Span_myInfo").removeAttr("class");
			} else if ($("#stf_pw1_myInfo").val() == $("#stf_pw2_myInfo").val()) {
				$("#stf_pw_myInfo").val($("#stf_pw1_myInfo").val());
				$("#stf_pw1_Div_myInfo").attr("class",	"has-success has-feedback");
				$("#stf_pw1_Span_myInfo").attr("class", "glyphicon glyphicon-ok form-control-feedback");
			} else if ($("#stf_pw1_myInfo").val() != $("#stf_pw2_myInfo").val()) {
				$("#stf_pw_myInfo").val("");
				$("#stf_pw1_Div_myInfo").attr("class",	"has-error has-feedback");
				$("#stf_pw1_Span_myInfo").attr("class", "glyphicon glyphicon-remove form-control-feedback");
			}
		});

		// update 비밀번호 자동 입력2
		$("#stf_pw2_myInfo").on("keyup", function() {
			if ($("#stf_pw2_myInfo").val() == "" && $("#stf_pw1_myInfo").val() == "") {
				$("#stf_pw1_Div_myInfo").removeAttr("class");
				$("#stf_pw1_Span_myInfo").removeAttr("class");
			} else if ($("#stf_pw2_myInfo").val() == $("#stf_pw1_myInfo").val()) {
				$("#stf_pw_myInfo").val($("#stf_pw2_myInfo").val());
				$("#stf_pw1_Div_myInfo").attr("class",	"has-success has-feedback");
				$("#stf_pw1_Span_myInfo").attr("class", "glyphicon glyphicon-ok form-control-feedback");
			} else if ($("#stf_pw2_myInfo").val() != $("#stf_pw1_myInfo").val()) {
				$("#stf_pw_myInfo").val("");
				$("#stf_pw1_Div_myInfo").attr("class",	"has-error has-feedback");
				$("#stf_pw1_Span_myInfo").attr("class", "glyphicon glyphicon-remove form-control-feedback");
			}
		});
		
		$("#organizationBtn").on("click", function() {
			if ($("#organization").val() != "") {
				$("#navForm").submit();	
			}
		});
		
		
	});
	
	function tel() {
		var arrPhone = $("#phone").val().split("-");
		var arrTel = $("#tel").val().split("-");
		
		$("#phoneNum1_myInfo").val(arrPhone[0]);
		$("#phoneNum2_myInfo").val(arrPhone[1]);
		$("#phoneNum3_myInfo").val(arrPhone[2]);
		
		$("#telNum2_myInfo").val(arrTel[1]);
		$("#telNum3_myInfo").val(arrTel[2]);
	}
	
	
	function myInfoUpdate() {
		var params = {
				stf_pw : $("#stf_pw_myInfo").val()
			};

		$.ajax({
			url : "/nav/stfPwUpdate",
			type : "POST",
			dataType : "text",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				
			},
			success : function(data) {
				if (data > 0) {
					alert("비밀번호 수정을 성공하였습니다.");
				}
				else if (data == 0) {
					alert("비밀번호 수정을 실패하였습니다.");
				}
			},
			error : function(request, status, error) {
				alert("list search fail :: error code: "
						+ request.status + "\n" + "error message: "
						+ error + "\n");
			}
		});
	}
</script>

</head>
<body>
	<!-- Update Modal -->
	<div class="modal fade" id="myInfoModal" role="dialog">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">내 정보 수정</h4>
				</div>
				<div class="modal-body">

					<table class="tableMiddle table table-striped table-bordered">
						<colgroup>
							<col width="30%" />
							<col width="70%" />
						</colgroup>
						<thead>
						</thead>
						<form role="form">
							<tbody>
								<tr>
									<th class="text-center"><img id="imgView_myInfo" class="profileImg"
										src="${myInfoList.STF_PT_RT }"></th>
									<td>
										<h5>개인정보는 비밀번호만 변경이 가능합니다.</h5>
										<h5>다른 정보를 변경해야 할 경우 인사팀에 문의해주세요.</h5>
										<h5><strong>비밀번호</strong>는 공백일 경우 기존 데이터로 유지됩니다.</h5>
									</td>
								</tr>
								<tr>
									<th>이름</th>
									<td><input type="text" id="stf_nm_myInfo"
										class="form-control" readonly="readonly" value="${myInfoList.STF_NM }"></td>
								</tr>
								<tr>
									<th>비밀번호</th>
									<td>
										<div id="stf_pw1_Div_myInfo">
											<input type="password" id="stf_pw1_myInfo" class="form-control"
												placeholder="비밀번호"> <span id="stf_pw1_Span_myInfo"></span>
										</div>
									</td>
								</tr>
								<tr>
									<th>비밀번호 확인</th>
									<td><input type="password" id="stf_pw2_myInfo"
										class="form-control" placeholder="비밀번호 확인"> <input
										type="hidden" id="stf_pw_myInfo" class="form-control"></td>
								</tr>
								<tr>
									<th>사원번호</th>
									<td><input type="text" id="stf_sq1_myInfo" class="form-control"
										readonly="readonly" value="${myInfoList.STF_SQ }"></td>
								</tr>
								<tr>
									<th>부서</th>
									<td><input id="dpt_sq_myInfo" class="form-control" 
										readonly="readonly" value="${myInfoList.RNK_NM }"></td>
								</tr>
								<tr>
									<th>직급</th>
									<td><input id="rnk_sq_myInfo" class="form-control" 
										readonly="readonly" value="${myInfoList.DPT_NM }"></td>
								</tr>
								<tr>
									<th>주소</th>
									<td><input type="text" id="stf_cm_add_myInfo" class="form-control" 
										readonly="readonly" value="${myInfoList.STF_CM_ADD }"></td>
								</tr>
								<tr>
									<th>상세주소</th>
									<td><input type="text" id="stf_dt_add_myInfo" 
										class="form-control" readonly="readonly" value="${myInfoList.STF_DT_ADD }"></td>
								</tr>
								<tr>
									<th>이메일</th>
									<td><input type="email" id="stf_eml_myInfo" 
										class="form-control" readonly="readonly" value="${myInfoList.STF_EML }"></td>
								</tr>

								<tr>
									<th>휴대폰</th>
									<td>
										<input type="hidden" id="phone" value="${myInfoList.STF_PH }">
										<div class="col-sm-2 col-md-2 leftNoPadding rightNoPadding">
											<input id="phoneNum1_myInfo" class="form-control text-center" readonly="readonly">
										</div>
										<div
											class="col-sm-1 col-md-1 text-center leftNoPadding rightNoPadding">
											<h5>-</h5>
										</div>
										<div class="col-sm-4 col-md-4 leftNoPadding rightNoPadding">
											<input type="text" id="phoneNum2_myInfo" class="form-control telNumMax text-center" readonly="readonly"/>
										</div>
										<div
											class="col-sm-1 col-md-1 text-center leftNoPadding rightNoPadding">
											<h5>-</h5>
										</div>
										<div class="col-sm-4 col-md-4 leftNoPadding rightNoPadding">
											<input type="text" id="phoneNum3_myInfo" class="form-control telNumMax text-center" readonly="readonly"/>
										</div>
									</td>
								</tr>
								<tr>
									<th>내선번호</th>
									<td>
										<input type="hidden" id="tel" value="${myInfoList.STF_BS_PH }">
									
										<div class="col-sm-2 col-md-2 leftNoPadding rightNoPadding">
											<input type="text" id="telNum1_myInfo" class="form-control text-center"
												value="070" readonly="readonly" />
										</div>
										<div
											class="col-sm-1 col-md-1 text-center leftNoPadding rightNoPadding">
											<h5>-</h5>
										</div>
										<div class="col-sm-4 col-md-4 leftNoPadding rightNoPadding">
											<input type="text" id="telNum2_myInfo" class="form-control telNumMax text-center" readonly="readonly"/>
										</div>
										<div
											class="col-sm-1 col-md-1 text-center leftNoPadding rightNoPadding">
											<h5>-</h5>
										</div>
										<div class="col-sm-4 col-md-4 leftNoPadding rightNoPadding">
											<input type="text" id="telNum3_myInfo" class="form-control telNumMax text-center" readonly="readonly"/>
										</div>
									</td>
								</tr>
								<tr>
									<th>입사일</th>
									<td><input type="date" id="stf_ent_myInfo"
										class="form-control" readonly="readonly" value="${myInfoList.STF_ENT }"></td>
								</tr>
					</tbody>
					</form>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" id="myInfoUpdate" class="btn btn-success">수정</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
				</div>
			</div>
		</div>
	</div>

	<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#myNavbar">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">GroupWare</a>
				</div>
				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav">
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="#">편지함<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="/email/rcvList">받은편지함</a></li>
								<li><a href="/email/sndList">보낸편지함</a></li>
								<li><a href="/email/keepList">보관함</a></li>
							</ul></li>
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="#">전자결재<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="/approval/approvalList?div_apv_sq=1">업무</a></li>
								<li><a href="/approval/approvalList?div_apv_sq=2">파견</a></li>
								<li><a href="/approval/approvalList?div_apv_sq=3">경비지출</a></li>
								<li><a href="/approval/approvalList?div_apv_sq=4">초과근무</a></li>
								<li><a href="/approval/approvalList?div_apv_sq=5">휴가</a></li>
							</ul></li>
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="#">일정<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="/schedule/scheduleList?scd_sq=1">회사일정</a></li>
								<li><a href="/schedule/scheduleList?scd_sq=2">부서일정</a></li>
								<li><a href="/schedule/scheduleList?scd_sq=3">개인일정</a></li>
							</ul></li>
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="#">게시판<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="/sboard/list?ntc_div_sq=1">자유게시판</a></li>
								<li><a href="/sboard/list?ntc_div_sq=2">사내동호회</a></li>
								<li><a href="/sboard/list?ntc_div_sq=3">중고장터</a></li>
							</ul></li>
					    <li><a href="/data/dataList">자료실</a></li>
							
							<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="#">관리자<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="/admin/officerList">구성원 관리</a></li>
							</ul></li>
					</ul>

					<form id="navForm" class="navbar-form navbar-right" method="post" action="/officer/organization">
						<div class="input-group">
							<input type="text" id ="organization" class="form-control" placeholder="Search" name="navStfNm">
							<div class="input-group-btn">
								<button id="organizationBtn" class="btn btn-default" type="button">
									<i class="glyphicon glyphicon-search"></i>
								</button>
							</div>
						</div>
					</form>
					<ul class="nav navbar-nav navbar-right">
					<li class="topMenuLi"><a class="menuLink" href="/login/logout">Logout</a>
						<li><a href="#" id="myInfo" data-toggle="modal" data-backdrop="static"
								data-target="#myInfoModal"><span class="glyphicon glyphicon-user"></span>
								Profile</a></li>
					</ul>
				</div>
			</div>
		</nav>
</body>
</html>
