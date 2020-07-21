<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보</title>
<script type="text/javascript" src="http://www.chartjs.org/dist/2.9.3/Chart.min.js"></script>
<script type="text/javascript">
var randomColorFactor = function () { 
	return Math.round(Math.random()*255); // 0 ~ 255
};
var randomColor = function (opacity) { // 1 : 불투명
	return "rgba(" + randomColorFactor() + "," +// r
			randomColorFactor() + "," +// g
			randomColorFactor() + "," +// b
			(opacity || '.3') + ")";
};
$(function(){
	piegraph();
	bargraph();
	
})

function allchkbox(allchk){
		$("input[name=mailchk]").prop("checked", allchk.checked);
}

function piegraph(){
	console.log("ajax 시작");
	$.ajax("/shop5/ajax/graph1.shop",{
		success: function(data){
			pieGraphPrint(data);
		},
		error: function(e){
			alert("서버오류: "+ e.status);
		}
	})
}

function bargraph(){
	console.log("ajax 시작");
	$.ajax("/shop5/ajax/graph2.shop",{
		success: function(data){
			barGraphPrint(data);
		},
		error: function(e){
			alert("서버오류: "+ e.status);
		}
	})
}

function pieGraphPrint(data){
	console.log(data);
	var rows = JSON.parse(data);
	var names = [];
	var datas = [];
	var colors = [];
	$.each(rows, function(index, item) {
		names[index] = item.name;
		datas[index] = item.cnt;
		colors[index] = randomColor(1);
	})
	var config = {
		type : 'pie',
		data : {
			datasets : [{
				data : datas,
				backgroundColor : colors
			}],
			labels : names
		},
		options : {
			responsive : true,
			legend : {position : 'top'},
			title : {
				display : true,
				text : '회원 성 비율',
				position : 'bottom'
			}
		}
	}
	var ctx = document.getElementById("canvas1").getContext("2d");
	new Chart(ctx, config);
}

function barGraphPrint(data){
	console.log(data);
	var rows = JSON.parse(data);
	var names = [];
	var datas = [];
	var colors = [];
	$.each(rows, function(index, item) {
		names[index] = item.regdate;
		datas[index] = item.cnt;
		colors[index] = randomColor(0.7);
	})
	var config = {
		type: 'bar',
		data: {
			labels: names,
			datasets: [
				{
					type: 'line',
					borderWidth: 2,
					borderColor: colors,
					label: '건수',
					fill: false,
					data: datas,
				},{
					type: 'bar',
					label: '건수',
					backgroundColor: colors,
					data: datas,
				}
			]
		},
		options: {
			responsive: true,
			title: {
				display: true,
				text: '최근 7일간 등록한 게시물 수',
				position: 'bottom'
			},
			legend: {display: false},
			scales: {
				xAxes: [{
					display: true,
					stacked: true // 기본값(0)부터 시작
				}],
				yAxes: [{
					display: true,
					scaleLabel: {
						display: true,
						labelString: "게시물 수"
					},
					stacked: true // 기본값(0)부터 시작
				}]
			}
		}
	}
	var ctx = document.getElementById("canvas2").getContext("2d");
	new Chart(ctx, config);
}
</script>
</head>
<body>
<h2>환영합니다. ${sessionScope.loginUser.username }님</h2>
<div style="width: 40%">
<canvas id="canvas1"></canvas>
</div>
<div style="width: 40%">
<canvas id="canvas2"></canvas>
</div>
<a href="mypage.shop?id=${loginUser.userid}">mypage</a><br>
<a href="logout.shop">로그아웃</a>
</body>
</html>