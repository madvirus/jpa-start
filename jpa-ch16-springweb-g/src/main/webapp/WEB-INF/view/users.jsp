<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>사용자 목록</title>
</head>
<body>
    <table>
        <thead>
        <tr>
            <td>이름</td>
            <td>이메일</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${users}">
            <tr>
                <td>${u.name}</td>
                <td>${u.email}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>