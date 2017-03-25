<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>자료실</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style type="text/css">
/* Remove the navbar's default rounded borders and increase the bottom margin */
.box {
	width: 65%;
	margin: auto; /* 화면 중앙에 배치*/
}

.box-title {
	margin: auto;
	text-align: center;
}

.newBtn {
	float: right;
}

.regButton {
	float: right;
}
</style>
<script type="text/javascript">
	
	$(document).ready(function() {
		/* 자료실 검색 */
		$("#search").on("click", function() {
			if ($("#keyword").val() == "") {
				alert("검색어를 최소 1글자 이상 입력해주세요.");
				return;
			} else if ($("#keyword").val() != "")
				var params = {
					cate : $("#cate").val(),
					keyword : $("#keyword").val(),
				
				};
				dataListSearch(params);
		});
	});
	
	// Ajax 페이징 처리
	$(document).on("click", "#pageIndexListAjax > li > a", function() {
		var params = {
				cate : $("#cate").val(),
				keyword : $("#keyword").val(),
				page : $(this).attr("data-page")
			};
		
		dataListSearch(params);
	});
	
	/* 지료실 검색 */
	function dataListSearch(params) {
		$.ajax({
			url : "/data/dataListSearch",
			type : "POST",
			dataType : "json",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
		    	$("#list > tbody").empty();
		    	$("#pageIndexList").empty();
		    },
		
			success : function(data) {
			   
				var dataList = data.dataList;
				var pageIndexListAjax = data.pageIndexListAjax;
	
				var tbody = $("#list > tbody");

				$.each(dataList, function(idx, val) {
					tbody.append($('<tr>').append($('<td>',	{text : val.data_sq}))
										  .append($('<td>',	{html : "<a href='/data/read?data_sq="+val.data_sq+"'>"+val.data_nm+"</a>"}))
										  .append($('<td>',	{text : val.stf_nm}))
										  .append($('<td>',	{text : val.data_dt})) 
										  .append($('<td>',	{html : "<span class="+"badge bg-red"+">"+val.data_hits+"</span>"})));
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


		<div class="content"></div>
		<div class="row">
			<!-- left column -->


			<div class="col-md-12">

				<div class='box'></div>

				<div class="box">
					<h3 class="box-title">자료실</h3>
					<div id ="searchBox" class="col-sm-11 col-md-11 text-right">
							<form id="rightTop" class="form-inline" onsubmit="return false;">
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
					<div class="regButton">
						<button type="button" class="btn btn-primary" id='newBtn'>자료등록</button>
					</div>
					<div class="box-body">

						<table id ="list" class="table table-striped">
						<thead>
							<tr>
								<th style="width: 55px">번호</th>
								<th>제목</th>
								<th style="width: 120px">작성자</th>
								<th style="width: 200px">작성일</th>
								<th style="width: 80px">조회수</th>
							</tr>
                       </thead>
                       <tbody>
							<c:forEach items="${list}" var="dataVO">

								<tr>
									<td>${dataVO.data_sq}</td>
									<td><a href='/data/read?data_sq=${dataVO.data_sq}'>${dataVO.data_nm}</a></td>
									<td>${dataVO.stf_nm}</td>
									<td>${dataVO.data_dt}</td>
									<td><span class="badge bg-red">${dataVO.data_hits}</span></td>
								</tr>

							</c:forEach>
                      </tbody>
						</table>

					</div>
				 	<div id="pageIndexList" class="text-center">
						${pageIndexList}
					</div>

				</div>
				<!-- /.box-footer-->
			</div>
		</div>


		<!-- footer 시작 -->
		<div id="footer">
			<c:import url="../import/footer.jsp" />
		</div>
		<!-- footer 종료 -->
	</div>
</body>


<script>
	var result = '${msg}';

	if (result == 'SUCCESS') {
		alert("처리가 완료되었습니다.");
	}
</script>

<script>
	$(document).ready(function() {

		$('#newBtn').on("click", function() {

			self.location = "register";

		});

	});
</script>


</html>

