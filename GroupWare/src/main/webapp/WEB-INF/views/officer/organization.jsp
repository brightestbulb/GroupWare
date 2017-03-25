<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

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

/*  */
#title {
	border-bottom: 2px solid #727377;
}

#rightMenu {
	margin-top: 10px;
}

.profileImg {
	height: 128px;
	width: 96px;
}

#officerList > thead > tr > th, #officerList > tbody > tr > td {
	text-align: center;
}
</style>

<script type="text/javascript">
	
	$(document).ready(function() {
		// Ajax 페이징 처리
		$(document).on("click", "#pageIndexListAjax > li > a", function() {
			var params = {
					navStfNm : $("#navStfNm").val(),
					page : $(this).attr("data-page")
				};
			
			officerListSearch(params);
		});
		
		/* 사원 검색 */
		function officerListSearch(params) {
			$.ajax({
				url : "/officer/organizationAjax",
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
						tbody.append($('<tr>').append($('<td>',	{html : "<img src='"+val.STF_PT_RT+"' class='profileImg'/>"}))
											  .append($('<td>',	{text : val.STF_NM}))
											  .append($('<td>',	{text : val.RNK_NM}))
											  .append($('<td>',	{text : val.DPT_NM}))
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
	});
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


		<!-- content 시작 -->
		<div id="content">
			<div id="title">
				<h3>조직도</h3>
				사내 조직 및 임직원을 확인할 수 있습니다.
			</div>

			<div>
				<!-- 좌측매뉴 -->
				<div id="leftMenu" class="col-sm-4 col-md-3">
					<div id="leftTop">
						<h4>
							<label>조직도</label>
						</h4>
					</div>
					<div>
						<ul class="easyui-tree">
							<li><span>부서명</span>
								<ul>
									<c:forEach items="${selectDpt_Div_Tb}" var="dptmap">
										<li data-options="state:'closed'"><span>${dptmap.DPT_NM}</span>
											<ul>
												<c:forEach items="${selectOrganization}" var="stfmap">
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
					<div id="rightBottom">
						<div>
							<label>전체 : </label> <span id="userCount">${officerListCount}</span>명
							<input type="hidden" id="navStfNm" value="${navStfNm }"/>
						</div>
						<div class="table-responsive">
							<table id="officerList" class="tableMiddle table table-hover">
								<colgroup>
									<col width="96PX" />
									<col width="15%" />
									<col width="15%" />
									<col width="15%" />
									<col width="15%" />
									<col width="15%" />
									<col width="15%" />
								</colgroup>
								<thead>
									<tr class="active">
										<th class="text-center">사진</th>
										<th class="text-center">이름</th>
										<th class="text-center">직급</th>
										<th class="text-center">조직</th>
										<th class="text-center">핸드폰번호</th>
										<th class="text-center">내선번호</th>
										<th class="text-center">이메일</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${officerList}" var="map">
										<tr>
											<td class="text-center"><img src="${map.STF_PT_RT}" class="profileImg"/></td>
											<td class="text-center">${map.STF_NM}</td>
											<td class="text-center">${map.RNK_NM}</td>
											<td class="text-center">${map.DPT_NM}</td>
											<td class="text-center">${map.STF_PH}</td>
											<td class="text-center">${map.STF_BS_PH}</td>
											<td class="text-center">${map.STF_EML}</td>
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
