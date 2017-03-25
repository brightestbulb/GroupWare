<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>보낸편지함</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- 다음 주소 API -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<style type="text/css">

#footer {
	margin-top: 50px;
	background-color: #f2f2f2;
	padding: 25px;
}

#content {
	overflow: hidden;
	width: 55%;
	margin: auto; /* 화면 중앙에 배치*/
}

#title {
	border-bottom: 2px solid #727377;
	
}

#title>*, #leftTop>* {
	display: inline-block;
}

#rightMenu {
	margin-top: 10px;
}

#rightTop{
float:right;
}
form {
	margin-bottom: 10px;
}

</style>

<script type="text/javascript">
	$(document).ready(function() {
		
		
		$(".eml_snd_rd").on("click", function() {

			var eml = $(this).attr("data-value");
			 sndEmailRead(eml);
		});



		function sndEmailRead(eml) {
			var params = {
				eml_sq : eml
			};
			$.ajax({
				url : "/email/emailRead",
				dataType : "json",
				type : "POST",
				contentType: "application/json; charset=UTF-8",
				data : JSON.stringify(params), 
				beforeSend : function() { 

					$("#eml_pl_nm1").val(""); 
					$("#eml_nm1").val(""); 
					$("#rcv_dt1").val(""); 
				},
				success : function(data) { 
					console.log(data);

				 var eml_pl_nm = data.eml_pl_nm;
				 var eml_nm = data.eml_nm;
				 var eml_cnt = data.eml_cnt;
				 var rcv_dt = data.rcv_dt;
				 
				 $("#eml_pl_nm1").text(eml_pl_nm); 
				 $("#eml_nm1").text(eml_nm); 
				 $("#eml_cnt1").text(eml_cnt); 
				 $("#rcv_dt1").text(rcv_dt); 
				},

				error : function(request, status, error) {
					alert("list search fail :: error code: "+ request.status + "\n" + "error message: "+ error + "\n");
				}
			});
		}
		
		// Ajax 페이징 처리
		$(document).on("click", "#pageIndexListAjax > li > a", function() {
			var params = {
					cate : $("#cate").val(),
					keyword : $("#keyword").val(),
					page : $(this).attr("data-page")
				};
			
			sndEmailListSearch(params);
		});
		
		/* 보낸편지함 검색 */
		$("#search").on("click", function() {
			if ($("#keyword").val() == "") {
				alert("검색어를 최소 1글자 이상 입력해주세요.");
				return;
			} else if ($("#keyword").val() != "")
				var params = {
					cate : $("#cate").val(),
					keyword : $("#keyword").val(),
				};
			       sndEmailListSearch(params);
		});
	});
	
	/* 보낸편지함 검색 */
	function sndEmailListSearch(params) {
		$.ajax({
			url : "/email/sndEmailListSearch",
			type : "POST",
			dataType : "json",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
				$("#sndList > tbody").empty();
				$("#pageIndexList").empty();
			},
			success : function(data) {
			   
				var sndList = data.sndList;
				var pageIndexListAjax = data.pageIndexListAjax;
				var tbody = $("#sndList > tbody");

				$.each(sndList, function(idx, val) {
					tbody.append($('<tr>').append($('<td>',	{text : val.snd_eml_sq}))
										  .append($('<td>',	{html : "<a href='#' data-toggle='modal' data-target='#readModal'>"+val.eml_nm+"</a>"}))
										  .append($('<td>',	{text : val.stf_nm}))
										  .append($('<td>',	{text : val.snd_dt})));
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
		<div id="content">
			<div id="title">
				<h3>보낸편지함</h3>
			</div>
			<div>
				<div id="rightMenu" class="col-md-13">
			      <div class="form-inline">
                  
                  
                    <form id="searchBox" class="form-inline" onsubmit="return false;">
						<select id="cate" class="form-control">
									<option value="1">작성자</option>
									<option value="2">제목</option>
								</select>

								<div class="input-group">
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
					<div class="row">
						
						<div class="table-responsive">
							<table id="sndList" class="table table-hover">
								<colgroup>
									<col width="30px" />
									<col width="250px" />
									<col width="60px" />
									<col width="100px" />
								</colgroup>
								<thead>
									<tr class="active">
								     	<th style="padding-left:18px;">번호</th>
										<th>제목</th>
										<th>이름</th>
										<th>발송시간</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${sndList}" var="emailVO">
										<tr class="eml_snd_rd" data-value="${emailVO.eml_sq}" valign="middle">
										 	<td>${emailVO.eml_sq}</td> 
											<td><a href="#" data-toggle="modal" data-target="#readModal">${emailVO.eml_nm}</a></td>
											<td>${emailVO.stf_nm}</td>
											<td>${emailVO.snd_dt}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					  <div id="pageIndexList" class="text-center">
						${pageIndexList}
					</div>
				</div>
			</div>
		</div>
		<!-- content 종료 -->
		
		<!-- 조회모달 시작 -->

		<div class="modal fade" id="readModal" role="dialog">

			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">메일조회창</h4>
					</div>
					<div class="modal-body">

						<table class="table table-striped table-bordered">
							<colgroup>
								<col width="30%" />
								<col width="70%" />
							</colgroup>

							<tbody>
								<tr>
									<th>파일이름</th>
									<td id="eml_pl_nm1">${emailVO.eml_pl_nm}</td>
								</tr>
								<tr>
									<th>제목</th>
									<td id="eml_nm1">${emailVO.eml_nm}</td>
								</tr>
								<tr>
									<th>내용</th>
									<td><textarea id="eml_cnt1" readonly="readonly" rows="10" style=width:100%>${emailVO.eml_cnt}</textarea></td>
								</tr>
								<tr>
									<th>받은시간</th>
									<td id="rcv_dt1"><fmt:formatDate
											pattern="yyyy-MM-dd HH:mm" value="${emailVO.rcv_dt}" /></td>
								</tr>

							</tbody>
						</table>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success" data-dismiss="modal">확인</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 조회모달 종료 -->


		<!-- footer 시작 -->
		<div id="footer">
			<c:import url="../import/footer.jsp" />
		</div>
		<!-- footer 종료 -->
	</div>
</body>
</html>
