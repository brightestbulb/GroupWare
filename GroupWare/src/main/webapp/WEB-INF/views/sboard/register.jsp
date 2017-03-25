<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>게시글 등록</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript"
	src="//code.jquery.com/jquery-1.11.0.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="/resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<!--스마트에디터 -->

<style type="text/css">
#frm {
	width: 50%;
	margin: auto; /* 화면 중앙에 배치*/
}

.box-body {
	margin: auto; /* 화면 중앙에 배치*/
	width: 730px;
}
</style>


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
		<form role="form" method="post" id="frm" action="/sboard/register">
			<div class="box-body">
				<input type="text" name='ntc_nm' class="form-control"
					placeholder="제목을 입력해 주세요.">
				<textarea name="ntc_cnt" id="ntc_cnt" rows="10" cols="100"></textarea>
				<select class="form-control" name="ntc_div_sq">
					<option value="1">자유게시판</option>
					<option value="2">사내게시판</option>
					<option value="3">중고장터</option>
				</select>
			</div>

			<div class="text-center">
				<button type="button" id="okbutton" class="btn btn-primary">확인</button>
				<button id="cancel" type="button" class="btn btn-danger">취소</button>
			</div>
			<!-- content 종료 -->


		</form>
	</div>



	<!-- footer 시작 -->
	<div id="footer">
		<c:import url="../import/footer.jsp" />
	</div>
	<!-- footer 종료 -->

</body>
<script>
	var result = '${msg}';

	if (result == 'SUCCESS') {
		alert("처리가 완료되었습니다.");
	}
	
</script>

<script type="text/javascript">
var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
    oAppRef: oEditors,
    elPlaceHolder: "ntc_cnt",
    sSkinURI: "/resources/se2/SmartEditor2Skin.html",
    fCreator: "createSEditor2"
});
//전송버튼 클릭이벤트
$("#okbutton").click(function(){

    //id가 smarteditor인 textarea에 에디터에서 대입
   oEditors.getById["ntc_cnt"].exec("UPDATE_CONTENTS_FIELD", []);
   
   //폼 submit
   $("#frm").submit();
})
 

</script>

<script type="text/javascript">
    	$(document).ready(function() {
			$("#cancel").click(function() {
				location.href="/sboard/list?ntc_div_sq=1";
			});
		});
    </script>

</html>

