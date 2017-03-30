<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>결재</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style type="text/css">
/* Remove the navbar's default rounded borders and increase the bottom margin */
.box {
	width: 55%;
	margin: auto; /* 화면 중앙에 배치*/
}

#searchBox {
	margin-bottom: 10px;
}
</style>

<script type="text/javascript">
	$(document).ready(
			function() {

				$("#okbutton").click(function() {

					$("#frm").submit();
				});

				$(".apv_sq_rd").on("click", function() {
					var apv = $(this).attr("data-value"); //attr도 map과 같은 구조인데 키값만 넣으면 그 값을 갖고 오라는 뜻

					$("#apv").val(apv);
					approvalRead(apv);
				});

				//결재 조회
				function approvalRead(apv) {

					var params = {
						apv_sq : apv
					};

					$.ajax({
						url : "/approval/approvalRead",
						type : "POST",
						dataType : "json",
						data : JSON.stringify(params),
						contentType : "application/json; charset=UTF-8",
						beforeSend : function() {
							$("#apv_pl_nm1").empty();
							$("#apv_nm1").empty();
							$("#div_apv_nm").empty();
							$("#stf_nm").empty();
							$("#mid_nm").empty();
							$("#apv_ok").empty();
						},
						success : function(data) {
							console.log(data);

							var apv_pl_nm = data.apv_pl_nm;
							console.log(apv_pl_nm);
							var apv_nm = data.apv_nm;
							console.log(apv_nm);
							var apv_ok = data.apv_ok;
							console.log(apv_ok);
							var div_apv_nm = data.div_apv_nm;
							console.log(div_apv_nm);
							var stf_nm = data.stf_nm;
							console.log(stf_nm);
							var mid_nm = data.mid_nm;
							console.log(mid_nm);
							var stf_admn_sq = data.stf_admn_sq;
							console.log(stf_admn_sq);
							var apv_pl_rt = data.apv_pl_rt;
							console.log(apv_pl_rt);

							$("#apv_pl_nm1").text(apv_pl_nm);
							$("#apv_nm1").text(apv_nm);
							$("#div_apv_nm").text(div_apv_nm);
							$("#stf_nm").text(stf_nm);
							$("#mid_nm").text(mid_nm);
							$("#apv_ok").text(apv_ok);
					  	    $("#apv_pl_rt1").html('<iframe style="float: right;" src = "/resources/ViewerJS/#../file/'+ apv_pl_rt + '" width="530" height="300" allowfullscreen webkitallowfullscreen></iframe>')

							if (stf_admn_sq == '1') {
								$("#apv_ok_sq1").hide();
							} else if (stf_admn_sq == '2') {
								$("#fnl_admn").remove();
							}
							 else if(stf_admn_sq == '4'){
								 console.log(stf_admn_sq);
								$("#mid_admn").remove();
							}

						},
						error : function(request, status, error) {
							alert("list search fail :: error code: "
									+ request.status + "\n" + "error message: "
									+ error + "\n");
						}
					});
				}

				//중간승인자의 승인부분 
				$("#apv_sq_md").on("click", function() {

					var apv_sq = $("#apv").val();

					approvalOk(apv_sq);
				});

				function approvalOk(apv_sq) {

					var params = {
						apv_sq : apv_sq,
						apv_ok_sq : $("#apv_ok_sq1").val()
					};

					$.ajax({
						url : "/approval/approvalOk",
						type : "POST",
						dataType : "text",
						data : JSON.stringify(params),
						contentType : "application/json; charset=UTF-8",
						success : function(result) {
							if (result == 'SUCCESS') {
								alert("처리되었습니다");
								window.location.reload();

							}
						}
					});
				}
				// Ajax 페이징 처리
				$(document).on("click", "#pageIndexListAjax > li > a",
						function() {
							var params = {
								cate : $("#cate").val(),
								keyword : $("#keyword").val(),
								page : $(this).attr("data-page"),
								div_apv_sq : $("#div_apv_sq").val()
							};

							apvListSearch(params);
						});
				/* 게시물 검색 */
				$("#search").on("click", function() {
					if ($("#keyword").val() == "") {
						alert("검색어를 최소 1글자 이상 입력해주세요.");
					} else if ($("#keyword").val() != "")
						var params = {
							cate : $("#cate").val(),
							keyword : $("#keyword").val(),
							div_apv_sq : $("#div_apv_sq").val()
						};
					apvListSearch(params);
				});
			});

	/* 결재 검색 */
	function apvListSearch(params) {
		$
				.ajax({
					url : "/approval/apvListSearch",
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
						var apvList = data.apvList;
						var pageIndexListAjax = data.pageIndexListAjax;

						var tbody = $("#list > tbody");

						$
								.each(
										apvList,
										function(idx, val) {
											tbody
													.append($('<tr>')
															.append(
																	$(
																			'<td>',
																			{
																				text : val.apv_sq
																			}))
															.append(
																	$(
																			'<td>',
																			{
																				html : "<a href='#' class='apv_sq_rd' data-value="+val.apv_sq+"' data-toggle='modal' data-target='#readModal' >"
																						+ val.apv_nm
																						+ "</a>"
																			}))
															.append(
																	$(
																			'<td>',
																			{
																				text : val.stf_nm
																			}))
															.append(
																	$(
																			'<td>',
																			{
																				text : val.div_apv_nm
																			}))
															.append(
																	$(
																			'<td>',
																			{
																				text : val.apv_ok
																			})));
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


		<!--결재 모달창 시작 -->

		<div class="modal fade" id="insertModal" role="dialog">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">결재등록창</h4>
					</div>

					<div class="modal-body">

						<form role="form" id="frm" method="post" enctype="multipart/form-data"
							action="/approval/register">
							<table class="table table-striped table-bordered">

								<colgroup>
									<col width="30%" />
									<col width="70%" />
								</colgroup>

								<tbody>
									<tr>
										<th>제목(*)</th>
										<td><input type="text" id="apv_nm" name="apv_nm"
											class="form-control" placeholder="제목"></td>
									</tr>
									<tr>
										<th>결재구분(*)</th>
										<td><select id="div_apv_sq" class="form-control"
											name="div_apv_sq">
												<option value="1">업무</option>
												<option value="2">파견</option>
												<option value="3">경비지출</option>
												<option value="4">초과근무</option>
												<option value="5">휴가</option>
										</select></td>
									</tr>
									<tr>
										<th>중간승인자(*)</th>
										<td><input type="text" id="stf_mid_sq" name="stf_mid_sq"
											class="form-control" placeholder="중간승인자 사원번호입력"></td>
									</tr>
								<!-- 	<tr>
										<th>결재파일 이름</th>
										<td><input type="text" id="apv_pl_nm" name="apv_pl_nm"
											class="form-control"></td>
									</tr>  -->
									<tr>
										<th>결재파일 업로드</th>
										<td><input type="file" id="apv_pl_rt" name="file"
											class="form-control"></td>
									</tr>
								</tbody>

							</table>
						</form>
					</div>

					<div class="modal-footer">
						<button type="button" id="okbutton" class="btn btn-primary"
							data-dismiss="modal">입력</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
					</div>

				</div>

			</div>

		</div>

		<!--결재모달창 끝  -->

		<!--결재리스트 목록 시작-->

		<div id="content">
			<div class="col-md-13">
				<div class="box">
					<!-- <form id="rightTop" class="form-inline"> -->
					<div id="searchBox" class="col-sm-11 col-md-11 text-right">
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

					<button type="button" id="officerInsert" class="btn btn-success"
						data-toggle="modal" data-target="#insertModal">결재등록</button>
					<!-- </form> -->
					<div class="box-body">
						<div>
							<table id="list" class="table table-hover">
								<colgroup>
									<col width="40px" />
									<col width="150px" />
									<col width="60px" />
									<col width="60px" />
									<col width="40px" />

								</colgroup>
								<thead>
									<tr class="active">
										<th>번호</th>
										<th>제목</th>
										<th>제출자</th>
										<th>결재구분</th>
										<th>진행상황</th>
									</tr>
								</thead>

								<tbody>

									<c:forEach items="${approvalList}" var="approvalVO">
										<input type='hidden' name='div_apv_sq' id="div_apv_sq"
											value="${approvalVO.div_apv_sq}">
										<tr>
											<td>${approvalVO.apv_sq}</td>
											<td class="apv_sq_rd" data-value="${approvalVO.apv_sq}"><a
												href='#' data-toggle="modal" data-target="#readModal">${approvalVO.apv_nm}</a></td>
											<td>${approvalVO.stf_nm}</td>
											<td>${approvalVO.div_apv_nm}</td>
											<td>${approvalVO.apv_ok}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div id="pageIndexList" class="text-center">${pageIndexList}
					</div>
				</div>
				<!-- /.box-footer-->
			</div>
		</div>
		<!--결재리스트 목록 종료-->

		<!-- 조회모달 시작 -->

		<div class="modal fade" id="readModal" role="dialog">
			<div class="modal-dialog modal-lg" style="width: 560px">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">결재조회창</h4>
					</div>
					<div class="modal-body">
						<div>
							<input type="hidden" id="apv" name="apv"
								value="${approvalVO.apv_sq}">

						</div>

						<table class="table table-striped table-bordered">
							<colgroup>
								<col width="30%" />
								<col width="70%" />
							</colgroup>

							<tbody>
						<%-- 		<tr>
									<th>파일이름</th>
									<td id="apv_pl_nm1">${approvalVO.apv_pl_nm}</td>
								</tr> --%>
								<tr>
									<th>제목</th>
									<td id="apv_nm1">${approvalVO.apv_nm}</td>
								</tr>
								<tr>
									<th>결재구분</th>
									<td id="div_apv_nm">${approvalVO.div_apv_nm}</td>
								</tr>
								<tr>
									<th>제출자</th>
									<td id="stf_nm">${approvalVO.stf_nm}</td>
								</tr>
								<tr>
									<th>중간승인자</th>
									<td id="mid_nm">${approvalVO.mid_nm}</td>
								</tr>
								<tr>
									<th>결재현황</th>
									<td id="apv_ok">${approvalVO.apv_ok}</td>
								</tr>
							</tbody>
							
						</table>
					<div id ="apv_pl_rt1">
						</div>
						
					</div>
					<div class="modal-footer">
						<select id="apv_ok_sq1" class="form-control" name="apv_ok_sq1"
							data-value="apv_ok_sq1">
							<option value="">선택</option>
							<option value="1">거절</option>
							<option id="mid_admn" value="2">승인(중간)</option>
							<option id="fnl_admn" value="4">승인(최종)</option>
						</select>

						<button type="button" id="apv_sq_md" class="btn btn-primary"
							data-dismiss="modal" style="margin-top: 15px">확인</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal"
							style="margin-top: 15px">취소</button>
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


<script>
	var result = '${msg}';

	if (result == 'SUCCESS') {
		alert("처리가 완료되었습니다.");
	}
</script>



</html>

