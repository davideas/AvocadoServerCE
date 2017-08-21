<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Avocado Server Error Page</title>
</head>
<body>
<h1>Error Page</h1>
<p>Application has encountered an error. Please contact support on ...</p>

<!--
Failed URL: ${url}
Exception:  ${exception.message}
<c:forEach items="${exception.stackTrace}" var="ste">    ${ste}
</c:forEach>
-->

</body>
</html>

