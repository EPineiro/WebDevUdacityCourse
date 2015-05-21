<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<title>New Post</title>
    <link type="text/css" rel="stylesheet" href="/styles/main.css" />
</head>

<body>

 <h2>New Post</h2>
    <form method="post" action="/blog/newpost">
      <table>
        <tr>
          <td class="label">
            subject
          </td>
          <td>
            <input type="text" name="subject" value="${fn:escapeXml(param.subject)}">
          </td>
        </tr>

        <tr>
          <td class="label">
            blog
          </td>
          <td>
           <textarea name="content">${fn:escapeXml(param.content)}</textarea>
          </td>
        </tr>

        <tr>
           <td class="error">
             <c:out value="${errors['invalidContent']}"></c:out>
          </td>
        </tr>

      </table>

      <input type="submit">
    </form>
  </body>
</html>