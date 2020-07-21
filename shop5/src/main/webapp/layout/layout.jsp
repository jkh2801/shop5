<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><decorator:title></decorator:title></title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.ckeditor.com/4.5.7/standard/ckeditor.js"></script>
<script>
	$(function() {
		exchangeRate(); //환율정보를 ajax를 통해 크롤링하기
		exchangeRate2();
	})
	
	function exchangeRate() {
		$.ajax("${path}/ajax/exchange1.shop",{
			success : function(data){
				$("#exchange").html(data);
			},
			error : function(e){
				alert("환율 조회시 서버 오류: " + e.status);
			}
		})
	}
	
	function exchangeRate2() {
		$.ajax("${path}/ajax/exchange2.shop",{
			success : function(data){
				$("#exchange2").html(data);
			},
			error : function(e){
				alert("환율 조회시 서버 오류: " + e.status);
			}
		})
	}
	
	
		// Get the Sidebar
		var mySidebar = document.getElementById("mySidebar");
		// Get the DIV with overlay effect
		var overlayBg = document.getElementById("myOverlay");
		// Toggle between showing and hiding the sidebar, and add overlay effect
		function w3_open() {
			if (mySidebar.style.display === 'block') {
				mySidebar.style.display = 'none';
				overlayBg.style.display = "none";
			} else {
				mySidebar.style.display = 'block';
				overlayBg.style.display = "block";
			}
		}
		// Close the sidebar with the close button
		function w3_close() {
			mySidebar.style.display = "none";
			overlayBg.style.display = "none";
		}
	</script>
<decorator:head></decorator:head>
<link rel="stylesheet" href="${path}/css/main.css">
</head>
<body>
<table>
<tr>
<td colspan="3" align="right">
<c:if test="${empty sessionScope.loginUser}"><a href="${path}/user/login.shop">로그인</a>
<a href="${path}/user/userEntry.shop">회원가입</a></c:if>
<c:if test="${!empty sessionScope.loginUser}">
${sessionScope.loginUser.username }님
<a href="${path}/user/logout.shop">로그아웃</a>
</c:if>
</td>
</tr>
<tr>
<td width="15%" valign="top">
<a href="${path}/user/main.shop">회원관리</a><br>
<a href="${path}/item/list.shop">상품관리</a><br>
<a href="${path}/board/list.shop">게시판</a><br>
<a href="${path}/chat/chat.shop">채팅</a><br>
	<%-- ajax을 통해 얻은 환율 정보 내용 출력 --%>
	<div id="exchange"></div>
	
	<%-- ajax을 통해 KEB 하나은행 정보 출력하기: USD, JPY, ERU, CNY : 매매기준율, 현찰사실 때, 현찰파실 때 --%>
	<div id="exchange2"></div>
</td>
<td colspan="2" align="left" valign="top">
<decorator:body></decorator:body>
</td>
</tr>
<tr>
<td colspan="3">구디아카데미 since 2016</td>
</tr>
</table>
</body>
</html>