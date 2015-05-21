<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<title>Welcome</title>
<link type="text/css" rel="stylesheet" href="/styles/main.css" />
</head>

<body>

	<a href="/blog" class="main-title"> CS 253 Blog </a>
	<h2>
		Welcome, <c:out value="${username}"></c:out>!
	</h2>
</body>
</html>