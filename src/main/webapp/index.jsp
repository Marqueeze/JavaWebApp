<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>

<html>
    <body>
        <h2>Hello World!</h2>
        <h3>users</h3>
        <c:forEach items="${users}" var="user">
            <c:out value="${user.name}"><br>
            </c:out>
        </c:forEach>
    </body>
</html>