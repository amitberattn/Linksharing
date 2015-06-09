<%--
  Created by IntelliJ IDEA.
  User: amit
  Date: 9/6/15
  Time: 11:22 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<g:form controller="invitation" action="sendInvitation">
    Email:<input type="text" name="email">
    Subject:<input type="text" name="subject">
    <input type="submit" name="submit" value="Submit">
</g:form>
</body>
</html>