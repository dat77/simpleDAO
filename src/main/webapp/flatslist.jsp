<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Real estate</title>
</head>
<body>
<h2>Flats list</h2>
<c:if test="${flatslist ne null}">
    <table border="1">
        <c:forEach items="${flatslist}" var="flat">
            <tr>
                <c:forEach items="${flat}" var="item">
                    <td><c:out value="${item}"/></td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
