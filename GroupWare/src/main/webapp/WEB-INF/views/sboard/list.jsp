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
/* Remove the navbar's default rounded borders and increase the bottom margin */
.box {
	width: 65%;
	margin: auto; /* 화면 중앙에 배치*/
}

#title {
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
	
		
		
		// Ajax 페이징 처리
		$(document).on("click", "#pageIndexListAjax > li > a", function() {
			var params = {
					cate : $("#cate").val(),
					keyword : $("#keyword").val(),
					page : $(this).attr("data-page"),
					ntc_div_sq : $('#ntc_div_sq').val()
				};
			
			boardListSearch(params);
		});
		
		/* 게시물 검색 */
		$("#search").on("click", function() {
			if ($("#keyword").val() == "") {
				alert("검색어를 최소 1글자 이상 입력해주세요.");
				return;
			} else if ($("#keyword").val() != "")
				var params = {
					cate : $("#cate").val(),
					keyword : $("#keyword").val(),
					ntc_div_sq : $("#ntc_div_sq").val()
				};
			
				boardListSearch(params);
		});
		
	});
	/* 게시물 검색 */
	function boardListSearch(params) {
		$.ajax({
			url : "/sboard/boardListSearch",
			type : "POST",
			dataType : "json",
			data : JSON.stringify(params),
			contentType : "application/json; charset=UTF-8",
			beforeSend : function() {
		    	$("#list > tbody").empty();
		    	$("#pageIndexList").empty();
		    	
		
		    },
		
			success : function(data) {
	
				console.log(params);
				var boardList = data.boardList;
				var pageIndexListAjax = data.pageIndexListAjax;
	
				var tbody = $("#list > tbody");

				$.each(boardList,	function(idx, val) {
					tbody.append($('<tr>').append($('<td>',	{text : val.ntc_sq}))
										  .append($('<td>',	{html : "<a href='/sboard/read?ntc_sq="+val.ntc_sq+"'>"+val.ntc_nm+"</a>"}))
										  .append($('<td>',	{text : val.stf_nm}))
										  .append($('<td>',	{text : val.ntc_dt})) 
										  .append($('<td>',	{html : "<span class="+"badge bg-red"+">"+val.ntc_hits+"</span>"})));
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


				<div class="box">
					<!-- <h3 class="box-title">게시판</h3>  -->
				<div id="title">
				<h3>
				   <c:choose>
				      <c:when test="${list[0].ntc_div_sq == 1}">자유게시판</c:when>
				      <c:when test="${list[0].ntc_div_sq == 2}">사내동호회</c:when>
				      <c:when test="${list[0].ntc_div_sq == 3}">중고장터</c:when>
				   </c:choose>
				</h3>
				</div>	
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
						<button type="button" class="btn btn-primary" id='newBtn'>새글등록</button>
					</div>
					<div class="box-body">
                     <input type='hidden' value="Count">
                     <input type='hidden' name='ntc_div_sq' id="ntc_div_sq" value="${list[0].ntc_div_sq}">
						<table id ="list" class="table table-hover">
							<thead>
								<tr>
									<th style="width: 50px">번호</th>
									<th>제목</th>
									<th style="width: 120px">작성자</th>
									<th style="width: 200px">작성일</th>
									<th style="width: 80px">조회수</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="boardVO">
                                  <%-- <input type='text' name='ntc_div_sq' id="ntc_div_sq" value="${boardVO.ntc_div_sq}"> --%>
									<tr>
										<td>${boardVO.ntc_sq}</td>
										<td><a href='/sboard/read?ntc_sq=${boardVO.ntc_sq}'>${boardVO.ntc_nm}</a></td>
										<td>${boardVO.stf_nm}</td>
										<td>${boardVO.ntc_dt}</td>
										<td><span class="badge bg-red">${boardVO.ntc_hits}</span></td>
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
	} else if (result == "test") {
		alert("관리자만 접근할 수 있습니다.");
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

