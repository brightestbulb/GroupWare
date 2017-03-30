<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>게시판</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style type="text/css">
.box {
	width: 50%;
	margin: auto; /* 화면 중앙에 배치*/
}

.box-body-tb {
	font-size: 120%;
}

.rplyb {
	float: right;
}

#ntcdt {
	float: right;
}

#bfooter {
	float: right;
}



</style>

<script>
	$(document).ready(function() {
	

		var formObj = $("form[role='form']"); //formObj는 위에 선언된 form 태그를 의미
		//여러 작업을하려면 현재의 조회페이지에서 수정할 수 있는 화면으로 이동해야 하기 때문에
		console.log(formObj); // 위의 form 태그의 현재 페이지 정보(data_sq 등 )를 가지고 태그의 속성을 수정,전송

		$("#modifyBtn").on("click", function() {
			formObj.attr("action", "/sboard/modifyPage");
			formObj.attr("method", "get");
			formObj.submit();
		});

		$("#removeBtn").on("click", function() {
			formObj.attr("action", "/sboard/removePage");
			formObj.submit();
		});

		$("#goListBtn").on("click", function() {
			formObj.attr("method", "get");
			formObj.attr("action", "/sboard/list");
			formObj.submit();
		});
	

	//댓글 추가
	$("#replyAddBtn").on("click", function() {

		var ntc_sq = $(this).attr("data-value");
		var stf_sq = $("#newReplyWriter").val();
		var rpy_cnt = $("#newReplyText").val();

		var params = {
			ntc_sq : ntc_sq,
			stf_sq : stf_sq,
			rpy_cnt : rpy_cnt
		}

		console.log(params);

		$.ajax({
			url : "/ntcReplies/register",
			type : "POST",
			dataType : "text",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",
			success : function(data) {
				console.log(data);

				alert("등록 되었습니다.");
				window.location.reload();

			}
		});
	});

	
	//댓글 수정 조회
	$(".replyModBtn").on("click", function() {
		
		var rpy = $(this).attr("data-value");
		$(".div3").val(rpy);
		replyRead(rpy);
		
		
	});

	function replyRead(rpy) {

		var params = {
			rpy_sq : rpy
		};

		console.log(params);   //object 값

		$.ajax({
			url : "/ntcReplies/replyMod",
			type : "POST",
			dataType : "json",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",

			success : function(data) {
				
			var rpy_sq = data.rpy_sq;
			var rpy_cnt = data.rpy_cnt;
			$("#"+rpy_sq).html("<textarea id=rpy_cnt1 style=width:100% rows=3>"+rpy_cnt+
					"</textarea><br></br><button id=replyModFNS data-value="+rpy_sq+">수정완료</button><button id=replyCelBtn>취소</button>");
			
			},
			 error: function(request, status, error) {
			    	alert("list search fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
			 }  	});
	}

	
	//댓글 수정완료
	$(document).on("click","#replyModFNS", function() {

		var rpy_sq = $(this).attr("data-value");
		replyModOk(rpy_sq);
	});

	function replyModOk(rpy_sq) {

		var params = {
			rpy_sq : rpy_sq,
			rpy_cnt : $("#rpy_cnt1").val()
			
		};

		console.log(params);   //object 값

		$.ajax({
			url : "/ntcReplies/replyUpdate",
			type : "POST",
			dataType : "text",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",

			success : function(result) {
				if(result=='SUCCESS'){
			    	alert("수정이 완료되었습니다");
			    	window.location.reload();
			}
			}
			});
	}

	$(document).on("click","#replyCelBtn", function() {   //댓글수정 취소버튼

		window.location.reload();
	});
	
	
	//댓글삭제
	$(".replyDelBtn").on("click", function() {
		
		var rpy = $(this).attr("data-value");
		$(".div3").val(rpy);

		replyRemove(rpy);

	});

	function replyRemove(rpy) {
		var params = {
			rpy_sq : rpy
		}

		console.log(params);

		$.ajax({
			url : "/ntcReplies/delete",
			type : "POST",
			dataType : "text",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",

			success : function(data) {
				console.log(data);

				alert("삭제 되었습니다.");
				window.location.reload();

			}
		});
	}
	});
</script>

</head>
<body>
	<div id="wrap">
		<!-- header 시작 -->
		<div>
			<c:import url="../import/header_officer.jsp" />
		</div>
		<!-- header 종료 -->


		<!-- nav 시작 -->
		<div>
			<c:import url="../import/nav.jsp" />
		</div>
		<!-- nav 종료 -->



		<!-- content 시작 -->
		<section class="content">
			<div class="row">
				<!-- left column -->
				<div class="col-md-12">
					<!-- general form elements -->
					<div class='box'>

						<div class='box-body'></div>
					</div>

					<div class="box box-primary">
						<div class="box-header">
							<h3 class="box-title"></h3>
						</div>
						<!-- /.box-header -->

                        <!-- 게시판 조회 -->
						<form role="form" action="modifyPage" method="post">
							<!-- form 태그 내에서 ntc_sq,ntc_div_sq 정보를 가지도록 작업 -->

							<input type='hidden' name='ntc_sq' value="${boardVO.ntc_sq}">
							<input type='hidden' name='ntc_div_sq' value="${boardVO.ntc_div_sq}">
						</form>

						<div class="box-body-tb">
							<table class="table">

								<tr>
									<th colspan="2" width="300px" align="center">${boardVO.ntc_nm}</th>
								</tr>
								<tr>
									<td width="100px">${boardVO.stf_nm}<span id="ntcdt">${boardVO.ntc_dt}</span></td>
								</tr>
								<tr>
									<td colspan="2" height="350px">${boardVO.ntc_cnt}</td>
								</tr>
							</table>
						</div>
						<!-- /.box-body -->

						<div id="bfooter">

							<button type="submit" class="btn btn-warning" id="modifyBtn">수정</button>
							<button type="submit" class="btn btn-danger" id="removeBtn">삭제</button>
							<button type="submit" class="btn btn-primary" id="goListBtn">목록</button>
						</div>

                        <!-- 게시글 조회 끝  -->

					</div>
					<!-- /.box -->
				</div>

				<div>&nbsp;</div>
				<!-- 공백주기 -->
				<div>&nbsp;</div>
				<!-- 공백주기 -->

				<!-- 리플추가 폼 -->
				<div id="replybox">

					<div class="box box-primary" id="replybox">
						<div class="box-header">
							 <h3 class="box-title">리플</h3> 
						</div>
						<div class="box-body">
							<textarea class="form-control" rows="3" placeholder="리플을 작성해 주세요"
								id="newReplyText"></textarea>
						</div>
						<!-- /.box-body -->
						<div class="box-footer">
							<button type="button" class="btn btn-primary" id="replyAddBtn"
								data-value="${boardVO.ntc_sq}">리플 추가</button>
						</div>
					</div>
				</div>
				<div>&nbsp;</div>
				<!-- 공백주기 -->
				<div>&nbsp;</div>
				<!-- 공백주기 -->


				<!-- 댓글 리스트  -->
				<div class="box">	
					<table class="table">
						<th bgcolor="#dcdcdc"><h4>댓글</h4></th>
						<c:forEach items="${list}" var="NtcReplyVO">
							<tr class="rpy_ck" data-value="${NtcReplyVO.rpy_sq}"> 
								<td style="width: 20px">
								<span style= "font-size:1.3em">${NtcReplyVO.stf_nm}&nbsp;&nbsp;&nbsp;
										<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${NtcReplyVO.rpy_mod}" /></span>
										<br></br>
										<div id="${NtcReplyVO.rpy_sq}">${NtcReplyVO.rpy_cnt}
								
								<c:set var="stf_sq" value="${stf_sq}"/>
								<c:set var="wt_stf_sq" value="${NtcReplyVO.stf_sq}"/>
								  <c:if test ="${stf_sq eq wt_stf_sq}">
								  <div class="rplyb"> 
										<button  class="replyModBtn" data-value="${NtcReplyVO.rpy_sq}">수정</button>
										<button  class="replyDelBtn" data-value="${NtcReplyVO.rpy_sq}">삭제</button>
									</div>
								  </c:if> 
									
								 </div>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<!-- 댓글 리스트 끝 -->
			</div>
		</section>
		<!-- /.content -->
	</div>
</body>
</html>