<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	 <title>Sign Up</title>
     <link type="text/css" rel="stylesheet" href="/styles/main.css" />
</head>

<body>

 <h2>Signup</h2>
    <form method="post" action="/blog/singup">
      <table>
        <tr>
          <td class="label">
            Username
          </td>
          <td>
            <input type="text" name="username" value="${fn:escapeXml(param.username)}">
          </td>
          <td class="error">
            <c:out value="${errors['invalidUser']}"></c:out>
            <c:out value="${errors['userAlreadyExists']}"></c:out>
          </td>
        </tr>

        <tr>
          <td class="label">
            Password
          </td>
          <td>
            <input type="password" name="password" value="">
          </td>
          <td class="error">
             <c:out value="${errors['invalidPass']}"></c:out>
          </td>
        </tr>

        <tr>
          <td class="label">
            Verify Password
          </td>
          <td>
            <input type="password" name="verify" value="">
          </td>
          <td class="error">
             <c:out value="${errors['passNotMatch']}"></c:out>
          </td>
        </tr>

        <tr>
          <td class="label">
            Email (optional)
          </td>
          <td>
            <input type="text" name="email" value="${fn:escapeXml(param.email)}">
          </td>
          <td class="error">
             <c:out value="${errors['invalidEmail']}"></c:out>
          </td>
        </tr>
      </table>

      <input type="submit">
    </form>
  </body>
</html>