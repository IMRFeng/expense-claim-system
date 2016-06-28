<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Welcome ECS Dashboard</title>
	<!-- Favicon and touch icons -->
	<link rel="shortcut icon" href="assets/ico/favicon.png"/>
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/receipt-icon-64.png"/>
	
    <link href="assets/bootstrap/css/bootstrap.css" rel="stylesheet"/>
	<link rel="stylesheet" href="assets/css/menu.css" type="text/css" media="screen, projection"></link>
	<link rel="stylesheet" type="text/css" href="assets/css/chartist.css"/>
	<link rel="stylesheet" type="text/css" href="assets/css/main.css"/>
    <script src="assets/js/jquery-1.9.1.min.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>
	<!-- Header Page -->
	<tiles:insertAttribute name="header" />
	<div class="container-fluid">
		<div class="row">
			<!-- Menu Page -->
			<tiles:insertAttribute name="menu" />
			<!-- Body Page -->
			<tiles:insertAttribute name="body" />
		</div>
	</div>
	<!-- Footer Page -->
	<tiles:insertAttribute name="footer" />
	<a href="javascript:void(0)" class="tooltip-info btn back-to-top btn-light btn-fixed-bottom" data-trigger="hover" 
		data-placement="top" data-toggle="tooltip" data-original-title="Back to top"> 
		<span class="glyphicon glyphicon-chevron-up"></span> 
	</a>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("a").tooltip();
			
			var duration = 420;
			var showOffset = 220;
			var btnFixed = '.btn-fixed-bottom';
			
			$(window).scroll(function () {
			    if ($(this).scrollTop() > showOffset) {
			      $(btnFixed).fadeIn(duration);
			    } else {
			      $(btnFixed).fadeOut(duration);
			    }
			  });
			
			$(".back-to-top").click(function (event) {
			    event.preventDefault();
			    $('html, body').animate({
			      scrollTop: 0
			    }, duration);
			    return false;
			  });
		});
	</script>

</body>
</html>