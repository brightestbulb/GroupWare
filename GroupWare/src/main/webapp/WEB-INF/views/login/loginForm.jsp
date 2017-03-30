<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>그룹웨어 로그인</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
body, #wrap {
	height: 100%
}

#loginImg {
	background-image: url(/resources/img/main.jpg);
	position: absolute;
 	width: 1930px; 
	height: 700px;
	top: 50%;
	margin-top: -250px;
 	background-size: 1925px 500px;
 	background-repeat : no-repeat;
/* 	opacity: 0.4; */
}

#loginForm {
	position: absolute;
	width : 300px;
	right: 50px;
	top: 50%;
	margin-top: -106px;
	
}
button {
	width: 100%;
}

</style>

<script type="text/javascript">
	$(document).ready(function() {
		var errMsg = "${errMsg}";

		if (errMsg != "")
			alert(errMsg);

		var formObj = $("form[role='form']");

		console.log(formObj);

		$("#loginBtn").click(function() {
			if ($("#stf_sq").val() == "") {
				alert("계정을 입력해주세요.");
				return;
			} else if ($("#stf_pw").val() == "") {
				alert("비밀번호를 입력해주세요.");
				return;
			}

			formObj.submit();
		});

	});
</script>
</head>
<body>

	<div id="wrap">

		<div id="loginImg">
		
		</div>
		<div>
			<form action="/login/login" role="form" id="loginForm" class="form-horizontal" method="post">
				<div class="form-group">
					<!-- <label for="exampleInputEmail1">사원번호</label> -->
					<div class="input-group">
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-user" aria-hidden="true"></span></span> <input
							type="text" class="form-control" id="stf_sq" name="stf_sq" placeholder="Account">
					</div>
				</div>
				<div class="form-group">
					<!-- <label for="exampleInputEmail1">비밀번호</label> -->
					<div class="input-group">
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></span> <input
							type="password" class="form-control" id="stf_pw" name="stf_pw" placeholder="Password">
					</div>
				</div>
				<div class="checkbox">
					<!-- <label> <input type="checkbox"> 아이디 저장
					</label> -->
				</div>
				<button id="loginBtn" type="button" class="btn btn-primary">Login</button>
			</form>
		</div>
		
	</div>

</body>
</html>
