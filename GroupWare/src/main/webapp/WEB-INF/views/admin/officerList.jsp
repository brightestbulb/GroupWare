<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>구성원 관리</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<!-- ajaxForm -->
<script src="http://malsup.github.com/min/jquery.form.min.js"></script>

<!-- 다음 주소 API -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<!-- jQuery EasyUi API -->
<link rel="stylesheet" type="text/css"
	href="/resources/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="/resources/easyui/themes/icon.css">
<script type="text/javascript"
	src="/resources/easyui/jquery.easyui.min.js"></script>

<style type="text/css">
/* Remove the navbar's default rounded borders and increase the bottom margin */
.navbar {
	margin-bottom: 50px;
	border-radius: 0;
}

/* Remove the jumbotron's default bottom margin */
.jumbotron {
	margin-bottom: 0;
}

/* Add a gray background color and some padding to the footer */
#footer {
	margin-top: 50px;
	background-color: #f2f2f2;
	padding: 25px;
}

#content {
	margin-left: 50px;
	margin-right: 50px;
	overflow: hidden;
}

#title {
	border-bottom: 2px solid #727377;
}

#title>*, #leftTop>* {
	display: inline-block;
}

#leftMenu, #rightMenu {
	margin-top: 10px;
}

#leftTop>button {
	margin-left: 5px;
}

form {
	margin-bottom: 10px;
}

.telNumMax, #phoneNum1, #telNum1, #phoneNum1_up, #telNum1_up {
	text-align-last: center;
}

.leftNoPadding {
	padding-left: 0px;
}

.rightNoPadding {
	padding-right: 0px;
}

.profileImg {
	height: 128px;
	width: 96px;
}

.tableMiddle > thead > tr > th, .tableMiddle > thead > tr > td,
.tableMiddle > tbody > tr > th, .tableMiddle > tbody > tr > td {
	vertical-align: middle;
}

#dpt_sq_dept {
    height: 300px;
}

#departmentModal > div {
	top: 50%;
	margin-top: -260px;
}

.overError {
	overflow: hidden;
}

#dpt_sq_dept {
	overflow: auto;
}

.small-icon {
	line-height: 20px;
	height: 20px;
	width: 20px;
	display: none;
	pointer-events: auto
}


#deptNmUp {
	width: 167px;
}

#officerList > thead > tr > th, #officerList > tbody > tr > td {
	text-align: center;
}

</style>

<script type="text/javascript">
	
	$(document).ready(function() {

		
		/* 사원 검색 */
		$("#search").on("click", function() {
			if ($("#keyword").val() == "") {
				alert("검색어를 최소 1글자 이상 입력해주세요.");
				return;
			} else if ($("#keyword").val() != "")
				var params = {
					cate : $("#cate").val(),
					keyword : $("#keyword").val()
				};
			
				officerListSearch(params);
		});
		
		// Ajax 페이징 처리
		$(document).on("click", "#pageIndexListAjax > li > a", function() {
			var params = {
					cate : $("#cate").val(),
					keyword : $("#keyword").val(),
					page : $(this).attr("data-page")
				};
			
			officerListSearch(params);
		});
		
		/* 입사일 자동 입력 */
		$("#officerInsertModal").on("click", function() {
			var date = new Date();
	
			var yyyy = date.getFullYear();
			var mm = (date.getMonth() + 1)
			var dd = date.getDate();
	
			if (mm < 10) {
				mm = "0" + mm;
			}
			if (dd < 10) {
				dd = "0" + dd;
			}
	
			date = yyyy + "-" + mm + "-" + dd;
	
			$("#stf_ent").val(date);
		});

		/* 한글 입력 방지 */
		$("#stf_eml, #stf_sq1").on("keyup", function() {
			$(this).val($(this).val().replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, ""));
		});
				
		// Update 숫자만 입력
		$(".telNumMax").on("keyup", function() {
			$(this).val($(this).val().replace(/[^0-9]/gi, ""));
		
			// 전화번호 최대 입력 방지
			if ($(this).val().length > 4) {
				$(this).val($(this).val().substring(0, 4));
			}
			
			if ($(this).attr("id") == "phoneNum2" || $(this).attr("id") == "phoneNum3") {
				var phoneNum = $("#phoneNum1").val() + "-" + $("#phoneNum2").val() + "-" + $("#phoneNum3").val();
				$("#stf_ph").val(phoneNum);
			}
			else if ($(this).attr("id") == "telNum2" || $(this).attr("id") == "telNum3") {
				var telNum = $("#telNum1").val() + "-" + $("#telNum2").val() + "-" + $("#telNum3").val();
				$("#stf_bs_ph").val(telNum);
			}			
			else if ($(this).attr("id") == "phoneNum2_up" || $(this).attr("id") == "phoneNum3_up") {
				var phoneNum = $("#phoneNum1_up").val() + "-" + $("#phoneNum2_up").val() + "-" + $("#phoneNum3_up").val();
				$("#stf_ph_up").val(phoneNum);
			}
			else if ($(this).attr("id") == "telNum2_up" || $(this).attr("id") == "telNum3_up") {
				var telNum = $("#telNum1_up").val() + "-" + $("#telNum2_up").val() + "-" + $("#telNum3_up").val();
				$("#stf_bs_ph_up").val(telNum);
			}
		});

		// insert 비밀번호 자동 입력1
		$("#stf_pw1").on("keyup", function() {
			if ($("#stf_pw1").val() == "" && $("#stf_pw2").val() == "") {
				$("#stf_pw1_Div").removeAttr("class");
				$("#stf_pw1_Span").removeAttr("class");
			} else if ($("#stf_pw1").val() == $("#stf_pw2").val()) {
				$("#stf_pw").val($("#stf_pw1").val());
				$("#stf_pw1_Div").attr("class",	"has-success has-feedback");
				$("#stf_pw1_Span").attr("class", "glyphicon glyphicon-ok form-control-feedback");
			} else if ($("#stf_pw1").val() != $("#stf_pw2").val()) {
				$("#stf_pw").val("");
				$("#stf_pw1_Div").attr("class",	"has-error has-feedback");
				$("#stf_pw1_Span").attr("class", "glyphicon glyphicon-remove form-control-feedback");
			}
		});

		// insert 비밀번호 자동 입력2
		$("#stf_pw2").on("keyup", function() {
			if ($("#stf_pw2").val() == "" && $("#stf_pw1").val() == "") {
				$("#stf_pw1_Div").removeAttr("class");
				$("#stf_pw1_Span").removeAttr("class");
			} else if ($("#stf_pw2").val() == $("#stf_pw1").val()) {
				$("#stf_pw").val($("#stf_pw2").val());
				$("#stf_pw1_Div").attr("class",	"has-success has-feedback");
				$("#stf_pw1_Span").attr("class", "glyphicon glyphicon-ok form-control-feedback");
			} else if ($("#stf_pw2").val() != $("#stf_pw1").val()) {
				$("#stf_pw").val("");
				$("#stf_pw1_Div").attr("class",	"has-error has-feedback");
				$("#stf_pw1_Span").attr("class", "glyphicon glyphicon-remove form-control-feedback");
			}
		});
		
		// update 비밀번호 자동 입력1
		$("#stf_pw1_up").on("keyup", function() {
			if ($("#stf_pw1_up").val() == "" && $("#stf_pw2_up").val() == "") {
				$("#stf_pw1_Div_up").removeAttr("class");
				$("#stf_pw1_Span_up").removeAttr("class");
			} else if ($("#stf_pw1_up").val() == $("#stf_pw2_up").val()) {
				$("#stf_pw_up").val($("#stf_pw1_up").val());
				$("#stf_pw1_Div_up").attr("class",	"has-success has-feedback");
				$("#stf_pw1_Span_up").attr("class", "glyphicon glyphicon-ok form-control-feedback");
			} else if ($("#stf_pw1_up").val() != $("#stf_pw2_up").val()) {
				$("#stf_pw_up").val("");
				$("#stf_pw1_Div_up").attr("class",	"has-error has-feedback");
				$("#stf_pw1_Span_up").attr("class", "glyphicon glyphicon-remove form-control-feedback");
			}
		});

		// update 비밀번호 자동 입력2
		$("#stf_pw2_up").on("keyup", function() {
			if ($("#stf_pw2_up").val() == "" && $("#stf_pw1_up").val() == "") {
				$("#stf_pw1_Div_up").removeAttr("class");
				$("#stf_pw1_Span_up").removeAttr("class");
			} else if ($("#stf_pw2_up").val() == $("#stf_pw1_up").val()) {
				$("#stf_pw_up").val($("#stf_pw2_up").val());
				$("#stf_pw1_Div_up").attr("class",	"has-success has-feedback");
				$("#stf_pw1_Span_up").attr("class", "glyphicon glyphicon-ok form-control-feedback");
			} else if ($("#stf_pw2_up").val() != $("#stf_pw1_up").val()) {
				$("#stf_pw_up").val("");
				$("#stf_pw1_Div_up").attr("class",	"has-error has-feedback");
				$("#stf_pw1_Span_up").attr("class", "glyphicon glyphicon-remove form-control-feedback");
			}
		});
		
		// insert 사원번호 자동 입력
		$("#stfNumSearchBtn").on("click", function() {
			if ($("#stf_sq1").val() == "") {
				alert("사원번호를 입력해주세요.");
			} else if ($("#stf_sq1").val() != "") {
				selectStf_sq($("#stf_sq1").val());
			}
		});

		// insert 사원번호 다시 체크
		$("#stf_sq1").on("keyup", function() {
			if ($("#stf_sq1").val() != $("#stf_sq").val()) {
				$("#stf_sq_Div").removeAttr("class");
				$("#stf_sq_Span").removeAttr("class");
				$("#stf_sq").val("");
			}
		});

		// update 사원번호 자동 입력
		$("#stfNumSearchBtn_up").on("click", function() {
			if ($("#stf_sq1_up").val() == "") {
				alert("사원번호를 입력해주세요.");
			} else if ($("#stf_sq1_up").val() != "") {
				selectStf_sq($("#stf_sq1_up").val());
			}
		});

		// update 사원번호 다시 체크
		$("#stf_sq1_up").on("keyup", function() {
			if ($("#stf_sq1_up").val() != $("#stf_sq").val()) {
				$("#stf_sq_Div_up").removeAttr("class");
				$("#stf_sq_Span_up").removeAttr("class");
				$("#stf_sq_up").val("");
			}
		});
		
		// 최대 입력 방지
		$("#stf_sq1, #stf_sq1_up").on("keyup", function() {
			if ($(this).val().length > 10) {
				$(this).val($(this).val().substring(0, 10));
			}
		});


		// 최대 입력 방지
		$("#stf_dt_add").on("keyup", function() {
			if ($(this).val().length > 33) {
				$(this).val($(this).val().substring(0, 33));
			}
		});

		/* 구성원 추가 */
		$("#officerInsert").on("click",	function() {
	
			var emailCheck = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

			if ($("#file").val() == "") {
				alert("프로필 사진을 확인해주세요.");
				return;
			} else if ($("#stf_nm").val() == "") {
				alert("이름을 확인해주세요.");
				return;
			} else if ($("#stf_pw").val() == "") {
				alert("비밀번호를 확인해주세요.");
				return;
			} else if ($("#stf_sq").val() == "") {
				alert("사원번호를 확인해주세요.");
				return;
			} else if ($("#stf_cm_add").val() == ""
					|| $("#stf_dt_add").val() == "") {
				alert("주소를 확인해주세요.");
				return;
			} else if (!emailCheck.test($(
					"#stf_eml").val())) {
				alert("이메일을 확인해주세요.");
				return;
			} else if ($("#stf_ph").val().length != 13) {
				alert("휴대폰 번호를 확인해주세요.");
				return;
			} else if ($("#stf_bs_ph").val().length != 13) {
				alert("내선번호를 확인해주세요.");
				return;
			}
			else if ($("#stf_ent").val() == "") {
				alert("입사일을 확인해주세요.");
				return;
			}

			officerInsert();
		});
		
		/* 구성원 추가 */
		$("#officerUpdate").on("click",	function() {
	
			var emailCheck = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

			if ($("#stf_nm_up").val() == "") {
				alert("이름을 확인해주세요.");
				return;
			} else if ($("#stf_pw1_up").val() != $("#stf_pw2_up").val()) {
				alert("비밀번호를 확인해주세요.");
				return;
			} else if ($("#stf_cm_add_up").val() == ""
					|| $("#stf_dt_add_up").val() == "") {
				alert("주소를 확인해주세요.");
				return;
			} else if (!emailCheck.test($(
					"#stf_eml_up").val())) {
				alert("이메일을 확인해주세요.");
				return;
			} else if ($("#stf_ph_up").val().length != 13) {
				alert("휴대폰 번호를 확인해주세요.");
				return;
			} else if ($("#stf_bs_ph_up").val().length != 13) {
				alert("내선번호를 확인해주세요.");
				return;
			} else if ($("#stf_ent_up").val() == "") {
				alert("입사일을 확인해주세요.");
				return;
			}

			officerUpdate();
		});
		
		/* 파일(이미지) 미리보기 */
		$("#file, #file_up").on("change", function(event) {
			var input = this;

			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#imgView').attr('src', e.target.result);
					$('#imgView_up').attr('src', e.target.result);
				}
				reader.readAsDataURL(input.files[0]);
			}
		});
		
		// 구성원 수정 모달 띄우기
		$("#officerUpdateModal").on("click", function() {
			
			if ($(".radio").is(":checked") == false) {
				alert("사원을 선택해주세요.");
				return;
			}
			
			$("#officerUpdateModal").attr("data-target", "#updateModal");
			
			selectUpdateOfficer();
		});
		
		// 부서명 입력
		$("#deptInsert").on("click", function() {
			deptInsert();
		});

		// 부서 관리 input으로 변경
		$(document).on("dblclick", ".deptDiv", function() {
			var dtp_sq = $(this).attr("data-value");
			var dtp_nm = $(this).text();
			
			deptList(dtp_sq, dtp_nm);
		});
			 	 
		// 부서명 수정 진행
		$(document).on("click", "#deptUpdate", function() {
			selectDeptNm();
		});
		
		// 최대 입력 방지
		$(document).on("keyup", "#addDept, #deptNmUp", function() {
			if ($(this).val().length > 8) {
				$(this).val($(this).val().substring(0, 8));
			}
		});
		
		$(document).on("mouseenter", ".deptDiv", function() {
			$(this).parent().children("span").css("display", "inline");
			$(this).css({"background-color" : "#1E90FF", "color" : "white", "cursor" : "default"});
		});
		
		$(document).on("mouseenter", ".small-icon", function() {
			$(this).parent().children("div").css({"background-color" : "#1E90FF", "color" : "white", "cursor" : "default"});
			$(this).css("display", "inline");
		});
				
		$(document).on("mouseleave", ".deptDiv", function() {
			$(".small-icon").css("display", "none");
			$(this).removeAttr("style");
		});
		
		$(document).on("mouseleave", ".small-icon", function() {
			$(".small-icon").css("display", "none");
			$(this).parent().children("div").removeAttr("style");
		});
		
		// 부서명 삭제
		$(document).on("click", ".small-icon", function() {
			var dpt_sq = $(this).parent().children("div").attr("data-value");
			deptDelete(dpt_sq);
		});
		
		// 조직도 닫은 후 강제 리다이렉트
		$("#deptClose").on("click", function() {
			window.location = "/admin/officerList";
		});
		
	});

	/* 사원 검색 */
	function officerListSearch(params) {
		$.ajax({
			url : "/admin/officerListSearch",
			type : "POST",
			dataType : "json",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				$("#userCount").empty();
				$("#officerList > tbody").empty();
				$("#pageIndexList").empty();
			},
			success : function(data) {
	
				var officerListCount = data.officerListCount;
				var officerList = data.officerList;
				var pageIndexListAjax = data.pageIndexListAjax;
	
				$("#userCount").html(officerListCount);
	
				var tbody = $("#officerList > tbody");

				$.each(officerList,	function(idx, val) {
					tbody.append($('<tr>').append($('<td>',	{html : "<input type='radio' class='radio' value='"+val.STF_SQ+"'>"}))
										  .append($('<td>',	{html : "<img src='"+val.STF_PT_RT+"' class='profileImg'/>"}))
										  .append($('<td>',	{text : val.STF_NM}))
										  .append($('<td>',	{text : val.RNK_NM}))
										  .append($('<td>',	{text : val.DPT_NM}))
										  .append($('<td>',	{text : val.ADMN_PW}))
										  .append($('<td>',	{text : val.STF_PH}))
										  .append($('<td>',	{text : val.STF_BS_PH}))
										  .append($('<td>',	{text : val.STF_EML})));
				});
				
				$("#pageIndexList").html(pageIndexListAjax);
			},
			error : function(request, status, error) {
				alert("list search fail :: error code: "
						+ request.status + "\n" + "error message: "
						+ error + "\n");
			}
		});
	}

	// 사원번호 중복 검색
	function selectStf_sq(data) {
		var params = {
			stf_sq : data
		};

		$.ajax({
			url : "/admin/selectStf_Sq",
			type : "POST",
			dataType : "text",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				$("#stf_sq").val("");
				$("#stf_sq_up").val("");
			},
			success : function(data) {

				var result = Number(data);

				if (result > 0) {
					alert("이미 존재하는 사원번호 입니다.");
					$("#stf_sq_Div").attr("class", "has-error has-feedback");
					$("#stf_sq_Span").attr("class", "glyphicon glyphicon-remove form-control-feedback");
					$("#stf_sq_Div_up").attr("class", "has-error has-feedback");
					$("#stf_sq_Span_up").attr("class", "glyphicon glyphicon-remove form-control-feedback");
				} else if (result == 0) {
					$("#stf_sq").val($("#stf_sq1").val());
					$("#stf_sq_up").val($("#stf_sq1_up").val());
					
					$("#stf_sq_Div").attr("class", "has-success has-feedback");
					$("#stf_sq_Span").attr("class", "glyphicon glyphicon-ok form-control-feedback");
					$("#stf_sq_Div_up").attr("class", "has-success has-feedback");
					$("#stf_sq_Span_up").attr("class", "glyphicon glyphicon-ok form-control-feedback");
				}
			},
			error : function(request, status, error) {
				alert("list search fail :: error code: "
						+ request.status + "\n" + "error message: "
						+ error + "\n");
			}
		});
	}

	// 사원 등록 Ajax 파일 업로드
	function officerInsert() {

		var params = {
			stf_sq : $("#stf_sq").val(),
			admn_sq : $("#admn_sq").val(),
			dpt_sq : $("#dpt_sq").val(),
			rnk_sq : $("#rnk_sq").val(),
			stf_nm : $("#stf_nm").val(),
			stf_pw : $("#stf_pw").val(),
			stf_ph : $("#stf_ph").val(),
			stf_cm_add : $("#stf_cm_add").val(),
			stf_dt_add : $("#stf_dt_add").val(),
			stf_bs_ph : $("#stf_bs_ph").val(),
			stf_eml : $("#stf_eml").val(),
			stf_ent : $("#stf_ent").val(),
		};

		$("#officerInsertForm").ajaxForm({
			url : "/admin/officerInsert",
			type : "POST",
			dataType : "text",
			data : JSON.stringify(params),
			enctype : "multipart/form-data",
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				$("#officerInsert").attr("disabled", "disabled");
			},
			success : function(data) {
				if (data == "SUCCESS") {
					alert("정상적으로 입력되었습니다.");
					window.location = "/admin/officerList";
				} else if (data == "FAIL") {
					alert("입력을 실패하였습니다.");
				}
			},
			error : function(request, status, error) {
				alert("list search fail :: error code: "
						+ request.status + "\n" + "error message: "
						+ error + "\n");
			}
		}).submit();

		$("#officerInsert").attr("disabled");
	}

	// 사원 정보 불러오기
	function selectUpdateOfficer() {
		var stf_sq = $("input[type=radio]:checked").val();

		var params = {
				stf_sq : stf_sq
			};
		
		$.ajax({
			url : "/admin/selectUpdateOfficer",
			type : "POST",
			dataType : "json",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				
			},
			success : function(data) {
				
				$("#imgView_up").attr("src", data.STF_PT_RT);
				$("#stf_nm_up").val(data.STF_NM);
				$("#stf_sq1_up").val(data.STF_SQ);
				$("#stf_sq_up").val(data.STF_SQ);
				$("#stf_sq_old").val(data.STF_SQ);
				$("#admn_sq_up").val(data.ADMN_SQ);
				$("#dpt_sq_up").val(data.DPT_SQ);
				$("#rnk_sq_up").val(data.RNK_SQ);
				$("#stf_cm_add_up").val(data.STF_CM_ADD);
				$("#stf_dt_add_up").val(data.STF_DT_ADD);
				$("#stf_eml_up").val(data.STF_EML);
				
				var arrPhoneNum = data.STF_PH.split("-");
				
				$("#phoneNum1_up").val(arrPhoneNum[0]);
				$("#phoneNum2_up").val(arrPhoneNum[1]);
				$("#phoneNum3_up").val(arrPhoneNum[2]);
				$("#stf_ph_up").val(data.STF_PH);
				
				var arrTelNum = data.STF_BS_PH.split("-");
				
				$("#telNum1_up").val(arrTelNum[0]);
				$("#telNum2_up").val(arrTelNum[1]);
				$("#telNum3_up").val(arrTelNum[2]);
				$("#stf_bs_ph_up").val(data.STF_BS_PH);
				
				$("#stf_ent_up").val(data.STF_ENT);
				
			},
			error : function(request, status, error) {
				alert("list search fail :: error code: "
						+ request.status + "\n" + "error message: "
						+ error + "\n");
			}
		});
	}
	
	// 사원 등록 Ajax 파일 업로드
	function officerUpdate() {

		var params = {
			stf_sq : $("#stf_sq_up").val(),
			stf_sq_old : $("#stf_sq_old").val(),
			admn_sq : $("#admn_sq_up").val(),
			dpt_sq : $("#dpt_sq_up").val(),
			rnk_sq : $("#rnk_sq_up").val(),
			stf_nm : $("#stf_nm_up").val(),
			stf_pw : $("#stf_pw_up").val(),
			stf_ph : $("#stf_ph_up").val(),
			stf_cm_add : $("#stf_cm_add_up").val(),
			stf_dt_add : $("#stf_dt_add_up").val(),
			stf_bs_ph : $("#stf_bs_ph_up").val(),
			stf_eml : $("#stf_eml_up").val(),
			stf_ent : $("#stf_ent_up").val(),
		};

		$("#officerUpdateForm").ajaxForm({
			url : "/admin/officerUpdate",
			type : "POST",
			dataType : "text",
			data : JSON.stringify(params),
			enctype : "multipart/form-data",
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				$("#officerUpdate").attr("disabled", "disabled");
			},
			success : function(data) {
				if (data == "SUCCESS") {
					alert("정상적으로 수정되었습니다.");
					window.location = "/admin/officerList";
				} else if (data == "FAIL") {
					alert("입력을 실패하였습니다.");
				}
			},
			error : function(request, status, error) {
				alert("list search fail :: error code: "
						+ request.status + "\n" + "error message: "
						+ error + "\n");
			}
		}).submit();

		$("#officerInsert").attr("disabled");
	}
	
	// 부서명 등록
	function deptInsert() {
		var params = {
				dpt_nm : $("#addDept").val()
			};

		$.ajax({
			url : "/admin/deptInsert",
			type : "POST",
			dataType : "text",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				$("#addDept").val("");
			},
			success : function(data) {
				if (data > 0) {
					alert("부서 추가를 성공하였습니다.");
					deptList();
				}
				else if (data == 0) {
					alert("부서 추가를 실패하였습니다.");
				}
			},
			error : function(request, status, error) {
				alert("list search fail :: error code: "
						+ request.status + "\n" + "error message: "
						+ error + "\n");
			}
		});
	}
	
	// 부서명 다시 가져오기
	function deptList(dpt_sq, dpt_nm) {
		$.ajax({
			url : "/admin/selectDpt_Div_Tb",
			type : "POST",
			dataType : "json",
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				$("#dpt_sq_dept").empty();
			},
			success : function(data) {
				
				var div = $("#dpt_sq_dept");
				
				$.each(data, function(idx, val) {
					if (val.DPT_SQ != dpt_sq) {
						//div.append($('<div>', {class : "deptDiv", "data-value" : val.DPT_SQ, text : val.DPT_NM}))
						div.append($('<div>', {"class" : "has-feedback"})
						   .append($('<div>', {"class" : "deptDiv", "data-value" : val.DPT_SQ, text : val.DPT_NM}))
						   .append($('<span>', {"class" : "glyphicon glyphicon-remove form-control-feedback small-icon"})))
					}
					else if (val.DPT_SQ == dpt_sq) {
						div.append($('<input>', {type : "text", id : "deptNmUp", "data-value" : val.DPT_SQ, value : val.DPT_NM}))
						div.append($('<button>', {id : "deptUpdate", text : "수정"}))
					}
				});
				
			},
			error : function(request, status, error) {
				alert("list search fail :: error code: "
						+ request.status + "\n" + "error message: "
						+ error + "\n");
			}
		});
	}
	
	// 동일한 부서명 체크
	function selectDeptNm() {
		var params = {
				dpt_nm : $("#deptNmUp").val()
			};

		$.ajax({
			url : "/admin/selectDeptNm",
			type : "POST",
			dataType : "text",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				
			},
			success : function(data) {
				if (data > 0) {
					alert("같은 이름의 부서명이 존재합니다.");
				}
				else if (data == 0) {
					deptUpdate();
				}
			},
			error : function(request, status, error) {
				alert("list search fail :: error code: "
						+ request.status + "\n" + "error message: "
						+ error + "\n");
			}
		});
	}
	
	// 부서명 수정
	function deptUpdate() {
		var params = {
				dpt_sq : $("#deptNmUp").attr("data-value"),
				dpt_nm : $("#deptNmUp").val()
			};

		$.ajax({
			url : "/admin/deptUpdate",
			type : "POST",
			dataType : "text",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				
			},
			success : function(data) {
				if (data > 0) {
					alert("부서명 수정을 성공하였습니다.");
					deptList();
				}
				else if (data == 0) {
					alert("부서명 수정을 실패하였습니다.");
				}
			},
			error : function(request, status, error) {
				alert("list search fail :: error code: "
						+ request.status + "\n" + "error message: "
						+ error + "\n");
			}
		});
	}
	
	// 부서명 삭제
	function deptDelete(data) {
		var params = {
				dpt_sq : data
			};
		
		$.ajax({
			url : "/admin/deptDelete",
			type : "POST",
			dataType : "text",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				
			},
			success : function(data) {
				
				if (data > 0) {
					alert("부서 삭제를 성공하였습니다.");
					deptList();
				}
				else if (data == 0) {
					alert("부서 삭제를 실패하였습니다.");
				}
				else if (data == -1) {
					alert("부서에 임직원이 존재하여 삭제할 수 없습니다.");
				}
				
			},
			error : function(request, status, error) {
				alert("list search fail :: error code: "
						+ request.status + "\n" + "error message: "
						+ error + "\n");
			}
		});
	}
	
	/* 다음 주소 API */
	function addrSearch() {
		new daum.Postcode({
			oncomplete : function(data) {
				var str = "[" + data.zonecode + "] " + data.address
				$("#stf_cm_add").val(str);
				$("#stf_cm_add_up").val(str);
			}
		}).open();
	}
</script>

</head>
<body>
	<div id="wrap">
		<!-- header 시작 -->
		<div>
			<c:import url="../import/header_admin.jsp" />
		</div>
		<!-- header 종료 -->


		<!-- nav 시작 -->
		<div>
			<c:import url="../import/nav.jsp" />
		</div>
		<!-- nav 종료 -->

		<!-- Department Modal -->
		<div class="modal fade" id="departmentModal" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" id="deptClose" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">조직 관리</h4>
					</div>
					<div class="modal-body">
						<div id="dpt_sq_dept" class="form-group well">
							<c:forEach items="${selectDpt_Div_Tb}" var="map">
								<div class="has-feedback">
									<div class="deptDiv" data-value="${map.DPT_SQ}">${map.DPT_NM}</div>
									<span class="glyphicon glyphicon-remove form-control-feedback small-icon"></span>
								</div>
							</c:forEach>
						</div>
						<div class="form-group overError">
							<div class="col-xs-10 col-md-10 leftNoPadding">
								<input type="text" id="addDept" class="form-control" placeholder="부서명">
							</div>
	
							<button type="button" id="deptInsert"
								class="btn btn-success col-xs-2 col-md-2 leftNoPadding rightNoPadding">추가</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Insert Modal -->
		<div class="modal fade" id="insertModal" role="dialog">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">구성원 추가</h4>
					</div>
					<div class="modal-body">

						<table class="tableMiddle table table-striped table-bordered ">
							<colgroup>
								<col width="30%" />
								<col width="70%" />
							</colgroup>
							<thead>
							</thead>
							<form id="officerInsertForm" action="/admin/officerInsert"
								method="post" enctype="multipart/form-data">
								<tbody>
									<tr>
										<th class="text-center"><img id="imgView" class="profileImg"
											src="/resources/img/user.png"> <input type="file"
											id="file" name="file" class="form-control"></th>
										<td>
											<h5>이미지는 가로 96px, 세로 128px를 준수 해주시기 바랍니다.</h5>
											<h5>(*)이 작성된 칸은 필수항목 입니다.</h5>
										</td>
									</tr>
									<tr>
										<th>이름(*)</th>
										<td><input type="text" id="stf_nm" name="stf_nm"
											class="form-control" placeholder="이름"></td>
									</tr>
									<tr>
										<th>비밀번호(*)</th>
										<td>
											<div id="stf_pw1_Div">
												<input type="password" id="stf_pw1" class="form-control"
													placeholder="비밀번호"> <span id="stf_pw1_Span"></span>
											</div>
										</td>
									</tr>
									<tr>
										<th>비밀번호 확인(*)</th>
										<td><input type="password" id="stf_pw2"
											class="form-control" placeholder="비밀번호 확인"> <input
											type="hidden" id="stf_pw" name="stf_pw" class="form-control"
											placeholder="비밀번호"></td>
									</tr>
									<tr>
										<th>사원번호(*)</th>
										<td>
											<div class="col-sm-9 col-md-10 leftNoPadding">
												<div id="stf_sq_Div">
													<input type="text" id="stf_sq1" class="form-control"
														placeholder="사원번호"> <span id="stf_sq_Span"></span>
												</div>
											</div>

											<button type="button" id="stfNumSearchBtn"
												class="btn btn-default col-sm-3 col-md-2">중복확인</button> <input
											type="hidden" id="stf_sq" name="stf_sq" class="form-control">
										</td>
									</tr>
									<tr>
										<th>권한(*)</th>
										<td><select id="admn_sq" name="admn_sq"
											class="form-control">
												<c:forEach items="${selectAdmn_Tb}" var="map">
													<option value="${map.ADMN_SQ}">${map.ADMN_PW}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th>부서(*)</th>
										<td><select id="dpt_sq" name="dpt_sq"
											class="form-control">
												<c:forEach items="${selectDpt_Div_Tb}" var="map">
													<option value="${map.DPT_SQ}">${map.DPT_NM}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th>직급(*)</th>
										<td><select id="rnk_sq" name="rnk_sq"
											class="form-control">
												<c:forEach items="${selectRnk_Tb}" var="map">
													<option value="${map.RNK_SQ}">${map.RNK_NM}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th>주소(*)</th>
										<td>

											<div class="col-sm-9 col-md-10 leftNoPadding">
												<input type="text" id="stf_cm_add" name="stf_cm_add"
													class="form-control" placeholder="주소" readonly="readonly">
											</div>
											<button type="button"
												class="btn btn-default col-sm-3 col-md-2"
												onclick="addrSearch();">주소검색</button>
										</td>
									</tr>
									<tr>
										<th>상세주소(*)</th>
										<td><input type="text" id="stf_dt_add" name="stf_dt_add"
											class="form-control" placeholder="상세주소"></td>
									</tr>
									<tr>
										<th>이메일(*)</th>
										<td><input type="email" id="stf_eml" name="stf_eml"
											class="form-control" placeholder="이메일"></td>
									</tr>

									<tr>
										<th>휴대폰(*)</th>
										<td>

											<div class="col-sm-2 col-md-2 leftNoPadding rightNoPadding">
												<select id="phoneNum1" class="form-control">
													<option value="010">010</option>
													<option value="011">011</option>
													<option value="016">016</option>
													<option value="017">017</option>
													<option value="018">018</option>
													<option value="019">019</option>
												</select>
											</div>
											<div
												class="col-sm-1 col-md-1 text-center leftNoPadding rightNoPadding">
												<h5>-</h5>
											</div>
											<div class="col-sm-4 col-md-4 leftNoPadding rightNoPadding">
												<input type="text" id="phoneNum2" class="form-control telNumMax" />
											</div>
											<div
												class="col-sm-1 col-md-1 text-center leftNoPadding rightNoPadding">
												<h5>-</h5>
											</div>
											<div class="col-sm-4 col-md-4 leftNoPadding rightNoPadding">
												<input type="text" id="phoneNum3" class="form-control telNumMax" />
											</div> <input type="hidden" id="stf_ph" name="stf_ph"
											class="form-control">
										</td>
									</tr>
									<tr>
										<th>내선번호</th>
										<td>
											<div class="col-sm-2 col-md-2 leftNoPadding rightNoPadding">
												<input type="text" id="telNum1" class="form-control"
													value="070" readonly="readonly" />
											</div>
											<div
												class="col-sm-1 col-md-1 text-center leftNoPadding rightNoPadding">
												<h5>-</h5>
											</div>
											<div class="col-sm-4 col-md-4 leftNoPadding rightNoPadding">
												<input type="text" id="telNum2" class="form-control telNumMax" />
											</div>
											<div
												class="col-sm-1 col-md-1 text-center leftNoPadding rightNoPadding">
												<h5>-</h5>
											</div>
											<div class="col-sm-4 col-md-4 leftNoPadding rightNoPadding">
												<input type="text" id="telNum3" class="form-control telNumMax" />
											</div> <input type="hidden" id="stf_bs_ph" name="stf_bs_ph"
											class="form-control">
										</td>
									</tr>
									<tr>
										<th>입사일(*)</th>
										<td><input type="date" id="stf_ent" name="stf_ent"
											class="form-control" placeholder="입사일"></td>
									</tr>
								</tbody>
							</form>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" id="officerInsert" class="btn btn-success">등록</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Update Modal -->
		<div class="modal fade" id="updateModal" role="dialog">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">구성원 수정</h4>
					</div>
					<div class="modal-body">

						<table class="tableMiddle table table-striped table-bordered">
							<colgroup>
								<col width="30%" />
								<col width="70%" />
							</colgroup>
							<thead>
							</thead>
							<form id="officerUpdateForm" action="/admin/officerUpdate"
								method="post" enctype="multipart/form-data">
								<tbody>
									<tr>
										<th class="text-center"><img id="imgView_up" class="profileImg"
											src="/resources/img/user.png"> <input type="file"
											id="file_up" name="file" class="form-control"></th>
										<td>
											<h5>이미지는 가로 96px, 세로 128px를 준수 해주시기 바랍니다.</h5>
											<h5>(*)이 작성된 칸은 필수항목 입니다.</h5>
											<h5><strong>프로필 사진</strong>, <strong>비밀번호</strong>, <strong>사원번호</strong>는
											공백일 경우 기존 데이터로 유지됩니다.</h5>
										</td>
									</tr>
									<tr>
										<th>이름(*)</th>
										<td><input type="text" id="stf_nm_up" name="stf_nm"
											class="form-control" placeholder="이름"></td>
									</tr>
									<tr>
										<th>비밀번호(*)</th>
										<td>
											<div id="stf_pw1_Div_up">
												<input type="password" id="stf_pw1_up" class="form-control"
													placeholder="비밀번호"> <span id="stf_pw1_Span_up"></span>
											</div>
										</td>
									</tr>
									<tr>
										<th>비밀번호 확인(*)</th>
										<td><input type="password" id="stf_pw2_up"
											class="form-control" placeholder="비밀번호 확인"> <input
											type="hidden" id="stf_pw_up" name="stf_pw" class="form-control"
											placeholder="비밀번호"></td>
									</tr>
									<tr>
										<th>사원번호(*)</th>
										<td>
											<input
											type="hidden" id="stf_sq_old" name="stf_sq_old" class="form-control">
											<div class="col-sm-9 col-md-10 leftNoPadding">
												<div id="stf_sq_Div_up">
													<input type="text" id="stf_sq1_up" class="form-control"
														placeholder="사원번호"> <span id="stf_sq_Span_up"></span>
												</div>
											</div>

											<button type="button" id="stfNumSearchBtn_up"
												class="btn btn-default col-sm-3 col-md-2">중복확인</button> <input
											type="hidden" id="stf_sq_up" name="stf_sq" class="form-control">
											
										</td>
									</tr>
									<tr>
										<th>권한(*)</th>
										<td><select id="admn_sq_up" name="admn_sq"
											class="form-control">
												<c:forEach items="${selectAdmn_Tb}" var="map">
													<option value="${map.ADMN_SQ}">${map.ADMN_PW}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th>부서(*)</th>
										<td><select id="dpt_sq_up" name="dpt_sq"
											class="form-control">
												<c:forEach items="${selectDpt_Div_Tb}" var="map">
													<option value="${map.DPT_SQ}">${map.DPT_NM}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th>직급(*)</th>
										<td><select id="rnk_sq_up" name="rnk_sq"
											class="form-control">
												<c:forEach items="${selectRnk_Tb}" var="map">
													<option value="${map.RNK_SQ}">${map.RNK_NM}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th>주소(*)</th>
										<td>

											<div class="col-sm-9 col-md-10 leftNoPadding">
												<input type="text" id="stf_cm_add_up" name="stf_cm_add"
													class="form-control" placeholder="주소" readonly="readonly">
											</div>
											<button type="button"
												class="btn btn-default col-sm-3 col-md-2"
												onclick="addrSearch();">주소검색</button>
										</td>
									</tr>
									<tr>
										<th>상세주소(*)</th>
										<td><input type="text" id="stf_dt_add_up" name="stf_dt_add"
											class="form-control" placeholder="상세주소"></td>
									</tr>
									<tr>
										<th>이메일(*)</th>
										<td><input type="email" id="stf_eml_up" name="stf_eml"
											class="form-control" placeholder="이메일"></td>
									</tr>

									<tr>
										<th>휴대폰(*)</th>
										<td>

											<div class="col-sm-2 col-md-2 leftNoPadding rightNoPadding">
												<select id="phoneNum1_up" class="form-control">
													<option value="010">010</option>
													<option value="011">011</option>
													<option value="016">016</option>
													<option value="017">017</option>
													<option value="018">018</option>
													<option value="019">019</option>
												</select>
											</div>
											<div
												class="col-sm-1 col-md-1 text-center leftNoPadding rightNoPadding">
												<h5>-</h5>
											</div>
											<div class="col-sm-4 col-md-4 leftNoPadding rightNoPadding">
												<input type="text" id="phoneNum2_up" class="form-control telNumMax" />
											</div>
											<div
												class="col-sm-1 col-md-1 text-center leftNoPadding rightNoPadding">
												<h5>-</h5>
											</div>
											<div class="col-sm-4 col-md-4 leftNoPadding rightNoPadding">
												<input type="text" id="phoneNum3_up" class="form-control telNumMax" />
											</div> <input type="hidden" id="stf_ph_up" name="stf_ph"
											class="form-control">
										</td>
									</tr>
									<tr>
										<th>내선번호</th>
										<td>
											<div class="col-sm-2 col-md-2 leftNoPadding rightNoPadding">
												<input type="text" id="telNum1_up" class="form-control"
													value="070" readonly="readonly" />
											</div>
											<div
												class="col-sm-1 col-md-1 text-center leftNoPadding rightNoPadding">
												<h5>-</h5>
											</div>
											<div class="col-sm-4 col-md-4 leftNoPadding rightNoPadding">
												<input type="text" id="telNum2_up" class="form-control telNumMax" />
											</div>
											<div
												class="col-sm-1 col-md-1 text-center leftNoPadding rightNoPadding">
												<h5>-</h5>
											</div>
											<div class="col-sm-4 col-md-4 leftNoPadding rightNoPadding">
												<input type="text" id="telNum3_up" class="form-control telNumMax" />
											</div> <input type="hidden" id="stf_bs_ph_up" name="stf_bs_ph"
											class="form-control">
										</td>
									</tr>
									<tr>
										<th>입사일(*)</th>
										<td><input type="date" id="stf_ent_up" name="stf_ent"
											class="form-control" placeholder="입사일"></td>
									</tr>
								</tbody>
							</form>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" id="officerUpdate" class="btn btn-success">수정</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- content 시작 -->
		<div id="content">
			<div id="title">
				<h3>구성원 관리</h3>
				구성원, 조직을 추가하거나 정보를 수정할 수 있습니다.
			</div>

			<div>
				<!-- 좌측매뉴 -->
				<div id="leftMenu" class="col-sm-4 col-md-3">
					<div id="leftTop">
						<h4>
							<label>조직도</label>
						</h4>
						<button type="button" class="btn btn-warning pull-right" data-toggle="modal"
								 data-backdrop="static" data-target="#departmentModal">관리</button>
					</div>
					<div>
						<ul class="easyui-tree">
							<li><span>부서명</span>
								<ul>
									<c:forEach items="${selectDpt_Div_Tb}" var="dptmap">
										<li data-options="state:'closed'"><span>${dptmap.DPT_NM}</span>
											<ul>
												<c:forEach items="${selectStf_tb}" var="stfmap">
													<c:if test="${dptmap.DPT_NM eq stfmap.DPT_NM}">
														<li>[${stfmap.DPT_NM}/${stfmap.RNK_NM}]
															${stfmap.STF_NM}</li>
													</c:if>
												</c:forEach>
											</ul></li>
									</c:forEach>
								</ul></li>
						</ul>
					</div>
				</div>
				<!-- 우측매뉴 -->
				<div id="rightMenu" class="col-sm-14 col-md-9">
					<div class="row">
						<div class="col-sm-7 col-md-5">
							<button type="button" id="officerInsertModal"
								class="btn btn-success" data-toggle="modal" data-backdrop="static"
								data-target="#insertModal">구성원 추가</button>
							<button type="button" id="officerUpdateModal" class="btn btn-warning"  
								data-backdrop="static" data-toggle="modal">구성원 수정</button>
							<!-- <button type="button" class="btn btn-danger">구성원 삭제</button> -->
						</div>
						<div class="col-sm-11 col-md-7 text-right">
							<form id="rightTop" class="form-inline" onsubmit="return false;">
								<!-- <button type="button" id="search" class="btn btn-primary">
									<span class="glyphicon glyphicon-search"></span>
								</button>
								<input id="keyword" type="text" class="form-control"
									placeholder="Search"> -->
								<select id="cate" class="form-control">
									<option value="1">이름</option>
									<option value="2">직급</option>
									<option value="3">부서</option>
								</select>

								<div class="input-group">      <!-- 구성원 검색 -->
									<input type="text" id="keyword" class="form-control"
										placeholder="Search">
									<div class="input-group-btn">
										<button type="button" class="btn btn-default" id="search">
											<i class="glyphicon glyphicon-search"></i>
										</button>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div id="rightBottom">
						<div>
							<label>전체 : </label> <span id="userCount">${officerListCount}</span>명
						</div>
						<div class="table-responsive">
							<table id="officerList" class="tableMiddle table table-hover">
								<colgroup>
									<col width="5%" />
									<col width="96px" />
									<col width="10%" />
									<col width="10%" />
									<col width="10%" />
									<col width="10%" />
									<col width="15%" />
									<col width="15%" />
									<col width="15%" />
								</colgroup>
								<thead>
									<tr class="active">
										<th>선택</th>
										<th>사진</th>
										<th>이름</th>
										<th>직급</th>
										<th>조직</th>
										<th>권한</th>
										<th>핸드폰번호</th>
										<th>내선번호</th>
										<th>이메일</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${officerList}" var="map">
										<tr>
											<td><input type="radio" class="radio"
												value="${map.STF_SQ}"></td>
											<td><img src="${map.STF_PT_RT}" class="profileImg"/></td>
											<td>${map.STF_NM}</td>
											<td>${map.RNK_NM}</td>
											<td>${map.DPT_NM}</td>
											<td>${map.ADMN_PW}</td>
											<td>${map.STF_PH}</td>
											<td>${map.STF_BS_PH}</td>
											<td>${map.STF_EML}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div id="pageIndexList" class="text-center">
							${pageIndexList }
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- content 종료 -->


		<!-- footer 시작 -->
		<div id="footer">
			<c:import url="../import/footer.jsp" />
		</div>
		<!-- footer 종료 -->
	</div>
</body>


</html>
