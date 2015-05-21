<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	 <title>Sign In</title>
     <link type="text/css" rel="stylesheet" href="/styles/main.css" />
</head>

<body>

 <h2>Signup</h2>
    <form method="post" action="/blog/login">
      <table>
        <tr>
          <td class="label">
            Username
          </td>
          <td>
            <input type="text" name="username" value="${fn:escapeXml(param.username)}">
          </td>
          <td class="error">
            <c:out value="${errors['invalidLogin']}"></c:out>
          </td>
        </tr>

        <tr>
          <td class="label">
            Password
          </td>
          <td>
            <input type="password" name="password" value="">
          </td>
        </tr>

      </table>

      <input type="submit">
    </form>
  </body>
</html>