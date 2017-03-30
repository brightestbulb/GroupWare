<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>자료조회</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style type="text/css">
/* Remove the navbar's default rounded borders and increase the bottom margin */
.box {
	width: 50%;
	margin: auto; /* 화면 중앙에 배치*/
}

.box-body-tb {
	font-size: 120%
}

#bfooter {
	float: right;
}

#rplyb {
	float: right;
}
</style>


<script type="text/javascript">
	$(document).ready(function() {

		var formObj = $("form[role='form']");
		
		console.log(formObj);
		
		$("#modifyBtn").on("click", function(){
			formObj.attr("action", "/data/modifyPage");
			formObj.attr("method", "get");		
			formObj.submit();
		});
		
		$("#removeBtn").on("click", function(){
			formObj.attr("action", "/data/removePage");
			formObj.submit();
		});
		
		$("#goListBtn").on("click", function(){
			formObj.attr("method", "get");
			formObj.attr("action", "/data/dataList");
			formObj.submit();
		});
	

		//댓글 추가
		$("#replyAddBtn").on("click", function() {

			var data_sq = $(this).attr("data-value");
			var stf_sq = $("#newReplyWriter").val();
			var dt_rpy_cnt = $("#newReplyText").val();

			var params = {
				data_sq : data_sq,
				stf_sq : stf_sq,
				dt_rpy_cnt : dt_rpy_cnt
			}

			console.log(params);

			$.ajax({
				url : "/dataReplies/register",
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
				dt_rpy_sq : rpy
			};

			console.log(params);   //object 값

			$.ajax({
				url : "/dataReplies/replyMod",
				type : "POST",
				dataType : "json",
				data : JSON.stringify(params),
				contentType : "application/json; charset=UTF-8",

				success : function(data) {
					
					
					var dt_rpy_sq = data.dt_rpy_sq;
			    	var dt_rpy_cnt = data.dt_rpy_cnt;
			    	
			    	$("#"+dt_rpy_sq).html("<textarea id=rpy_cnt1 style=width:100% rows=3>"+dt_rpy_cnt+
			    		"</textarea><br></br><button id=replyModFNS data-value="+dt_rpy_sq+">수정완료</button><button id=replyCelBtn>취소</button>");
			    	
				},
				 error: function(request, status, error) {
				    	alert("list search fail :: error code: " + request.status + "\n" + "error message: " + error + "\n");
				 }  	});
		}

		
		//댓글 수정완료
		$(document).on("click","#replyModFNS", function() {

			var dt_rpy_sq = $(this).attr("data-value");
			replyModOk(dt_rpy_sq);
		});

		function replyModOk(dt_rpy_sq) {

			var params = {
				dt_rpy_sq : dt_rpy_sq,
				dt_rpy_cnt : $("#rpy_cnt1").val()
				
			};

			console.log(params);   //object 값

			$.ajax({
				url : "/dataReplies/replyUpdate",
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
				dt_rpy_sq : rpy
			}

			console.log(params);

			$.ajax({
				url : "/dataReplies/delete",
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

	})
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

		<div class="content">
			<div class="row">
				<!-- 조회시작 -->
				<div class="col-md-12">
					<!-- general form elements -->
					<div class='box'>
						<div class='box-body'></div>
					</div>

					<div class="box box-primary">
						
						<!-- /.box-header -->
						<form role="form" action="modifyPage" method="post">
							<!-- form 태그 내에서 data_sq 정보를 가지도록 작업 -->
							<input type='hidden' id ="data_sq1" name='data_sq' value="${dataVO.data_sq}">
						</form>

						<div class="box-body-tb">
							<table class="table">
								<tr>
									<th colspan="2" width="300px">${dataVO.data_nm}</th>
									<span id="datadt"><td width="180px">${dataVO.data_dt}</span>
									</td>


								</tr>
								<tr>
									<td width="100px">${dataVO.stf_nm}</td>
									<td colspan="4" align="right"><a href="${dataVO.data_crs}"download>${dataVO.data_pl_nm}</a></td>
								</tr>
								<tr>
									<td colspan="4" height="350px">${dataVO.data_cnt}</td>
								</tr>
							</table>
						</div>
						<!-- /.box-body -->

						<div class="bfooter">
							<button type="submit" class="btn btn-warning" id="modifyBtn">수정</button>
							<button type="submit" class="btn btn-danger" id="removeBtn">삭제</button>
							<button type="submit" class="btn btn-primary" id="goListBtn">목록</button>
						</div>
					</div>

				</div>
				<!--조회 종료 -->

				<div>&nbsp;</div>
				<!-- 공백주기 -->
				<div>&nbsp;</div>
				<!-- 공백주기 -->

				<!-- 댓글등록 폼 -->
				<div id="replybox">

					<div class="box box-primary" id="replybox">
						<div class="box-header">
							<h3 class="box-title">리플</h3>
						</div>
						<div class="box-body">
							<!-- <label for="exampleInputEmail1">사원번호</label> <input
								class="form-control" type="text" placeholder="USER ID"
								id="newReplyWriter"> -->
							<textarea class="form-control" rows="3" placeholder="댓글 내용을 작성해 주세요"
								id="newReplyText"></textarea>
						</div>
						<div class="box-footer">
							<button type="button" class="btn btn-primary" id="replyAddBtn"
								data-value="${dataVO.data_sq}">리플 추가</button>
						</div>
					</div>
				</div>
				<div>&nbsp;</div>
				<!-- 공백주기 -->
				<div>&nbsp;</div>
				<!-- 공백주기 -->


                <!-- 댓글리스트  -->
				<div class="box">
					<table class="table">
						<th bgcolor="#dcdcdc"><h4>댓글</h4></th>
						<c:forEach items="${list}" var="DataReplyVO">
							<tr class="rpy_ck" data-value="${DataReplyVO.dt_rpy_sq}">
								<td style="width: 20px"><span style= "font-size:1.3em">${DataReplyVO.stf_nm}&nbsp;&nbsp;&nbsp;
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${DataReplyVO.dt_rpy_mod}" /></span>
									<br></br>
									<div id="${DataReplyVO.dt_rpy_sq}">${DataReplyVO.dt_rpy_cnt}
									<c:set var="stf_sq" value="${stf_sq}"/>
								<c:set var="wt_stf_sq" value="${DataReplyVO.stf_sq}"/>
								  <c:if test ="${stf_sq eq wt_stf_sq}">
									<div id="rplyb">
										<button class="replyModBtn" 
											data-value="${DataReplyVO.dt_rpy_sq}">수정</button>
										<button class="replyModBtn" 
											data-value="${DataReplyVO.dt_rpy_sq}">삭제</button>
								    </div>
								    </c:if>
								    </div>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<!-- 댓글리스트 끝 -->
				
				
			</div>
		</div>
		<!-- /.content -->

	</div>
</body>
</html>
