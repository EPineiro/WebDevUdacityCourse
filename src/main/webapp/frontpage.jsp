<%@ page import="java.util.List"%>
<%@ page import="com.udacity.webdev.entities.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<title>Front page</title>
<link type="text/css" rel="stylesheet" href="/styles/main.css" />
</head>

<body>

	<a href="/blog" class="main-title"> CS 253 Blog </a>
	<div class=".login-area">
		<c:choose>
		    <c:when test="${fn:contains(header.cookie, 'user_id=')}">
		        <a href="blog/logout" class=".login-link"><c:out value="${ cookie['user_name'].value }"></c:out>(logout)</a>
		    </c:when>
		    <c:otherwise>
		        <a href="blog/login" class=".login-link">login</a> |
				<a href="blog/singup" class=".login-link">register</a>
		    </c:otherwise>
		</c:choose>
	</div>
	<br>
	<br>

	<div id="content">
		<c:if test="${ fn:length(entries) gt 1 }">
			<a href="blog/newpost" class=".newpost-link">New Post</a>
			<br>
			<br>
		</c:if>
		<c:forEach var="entry" items="${ entries }">
			<div class="post">
				<div class="post-heading">
					<a href="/blog/${ entry.id }" class="post-title">
						<c:out value="${ fn:escapeXml(entry.subject) }"></c:out>
					</a>

					<div class="post-date">
						<fmt:formatDate pattern="MMM dd, yyyy" value="${ entry.date }"/>
					</div>
				</div>

				<div class="post-content">
					<c:out value="${ fn:escapeXml(entry.content) }"></c:out>
				</div>
			</div>
			<br>
			<br>
		</c:forEach>
	</div>

</body>
</html>